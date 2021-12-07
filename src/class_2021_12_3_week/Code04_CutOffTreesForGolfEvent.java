package class_2021_12_3_week;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 测试链接 : https://leetcode.com/problems/cut-off-trees-for-golf-event/
public class Code04_CutOffTreesForGolfEvent {

	public static int cutOffTree(List<List<Integer>> forest) {
		int n = forest.size();
		int m = forest.get(0).size();
		ArrayList<int[]> cells = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int val = forest.get(i).get(j);
				if (val > 1) {
					cells.add(new int[] { i, j, val });
				}
			}
		}
		cells.sort((a, b) -> a[2] - b[2]);
		int ans = 0, lastR = 0, lastC = 0;
		for (int[] cell : cells) {
			int step = distance(forest, lastR, lastC, cell[0], cell[1]);
			if (step == -1) {
				return -1;
			}
			ans += step;
			lastR = cell[0];
			lastC = cell[1];
			forest.get(lastR).set(lastC, 1);
		}
		return ans;
	}

	public static int distance(List<List<Integer>> forest, int sr, int sc, int tr, int tc) {
		if (sr == tr && sc == tc) {
			return 0;
		}
		int n = forest.size();
		int m = forest.get(0).size();
		boolean[][] seen = new boolean[n][m];
		LinkedList<int[]> deque = new LinkedList<>();
		seen[sr][sc] = true;
		deque.offerFirst(new int[] { 0, sr, sc });
		int[] dr = { -1, 1, 0, 0 };
		int[] dc = { 0, 0, -1, 1 };
		while (!deque.isEmpty()) {
			int[] cur = deque.pollFirst();
			int step = cur[0], r = cur[1], c = cur[2];
			seen[r][c] = true;
			for (int di = 0; di < 4; ++di) {
				int nr = r + dr[di];
				int nc = c + dc[di];
				if (nr >= 0 && nr < n && nc >= 0 && nc < m && !seen[nr][nc]) {
					if (nr == tr && nc == tc) {
						return step + 1;
					}
					if (forest.get(nr).get(nc) > 0) {
						boolean closer = 
								(di == 0 && r > tr) 
								|| (di == 1 && r < tr) 
								|| (di == 2 && c > tc)
								|| (di == 3 && c < tc);
						if (closer) {
							deque.offerFirst(new int[] { step + 1, nr, nc });
						} else {
							deque.offerLast(new int[] { step + 1, nr, nc });
						}
					}
				}
			}
		}
		return -1;
	}

}

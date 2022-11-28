package class_2022_12_2_week;

import java.util.ArrayList;

// 来自亚马逊、谷歌、微软、Facebook、Bloomberg
// 测试链接 : https://leetcode.cn/problems/making-a-large-island/
public class Code01_MakingALargeIsland {

	public static int largestIsland(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		ArrayList<Integer> sizes = new ArrayList<>();
		sizes.add(0);
		sizes.add(0);
		int id = 2;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 1) {
					int curSize = infect(grid, i, j, id, n, m);
					ans = Math.max(ans, curSize);
					sizes.add(id++, curSize);
				}
			}
		}
		boolean[] visited = new boolean[id];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 0) {
					int up = i - 1 >= 0 ? grid[i - 1][j] : 0;
					int down = i + 1 < n ? grid[i + 1][j] : 0;
					int left = j - 1 >= 0 ? grid[i][j - 1] : 0;
					int right = j + 1 < m ? grid[i][j + 1] : 0;
					int merge = 1 + sizes.get(up);
					visited[up] = true;
					if (!visited[down]) {
						merge += sizes.get(down);
						visited[down] = true;
					}
					if (!visited[left]) {
						merge += sizes.get(left);
						visited[left] = true;
					}
					if (!visited[right]) {
						merge += sizes.get(right);
						visited[right] = true;
					}
					ans = Math.max(ans, merge);
					visited[up] = false;
					visited[down] = false;
					visited[left] = false;
					visited[right] = false;
				}
			}
		}
		return ans;
	}

	public static int infect(int[][] grid, int i, int j, int v, int n, int m) {
		if (i < 0 || i == n || j < 0 || j == m || grid[i][j] != 1) {
			return 0;
		}
		int ans = 1;
		grid[i][j] = v;
		ans += infect(grid, i - 1, j, v, n, m);
		ans += infect(grid, i + 1, j, v, n, m);
		ans += infect(grid, i, j - 1, v, n, m);
		ans += infect(grid, i, j + 1, v, n, m);
		return ans;
	}

}

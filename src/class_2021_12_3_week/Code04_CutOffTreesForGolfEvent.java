package class_2021_12_3_week;

import java.util.Arrays;
import java.util.List;

// leetcode增加了新的数据
// 导致原来的实现不通过了
// 一怒之下，做了各种优化
// 思路完全和课上讲的一样，就是实现完全重来了
// 目前打败100%的人
// 测试链接 : https://leetcode.com/problems/cut-off-trees-for-golf-event/
public class Code04_CutOffTreesForGolfEvent {

	public static int MAXN = 51;
	public static int MAXM = 51;
	public static int LIMIT = MAXN * MAXM;
	public static int[][] f = new int[MAXN][MAXM];
	public static int[][] arr = new int[LIMIT][3];
	public static int[] move = new int[] { 1, 0, -1, 0, 1 };
	public static int n, m;

	// 为了快，手撸双端队列
	public static int[][] deque = new int[LIMIT][3];
	// 双端队列的头、尾、大小
	public static int l, r, size;

	// 初始化双端队列
	public static void buildDeque() {
		l = -1;
		r = -1;
		size = 0;
	}

	// 双端队列从头部弹出
	public static int[] pollFirst() {
		int[] ans = deque[l];
		if (l < LIMIT - 1) {
			l++;
		} else {
			l = 0;
		}
		size--;
		if (size == 0) {
			l = r = -1;
		}
		return ans;
	}

	// 双端队列从头部加入
	public static void offerFirst(int x, int y, int d) {
		if (l == -1) {
			deque[0][0] = x;
			deque[0][1] = y;
			deque[0][2] = d;
			l = r = 0;
		} else {
			int fill = l == 0 ? (LIMIT - 1) : (l - 1);
			deque[fill][0] = x;
			deque[fill][1] = y;
			deque[fill][2] = d;
			l = fill;
		}
		size++;
	}

	// 双端队列从尾部加入
	public static void offerLast(int x, int y, int d) {
		if (l == -1) {
			deque[0][0] = x;
			deque[0][1] = y;
			deque[0][2] = d;
			l = r = 0;
		} else {
			int fill = (r == LIMIT - 1) ? 0 : (r + 1);
			deque[fill][0] = x;
			deque[fill][1] = y;
			deque[fill][2] = d;
			r = fill;
		}
		size++;
	}

	public static int cutOffTree(List<List<Integer>> forest) {
		n = forest.size();
		m = forest.get(0).size();
		int cnt = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int value = forest.get(i).get(j);
				f[i][j] = value > 0 ? 1 : 0;
				if (value > 1) {
					arr[cnt][0] = value;
					arr[cnt][1] = i;
					arr[cnt++][2] = j;
				}
			}
		}
		Arrays.sort(arr, 0, cnt, (a, b) -> a[0] - b[0]);
		int ans = 0;
		for (int i = 0, x = 0, y = 0, block = 2; i < cnt; i++, block++) {
			int toX = arr[i][1];
			int toY = arr[i][2];
			int step = walk(x, y, toX, toY, block);
			if (step == -1) {
				return -1;
			}
			ans += step;
			x = toX;
			y = toY;
		}
		return ans;
	}

	public static int walk(int a, int b, int c, int d, int block) {
		buildDeque();
		offerFirst(a, b, 0);
		while (size > 0) {
			int[] cur = pollFirst();
			int x = cur[0];
			int y = cur[1];
			int distance = cur[2];
			if (f[x][y] != block) {
				f[x][y] = block;
				if (x == c && y == d) {
					return distance;
				}
				for (int i = 1; i < 5; i++) {
					int nextX = x + move[i];
					int nextY = y + move[i - 1];
					if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < m && f[nextX][nextY] != 0
							&& f[nextX][nextY] != block) {
						if ((i == 1 && y < d) || (i == 2 && x > c) || (i == 3 && y > d) || (i == 4 && x < c)) {
							// 离的更近
							offerFirst(nextX, nextY, distance + 1);
						} else {
							// 离的更远
							offerLast(nextX, nextY, distance + 1);
						}
					}
				}
			}
		}
		return -1;
	}

}

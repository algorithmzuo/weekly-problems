package class_2023_01_3_week;

// 测试链接 : https://leetcode.cn/problems/stamping-the-grid/
public class Code02_StampingTheGrid {

	public static boolean possibleToStamp(int[][] grid, int h, int w) {
		int n = grid.length;
		int m = grid[0].length;
		int[][] sum = new int[n + 1][m + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				sum[i + 1][j + 1] = grid[i][j];
			}
		}
		build(sum);
		int[][] diff = new int[n + 2][m + 2];
		for (int a = 1, c = a + h - 1; c <= n; a++, c++) {
			for (int b = 1, d = b + w - 1; d <= m; b++, d++) {
				if (empty(sum, a, b, c, d)) {
					set(diff, a, b, c, d);
				}
			}
		}
		build(diff);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 0 && diff[i + 1][j + 1] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static void build(int[][] m) {
		for (int i = 1; i < m.length; i++) {
			for (int j = 1; j < m[0].length; j++) {
				m[i][j] += m[i - 1][j] + m[i][j - 1] - m[i - 1][j - 1];
			}
		}
	}

	public static boolean empty(int[][] sum, int a, int b, int c, int d) {
		return sum[c][d] - sum[c][b - 1] - sum[a - 1][d] + sum[a - 1][b - 1] == 0;
	}

	public static void set(int[][] diff, int a, int b, int c, int d) {
		diff[a][b] += 1;
		diff[c + 1][d + 1] += 1;
		diff[c + 1][b] -= 1;
		diff[a][d + 1] -= 1;
	}

}

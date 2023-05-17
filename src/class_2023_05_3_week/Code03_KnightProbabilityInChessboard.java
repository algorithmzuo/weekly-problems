package class_2023_05_3_week;

// 测试链接 : https://leetcode.cn/problems/knight-probability-in-chessboard/
public class Code03_KnightProbabilityInChessboard {

	public double knightProbability(int n, int k, int row, int column) {
		double[][][] dp = new double[n][n][k + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int t = 0; t <= k; t++) {
					dp[i][j][t] = -1;
				}
			}
		}
		return process2(n, k, row, column, dp);
	}

	public double process2(int n, int rest, int r, int c, double[][][] dp) {
		if (r < 0 || r >= n || c < 0 || c >= n) {
			return 0;
		}
		if (dp[r][c][rest] != -1) {
			return dp[r][c][rest];
		}
		double ans = 0;
		if (rest == 0) {
			ans = 1;
		} else {
			ans += (process2(n, rest - 1, r - 2, c + 1, dp) / 8);
			ans += (process2(n, rest - 1, r - 1, c + 2, dp) / 8);
			ans += (process2(n, rest - 1, r + 1, c + 2, dp) / 8);
			ans += (process2(n, rest - 1, r + 2, c + 1, dp) / 8);
			ans += (process2(n, rest - 1, r + 2, c - 1, dp) / 8);
			ans += (process2(n, rest - 1, r + 1, c - 2, dp) / 8);
			ans += (process2(n, rest - 1, r - 1, c - 2, dp) / 8);
			ans += (process2(n, rest - 1, r - 2, c - 1, dp) / 8);
		}
		dp[r][c][rest] = ans;
		return ans;
	}

}

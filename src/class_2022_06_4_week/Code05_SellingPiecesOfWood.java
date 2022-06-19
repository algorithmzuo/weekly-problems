package class_2022_06_4_week;

// 测试链接 : https://leetcode.cn/problems/selling-pieces-of-wood/
public class Code05_SellingPiecesOfWood {

	public static long sellingWood(int m, int n, int[][] prices) {
		long[][] values = new long[m + 1][n + 1];
		for (int[] p : prices) {
			values[p[0]][p[1]] = Math.max(values[p[0]][p[1]], p[2]);
		}
		long[][] dp = new long[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				dp[i][j] = -1;
			}
		}
		return f(m, n, values, dp);
	}

	public static long f(int m, int n, long[][] values, long[][] dp) {
		if (m == 0 || n == 0) {
			return 0;
		}
		if (dp[m][n] != -1) {
			return dp[m][n];
		}
		long ans = values[m][n];
		for (int split = 1; split < m; split++) {
			ans = Math.max(ans, f(split, n, values, dp) + f(m - split, n, values, dp));
		}
		for (int split = 1; split < n; split++) {
			ans = Math.max(ans, f(m, split, values, dp) + f(m, n - split, values, dp));
		}
		dp[m][n] = ans;
		return ans;
	}

}

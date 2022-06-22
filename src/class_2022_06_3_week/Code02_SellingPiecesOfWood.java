package class_2022_06_3_week;

// 测试链接 : https://leetcode.cn/problems/selling-pieces-of-wood/
public class Code02_SellingPiecesOfWood {

	// 递归尝试版本
	public static long sellingWood1(int m, int n, int[][] prices) {
		long[][] values = new long[m + 1][n + 1];
		for (int[] p : prices) {
			values[p[0]][p[1]] = Math.max(values[p[0]][p[1]], p[2]);
		}
		return f1(m, n, values);
	}

	public static long f1(int m, int n, long[][] values) {
		if (m == 0 || n == 0) {
			return 0;
		}
		long ans = values[m][n];
		for (int split = 1; split < m; split++) {
			ans = Math.max(ans, f1(split, n, values) + f1(m - split, n, values));
		}
		for (int split = 1; split < n; split++) {
			ans = Math.max(ans, f1(m, split, values) + f1(m, n - split, values));
		}
		return ans;
	}

	// 递归版本 + 记忆化搜索
	public static long sellingWood2(int m, int n, int[][] prices) {
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
		return f2(m, n, values, dp);
	}

	public static long f2(int m, int n, long[][] values, long[][] dp) {
		if (m == 0 || n == 0) {
			return 0;
		}
		if (dp[m][n] != -1) {
			return dp[m][n];
		}
		long ans = values[m][n];
		for (int split = 1; split < m; split++) {
			ans = Math.max(ans, f2(split, n, values, dp) + f2(m - split, n, values, dp));
		}
		for (int split = 1; split < n; split++) {
			ans = Math.max(ans, f2(m, split, values, dp) + f2(m, n - split, values, dp));
		}
		dp[m][n] = ans;
		return ans;
	}

	// 严格位置依赖的动态规划版本 + 优化
	// 优化1 : prices中的单块收益直接填入dp表即可，如果有更好的分割方案，更新掉
	// 优化2 : 分割只需要枚举一半即可
	public static long sellingWood3(int m, int n, int[][] prices) {
		long[][] dp = new long[m + 1][n + 1];
		for (int[] p : prices) {
			dp[p[0]][p[1]] = p[2];
		}
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				for (int k = 1; k <= j >> 1; k++) {
					dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[i][j - k]);
				}
				for (int k = 1; k <= i >> 1; k++) {
					dp[i][j] = Math.max(dp[i][j], dp[k][j] + dp[i - k][j]);
				}
			}
		}
		return dp[m][n];
	}

}

package class_2023_07_4_week;

// 测试链接 : https://leetcode.cn/problems/profitable-schemes/
public class Code04_ProfitableSchemes {

	public static int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
		int mod = 1000000007;
		int[][] dp = new int[minProfit + 1][n + 1];
		for (int r = 0; r <= n; r++) {
			dp[0][r] = 1;
		}
		for (int i = group.length - 1; i >= 0; i--) {
			for (int p = minProfit; p >= 0; p--) {
				for (int r = n; r >= 0; r--) {
					int p1 = dp[p][r];
					int p2 = r - group[i] >= 0 ? dp[Math.max(0, p - profit[i])][r - group[i]] : 0;
					dp[p][r] = (p1 + p2) % mod;
				}
			}
		}
		return dp[minProfit][n];
	}

}

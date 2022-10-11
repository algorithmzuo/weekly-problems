package class_2022_10_4_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/minimum-cost-to-cut-a-stick/
public class Code03_MinimumCostToCutAStick {

	public static int minCost(int n, int[] cuts) {
		int m = cuts.length;
		Arrays.sort(cuts);
		int[] arr = new int[m + 2];
		arr[0] = 0;
		for (int i = 1; i <= m; ++i) {
			arr[i] = cuts[i - 1];
		}
		arr[m + 1] = n;
		int[][] dp = new int[m + 2][m + 2];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= m; j++) {
				dp[i][j] = -1;
			}
		}
		return process(arr, 1, m, dp);
	}

	public static int process(int[] arr, int l, int r, int[][] dp) {
		if (l > r) {
			return 0;
		}
		if (l == r) {
			return arr[r + 1] - arr[l - 1];
		}
		if (dp[l][r] != -1) {
			return dp[l][r];
		}
		int ans = Integer.MAX_VALUE;
		for (int k = l; k <= r; k++) {
			ans = Math.min(ans, process(arr, l, k - 1, dp) + process(arr, k + 1, r, dp));
		}
		ans += arr[r + 1] - arr[l - 1];
		dp[l][r] = ans;
		return ans;
	}
}

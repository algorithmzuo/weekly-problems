package class_2023_06_3_week;

// 测试链接 : https://leetcode.cn/problems/special-permutations/
public class Code06_SpecialPermutations {

	public static int specialPerm(int[] nums) {
		int n = nums.length;
		int[][] dp = new int[1 << n][n];
		for (int i = 0; i < (1 << n); i++) {
			for (int j = 0; j < n; j++) {
				dp[i][j] = -1;
			}
		}
		return process(nums, n, 0, 0, dp);
	}

	public static int mod = 1000000007;

	public static int process(int[] a, int n, int s, int p, int[][] dp) {
		if (dp[s][p] != -1) {
			return dp[s][p];
		}
		int ans = 0;
		if (s == (1 << n) - 1) {
			ans = 1;
		} else {
			for (int i = 0; i < n; i++) {
				if (s == 0 || ((s & (1 << i)) == 0 && (a[p] % a[i] == 0 || a[i] % a[p] == 0))) {
					ans = (ans + process(a, n, s | (1 << i), i, dp)) % mod;
				}
			}
		}
		dp[s][p] = ans;
		return ans;
	}

}

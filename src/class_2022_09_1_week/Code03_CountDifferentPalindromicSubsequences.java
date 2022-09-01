package class_2022_09_1_week;

import java.util.HashMap;

// 测试链接 : https://leetcode.cn/problems/count-different-palindromic-subsequences/
public class Code03_CountDifferentPalindromicSubsequences {

	// 支持任意字符集
	// 时间复杂度O(N^2)
	public static int countPalindromicSubsequences(String str) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		int n = s.length;
		int[] right = new int[n];
		int[] left = new int[n];
		HashMap<Character, Integer> last = new HashMap<>();
		for (int i = 0; i < n; i++) {
			left[i] = last.getOrDefault(s[i], -1);
			last.put(s[i], i);
		}
		last.clear();
		for (int i = n - 1; i >= 0; i--) {
			right[i] = last.getOrDefault(s[i], n);
			last.put(s[i], i);
		}
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int i = n - 2; i >= 0; i--) {
			for (int j = i + 1; j < n; j++) {
				if (s[i] == s[j]) {
					int l = Math.min(j, right[i]);
					int r = Math.max(i, left[j]);
					if (l > r) {
						dp[i][j] = dp[i + 1][j - 1] * 2 + 2;
					} else if (l == r) {
						dp[i][j] = dp[i + 1][j - 1] * 2 + 1;
					} else {
						dp[i][j] = dp[i + 1][j - 1] * 2 - dp[l + 1][r - 1] + mod;
					}
				} else {
					dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1] + mod;
				}
				dp[i][j] %= mod;
			}
		}
		return (int) dp[0][n - 1];
	}

}

package class_2022_10_4_week;

import java.util.HashMap;

// 给定一个字符串 s，返回 s 中不同的非空「回文子序列」个数 。
// 通过从 s 中删除 0 个或多个字符来获得子序列。
// 如果一个字符序列与它反转后的字符序列一致，那么它是「回文字符序列」。
// 如果有某个 i , 满足 ai != bi ，则两个序列 a1, a2, ... 和 b1, b2, ... 不同。
// 注意：结果可能很大，你需要对 109 + 7 取模 。
// 测试链接 : https://leetcode.cn/problems/count-different-palindromic-subsequences/
public class Code05_CountDifferentPalindromicSubsequences {

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

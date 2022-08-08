package class_2022_08_4_week;

import java.util.HashMap;

// 测试链接 : https://leetcode.cn/problems/count-different-palindromic-subsequences/
public class Code03_CountDifferentPalindromicSubsequences {

	// 限定字符集(a、b、c、d)
	// 时间复杂度O(N^2)
	public static int countPalindromicSubsequences1(String str) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		int n = s.length;
		int[] right = new int[n];
		int[] left = new int[n];
		int[] last = new int[4];
		last[0] = last[1] = last[2] = last[3] = -1;
		for (int i = 0; i < n; i++) {
			left[i] = last[s[i] - 'a'];
			last[s[i] - 'a'] = i;
		}
		last[0] = last[1] = last[2] = last[3] = n;
		for (int i = n - 1; i >= 0; i--) {
			right[i] = last[s[i] - 'a'];
			last[s[i] - 'a'] = i;
		}
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int d = 1; d < n; d++) {
			for (int i = 0; i < n - d; i++) {
				if (s[i] == s[i + d]) {
					int l = Math.min(i + d, right[i]);
					int r = Math.max(i, left[i + d]);
					if (l > r) {
						dp[i][i + d] = dp[i][i + d] = dp[i + 1][i + d - 1] * 2 + 2;
					} else if (l == r) {
						dp[i][i + d] = dp[i + 1][i + d - 1] * 2 + 1;
					} else {
						dp[i][i + d] = dp[i + 1][i + d - 1] * 2 - dp[l + 1][r - 1] + mod;
					}
				} else {
					dp[i][i + d] = dp[i][i + d - 1] + dp[i + 1][i + d] - dp[i + 1][i + d - 1] + mod;
				}
				dp[i][i + d] %= mod;
			}
		}
		return (int) dp[0][n - 1];
	}

	// 不需要限定字符集
	// 时间复杂度O(N^2)
	public static int countPalindromicSubsequences2(String str) {
		int mod = 1000000007;
		char[] s = str.toCharArray();
		int n = s.length;
		HashMap<Integer, Integer> right = new HashMap<>();
		HashMap<Integer, Integer> left = new HashMap<>();
		HashMap<Character, Integer> last = new HashMap<>();
		for (int i = 0; i < n; i++) {
			left.put(i, last.getOrDefault(s[i], -1));
			last.put(s[i], i);
		}
		last.clear();
		for (int i = n - 1; i >= 0; i--) {
			right.put(i, last.getOrDefault(s[i], n));
			last.put(s[i], i);
		}
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int d = 1; d < n; d++) {
			for (int i = 0; i < n - d; i++) {
				if (s[i] == s[i + d]) {
					int l = Math.min(i + d, right.get(i));
					int r = Math.max(i, left.get(i + d));
					if (l > r) {
						dp[i][i + d] = dp[i][i + d] = dp[i + 1][i + d - 1] * 2 + 2;
					} else if (l == r) {
						dp[i][i + d] = dp[i + 1][i + d - 1] * 2 + 1;
					} else {
						dp[i][i + d] = dp[i + 1][i + d - 1] * 2 - dp[l + 1][r - 1] + mod;
					}
				} else {
					dp[i][i + d] = dp[i][i + d - 1] + dp[i + 1][i + d] - dp[i + 1][i + d - 1] + mod;
				}
				dp[i][i + d] %= mod;
			}
		}
		return (int) dp[0][n - 1];
	}

}

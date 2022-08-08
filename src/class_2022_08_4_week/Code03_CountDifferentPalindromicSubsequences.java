package class_2022_08_4_week;

import java.util.HashMap;
import java.util.TreeSet;

// 测试链接 : https://leetcode.cn/problems/count-different-palindromic-subsequences/
public class Code03_CountDifferentPalindromicSubsequences {

	public static int mod = 1000000007;

	// 需要限定字符集
	// 时间复杂度O(N^2)
	public static int countPalindromicSubsequences1(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] right = new int[4][n];
		int[][] left = new int[4][n];
		int[] last = new int[4];
		last[0] = last[1] = last[2] = last[3] = -1;
		for (int i = 0; i < n; i++) {
			last[s[i] - 'a'] = i;
			for (int c = 0; c < 4; c++) {
				left[c][i] = last[c];
			}

		}
		last[0] = last[1] = last[2] = last[3] = n;
		for (int i = n - 1; i >= 0; i--) {
			last[s[i] - 'a'] = i;
			for (int c = 0; c < 4; c++) {
				right[c][i] = last[c];
			}
		}
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int d = 1; d < n; d++) {
			for (int i = 0; i < n - d; i++) {
				if (s[i] == s[i + d]) {
					int l = i + d;
					if (right[s[i] - 'a'][i + 1] != n) {
						l = right[s[i] - 'a'][i + 1];
					}
					int r = i;
					if (left[s[i] - 'a'][i + d - 1] != -1) {
						r = left[s[i] - 'a'][i + d - 1];
					}
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
	// 时间复杂度O(N^2 * logN)
	public static int countPalindromicSubsequences2(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		HashMap<Character, TreeSet<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			if (!map.containsKey(s[i])) {
				map.put(s[i], new TreeSet<>());
			}
			map.get(s[i]).add(i);
		}
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int d = 1; d < n; d++) {
			for (int i = 0; i < n - d; i++) {
				if (s[i] == s[i + d]) {
					int l = i + d;
					Integer ceiling = map.get(s[i]).ceiling(i + 1);
					if (ceiling != null) {
						l = ceiling;
					}
					int r = i;
					Integer floor = map.get(s[i]).floor(i + d - 1);
					if (floor != null) {
						r = floor;
					}
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

package class_2022_06_1_week;

// 测试链接 : https://leetcode.cn/problems/substring-with-largest-variance/
public class Code02_SubstringWithLargestVariance {

	public static int largestVariance(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int n = s.length();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = s.charAt(i) - 'a';
		}
		// continuous[i][j] : 假设字符串只有i字符和j字符，连续出现的i字符的数量
		int[][] continuous = new int[26][26];
		// dp[i][j] : 假设字符串只有i字符和j字符，(i字符数量 - j字符数量)的最大值
		int[][] dp = new int[26][26];
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 26; j++) {
				dp[i][j] = (Integer.MIN_VALUE / 2);
			}
		}
		int ans = 0;
		for (int j : arr) {
			for (int i = 0; i < 26; i++) {
				if (i != j) {
					dp[i][j] = Math.max(dp[i][j], continuous[i][j]) - 1;
					continuous[i][j] = 0;
					++continuous[j][i];
					++dp[j][i];
					ans = Math.max(ans, Math.max(dp[i][j], dp[j][i]));
				}
			}
		}
		return ans;
	}

}

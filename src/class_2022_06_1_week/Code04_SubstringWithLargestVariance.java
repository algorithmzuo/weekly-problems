package class_2022_06_1_week;

// 测试链接 : https://leetcode.cn/problems/substring-with-largest-variance/
public class Code04_SubstringWithLargestVariance {

	public static int largestVariance1(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int n = s.length();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = s.charAt(i) - 'a';
		}
		int ans = 0;
		for (int a = 0; a < 26; a++) {
			for (int b = 0; b < 26; b++) {
				if (a != b) {
					int continuousA = 0;
					boolean appearB = false;
					int max = 0;
					for (int i = 0; i < n; i++) {
						if (arr[i] != a && arr[i] != b) {
							continue;
						}
						if (arr[i] == a) {
							continuousA++;
							if (appearB) {
								max++;
							}
						} else {
							max = Math.max(max, continuousA) - 1;
							continuousA = 0;
							appearB = true;
						}
						ans = Math.max(ans, max);
					}
				}
			}
		}
		return ans;
	}

	public static int largestVariance2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int n = s.length();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = s.charAt(i) - 'a';
		}
		int[][] dp = new int[26][26];
		int[][] continuous = new int[26][26];
		boolean[][] appear = new boolean[26][26];
		int ans = 0;
		for (int i : arr) {
			for (int j = 0; j < 26; j++) {
				if (j != i) {
					++continuous[i][j];
					if (appear[i][j]) {
						++dp[i][j];
					}
					if (!appear[j][i]) {
						appear[j][i] = true;
						dp[j][i] = continuous[j][i] - 1;
					} else {
						dp[j][i] = Math.max(dp[j][i], continuous[j][i]) - 1;
					}
					continuous[j][i] = 0;
					ans = Math.max(ans, Math.max(dp[j][i], dp[i][j]));
				}
			}
		}
		return ans;
	}

}

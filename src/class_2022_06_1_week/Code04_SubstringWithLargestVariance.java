package class_2022_06_1_week;

// 字符串的 波动 定义为子字符串中出现次数 最多 的字符次数与出现次数 最少 的字符次数之差。
// 给你一个字符串 s ，它只包含小写英文字母。请你返回 s 里所有 子字符串的 最大波动 值。
// 子字符串 是一个字符串的一段连续字符序列。
// 注意：必须同时有，最多字符和最少字符的字符串才是有效的
// 测试链接 : https://leetcode.cn/problems/substring-with-largest-variance/
public class Code04_SubstringWithLargestVariance {

	public static int largestVariance1(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int n = s.length();
		// a b a c b b a
		// 0 1 0 2 1 1 0
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = s.charAt(i) - 'a';
		}
		int ans = 0;
		// 26 * 26 * n O(N)
		for (int more = 0; more < 26; more++) {
			for (int less = 0; less < 26; less++) {
				if (more != less) {
					int continuousA = 0;
					boolean appearB = false;
					int max = 0;
					// 从左到右遍历，
					for (int i = 0; i < n; i++) {
						if (arr[i] != more && arr[i] != less) {
							continue;
						}
						if (arr[i] == more) { // 当前字符是more
							continuousA++;
							if (appearB) {
								max++;
							}
						} else { // 当前字符是B
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
		// a b a c b b a
		// 0 1 0 2 1 1 0
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = s.charAt(i) - 'a';
		}
		// dp[a][b] = more a less b max
		// dp[b][a] = more b less a max
		int[][] dp = new int[26][26];
		// continuous[a][b] more a less b 连续出现a的次数
		// continuous[b][a] more b less a 连续出现b的次数
		int[][] continuous = new int[26][26];
		// appear[a][b] more a less b b有没有出现过
		// appear[b][a] more b less a a有没有出现过
		boolean[][] appear = new boolean[26][26];
		int ans = 0;
		// 26 * N
		for (int i : arr) {
			for (int j = 0; j < 26; j++) {
				if (j != i) {
					// i,j
					// more i less j 三个变量 连续出现i，j有没有出现过，i-j max
					// more j less i 三个变量 连续出现j，i有没有出现过，j-i max
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

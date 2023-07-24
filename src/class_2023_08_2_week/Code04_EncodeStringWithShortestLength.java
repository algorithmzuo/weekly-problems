package class_2023_08_2_week;

// 测试链接 : https://leetcode.cn/problems/encode-string-with-shortest-length/
public class Code04_EncodeStringWithShortestLength {

	public static int[] next;
	public static String[][] dp;

	public static String encode(String s) {
		int n = s.length();
		if (n == 0)
			return s;
		next = new int[n];
		dp = new String[n][n];
		for (int interval = 0; interval < n; interval++) {
			for (int start = interval; start < n; start++) {
				int i = start - interval;
				int j = start;
				dp[i][j] = encoding(s, i, j);
				for (int k = i; k < j; k++) {
					if (dp[i][j].length() > dp[i][k].length() + dp[k + 1][j].length()) {
						dp[i][j] = dp[i][k] + dp[k + 1][j];
					}
				}
			}
		}
		return dp[0][n - 1];
	}

	public static String encoding(String s, int st, int e) {
		// 不能被压缩
		if (s.length() <= 4)
			return s.substring(st, e + 1);
		// 相对位置中的"-1"
		next[st] = st - 1;
		int t;
		// KMP next 数组计算
		for (int i = st + 1; i <= e; i++) {
			t = next[i - 1];
			while (t != st - 1 && s.charAt(t + 1) != s.charAt(i)) {
				t = next[t];
			}
			next[i] = (s.charAt(t + 1) != s.charAt(i)) ? t : t + 1;
		}
		// 前后最长真字串长度,不要忘记减去基准st
		int sub = next[e] + 1 - st;
		// [st,e].length
		int len = e - st + 1;

		if (len % (len - sub) != 0 || sub == 0) {
			return s.substring(st, e + 1);
		}
		int newLen = dp[st][st + len - sub - 1].length() + 2 + String.valueOf(len / (len - sub)).length();
		// 如果字符串能被某段表示压缩，一定用该段的最压缩形式(dp中的值)，而不是直接substrin
		return (newLen < len) ? len / (len - sub) + "[" + dp[st][st + len - sub - 1] + "]" : s;
	}

}

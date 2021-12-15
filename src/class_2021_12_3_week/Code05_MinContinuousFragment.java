package class_2021_12_3_week;

// 你会得到一个长度为 N 的字符串 S，由字符'a'和'b'组成，其他空隙由 '?' 表示。
// 你的任务是用一个'a'字符或'b'字符替换每个这样的间隙，以便S的最长片段，仅由'a'字符或'b'字符组成，尽可能短。
// 例如，S = "aa??bbb"，如果将"??"替换为"aa"，则由相等字符组成的最长片段的长度为4，即"aaaabbb"。
// 通过将"??"替换为"ba"，可以得到更好的结果，从而得到"aababbb"
// 给定长度为 N 的字符串 S，在用字母替换所有"?"字符后，返回由相等字符组成的 S 的最长片段的最小可能长度。
// 例子:
// 给定 S = "aa??bbb"  函数应该返回3
// 给定 S = "a?b?aa?b?a"  函数应该返回2
// 给定 S = "??b??"   函数应该返回1
// 给定 S = "aa?b?aa" 函数应返回3
// 字符串S仅由以下字符组成："a"、"b"和"?";
// S的长度 <= 10^6
public class Code05_MinContinuousFragment {

	// 暴力方法
	// 为了验证
	public static int minContinuous1(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		return process1(str, 0);
	}

	public static int process1(char[] str, int index) {
		if (index == str.length) {
			return maxLen(str);
		} else {
			if (str[index] != '?') {
				return process1(str, index + 1);
			} else {
				str[index] = 'a';
				int p1 = process1(str, index + 1);
				str[index] = 'b';
				int p2 = process1(str, index + 1);
				str[index] = '?';
				return Math.min(p1, p2);
			}
		}
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int minContinuous2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int L = 0;
		int R = -1;
		for (int i = 0; i < N; i++) {
			if (str[i] != '?') {
				set(str, L, R);
				L = i + 1;
				R = i;
			} else {
				R++;
			}
		}
		set(str, L, R);
		for (int i = 1; i < N; i++) {
			if (str[i] == '?') {
				for (L = i - 1; L >= 0 && str[L] == str[i - 1]; L--)
					;
				for (R = i + 1; R < N && str[R] == str[i + 1]; R++)
					;
				L = i - L - 1;
				R = R - i - 1;
				if (L <= R) {
					str[i] = str[i - 1];
				} else {
					str[i] = str[i + 1];
				}
			}
		}
		return maxLen(str);
	}

	public static void set(char[] str, int L, int R) {
		int N = str.length;
		if (L > R) {
			return;
		}
		if (L == 0 && R == N - 1) {
			for (int i = 0; i < N; i++) {
				str[i] = (i & 1) == 0 ? 'a' : 'b';
			}
		} else if (L == 0) {
			for (int i = R; i >= 0; i--) {
				str[i] = str[i + 1] == 'a' ? 'b' : 'a';
			}
		} else if (R == N - 1) {
			for (int i = L; i < str.length; i++) {
				str[i] = str[i - 1] == 'a' ? 'b' : 'a';
			}
		} else {
			if (str[L - 1] == str[R + 1] || L != R) {
				for (; L <= R; L++, R--) {
					str[L] = str[L - 1] == 'a' ? 'b' : 'a';
					str[R] = str[R + 1] == 'a' ? 'b' : 'a';
				}
			}
		}
	}

	public static int maxLen(char[] str) {
		int ans = 1;
		int cur = 1;
		for (int i = 1; i < str.length; i++) {
			if (str[i] != str[i - 1]) {
				ans = Math.max(ans, cur);
				cur = 1;
			} else {
				cur++;
			}
		}
		ans = Math.max(ans, cur);
		return ans;
	}

	public static char[] arr = { 'a', 'b', '?' };

	public static String randomString(int len) {
		int N = (int) (Math.random() * (len + 1));
		char[] str = new char[N];
		for (int i = 0; i < N; i++) {
			str[i] = arr[(int) (Math.random() * 3)];
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int len = 35;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			String s = randomString(len);
			int ans1 = minContinuous1(s);
			int ans2 = minContinuous2(s);
			if (ans1 != ans2) {
				System.out.println(s);
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");
	}

}

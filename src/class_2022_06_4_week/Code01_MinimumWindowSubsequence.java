package class_2022_06_4_week;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

// 给定字符串 S and T，找出 S 中最短的（连续）子串 W ，使得 T 是 W 的 子序列 。
// 如果 S 中没有窗口可以包含 T 中的所有字符，返回空字符串 ""。
// 如果有不止一个最短长度的窗口，返回开始位置最靠左的那个。
// 示例 1：
// 输入：
// S = "abcdebdde", T = "bde"
// 输出："bcde"
// 解释：
// "bcde" 是答案，因为它在相同长度的字符串 "bdde" 出现之前。
// "deb" 不是一个更短的答案，因为在窗口中必须按顺序出现 T 中的元素。
// 测试链接 : https://leetcode.cn/problems/minimum-window-subsequence/
public class Code01_MinimumWindowSubsequence {

//	public static int minLenContainsT1(char[] s, char[] t) {
//		int n = s.length;
//		int m = t.length;
//		
//		// s = xya...a...
//		// t = abc
//		int min = Integer.MAX_VALUE;
//		for (int i = 0; i < n; i++) {
//			if(s[i] == t[0]) {
//				int findEnd = findEnd(s, t, i);
//				if(findEnd != -1) { // 找到了！s[i...findEnd]答案
//					min = Math.min(min, findEnd - i + 1);
//				}
//			}
//		}
//		return min;
//	}
//	
//	// s = a.....?
//	//     si
//	// t = abcd
//	//     0
//	// 最后的？，s[si...?] 整出来t的整体
//	// 如果从si出发，就没有t的整体，返回-1
//	public static int findEnd(char[] s, char[] t, int si) {
//		
//	}

	public static void main(String[] args) {
		String s = "xxaxxbxxcxx";
		// 0 8
		String t = "abc";
		System.out.println(minLen(s, t));
	}

	public static int minLen(String str, String target) {
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int len = Integer.MAX_VALUE;
		for (int i = 0; i < s.length; i++) {
			// 0 > t
			// 1 > t
			// 2 > t
			int end = zuo(s, t, i, 0);
			if (end != Integer.MAX_VALUE) {
				int cur = end - i;
				len = Math.min(len, cur);
			}
		}
		return len;
	}

	// s[si.....]
	// t[ti....]
	// 把t的整体，都配出来，s在哪能尽早结束！的下个位置
	// s = x x a x x b x x c x a b c
	// 0 1 2 3 4 5 6 7 8 9 10 11 12
	// t = a b c
	// 0 1 2
	// s[1...] t[0...]
	// s[4...] t[1...] 8
	// s[4...] t[0...] 12
	public static int zuo(char[] s, char[] t, int si, int ti) {
		if (ti == t.length) { // 配完了！
			return si;
		}
		// ti < t.length;
		if (si == s.length) {
			return Integer.MAX_VALUE;
		}
		// 都有字符
		// 可能性1：根本不让s[si]去消化掉t[ti]
		int p1 = zuo(s, t, si + 1, ti);
		// 可能性2：让s[si]去消化掉t[ti]
		int p2 = Integer.MAX_VALUE;
		if (s[si] == t[ti]) {
			// si ti
			p2 = zuo(s, t, si + 1, ti + 1);
		}
		return Math.min(p1, p2);
	}

	public String minWindow1(String s, String t) {
		char[] str = s.toCharArray();
		char[] target = t.toCharArray();
		int n = str.length;
		// key : 字符! value:有序表！
		HashMap<Character, TreeSet<Integer>> map = new HashMap<>();
		for (char cha : target) {
			map.put(cha, new TreeSet<>());
		}
		for (int i = 0; i < n; i++) {
			if (map.containsKey(str[i])) {
				map.get(str[i]).add(i);
			}
		}
		int ansLen = Integer.MAX_VALUE;
		int l = -1;
		int r = -1;
		for (int i = 0; i < n; i++) {
			if (str[i] == target[0]) {
				int right = right1(str, i, target, map);
				if (right != -1 && (right - i) < ansLen) {
					ansLen = right - i;
					l = i;
					r = right;
				}
			}
		}
		return l == -1 ? "" : s.substring(l, r);
	}

	public static int right1(char[] str, int si, char[] target, HashMap<Character, TreeSet<Integer>> map) {
		int ti = 0;
		while (ti != target.length) {
			if (si == str.length) {
				return -1;
			}
			if (str[si] == target[ti]) {
				si++;
				ti++;
			} else {
				Integer next = map.get(target[ti]).ceiling(si);
				if (next == null) {
					return -1;
				} else {
					si = next;
				}
			}

		}
		return si;
	}

	public String minWindow2(String s, String t) {
		char[] str = s.toCharArray();
		char[] target = t.toCharArray();
		int n = str.length;
		int[] last = new int[26];
		int[][] near = new int[n][26];
		for (int i = 0; i < n; i++) {
			Arrays.fill(near[i], -1);
		}
		for (int i = 0; i < n; i++) {
			int cha = str[i] - 'a';
			for (int j = last[cha]; j < i; j++) {
				near[j][cha] = i;
			}
			last[cha] = i;
		}
		int ansLen = Integer.MAX_VALUE;
		int l = -1;
		int r = -1;
		for (int i = 0; i < n; i++) {
			if (str[i] == target[0]) {
				int right = right2(str, i, target, near);
				if (right != -1 && (right - i) < ansLen) {
					ansLen = right - i;
					l = i;
					r = right;
				}
			}
		}
		return l == -1 ? "" : s.substring(l, r);
	}

	public static int right2(char[] str, int si, char[] target, int[][] near) {
		int ti = 0;
		while (ti != target.length) {
			if (si == str.length) {
				return -1;
			}
			if (str[si] == target[ti]) {
				si++;
				ti++;
			} else {
				si = near[si][target[ti] - 'a'];
			}
			if (si == -1) {
				return -1;
			}
		}
		return si;
	}

	public String minWindow3(String s, String t) {
		char[] str = s.toCharArray();
		char[] target = t.toCharArray();
		int len = Integer.MAX_VALUE;
		int l = -1;
		int r = -1;
		for (int si = 0; si < str.length; si++) {
			int right = process(str, target, si, 0);
			if (right != Integer.MAX_VALUE && right - si < len) {
				len = right - si;
				l = si;
				r = right;
			}
		}
		return l == -1 ? "" : s.substring(l, r);
	}

	public static int process(char[] str, char[] target, int si, int ti) {
		if (ti == target.length) {
			return si;
		}
		if (si == str.length) {
			return Integer.MAX_VALUE;
		}
		int r1 = process(str, target, si + 1, ti);
		int r2 = str[si] == target[ti] ? process(str, target, si + 1, ti + 1) : Integer.MAX_VALUE;
		return Math.min(r1, r2);
	}

	public String minWindow4(String s, String t) {
		char[] str = s.toCharArray();
		char[] target = t.toCharArray();
		int n = str.length;
		int m = target.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int si = 0; si <= n; si++) {
			dp[si][m] = si;
		}
		for (int ti = 0; ti < m; ti++) {
			dp[n][ti] = Integer.MAX_VALUE;
		}
		for (int si = n - 1; si >= 0; si--) {
			for (int ti = m - 1; ti >= 0; ti--) {
				int r1 = dp[si + 1][ti];
				int r2 = str[si] == target[ti] ? dp[si + 1][ti + 1] : Integer.MAX_VALUE;
				dp[si][ti] = Math.min(r1, r2);
			}
		}
		int len = Integer.MAX_VALUE;
		int l = -1;
		int r = -1;
		for (int si = 0; si < str.length; si++) {
			int right = dp[si][0];
			if (right != Integer.MAX_VALUE && right - si < len) {
				len = dp[si][0] - si;
				l = si;
				r = right;
			}
		}
		return l == -1 ? "" : s.substring(l, r);
	}

}

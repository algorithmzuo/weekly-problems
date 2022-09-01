package class_2022_09_3_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/longest-ideal-subsequence/
public class Code01_LongestIdealString {

	// 二维动态规划的解
	// N为字符串长度，E为字符集大小，K为差值要求
	// 时间复杂度O(N*E)
	// 空间复杂度O(N*E)
	public static int longestIdealString1(String s, int k) {
		int n = s.length();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = s.charAt(i) - 'a';
		}
		int[][] dp = new int[n][27];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= 26; j++) {
				dp[i][j] = -1; // -1代表没算过
			}
		}
		return f(arr, 0, 26, k, dp);
	}

	// 数组s中所有的值都在0~25对应a~z
	// 当前在s[i...]选择数字, 并且前一个数字是p
	// 如果p<26，说明选择的前一个数字是p
	// 如果p==26，说明之前没有选过任何数字
	// 返回在前一个数字是p的情况下，在s[i...]上选择数字，最长理想子序列能是多长
	// dp仅仅是缓存结构，暴力递归改动态规划常规技巧
	public static int f(int[] s, int i, int p, int k, int[][] dp) {
		if (i == s.length) {
			return 0;
		}
		if (dp[i][p] != -1) {
			return dp[i][p];
		}
		int p1 = f(s, i + 1, p, k, dp);
		int p2 = 0;
		if (p == 26 || Math.abs(s[i] - p) <= k) {
			p2 = 1 + f(s, i + 1, s[i], k, dp);
		}
		int ans = Math.max(p1, p2);
		dp[i][p] = ans;
		return ans;
	}

	// 一维动态规划从左往右递推版
	// N为字符串长度，E为字符集大小，K为差值要求
	// 时间复杂度O(N*K)
	// 空间复杂度O(E)
	public static int longestIdealString2(String s, int k) {
		int[] dp = new int[26];
		int c, l, r, pre, ans = 0;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i) - 'a';
			l = Math.max(c - k, 0);
			r = Math.min(c + k, 25);
			pre = 0;
			for (int j = l; j <= r; j++) {
				pre = Math.max(pre, dp[j]);
			}
			dp[c] = 1 + pre;
			ans = Math.max(ans, dp[c]);
		}
		return ans;
	}

	// 从左往右递推 + 线段树优化
	// N为字符串长度，E为字符集大小，K为差值要求
	// 时间复杂度O(N * logE)
	// 空间复杂度O(E)
	public static int longestIdealString3(String s, int k) {
		SegmentTree st = new SegmentTree(26);
		int c, pre, ans = 0;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i) - 'a' + 1;
			pre = st.max(Math.max(c - k, 1), Math.min(c + k, 26));
			ans = Math.max(ans, 1 + pre);
			st.update(c, 1 + pre);
		}
		return ans;
	}

	public static class SegmentTree {
		private int n;
		private int[] max;
		private int[] update;

		public SegmentTree(int maxSize) {
			n = maxSize + 1;
			max = new int[n << 2];
			update = new int[n << 2];
			Arrays.fill(update, -1);
		}

		public void update(int index, int c) {
			update(index, index, c, 1, n, 1);
		}

		public int max(int left, int right) {
			return max(left, right, 1, n, 1);
		}

		private void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt] != -1) {
				update[rt << 1] = update[rt];
				max[rt << 1] = update[rt];
				update[rt << 1 | 1] = update[rt];
				max[rt << 1 | 1] = update[rt];
				update[rt] = -1;
			}
		}

		private void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				max[rt] = C;
				update[rt] = C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				update(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		private int max(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return max[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int ans = 0;
			if (L <= mid) {
				ans = Math.max(ans, max(L, R, l, mid, rt << 1));
			}
			if (R > mid) {
				ans = Math.max(ans, max(L, R, mid + 1, r, rt << 1 | 1));
			}
			return ans;
		}

	}

}

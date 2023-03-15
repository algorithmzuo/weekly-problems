package class_2023_03_5_week;

import java.util.Arrays;

// https://leetcode.cn/problems/maximum-earnings-from-taxi/
public class Code01_MaximumEarningsFromTaxi {

	public static int MAXN = 100001;

	public static long[] max = new long[MAXN << 2];

	public static int n;

	public static void build(int l, int r, int rt) {
		if (l == r) {
			max[rt] = 0;
		} else {
			int mid = (l + r) / 2;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			pushUp(rt);
		}
	}

	public static long max(int r) {
		if (r < 1) {
			return 0;
		}
		return max(1, r, 1, n, 1);
	}

	private static long max(int L, int R, int l, int r, int rt) {
		if (L <= l && r <= R) {
			return max[rt];
		}
		int mid = (l + r) >> 1;
		long ans = 0;
		if (L <= mid) {
			ans = Math.max(ans, max(L, R, l, mid, rt << 1));
		}
		if (R > mid) {
			ans = Math.max(ans, max(L, R, mid + 1, r, rt << 1 | 1));
		}
		return ans;
	}

	public static void update(int index, long c) {
		update(index, c, 1, n, 1);
	}

	private static void update(int index, long c, int l, int r, int rt) {
		if (l == r) {
			max[rt] = Math.max(max[rt], c);
		} else {
			int mid = (l + r) >> 1;
			if (index <= mid) {
				update(index, c, l, mid, rt << 1);
			} else {
				update(index, c, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}
	}

	private static void pushUp(int rt) {
		max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
	}

	public static long maxTaxiEarnings1(int len, int[][] rides) {
		Arrays.sort(rides, (a, b) -> a[0] - b[0]);
		n = len;
		build(1, n, 1);
		for (int[] ride : rides) {
			long money = max(ride[0]) + ride[1] - ride[0] + ride[2];
			update(ride[1], money);
		}
		return max(n);
	}

	public static int[] sorted = new int[MAXN];

	public static long[] dp = new long[MAXN];

	public static long maxTaxiEarnings2(int len, int[][] rides) {
		int m = rides.length;
		for (int i = 0, j = 0; i < m; i++) {
			sorted[j++] = rides[i][0];
			sorted[j++] = rides[i][1];
		}
		Arrays.sort(rides, (a, b) -> a[0] - b[0]);
		Arrays.sort(sorted, 0, m << 1);
		Arrays.fill(dp, 0, m << 1, 0);
		int dpi = 0;
		long pre = 0;
		long ans = 0;
		for (int[] ride : rides) {
			int start = ride[0];
			int end = ride[1];
			int tips = ride[2];
			int srank = rank(sorted, m << 1, start);
			int erank = rank(sorted, m << 1, end);
			while (dpi <= srank) {
				pre = Math.max(pre, dp[dpi++]);
			}
			long money = pre + end - start + tips;
			ans = Math.max(money, ans);
			dp[erank] = Math.max(dp[erank], money);
		}
		return ans;
	}

	public static int rank(int[] sorted, int len, int num) {
		int ans = 0;
		int l = 0;
		int r = len - 1;
		int m = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (sorted[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}

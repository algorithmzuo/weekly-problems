package class_2022_05_4_week;

import java.util.Arrays;

// 来自字节
// 5.6笔试
// 给定一个数组arr，长度为n，最多可以删除一个连续子数组，
// 求剩下的数组，严格连续递增子数组的最大长度
// n <= 10^6
public class Code01_MaxIncreasingSubarrayCanDeleteContinuousPart {

	// 暴力方法
	// 为了验证
	public static int maxLen1(int[] arr) {
		int ans = max(arr);
		int n = arr.length;
		for (int L = 0; L < n; L++) {
			for (int R = L; R < n; R++) {
				int[] cur = delete(arr, L, R);
				ans = Math.max(ans, max(cur));
			}
		}
		return ans;
	}

	public static int[] delete(int[] arr, int L, int R) {
		int n = arr.length;
		int[] ans = new int[n - (R - L + 1)];
		int index = 0;
		for (int i = 0; i < L; i++) {
			ans[index++] = arr[i];
		}
		for (int i = R + 1; i < n; i++) {
			ans[index++] = arr[i];
		}
		return ans;
	}

	public static int max(int[] arr) {
		if (arr.length == 0) {
			return 0;
		}
		int ans = 1;
		int cur = 1;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[i - 1]) {
				cur++;
			} else {
				cur = 1;
			}
			ans = Math.max(ans, cur);
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N*logN)
	public static int maxLen2(int[] arr) {
		if (arr.length == 0) {
			return 0;
		}
		int n = arr.length;
		int[] sorted = new int[n];
		for (int i = 0; i < n; i++) {
			sorted[i] = arr[i];
		}
		Arrays.sort(sorted);
		SegmentTree st = new SegmentTree(n);
		st.update(rank(sorted, arr[0]), 1);
		int[] dp = new int[n];
		dp[0] = 1;
		int ans = 1;
		int cur = 1;
		for (int i = 1; i < n; i++) {
			int rank = rank(sorted, arr[i]);
			int p1 = arr[i - 1] < arr[i] ? (dp[i - 1] + 1) : 1;
			int p2 = rank > 1 ? (st.max(rank - 1) + 1) : 1;
			dp[i] = Math.max(p1, p2);
			ans = Math.max(ans, dp[i]);
			if (arr[i] > arr[i - 1]) {
				cur++;
			} else {
				cur = 1;
			}
			if (st.get(rank) < cur) {
				st.update(rank, cur);
			}
		}
		return ans;
	}

	public static int rank(int[] sorted, int num) {
		int l = 0;
		int r = sorted.length - 1;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (sorted[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans + 1;
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

		public int get(int index) {
			return max(index, index, 1, n, 1);
		}

		public void update(int index, int c) {
			update(index, index, c, 1, n, 1);
		}

		public int max(int right) {
			return max(1, right, 1, n, 1);
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

	// 为了验证
	public static int[] randomArray(int len, int v) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * v) - (int) (Math.random() * v);
		}
		return arr;
	}

	// 为了验证
	public static void main(String[] args) {
		int n = 100;
		int v = 20;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int m = (int) (Math.random() * n);
			int[] arr = randomArray(m, v);
			int ans1 = maxLen1(arr);
			int ans2 = maxLen2(arr);
			if (ans1 != ans2) {
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

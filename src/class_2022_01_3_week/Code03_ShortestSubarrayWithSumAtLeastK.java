package class_2022_01_3_week;

// 来自字节跳动
// 给定一个数组arr，其中的值有可能正、负、0
// 给定一个正数k
// 返回累加和>=k的所有子数组中，最短的子数组长度
// 本题测试链接 : https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
public class Code03_ShortestSubarrayWithSumAtLeastK {

	public static int shortestSubarray1(int[] arr, int k) {
		if (arr == null || arr.length < 1) {
			return -1;
		}
		int n = arr.length + 1;
		long[] sum = new long[n];
		for (int i = 1; i < n; i++) {
			sum[i] = sum[i - 1] + arr[i - 1];
		}
		int[] stack = new int[n];
		int size = 1;
		int ans = Integer.MAX_VALUE;
		for (int i = 1; i < n; i++) {
			int mostRight = mostRight(sum, stack, size, sum[i] - k);
			ans = Math.min(ans, mostRight == -1 ? Integer.MAX_VALUE : (i - mostRight));
			while (size > 0 && sum[stack[size - 1]] >= sum[i]) {
				size--;
			}
			stack[size++] = i;
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	public static int mostRight(long[] sum, int[] stack, int size, long aim) {
		int l = 0;
		int r = size - 1;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (sum[stack[m]] <= aim) {
				ans = stack[m];
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static int shortestSubarray2(int[] arr, int K) {
		int N = arr.length;
		long[] sum = new long[N + 1];
		for (int i = 0; i < N; i++) {
			sum[i + 1] = sum[i] + arr[i];
		}
		int ans = Integer.MAX_VALUE;
		int[] dq = new int[N + 1];
		int l = 0;
		int r = 0;
		for (int i = 0; i < N + 1; i++) {
			while (l != r && sum[i] - sum[dq[l]] >= K) {
				ans = Math.min(ans, i - dq[l++]);
			}
			while (l != r && sum[dq[r - 1]] >= sum[i]) {
				r--;
			}
			dq[r++] = i;
		}
		return ans != Integer.MAX_VALUE ? ans : -1;
	}

}

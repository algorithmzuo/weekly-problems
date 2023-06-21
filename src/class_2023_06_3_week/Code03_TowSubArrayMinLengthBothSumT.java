package class_2023_06_3_week;

import java.util.Arrays;
import java.util.HashMap;

// 来自字节
// 给定一个数组arr，长度为n，在其中要选两个不相交的子数组
// 两个子数组的累加和都要是T，返回所有满足情况中，两个子数组长度之和最小是多少
// 如果没有有效方法，返回-1
// 正式 :
// 2 <= n <= 10^6
// 1 <= arr[i] <= 10000
// 1 <= T <= 10^8
// 扩展 : 
// 2 <= n <= 10^6
// -10000 <= arr[i] <= 10000
// 1 <= T <= 10^8

public class Code03_TowSubArrayMinLengthBothSumT {

	// 暴力方法
	// 为了验证
	// 假设数组中的值可以正、负、0(就是扩展数据范围也能正确的暴力方法)
	public static int minLenBothT1(int[] arr, int t) {
		int n = arr.length;
		int[] sum = new int[n];
		sum[0] = arr[0];
		for (int i = 1; i < n; i++) {
			sum[i] = sum[i - 1] + arr[i];
		}
		int ans = Integer.MAX_VALUE;
		for (int a = 0; a < n - 1; a++) {
			for (int b = a; b < n - 1; b++) {
				for (int c = b + 1; c < n; c++) {
					for (int d = c; d < n; d++) {
						if (sum(sum, a, b) == t && sum(sum, c, d) == t) {
							ans = Math.min(ans, b + d - a - c + 2);
						}
					}
				}
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// 暴力方法
	// 为了验证
	public static int sum(int[] sum, int l, int r) {
		return l == 0 ? sum[r] : (sum[r] - sum[l - 1]);
	}

	// 正式方法
	// 时间复杂度O(N)
	// 数组都是非负数情况下的正确方法
	public static int minLenBothT2(int[] arr, int t) {
		int n = arr.length;
		if (t < 0) {
			return -1;
		}
		if (t == 0) {
			int cnt = 0;
			for (int num : arr) {
				if (num == 0) {
					cnt++;
				}
			}
			return cnt >= 2 ? 2 : -1;
		}
		int[] right = new int[n];
		Arrays.fill(right, Integer.MAX_VALUE);
		for (int l = 1, r = 1, sum = 0; l < n; l++) {
			r = Math.max(r, l);
			while (r < n && sum < t) {
				sum += arr[r++];
			}
			if (sum == t) {
				right[l] = r - l;
			}
			sum -= arr[l];
		}
		for (int i = n - 2; i >= 0; i--) {
			right[i] = Math.min(right[i], right[i + 1]);
		}
		int ans = Integer.MAX_VALUE;
		for (int r = n - 2, l = n - 2, sum = 0; r >= 0; r--) {
			l = Math.min(l, r);
			while (l >= 0 && sum < t) {
				sum += arr[l--];
			}
			if (sum == t && right[r + 1] != Integer.MAX_VALUE) {
				ans = Math.min(ans, r - l + right[r + 1]);
			}
			sum -= arr[r];
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// 扩展方法
	// 时间复杂度O(N)
	// 假设数组中的值可以正、负、0，就是扩展数据范围也能正确的方法
	public static int minLenBothT3(int[] arr, int t) {
		int n = arr.length;
		HashMap<Integer, Integer> sums = new HashMap<>();
		sums.put(0, -1);
		int[] left = new int[n];
		Arrays.fill(left, Integer.MAX_VALUE);
		for (int i = 0, sum = 0; i < n - 1; i++) {
			sum += arr[i];
			if (sums.containsKey(sum - t)) {
				left[i] = i - sums.get(sum - t);
			}
			sums.put(sum, i);
		}
		for (int i = 1; i < n - 1; i++) {
			left[i] = Math.min(left[i - 1], left[i]);
		}
		int ans = Integer.MAX_VALUE;
		sums.clear();
		sums.put(0, n);
		for (int i = n - 1, sum = 0; i >= 1; i--) {
			sum += arr[i];
			if (sums.containsKey(sum - t) && left[i - 1] != Integer.MAX_VALUE) {
				ans = Math.min(ans, left[i - 1] + sums.get(sum - t) - i);
			}
			sums.put(sum, i);
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// 为了测试
	// 随机数组长度为n
	// 值在[0, v]之间随机
	public static int[] randomArray1(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * (v + 1));
		}
		return ans;
	}

	// 为了测试
	// 随机数组长度为n
	// 值在[-v,+v]之间随机
	public static int[] randomArray2(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * (2 * v + 1)) - v;
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int T = 100;
		int testTimes = 10000;
		System.out.println("正式方法测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 2;
			int v = (int) (Math.random() * V) + 1;
			int[] arr = randomArray1(n, v);
			int t = (int) (Math.random() * T);
			int ans1 = minLenBothT1(arr, t);
			int ans2 = minLenBothT2(arr, t);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("正式方法测试结束");

		System.out.println("扩展方法测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 2;
			int v = (int) (Math.random() * V) + 1;
			int[] arr = randomArray2(n, v);
			int t = (int) (Math.random() * T);
			int ans1 = minLenBothT1(arr, t);
			int ans3 = minLenBothT3(arr, t);
			if (ans1 != ans3) {
				System.out.println("出错了!");
			}
		}
		System.out.println("扩展方法测试结束");
	}

}

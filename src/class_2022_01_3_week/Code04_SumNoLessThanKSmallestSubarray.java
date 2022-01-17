package class_2022_01_3_week;

// 来自字节跳动
// 给定一个数组arr，其中的值有可能正、负、0
// 给定一个正数k
// 返回累加和>=k的所有子数组中，最短的子数组长度
public class Code04_SumNoLessThanKSmallestSubarray {

	// 暴力方法
	// 为了验证
	public static int smallest1(int[] arr, int k) {
		if (arr == null || arr.length < 1) {
			return -1;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			int sum = 0;
			for (int j = i; j < arr.length; j++) {
				sum += arr[j];
				if (sum >= k) {
					ans = Math.min(ans, j - i + 1);
					break;
				}
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// 正式方法
	// 时间复杂度O(N*logN)
	public static int smallest2(int[] arr, int k) {
		if (arr == null || arr.length < 1) {
			return -1;
		}
		int n = arr.length + 1;
		int[] sum = new int[n];
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

	public static int mostRight(int[] sum, int[] stack, int size, int aim) {
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

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) - (int) (Math.random() * v);
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int size = 100;
		int value = 100;
		int kMax = 1000;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * size);
			int[] arr = randomArray(n, value);
			int k = (int) (Math.random() * kMax);
			int ans1 = smallest1(arr, k);
			int ans2 = smallest2(arr, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

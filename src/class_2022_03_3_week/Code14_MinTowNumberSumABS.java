package class_2022_03_3_week;

import java.util.Arrays;

// 来自学员问题
// 给定一个数组arr，可能有正、有负、有0，无序
// 只能挑选两个数字，想尽量让两个数字加起来的绝对值尽量小
// 返回可能的最小的值
public class Code14_MinTowNumberSumABS {

	public static int minSumABS1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return -1;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				ans = Math.min(ans, Math.abs(arr[i] + arr[j]));
			}
		}
		return ans;
	}

	public static int minSumABS2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return -1;
		}
		Arrays.sort(arr);
		int n = arr.length;
		int split = -1;
		for (int i = 0; i < n; i++) {
			if (arr[i] >= 0) {
				split = i;
				break;
			}
		}
		if (split == 0) {
			return arr[0] + arr[1];
		}
		if (split == -1) {
			return -arr[n - 2] - arr[n - 1];
		}
		int ans = Integer.MAX_VALUE;
		if (split + 1 < n) {
			ans = arr[split] + arr[split + 1];
		}
		if (split - 2 >= 0) {
			ans = Math.min(ans, -arr[split - 1] - arr[split - 2]);
		}
		for (int i = 0; i < split; i++) {
			ans = Math.min(ans, Math.abs(arr[i] + near(arr, split, -arr[i])));
		}
		return ans;
	}

	// arr[start...]是有序的
	// 返回离num最近的数字
	public static int near(int[] arr, int start, int num) {
		int l = start;
		int r = arr.length - 1;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr[m] <= num) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		if (ans == -1) {
			return arr[start];
		} else {
			if (ans == arr.length - 1) {
				return arr[arr.length - 1];
			} else {
				if (Math.abs(arr[ans] - num) <= Math.abs(arr[ans + 1] - num)) {
					return arr[ans];
				} else {
					return arr[ans + 1];
				}
			}
		}
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
		int len = 50;
		int value = 500;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, value);
			int ans1 = minSumABS1(arr);
			int ans2 = minSumABS2(arr);
			if (ans1 == ans2) {
				System.out.println("出错了!");
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

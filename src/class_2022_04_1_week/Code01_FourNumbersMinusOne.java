package class_2022_04_1_week;

import java.util.Arrays;

// 来自阿里笔试
// 牛牛今年上幼儿园了，老师叫他学习减法
// 老师给了他5个数字，他每次操作可以选择其中的4个数字减1
// 减一之后的数字不能小于0，因为幼儿园的牛牛还没有接触过负数
// 现在牛牛想知道，自己最多可以进行多少次这样的操作
// 扩展问题来自leetcode 2141，掌握了这个题原始问题就非常简单了
// leetcode测试链接 : https://leetcode.com/problems/maximum-running-time-of-n-computers/
public class Code01_FourNumbersMinusOne {

	public static long maxRunTime(int n, int[] arr) {
		Arrays.sort(arr);
		int size = arr.length;
		long[] sums = new long[size];
		sums[0] = arr[0];
		for (int i = 1; i < size; i++) {
			sums[i] = sums[i - 1] + arr[i];
		}
		long l = 0;
		long m = 0;
		long r = sums[size - 1] / n;
		long ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (ok(arr, sums, m, n)) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static boolean ok(int[] arr, long[] sum, long time, int num) {
		int left = mostLeft(arr, time);
		num -= arr.length - left;
		long rest = left == 0 ? 0 : sum[left - 1];
		return time * (long) num <= rest;
	}

	// >= time 最左
	public static int mostLeft(int[] arr, long time) {
		int l = 0;
		int m = 0;
		int r = arr.length - 1;
		int ans = arr.length;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr[m] >= time) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}

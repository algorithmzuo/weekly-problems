package class_2022_11_3_week;

import java.util.Arrays;

// 给定你一个整数数组 nums
// 我们要将 nums 数组中的每个元素移动到 A 数组 或者 B 数组中
// 使得 A 数组和 B 数组不为空，并且 average(A) == average(B)
// 如果可以完成则返回true ， 否则返回 false  。
// 注意：对于数组 arr ,  average(arr) 是 arr 的所有元素的和除以 arr 长度。
// 测试链接 : https://leetcode.cn/problems/split-array-with-same-average/
public class Code05_SplitArrayWithSameAverage {

	public static int n;
	public static int s;
	public static int l;
	public static int r;
	public static int[] lsum = new int[1 << 15];
	public static int[] rsum = new int[1 << 15];

	public static boolean splitArraySameAverage(int[] nums) {
		n = nums.length;
		if (n == 1) {
			return false;
		}
		s = 0;
		for (int num : nums) {
			s += num;
		}
		l = 0;
		r = 0;
		int[] larr = new int[n / 2];
		int[] rarr = new int[n - larr.length];
		for (int i = 0; i < larr.length; i++) {
			larr[i] = nums[i];
		}
		for (int i = larr.length, j = 0; i < nums.length; i++, j++) {
			rarr[j] = nums[i];
		}
		collectSum(larr, true);
		collectSum(rarr, false);
		Arrays.sort(rsum, 0, r);
		for (int i = 0; i < l; i++) {
			if (contains(-lsum[i])) {
				return true;
			}
		}
		return false;
	}

	public static void collectSum(int[] arr, boolean isLeft) {
		process(arr, 0, 0, 0, isLeft);
	}

	public static void process(int[] arr, int index, int sum, int num, boolean isLeft) {
		if (index == arr.length) {
			if (isLeft && num > 0) {
				lsum[l++] = n * sum - s * num;
			}
			if (!isLeft && num != arr.length) {
				rsum[r++] = n * sum - s * num;
			}
		} else {
			process(arr, index + 1, sum, num, isLeft);
			process(arr, index + 1, sum + arr[index], num + 1, isLeft);
		}
	}

	public static boolean contains(int num) {
		for (int left = 0, right = r - 1, mid = 0; left <= right;) {
			mid = (left + right) / 2;
			if (rsum[mid] == num) {
				return true;
			} else if (rsum[mid] < num) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return false;
	}

}

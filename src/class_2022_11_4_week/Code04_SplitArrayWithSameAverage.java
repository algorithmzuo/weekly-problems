package class_2022_11_4_week;

import java.util.Arrays;

// 给定你一个整数数组 nums
// 我们要将 nums 数组中的每个元素移动到 A 集合 或者 B 集合中
// 使得 A 集合和 B 集合不为空，并且 average(A) == average(B)
// 如果可以完成则返回true，否则返回false。
// 注意：对于数组 arr,  average(arr) 是 arr 的所有元素的和除以 arr 长度。
// 测试链接 : https://leetcode.cn/problems/split-array-with-same-average/
public class Code04_SplitArrayWithSameAverage {
//
//	public static int n;
//	public static int sum;
//
//	public static boolean can(int[] arr) {
//		n = arr.length;
//		if (n == 1) {
//			return false;
//		}
//		sum = 0;
//		for (int num : arr) {
//			sum += num;
//		}
//
//		int leftSize = n / 2;
//		int[] left = new int[leftSize];
//		for (int i = 0; i < leftSize; i++) {
//			left[i] = arr[i];
//		}
//
//		int rightSize = n - leftSize;
//		int[] right = new int[leftSize];
//		for (int i = 0, j = leftSize; j < n; i++, j++) {
//			right[i] = arr[j];
//		}
//		
//		HashSet<Integer> ans1 = new HashSet<>();
//		collect(left, 0, 0, 0, ans1);
//		HashSet<Integer> ans2 = new HashSet<>();
//		collect(right, 0, 0, 0, ans2);
//		
//		
//		
//		 // ans1 所有的指标 : 2 ^ 15  -> 3万
//		 // ans2  所有的指标 : 2 ^ 15
//		
//		
//		
//		for(int x : ans1) { // 2 ^ 15
//			// ans2 -x  O(1)
//		}
//		
//
//		// left : T1 S1
//		// right: T2 S2
//		// S1 + S2 / T1 + T2 == Sum / N
//		// N * S1 - Sum * T1 == - (N * S2 - Sum * T2)
//
//	}
//
//	// left[i.....]  X Y
//	// t, s
//	// ans
//	public static void collect(int[] part, 
//			int i, int t, int s, HashSet<Integer> ans) {
//		if(i == part.length) {
//			ans.add(n * s - sum * t);
//		}else {
//			collect(part, i+1, t, s, ans);
//			collect(part, i+1, t+1, s + part[i], ans);
//		}
//	}
//
//	// 左，

	public static int n;
	public static int s;
	public static int l;
	public static int r;
	public static int[] lvalues = new int[1 << 15];
	// 3000 l = 3000
	// 0...2999
	public static int[] rvalues = new int[1 << 15];

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
		// 左侧 : 收集指标的时候，不能一个数也没有
		collect(larr, true);
		// 右侧 : 收集指标的时候，不能所有数都用
		collect(rarr, false);
		Arrays.sort(rvalues, 0, r);
		for (int i = 0; i < l; i++) {
			// 左侧x  -x
			if (contains(-lvalues[i])) {
				return true;
			}
		}
		return false;
	}

	public static void collect(int[] arr, boolean isLeft) {
		process(arr, 0, 0, 0, isLeft);
	}

	public static void process(int[] arr, int index, int sum, int num, boolean isLeft) {
		if (index == arr.length) {
			if (isLeft && num > 0) {
				lvalues[l++] = s * num - n * sum;
			}
			if (!isLeft && num != arr.length) {
				rvalues[r++] = s * num - n * sum;
			}
		} else {
			process(arr, index + 1, sum, num, isLeft);
			process(arr, index + 1, sum + arr[index], num + 1, isLeft);
		}
	}

	public static boolean contains(int num) {
		for (int left = 0, right = r - 1, mid = 0; left <= right;) {
			mid = (left + right) / 2;
			if (rvalues[mid] == num) {
				return true;
			} else if (rvalues[mid] < num) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return false;
	}

}

package class_2022_02_2_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.com/problems/find-k-th-smallest-pair-distance/
public class Code03_FindKthSmallestPairDistance {

//	public static int smallestDistancePair(int[] nums, int k) {
//		int n = nums.length;
//		Arrays.sort(nums);
//		int l = 0;
//		int r = nums[n - 1] - nums[0];
//		int ans = 0;
//		while (l <= r) {
//			int cnt = 0;
//			int m = l + ((r - l) >> 1);
//			for (int i = 0, j = 0; i < n; i++) {
//				while (j < n && nums[j] <= nums[i] + m) {
//					j++;
//				}
//				cnt += j - i - 1;
//			}
//			if (cnt >= k) {
//				ans = m;
//				r = m - 1;
//			} else {
//				l = m + 1;
//			}
//		}
//		return ans;
//	}

	public static int smallestDistancePair(int[] nums, int k) {
		int n = nums.length;
		Arrays.sort(nums);
		int l = 0;
		int r = nums[n - 1] - nums[0];
		int ans = 0;
		while (l <= r) {
			int dis = l + ((r - l) >> 1);
			int cnt = f(nums, dis);
			if (cnt >= k) {
				ans = dis;
				r = dis - 1;
			} else {
				l = dis + 1;
			}
		}
		return ans;
	}

	// <= dis的数字对，有几个，返回
	public static int f(int[] arr, int dis) {
		int cnt = 0;
		for (int l = 0, r = 0; l < arr.length; l++) {
			while (r < arr.length && arr[r] <= arr[l] + dis) {
				r++;
			}
			cnt += r - l - 1;
		}
		return cnt;
	}

}

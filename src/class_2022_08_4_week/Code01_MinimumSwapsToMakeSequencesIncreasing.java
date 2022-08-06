package class_2022_08_4_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/minimum-swaps-to-make-sequences-increasing/
public class Code01_MinimumSwapsToMakeSequencesIncreasing {

	public static int minSwap1(int[] nums1, int[] nums2) {
		int[][] dp = new int[2][nums1.length];
		Arrays.fill(dp[0], -1);
		Arrays.fill(dp[1], -1);
		return process1(nums1, nums2, 0, 0, dp);
	}

	public static int process1(int[] nums1, int[] nums2, int pre, int index, int[][] dp) {
		if (index == nums1.length) {
			return 0;
		}
		if (dp[pre][index] != -1) {
			return dp[pre][index];
		}
		int nums1Last = index == 0 ? Integer.MIN_VALUE : (pre == 0 ? nums1[index - 1] : nums2[index - 1]);
		int nums2Last = index == 0 ? Integer.MIN_VALUE : (pre == 0 ? nums2[index - 1] : nums1[index - 1]);
		int p1 = Integer.MAX_VALUE;
		if (nums1Last < nums1[index] && nums2Last < nums2[index]) {
			p1 = process1(nums1, nums2, 0, index + 1, dp);
		}
		int p2 = Integer.MAX_VALUE;
		int next2 = Integer.MAX_VALUE;
		if (nums1Last < nums2[index] && nums2Last < nums1[index]) {
			next2 = process1(nums1, nums2, 1, index + 1, dp);
		}
		if (next2 != Integer.MAX_VALUE) {
			p2 = 1 + next2;
		}
		int ans = Math.min(p1, p2);
		dp[pre][index] = ans;
		return ans;
	}

	public static int minSwap2(int[] nums1, int[] nums2) {
		int n = nums1.length;
		int[][] dp = new int[2][n + 1];
		for (int index = n - 1; index >= 0; index--) {
			for (int pre = 0; pre < 2; pre++) {
				int nums1Last = index == 0 ? Integer.MIN_VALUE : (pre == 0 ? nums1[index - 1] : nums2[index - 1]);
				int nums2Last = index == 0 ? Integer.MIN_VALUE : (pre == 0 ? nums2[index - 1] : nums1[index - 1]);
				int p1 = Integer.MAX_VALUE;
				if (nums1Last < nums1[index] && nums2Last < nums2[index]) {
					p1 = dp[0][index + 1];
				}
				int p2 = Integer.MAX_VALUE;
				int next2 = Integer.MAX_VALUE;
				if (nums1Last < nums2[index] && nums2Last < nums1[index]) {
					next2 = dp[1][index + 1];
				}
				if (next2 != Integer.MAX_VALUE) {
					p2 = 1 + next2;
				}
				dp[pre][index] = Math.min(p1, p2);
			}
		}
		return dp[0][0];
	}

	public static int minSwap3(int[] nums1, int[] nums2) {
		int n = nums1.length;
		int postNo = 0;
		int postYes = 0;
		int curNo = 0;
		int curYes = 0;
		for (int index = n - 1; index >= 0; index--) {
			int last1 = index == 0 ? Integer.MIN_VALUE : nums1[index - 1];
			int last2 = index == 0 ? Integer.MIN_VALUE : nums2[index - 1];
			int p1 = Integer.MAX_VALUE;
			if (last1 < nums1[index] && last2 < nums2[index] && postNo != Integer.MAX_VALUE) {
				p1 = postNo;
			}
			int p2 = Integer.MAX_VALUE;
			if (last1 < nums2[index] && last2 < nums1[index] && postYes != Integer.MAX_VALUE) {
				p2 = 1 + postYes;
			}
			curNo = Math.min(p1, p2);
			last1 = index == 0 ? Integer.MIN_VALUE : nums2[index - 1];
			last2 = index == 0 ? Integer.MIN_VALUE : nums1[index - 1];
			p1 = Integer.MAX_VALUE;
			if (last1 < nums1[index] && last2 < nums2[index] && postNo != Integer.MAX_VALUE) {
				p1 = postNo;
			}
			p2 = Integer.MAX_VALUE;
			if (last1 < nums2[index] && last2 < nums1[index] && postYes != Integer.MAX_VALUE) {
				p2 = 1 + postYes;
			}
			curYes = Math.min(p1, p2);
			postNo = curNo;
			postYes = curYes;
		}
		return postNo;
	}

}

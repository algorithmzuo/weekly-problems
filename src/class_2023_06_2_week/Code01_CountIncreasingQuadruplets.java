package class_2023_06_2_week;

// 给你一个长度为 n 下标从 0 开始的整数数组 nums
// 它包含 1 到 n 的所有数字，请你返回上升四元组的数目。
// 如果一个四元组 (i, j, k, l) 满足以下条件，我们称它是上升的：
// 0 <= i < j < k < l < n 且
// nums[i] < nums[k] < nums[j] < nums[l] 。
// 测试链接 : https://leetcode.cn/problems/count-increasing-quadruplets/
public class Code01_CountIncreasingQuadruplets {

	public long countQuadruplets1(int[] nums) {
		int n = nums.length;
		int[][] right = new int[n][n + 1];
		for (int k = n - 2; k >= 2; k--) {
			for (int c = 1; c <= n; c++) {
				right[k][c] = right[k + 1][c];
			}
			for (int x = nums[k + 1] - 1; x > 0; x--) {
				right[k][x]++;
			}
		}
		long ans = 0;
		int[] left = new int[n + 1];
		for (int j = 1; j < n - 2; j++) {
			for (int x = nums[j - 1] + 1; x <= n; x++) {
				left[x]++;
			}
			for (int k = j + 1; k < n - 1; k++) {
				if (nums[j] > nums[k]) {
					ans += left[nums[k]] * right[k][nums[j]];
				}
			}
		}
		return ans;
	}

	// 非常强的思路和实现
	public static long countQuadruplets2(int[] nums) {
		int n = nums.length;
		long ans = 0;
		// dp[j]含义 :
		// 目前假设刚来到l位置，那么在l之前的范围上
		// 位置 : 0....i....j....k....l-1
		// 如果j做中间点，请问有多少三元组满足 : arr[i] < arr[k] < arr[j]
		// 就是dp[j]的含义
		long[] dp = new long[n];
		// i, j, k, l
		// [i] < [k] < [j] < [l]
		for (int l = 1; l < n; l++) {
			// 0...j-1范围上，比arr[l]小的个数
			int cnt = 0;
			for (int j = 0; j < l; j++) {
				if (nums[j] < nums[l]) {
					ans += dp[j];
					cnt++;
				} else {
					dp[j] += cnt;
				}
			}
		}
		return ans;
	}

}

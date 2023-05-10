package class_2023_05_2_week;

import java.util.Arrays;

// 给定一个整数数组  nums 和一个正整数 k，
// 找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
// 测试链接 : https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/
public class Code04_PartitionToKEqualSumSubsets {

	// 状态压缩的方法，正统，处理不了大长度
	// 其实任何方法都处理不了
	public static boolean canPartitionKSubsets1(int[] nums, int k) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		if (sum % k != 0) {
			return false;
		}
		return process1(nums, 0, 0, 0, sum / k, k, new int[1 << nums.length]) == 1;
	}

	public static int process1(int[] nums, int status, int sum, int sets, int limit, int k, int[] dp) {
		if (dp[status] != 0) {
			return dp[status];
		}
		int ans = -1;
		if (sets == k) {
			ans = 1;
		} else {
			for (int i = 0; i < nums.length; i++) {
				if ((status & (1 << i)) == 0 && sum + nums[i] <= limit) {
					if (sum + nums[i] == limit) {
						ans = process1(nums, status | (1 << i), 0, sets + 1, limit, k, dp);
					} else {
						ans = process1(nums, status | (1 << i), sum + nums[i], sets, limit, k, dp);
					}
					if (ans == 1) {
						break;
					}
				}
			}
		}
		dp[status] = ans;
		return ans;
	}

	public static boolean canPartitionKSubsets2(int[] nums, int k) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		if (sum % k != 0) {
			return false;
		}
		Arrays.sort(nums);
		return partitionK(new int[k], sum / k, nums, nums.length - 1);
	}

	// 100 10 -> 10
	// 100 2 -> 50
	// 100 4 -> 25
	// 当前的数字，nums[index]
	// nums[n-1] 给哪个桶
	// nums[n-2] 给哪个桶
	// ...
	// nums[0] 给哪个桶
	public static boolean partitionK(int[] group, int target, int[] nums, int index) {
		if (index < 0) {
			return true;
		}
		int num = nums[index];
		// len是桶的数量! 就是k
		int len = group.length;
		for (int i = 0; i < len; i++) {
			if (group[i] + num <= target) {
				// 放入后，不超
				group[i] += num;
				if (partitionK(group, target, nums, index - 1)) {
					return true;
				}
				group[i] -= num;
//				if (group[i] == 0) {
//					return false;
//				}
				while (i + 1 < group.length && group[i] == group[i + 1]) {
					i++;
				}
			}
		}
		return false;
	}

}

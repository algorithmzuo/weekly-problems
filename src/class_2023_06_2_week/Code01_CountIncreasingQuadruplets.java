package class_2023_06_2_week;

// 给你一个长度为 n 下标从 0 开始的整数数组 nums
// 它包含 1 到 n 的所有数字，请你返回上升四元组的数目。
// 如果一个四元组 (i, j, k, l) 满足以下条件，我们称它是上升的：
// 0 <= i < j < k < l < n 且
// nums[i] < nums[k] < nums[j] < nums[l] 。
// 测试链接 : https://leetcode.cn/problems/count-increasing-quadruplets/
public class Code01_CountIncreasingQuadruplets {

	// 非常强的思路和实现
	public static long countQuadruplets1(int[] nums) {
		int n = nums.length;
		long ans = 0;
		// dp[j]含义 :
		//    ............................l
		// 目前假设刚来到l位置，那么在l之前的范围上
		// 位置 : 0....i....j....k....l-1
		// 如果j做中间点，请问有多少三元组满足 : arr[i] < arr[k] < arr[j]
		// 就是 : 小 大(j位置的数) 中
		// 这种三元组的数量，就是dp[j]的含义
		long[] dp = new long[n];
		for (int l = 1; l < n; l++) {
			
			//          5           9
			//          j           l
			// 0 1 2 3 4 5 6 ....l-1
			for (int j = 0; j < l; j++) {
				if (nums[j] < nums[l]) {
					ans += dp[j];
				}
			}
			// dp[0...l-1]上的所有信息，有效的范围 : 0 .... l-1
			// dp[0...l-1]，扩充有效范围: 0........l
			// 目前比[l]数小的数的个数
			int cnt = 0;
			for (int j = 0; j < l; j++) {
				if (nums[j] < nums[l]) {
					cnt++;
				} else {
					dp[j] += cnt;
				}
			}
		}
		return ans;
	}

	// 非常强的思路和实现，彻底看不懂版，其实就是上面的代码做了逻辑合并
	public static long countQuadruplets2(int[] nums) {
		int n = nums.length;
		long ans = 0;
		long[] dp = new long[n];
		for (int l = 1; l < n; l++) {
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

package class_2023_02_1_week;

// 给定一个二进制数组 nums 和一个整数 k
// k位翻转 就是从 nums 中选择一个长度为 k 的 子数组
// 同时把子数组中的每一个 0 都改成 1 ，把子数组中的每一个 1 都改成 0
// 返回数组中不存在 0 所需的最小 k位翻转 次数。如果不可能，则返回 -1
// 子数组 是数组的 连续 部分。
// 测试链接 : https://leetcode.cn/problems/minimum-number-of-k-consecutive-bit-flips/
public class Code03_MinimumNumberOfKConsecutiveBitFlips {

	public int minKBitFlips(int[] nums, int k) {
		int n = nums.length;
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (l != r && i - queue[l] == k) {
				l++;
			}
			if (((r - l) & 1) == nums[i]) {
				queue[r++] = i;
				ans++;
			}
		}
		return (l != r && queue[r - 1] + k > n) ? -1 : ans;
	}

}

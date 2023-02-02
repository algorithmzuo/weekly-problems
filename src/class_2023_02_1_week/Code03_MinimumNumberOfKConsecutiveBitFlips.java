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
			// 双端队列有东西，l、r           l    i
			if (l != r && i - queue[l] == k) {
				// 意味着，双端队列大小变了！
				l++;
			}
			// r - l 是双端队列的大小
			// (r - l) & 1 == 1，
			// 说明队列大小是奇数(尾部状态为0)，那么nums[i] == 1该加入(因为和尾部不同)
			// (r - l) & 1 == 0，
			// 说明队列大小是偶数(尾部状态为1)，那么nums[i] == 0该加入(因为和尾部不同)
			// 所以综上，((r - l) & 1) == nums[i]，该加入
			if (((r - l) & 1) == nums[i]) {
				queue[r++] = i;
				ans++;
			}
		}
		// 最后的反转点长度够k，能翻转；否则不能
		return (l != r && queue[r - 1] + k > n) ? -1 : ans;
	}

}

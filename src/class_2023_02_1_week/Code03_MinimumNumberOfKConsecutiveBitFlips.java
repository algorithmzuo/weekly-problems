package class_2023_02_1_week;

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

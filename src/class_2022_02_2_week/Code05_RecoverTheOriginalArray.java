package class_2022_02_2_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.com/problems/recover-the-original-array/
public class Code05_RecoverTheOriginalArray {

	public static int[] recoverArray(int[] nums) {
		Arrays.sort(nums);
		int n = nums.length;
		// nums[0] -> 小数组的第0个
		int m = n >> 1;
		// 谁是大数组的第0个？不知道，试！first位置的数！
		for (int first = 1; first <= m; first++) {
			// d = 2 * k; k正数！
			int d = nums[first] - nums[0];
			if (d > 0 && (d & 1) == 0) {
				// 试图生成原始数组！ans！
				int[] ans = new int[m];
				int i = 0;
				boolean[] set = new boolean[n];
				int k = d >> 1;
				int l = 0;
				int r = first;
				while (r < n) {
					while (set[l]) {
						l++;
					}
					if (l == r) {
						r++;
					} else if (nums[r] - nums[l] > d) {
						break;
					} else if (nums[r] - nums[l] < d) {
						r++;
					} else {
						set[r++] = true;
						ans[i++] = nums[l++] + k;
					}
				}
				if (i == m) {
					return ans;
				}
			}
		}
		return null;
	}

}

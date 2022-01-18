package class_2022_02_2_week;

import java.util.Arrays;

public class Problem_2122_RecoverTheOriginalArray {

	public static int[] recoverArray(int[] nums) {
		Arrays.sort(nums);
		int n = nums.length;
		int m = n >> 1;
		boolean[] set = new boolean[n];
		for (int first = 1; first <= m; first++) {
			int d = nums[first] - nums[0];
			if (d > 0 && (d & 1) == 0) {
				int[] ans = new int[m];
				int i = 0;
				Arrays.fill(set, false);
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

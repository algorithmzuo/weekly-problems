package class_2022_11_3_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/sort-array-by-moving-items-to-empty-space/
public class Code03_SortArrayByMovingItemsToEmptySpace {

	public static int sortArray(int[] nums) {
		int n = nums.length, ans1 = 0, ans2 = 0, m, next;
		boolean[] touched = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!touched[i]) {
				touched[i] = true;
				m = 1;
				next = nums[i];
				while (next != i) {
					m++;
					touched[next] = true;
					next = nums[next];
				}
				if (m > 1) {
					ans1 += i == 0 ? (m - 1) : (m + 1);
				}
			}
		}
		Arrays.fill(touched, false);
		for (int i = n - 1; i >= 0; i--) {
			if (!touched[i]) {
				touched[i] = true;
				m = 1;
				next = nums[i] == 0 ? (n - 1) : (nums[i] - 1);
				while (next != i) {
					m++;
					touched[next] = true;
					next = nums[next] == 0 ? (n - 1) : (nums[next] - 1);
				}
				if (m > 1) {
					ans2 += i == n - 1 ? (m - 1) : (m + 1);
				}
			}
		}
		return Math.min(ans1, ans2);
	}

}

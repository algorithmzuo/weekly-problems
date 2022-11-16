package class_2022_11_3_week;

import java.util.Arrays;

// 来自谷歌
// 给定一个长度为N的数组，值一定在0~N-1范围，且每个值不重复
// 比如，arr = [4, 2, 0, 3, 1]
// 把0想象成洞，任何非0数字都可以来到这个洞里，然后在原本的位置留下洞
// 比如4这个数字，来到0所代表的洞里，那么数组变成 : 
// arr = [0, 2, 4, 3, 1]
// 也就是原来的洞被4填满，4走后留下了洞
// 任何数字只能搬家到洞里，并且走后留下洞
// 通过搬家的方式，想变成有序的，有序有两种形式
// 比如arr = [4, 2, 0, 3, 1]，变成
// [0, 1, 2, 3, 4]或者[1, 2, 3, 4, 0]都叫有序
// 返回变成任何一种有序的情况都可以，最少的数字搬动次数
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

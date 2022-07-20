package class_2022_07_3_week;

import java.util.Arrays;

// 一个整数区间 [a, b]  ( a < b ) 代表着从 a 到 b 的所有连续整数，包括 a 和 b。
// 给你一组整数区间intervals，请找到一个最小的集合 S，
// 使得 S 里的元素与区间intervals中的每一个整数区间都至少有2个元素相交。
// 输出这个最小集合S的大小。
// 测试链接 : https://leetcode.cn/problems/set-intersection-size-at-least-two/
public class Code01_SetIntersectionSizeAtLeastTwo {

	public static int intersectionSizeTwo(int[][] intervals) {
		// O(N*logN)
		// 区间根据，结束位置谁小，谁在前
		// 结束位置一样的，开头位置谁大，谁在前
		Arrays.sort(intervals, (a, b) -> a[1] != b[1] ? (a[1] - b[1]) : (b[0] - a[0]));
		// 区间排好序了
		// [1,7] [2,8] [1,8] [13,40]
		int n = intervals.length;
		// [1,7] pre = 6 pos =7
		int pos = intervals[0][1];
		int pre = pos - 1;
		int ans = 2;
		for (int i = 1; i < n; i++) {
			// intervals[i] = {开头，结尾}
			// 6 7 [<=6, 结尾]
			//
			//			if(intervals[i][0] <= pre) {
			//				continue;
			//			}
			// >6 讨论！
			if (intervals[i][0] > pre) {
				// 6 7 [开头>6, 结尾]
				// 1) 6 < 开头 <= 7
				// 只有7满足了当前的区间，我们要加个数字，结尾
				// 6 7   结尾
				//   pre pos
				// 6 7
				// 2) 6 < 开头、7 < 开头
				// 结尾-1  结尾
				//  pre   pos
				if (intervals[i][0] > pos) { // 对应的就是情况2)
					pre = intervals[i][1] - 1;
					ans += 2;
				} else { // 对应的就是情况1)
					pre = pos;
					ans += 1;
				}
				//  不管情况2)还是情况1)都需要这一句
				pos = intervals[i][1];
			}

		}
		return ans;
	}

}

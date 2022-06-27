package class_2022_07_1_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/set-intersection-size-at-least-two/
public class Code02_SetIntersectionSizeAtLeastTwo {

	public static int intersectionSizeTwo(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[1] != b[1] ? (a[1] - b[1]) : (b[0] - a[0]));
		int n = intervals.length;
		int pos = intervals[0][1];
		int pre = pos - 1;
		int ans = 2;
		for (int i = 1; i < n; i++) {
			if (intervals[i][0] > pre) {
				if (intervals[i][0] > pos) {
					pre = intervals[i][1] - 1;
					ans += 2;
				} else {
					pre = pos;
					ans += 1;
				}
				pos = intervals[i][1];
			}
		}
		return ans;
	}

}

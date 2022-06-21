package class_2022_06_4_week;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// 测试链接 : https://leetcode.cn/problems/employee-free-time/
public class Code02_EmployeeFreeTime {

	// 不要提交这个类
	public static class Interval {
		public int start;
		public int end;

		public Interval(int s, int e) {
			start = s;
			end = e;
		}
	}

	// 提交以下的code
	public static List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
		ArrayList<int[]> arr = new ArrayList<>();
		for (List<Interval> people : schedule) {
			for (Interval interval : people) {
				arr.add(new int[] { interval.start, interval.end, 0 });
				arr.add(new int[] { interval.end, interval.end, 1 });
			}
		}
		arr.sort((a, b) -> a[0] - b[0]);
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(arr.get(0)[1]);
		List<Interval> ans = new ArrayList<>();
		for (int i = 1; i < arr.size(); i++) {
			int[] cur = arr.get(i);
			if (cur[2] == 0) {
				if (set.isEmpty() && arr.get(i - 1)[0] != cur[0]) {
					ans.add(new Interval(arr.get(i - 1)[0], cur[0]));
				}
				set.add(cur[1]);
			} else {
				set.remove(cur[0]);
			}
		}
		return ans;
	}

}

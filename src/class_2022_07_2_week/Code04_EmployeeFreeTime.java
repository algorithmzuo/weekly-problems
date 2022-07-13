package class_2022_07_2_week;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// 给定员工的 schedule 列表，表示每个员工的工作时间。
// 每个员工都有一个非重叠的时间段  Intervals 列表，这些时间段已经排好序。
// 返回表示 所有 员工的 共同，正数长度的空闲时间 的有限时间段的列表，同样需要排好序。
// 测试链接 : https://leetcode.cn/problems/employee-free-time/
public class Code04_EmployeeFreeTime {

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
	// 哈希表！
	public static List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
		ArrayList<int[]> arr = new ArrayList<>();
		for (List<Interval> people : schedule) {
			for (Interval interval : people) {
				// 0 开始时间点，有个员工要上线
				// 1 结束时间点，有个员工要下线
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
				// 开始时间点来到的时候，来看看有没有空闲时间段
				//  3  7
				if (set.isEmpty() && arr.get(i - 1)[0] != cur[0]) {
					ans.add(new Interval(arr.get(i - 1)[0], cur[0]));
				}
				// 哈希表填人了，cur[1]
				set.add(cur[1]);
			} else {
				set.remove(cur[0]);
			}
		}
		return ans;
	}

}

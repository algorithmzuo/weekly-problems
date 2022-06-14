package class_2022_06_3_week;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// 测试链接 : https://leetcode.cn/problems/employee-free-time/
public class Code01_EmployeeFreeTime {

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
	public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
		TreeMap<Integer, Integer> m = new TreeMap<>();
		for (List<Interval> list : schedule) {
			for (Interval interval : list) {
				m.putIfAbsent(interval.start, 0);
				m.putIfAbsent(interval.end, 0);
				m.put(interval.start, m.get(interval.start) + 1);
				m.put(interval.end, m.get(interval.end) - 1);
			}
		}
		int cur = 0;
		int start = -1;
		List<Interval> ans = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
			cur += entry.getValue();
			if (cur == 0 && start == -1) {
				start = entry.getKey();
			} else {
				if (cur != 0 && start != -1) {
					ans.add(new Interval(start, entry.getKey()));
					start = -1;
				}
			}
		}
		return ans;
	}

}

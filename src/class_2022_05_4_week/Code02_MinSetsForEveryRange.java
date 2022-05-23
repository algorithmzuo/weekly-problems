package class_2022_05_4_week;

import java.util.Arrays;
import java.util.HashSet;

public class Code02_MinSetsForEveryRange {

	public static int minSets(int[][] ranges) {
		int n = ranges.length;
		// events[i] = {a, b, c}
		// a == 0, 表示这是一个区间的开始事件，这个区间结束位置是b
		// a == 1, 表示这是一个区间的结束事件，b的值没有意义
		// c表示这个事件的时间点，不管是开始事件还是结束事件，都会有c这个值
		int[][] events = new int[n << 1][3];
		for (int i = 0; i < n; i++) {
			events[i][0] = 0;
			events[i][1] = ranges[i][1];
			events[i][2] = ranges[i][0];
			events[i + n][0] = 1;
			events[i + n][2] = ranges[i][1];
		}
		Arrays.sort(events, (a, b) -> a[2] - b[2]);
		// 容器
		HashSet<Integer> set = new HashSet<>();
		int ans = 0;
		for (int[] event : events) {
			if (event[0] == 0) {
				set.add(event[1]);
			} else {
				if (set.contains(event[2])) {
					ans++;
					set.clear();
				}
			}
		}
		return ans;
	}

}

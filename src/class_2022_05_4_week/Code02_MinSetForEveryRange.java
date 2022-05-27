package class_2022_05_4_week;

import java.util.Arrays;
import java.util.HashSet;

// 给定区间的范围[xi,yi]，xi<=yi，且都是正整数
// 找出一个坐标集合set，set中有若干个数字
// set要和每个给定的区间，有交集
// 求set的最少需要几个数
// 比如给定区间 : [5, 8] [1, 7] [2, 4] [1, 9]
// set最小可以是: {2, 6}或者{2, 5}或者{4, 5}
public class Code02_MinSetForEveryRange {

	public static int minSet(int[][] ranges) {
		int n = ranges.length;
		// events[i] = {a, b, c}
		// a == 0, 表示这是一个区间的开始事件，这个区间结束位置是b
		// a == 1, 表示这是一个区间的结束事件，b的值没有意义
		// c表示这个事件的时间点，不管是开始事件还是结束事件，都会有c这个值
		int[][] events = new int[n << 1][3];
		for (int i = 0; i < n; i++) {
			// [3, 7]
			// (0,7,3)
			// (1,X,7)
			events[i][0] = 0;
			events[i][1] = ranges[i][1];
			events[i][2] = ranges[i][0];
			events[i + n][0] = 1;
			events[i + n][2] = ranges[i][1];
		}
		Arrays.sort(events, (a, b) -> a[2] - b[2]);
		// 容器
		HashSet<Integer> tmp = new HashSet<>();
		int ans = 0;
		for (int[] event : events) {
			if (event[0] == 0) {
				tmp.add(event[1]);
			} else {
				if (tmp.contains(event[2])) {
					ans++;
					tmp.clear();
				}
			}
		}
		return ans;
	}

}

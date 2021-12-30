package class_2021_12_5_week;

import java.util.Arrays;
import java.util.PriorityQueue;

// 来自华为
// 每个面试者有面试开始时间和结束时间
// 一个面试官一次只能面一个人
// 每个面试官有面试场次的数量限制
// 求几个面试官够用
public class Code03_InterviewManagerNumbers {

	public static int needManagers(int[][] times, int limit) {
		if (limit == 1) {
			return times.length;
		}
		// times[i][0] : i号面试的开始时间
		// times[i][1] : i号面试的结束时间
		Arrays.sort(times, (a, b) -> a[0] - b[0]);
		// heap中的数组[a,b]表示：该面试官a时间点醒来，已经参加了b场面试
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		int all = 1;
		heap.add(new int[] { 0, 0 });
		for (int[] one : times) {
			int start = one[0];
			int end = one[1];
			if (heap.isEmpty() || heap.peek()[0] > start) { // 增加人的逻辑
				heap.add(new int[] { end, 1 });
				all++;
			} else {
				int[] manager = heap.poll();
				if (manager[1] < limit - 1) {
					manager[0] = end;
					manager[1]++;
					heap.add(manager);
				}
			}
		}
		return all;
	}

}

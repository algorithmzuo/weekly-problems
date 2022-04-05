package class_2022_04_2_week;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// 来自美团
// 3.36笔试
// 给定一个正数n, 表示有0~n-1号任务
// 给定一个长度为n的数组time，time[i]表示i号任务做完的时间
// 给定一个二维数组matrix
// matrix[j] = {a, b} 代表：a任务想要开始，依赖b任务的完成
// 只要能并行的任务都可以并行，但是任何任务只有依赖的任务完成，才能开始
// 返回一个长度为n的数组ans，表示每个任务完成的时间
// 输入可以保证没有循环依赖
public class Code06_AllJobFinishTime {

	public static int[] finishTime(int n, int[] time, int[][] matrix) {
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			nexts.add(new ArrayList<>());
		}
		int[] in = new int[n];
		for (int[] line : matrix) {
			nexts.get(line[1]).add(line[0]);
			in[line[0]]++;
		}
		Queue<Integer> zeroInQueue = new LinkedList<>();
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			if (in[i] == 0) {
				zeroInQueue.add(i);
			}
		}
		while (!zeroInQueue.isEmpty()) {
			int cur = zeroInQueue.poll();
			ans[cur] += time[cur];
			for (int next : nexts.get(cur)) {
				ans[next] = Math.max(ans[next], ans[cur]);
				if (--in[next] == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return ans;
	}

}

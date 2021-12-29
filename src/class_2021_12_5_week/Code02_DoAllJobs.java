package class_2021_12_5_week;

import java.util.Arrays;
import java.util.PriorityQueue;

// 来自hulu
// 有n个人，m个任务，任务之间有依赖记录在int[][] depends里
// 比如: depends[i] = [a, b]，表示a任务依赖b任务的完成
// 其中 0 <= a < m，0 <= b < m
// 1个人1天可以完成1个任务，每个人都会选当前能做任务里，标号最小的任务
// 一个任务所依赖的任务都完成了，该任务才能开始做
// 返回n个人做完m个任务，需要几天
public class Code02_DoAllJobs {

	public static int days(int n, int m, int[][] depends) {
		if (n < 1) {
			return -1;
		}
		if (m <= 0) {
			return 0;
		}
		int[][] nexts = nexts(depends, m);
		int[] indegree = indegree(nexts, m);
		PriorityQueue<Integer> workers = new PriorityQueue<>();
		for (int i = 0; i < n; i++) {
			workers.add(0);
		}
		PriorityQueue<Integer> zeroIn = new PriorityQueue<>();
		for (int i = 0; i < m; i++) {
			if (indegree[i] == 0) {
				zeroIn.add(i);
			}
		}
		int[] start = new int[m];
		int finishAll = 0;
		int done = 0;
		while (!zeroIn.isEmpty()) {
			int job = zeroIn.poll();
			int wake = workers.poll();
			int finish = Math.max(start[job], wake) + 1;
			finishAll = Math.max(finishAll, finish);
			done++;
			for (int next : nexts[job]) {
				start[next] = Math.max(start[next], finish);
				if (--indegree[next] == 0) {
					zeroIn.add(next);
				}
			}
			workers.add(finish);
		}
		return done == m ? finishAll : -1;
	}

	public static int[][] nexts(int[][] depends, int m) {
		Arrays.sort(depends, (a, b) -> a[1] - b[1]);
		int n = depends.length;
		int[][] nexts = new int[m][0];
		if (n == 0) {
			return nexts;
		}
		int size = 1;
		for (int i = 1; i < n; i++) {
			if (depends[i - 1][1] != depends[i][1]) {
				int from = depends[i - 1][1];
				nexts[from] = new int[size];
				for (int k = 0, j = i - size; k < size; k++, j++) {
					nexts[from][k] = depends[j][0];
				}
				size = 1;
			} else {
				size++;
			}
		}
		int from = depends[n - 1][1];
		nexts[from] = new int[size];
		for (int k = 0, j = n - size; k < size; k++, j++) {
			nexts[from][k] = depends[j][0];
		}
		return nexts;
	}

	public static int[] indegree(int[][] nexts, int m) {
		int[] indegree = new int[m];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < nexts[i].length; j++) {
				indegree[nexts[i][j]]++;
			}
		}
		return indegree;
	}

	public static void main(String[] args) {
		// 2 -> 5 -> 6
		//           |
		//           v
		// 1 -> 4 -> 7
		//      ^
		//      |
		// 0 -> 3
		int[][] d = {
				{ 3, 0 },
				{ 4, 1 },
				{ 5, 2 },
				{ 4, 3 },
				{ 6, 5 },
				{ 7, 4 },
				{ 7, 6 }
			};
		System.out.println(days(3, 8, d));
		System.out.println(days(2, 8, d));
	}

}

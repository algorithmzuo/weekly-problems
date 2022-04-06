package class_2022_04_2_week;

import java.util.PriorityQueue;

// 来自网易
// 3.27笔试
// 一个二维矩阵，上面只有 0 和 1，只能上下左右移动
// 如果移动前后的元素值相同，则耗费 1 ，否则耗费 2。
// 问从左上到右下的最小耗费
public class Code04_MinDistanceFromLeftUpToRightDown {

	public static int bestWalk1(int[][] map) {
		int n = map.length;
		int m = map[0].length;
		int limit = n * m - 1;
		return process(map, n, m, limit, 0, 0, 0, map[0][0]) - 1;
	}

	public static int process(int[][] map, int n, int m, int limit, int step, int row, int col, int pre) {
		if (row < 0 || row == n || col < 0 || col == m || step > limit) {
			return Integer.MAX_VALUE;
		}
		int cost = pre == map[row][col] ? 1 : 2;
		if (row == n - 1 && col == m - 1) {
			return cost;
		}
		int p1 = process(map, n, m, limit, step + 1, row - 1, col, map[row][col]);
		int p2 = process(map, n, m, limit, step + 1, row + 1, col, map[row][col]);
		int p3 = process(map, n, m, limit, step + 1, row, col - 1, map[row][col]);
		int p4 = process(map, n, m, limit, step + 1, row, col + 1, map[row][col]);
		int next = Math.min(Math.min(p1, p2), Math.min(p3, p4));
		return next != Integer.MAX_VALUE ? (cost + next) : Integer.MAX_VALUE;
	}

	public static int bestWalk2(int[][] map) {
		int n = map.length;
		int m = map[0].length;
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		boolean[][] poped = new boolean[n][m];
		heap.add(new int[] { 0, 0, 0 });
		int ans = 0;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int dis = cur[0];
			int row = cur[1];
			int col = cur[2];
			if (poped[row][col]) {
				continue;
			}
			poped[row][col] = true;
			if (row == n - 1 && col == m - 1) {
				ans = dis;
				break;
			}
			add(dis, row - 1, col, map[row][col], n, m, map, poped, heap);
			add(dis, row + 1, col, map[row][col], n, m, map, poped, heap);
			add(dis, row, col - 1, map[row][col], n, m, map, poped, heap);
			add(dis, row, col + 1, map[row][col], n, m, map, poped, heap);
		}
		return ans;
	}

	public static void add(int preDistance, int row, int col, int preValue, int n, int m, int[][] map, boolean[][] used,
			PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && !used[row][col]) {
			heap.add(new int[] { preDistance + (map[row][col] == preValue ? 1 : 2), row, col });
		}
	}

	// 为了测试
	public static int[][] randomMatrix(int n, int m, int v) {
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans[i][j] = (int) (Math.random() * 2);
			}
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 4;
		int m = 4;
		int v = 10;
		int testTime = 10;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int[][] map = randomMatrix(n, m, v);
			int ans1 = bestWalk1(map);
			int ans2 = bestWalk2(map);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		n = 1000;
		m = 1000;
		v = 100;
		int[][] map = randomMatrix(n, m, v);
		System.out.println("性能测试开始");
		System.out.println("数据规模 : " + n + " * " + m);
		System.out.println("值的范围 : 0 ~ " + v);
		long start = System.currentTimeMillis();
		bestWalk2(map);
		long end = System.currentTimeMillis();
		System.out.println("运行时间(毫秒) : " + (end - start));
		System.out.println("性能测试结束");
	}

}

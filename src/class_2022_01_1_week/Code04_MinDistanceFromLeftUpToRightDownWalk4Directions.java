package class_2022_01_1_week;

import java.util.PriorityQueue;

// 来自学员问题
// 给定一个二维数组，其中全是非负数
// 每一步都可以往上、下、左、右四个方向运动
// 走过的路径，会沿途累加数字
// 返回从左下角走到右下角的累加和最小的多少
public class Code04_MinDistanceFromLeftUpToRightDownWalk4Directions {

	public static int bestWalk1(int[][] map) {
		int n = map.length;
		int m = map[0].length;
		int step = n * m - 1;
		return process(map, n, m, step, 0, 0, 0, 0);
	}

	public static int process(int[][] map, int n, int m, int limit, int step, int row, int col, int cost) {
		if (row < 0 || row == n || col < 0 || col == m || step > limit) {
			return Integer.MAX_VALUE;
		}
		if (row == n - 1 && col == m - 1) {
			return cost + map[row][col];
		}
		cost += map[row][col];
		int p1 = process(map, n, m, limit, step + 1, row - 1, col, cost);
		int p2 = process(map, n, m, limit, step + 1, row + 1, col, cost);
		int p3 = process(map, n, m, limit, step + 1, row, col - 1, cost);
		int p4 = process(map, n, m, limit, step + 1, row, col + 1, cost);
		return Math.min(Math.min(p1, p2), Math.min(p3, p4));
	}

	public static int bestWalk2(int[][] map) {
		
		int n = map.length;
		int m = map[0].length;
		// 堆
		// 每一个对象，都是一个小数组
		// {dis, row, col}
		//  0    1    2
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		// X,0,1 已经弹出了！ 以后在遇到(0,1)的事情，不管！
		// poped记录哪些位置弹出，哪些没有！
		boolean[][] poped = new boolean[n][m];
		heap.add(new int[] { map[0][0], 0, 0 });
		int ans = 0;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int dis = cur[0];
			int row = cur[1];
			int col = cur[2];
			if (poped[row][col]) {
				continue;
			}
			// 接下来就是要处理这个位置了！
			poped[row][col] = true;
			if (row == n - 1 && col == m - 1) {
				ans = dis;
				break;
			}
			add(dis, row - 1, col, n, m, map, poped, heap);
			add(dis, row + 1, col, n, m, map, poped, heap);
			add(dis, row, col - 1, n, m, map, poped, heap);
			add(dis, row, col + 1, n, m, map, poped, heap);
		}
		return ans;
	}

	public static void add(int pre, int row, int col, int n, int m, int[][] map, boolean[][] used,
			PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && !used[row][col]) {
			heap.add(new int[] { pre + map[row][col], row, col });
		}
	}

	// 为了测试
	public static int[][] randomMatrix(int n, int m, int v) {
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans[i][j] = (int) (Math.random() * v);
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

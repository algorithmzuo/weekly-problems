package class_2022_01_3_week;

import java.util.PriorityQueue;

// A*算法
// 过程和Dijskra高度相处
// 有到终点的预估函数
// 只要预估值<=客观上最优距离，就是对的
// 预估函数是一种吸引力：
// 1）合适的吸引力可以提升算法的速度
// 2）吸引力“过强”会出现错误
// 讲述A*算法
// 预估终点距离选择曼哈顿距离
// 要求先在体系学习班图的章节听过"Dijkstra算法"
public class Code01_AStarAlgorithm {

	// Dijkstra算法
	// map[i][j] == 0 代表障碍
	// map[i][j] > 0 代表通行代价
	public static int minDistance1(int[][] map, int startX, int startY, int targetX, int targetY) {
		if (map[startX][startY] == 0 || map[targetX][targetY] == 0) {
			return Integer.MAX_VALUE;
		}
		int n = map.length;
		int m = map[0].length;
		// heap小根堆
		// [20,1,7]
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		// 1,7已经处理过了，closed[1][7] = true
		boolean[][] closed = new boolean[n][m];
		heap.add(new int[] { map[startX][startY], startX, startY });
		int ans = Integer.MAX_VALUE;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int dis = cur[0];
			int row = cur[1];
			int col = cur[2];
			if (closed[row][col]) {
				continue;
			}
			closed[row][col] = true;
			if (row == targetX && col == targetY) {
				ans = dis;
				break;
			}
			add1(dis, row - 1, col, n, m, map, closed, heap);
			add1(dis, row + 1, col, n, m, map, closed, heap);
			add1(dis, row, col - 1, n, m, map, closed, heap);
			add1(dis, row, col + 1, n, m, map, closed, heap);
		}
		return ans;
	}

	public static void add1(int pre, int row, int col, int n, int m, int[][] map, boolean[][] closed,
			PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && map[row][col] != 0 && !closed[row][col]) {
			heap.add(new int[] { pre + map[row][col], row, col });
		}
	}

	// A*算法
	// map[i][j] == 0 代表障碍
	// map[i][j] > 0 代表通行代价
	public static int minDistance2(int[][] map, int startX, int startY, int targetX, int targetY) {
		if (map[startX][startY] == 0 || map[targetX][targetY] == 0) {
			return Integer.MAX_VALUE;
		}
		int n = map.length;
		int m = map[0].length;
		// [20,1,7]
		// [20,?,1,7]
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[0] + a[1]) - (b[0] + b[1]));
		boolean[][] closed = new boolean[n][m];
		heap.add(new int[] { map[startX][startY], distance(startX, startY, targetX, targetY), startX, startY });
		int ans = Integer.MAX_VALUE;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int fromDistance = cur[0];
			int row = cur[2];
			int col = cur[3];
			if (closed[row][col]) {
				continue;
			}
			closed[row][col] = true;
			if (row == targetX && col == targetY) {
				ans = fromDistance;
				break;
			}
			add2(fromDistance, row - 1, col, targetX, targetY, n, m, map, closed, heap);
			add2(fromDistance, row + 1, col, targetX, targetY, n, m, map, closed, heap);
			add2(fromDistance, row, col - 1, targetX, targetY, n, m, map, closed, heap);
			add2(fromDistance, row, col + 1, targetX, targetY, n, m, map, closed, heap);
		}
		return ans;
	}

	public static void add2(int pre, int row, int col, int targetX, int targetY, int n, int m, int[][] map,
			boolean[][] closed, PriorityQueue<int[]> heap) {
		if (row >= 0 && row < n && col >= 0 && col < m && map[row][col] != 0 && !closed[row][col]) {
			heap.add(new int[] { pre + map[row][col], distance(row, col, targetX, targetY), row, col });
		}

	}

	// 曼哈顿距离
	public static int distance(int curX, int curY, int targetX, int targetY) {
		return Math.abs(targetX - curX) + Math.abs(targetY - curY);
	}

	// 为了测试
	// map[i][j] == 0 代表障碍
	// map[i][j] > 0 代表通行代价
	public static int[][] randomMap(int len, int value) {
		int[][] ans = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (Math.random() < 0.2) {
					ans[i][j] = 0;
				} else {
					ans[i][j] = (int) (Math.random() * value);
				}
			}
		}
		return ans;
	}

	// 为了测试
	public static void printMap(int[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print((map[i][j] == 0 ? "X" : map[i][j]) + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int len = 100;
		int value = 50;
		int testTime = 10000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 2;
			int[][] map = randomMap(n, value);
			int startX = (int) (Math.random() * n);
			int startY = (int) (Math.random() * n);
			int targetX = (int) (Math.random() * n);
			int targetY = (int) (Math.random() * n);
			int ans1 = minDistance1(map, startX, startY, targetX, targetY);
			int ans2 = minDistance2(map, startX, startY, targetX, targetY);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				printMap(map);
				System.out.println(startX + "," + startY);
				System.out.println(targetX + "," + targetY);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");

		int[][] map = new int[4000][4000];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = (int) (Math.random() * 10);
			}
		}
		int startX = 0;
		int startY = 0;
		int targetX = 3456;
		int targetY = 3728;

		long start = System.currentTimeMillis();
		minDistance2(map, startX, startY, targetX, targetY);
		long end = System.currentTimeMillis();
		System.out.println("运行时间(毫秒) : " + (end - start));

//		System.out.println("性能测试开始");
//		int n = 10000;
//		int v = 500;
//		int[][] map = randomMap(n, v);
//		int startX = 0;
//		int startY = 0;
//		int targetX = n - 1;
//		int targetY = n - 1;
//		System.out.println("数据规模 : " + n + " * " + n);
//		long start = System.currentTimeMillis();
//		minDistance2(map, startX, startY, targetX, targetY);
//		long end = System.currentTimeMillis();
//		System.out.println("运行时间(毫秒) : " + (end - start));
//		System.out.println("性能测试结束");
	}

}

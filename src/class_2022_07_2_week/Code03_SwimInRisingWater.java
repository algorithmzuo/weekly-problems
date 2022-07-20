package class_2022_07_2_week;

import java.util.Arrays;
import java.util.PriorityQueue;

// 在一个 n x n 的整数矩阵 grid 中，
// 每一个方格的值 grid[i][j] 表示位置 (i, j) 的平台高度。
// 当开始下雨时，在时间为 t 时，水池中的水位为 t 。
// 你可以从一个平台游向四周相邻的任意一个平台，但是前提是此时水位必须同时淹没这两个平台。
// 假定你可以瞬间移动无限距离，也就是默认在方格内部游动是不耗时的。
// 当然，在你游泳的时候你必须待在坐标方格里面。
// 你从坐标方格的左上平台 (0，0) 出发。
// 返回 你到达坐标方格的右下平台 (n-1, n-1) 所需的最少时间 。
// 测试链接 ：https://leetcode.cn/problems/swim-in-rising-water
public class Code03_SwimInRisingWater {

	// 并查集的解法
	public static int swimInWater1(int[][] grid) {
		// 行号
		int n = grid.length;
		// 列号
		int m = grid[0].length;
		// [0,0,5]
		// [0,1,3]....
		int[][] points = new int[n * m][3];
		int pi = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				points[pi][0] = i;
				points[pi][1] = j;
				points[pi++][2] = grid[i][j];
			}
		}
		// 所有格子小对象，生成好了!
		// 排序！[a,b,c]  [d,e,f]
		Arrays.sort(points, (a, b) -> a[2] - b[2]);
		// 生成并查集！n * m
		// 初始化的时候，把所有格子独自成一个集合！
		UnionFind uf = new UnionFind(n, m);
		int ans = 0;
		for (int i = 0; i < points.length; i++) {
			int r = points[i][0];
			int c = points[i][1];
			int v = points[i][2];
			if (r > 0 && grid[r - 1][c] <= v) {
				uf.union(r, c, r - 1, c);
			}
			if (r < n - 1 && grid[r + 1][c] <= v) {
				uf.union(r, c, r + 1, c);
			}
			if (c > 0 && grid[r][c - 1] <= v) {
				uf.union(r, c, r, c - 1);
			}
			if (c < m - 1 && grid[r][c + 1] <= v) {
				uf.union(r, c, r, c + 1);
			}
			if (uf.isSameSet(0, 0, n - 1, m - 1)) {
				ans = v;
				break;
			}
		}
		return ans;
	}

	public static class UnionFind {
		public int col;
		public int pointsSize;
		public int[] father;
		public int[] size;
		public int[] help;

		public UnionFind(int n, int m) {
			col = m;
			pointsSize = n * m;
			father = new int[pointsSize];
			size = new int[pointsSize];
			help = new int[pointsSize];
			for (int i = 0; i < pointsSize; i++) {
				father[i] = i;
				size[i] = 1;
			}
		}

		private int find(int i) {
			int hi = 0;
			while (i != father[i]) {
				help[hi++] = i;
				i = father[i];
			}
			while (hi > 0) {
				father[help[--hi]] = i;
			}
			return i;
		}

		private int index(int i, int j) {
			return i * col + j;
		}

		public void union(int row1, int col1, int row2, int col2) {
			int f1 = find(index(row1, col1));
			int f2 = find(index(row2, col2));
			if (f1 != f2) {
				if (size[f1] >= size[f2]) {
					father[f2] = f1;
					size[f1] += size[f2];
				} else {
					father[f1] = f2;
					size[f2] += size[f1];
				}
			}
		}

		public boolean isSameSet(int row1, int col1, int row2, int col2) {
			return find(index(row1, col1)) == find(index(row2, col2));
		}

	}

	// Dijkstra算法
	public static int swimInWater2(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		boolean[][] visited = new boolean[n][m];
		heap.add(new int[] { 0, 0, grid[0][0] });
		int ans = 0;
		while (!heap.isEmpty()) {
			int r = heap.peek()[0];
			int c = heap.peek()[1];
			int v = heap.peek()[2];
			heap.poll();
			if (visited[r][c]) {
				continue;
			}
			visited[r][c] = true;
			if (r == n - 1 && c == m - 1) {
				ans = v;
				break;
			}
			add(grid, heap, visited, r - 1, c, v);
			add(grid, heap, visited, r + 1, c, v);
			add(grid, heap, visited, r, c - 1, v);
			add(grid, heap, visited, r, c + 1, v);
		}
		return ans;
	}

	public static void add(int[][] grid, PriorityQueue<int[]> heap, boolean[][] visited, int r, int c, int preV) {
		if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && !visited[r][c]) {
			heap.add(new int[] { r, c, preV + Math.max(0, grid[r][c] - preV) });
		}
	}

}

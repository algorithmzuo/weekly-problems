package class_2022_12_4_week;

// 你现在手里有一份大小为 n x n 的 网格 grid
// 上面的每个 单元格 都用 0 和 1 标记好了其中 0 代表海洋，1 代表陆地。
// 请你找出一个海洋单元格，这个海洋单元格到离它最近的陆地单元格的距离是最大的
// 并返回该距离。如果网格上只有陆地或者海洋，请返回 -1。
// 我们这里说的距离是「曼哈顿距离」（ Manhattan Distance）：
// (x0, y0) 和 (x1, y1) 这两个单元格之间的距离是 |x0 - x1| + |y0 - y1| 。
// 测试链接 : https://leetcode.cn/problems/as-far-from-land-as-possible/
public class Code03_AsFarFromLandAsPossible {

	// 队列接受一个东西，比如(i,j)，就加到r位置
	// queue[r][0] = i
	// queue[r++][1] = j
	// 队列弹出一个东西，就把l位置的东西弹出
	public static int[][] queue = new int[10000][2];
	public static int l;
	public static int r;
	// 一个东西进入了队列，比如(i,j)进入了，visited[i][j] = true
	// 如果(i,j)没进入过，visited[i][j] = false
	public static boolean[][] visited = new boolean[100][100];
	// find表示发现了多少海洋
	public static int find;

	public static int maxDistance(int[][] grid) {
		// 清空变量
		// 只要l = 0，r = 0，队列就算被清空了
		l = 0;
		r = 0;
		find = 0;
		int n = grid.length;
		int m = grid[0].length;
		// 清空visited
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				visited[i][j] = false;
			}
		}
		// 大体思路 :
		// 1) 先把所有的陆地加入队列，并且统计一共有多少海洋
		int seas = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 1) {
					visited[i][j] = true;
					queue[r][0] = i;
					queue[r++][1] = j;
				} else {
					seas++;
				}
			}
		}
		// 2) 从陆地开始广播出去(bfs)，每一块陆地的上、下、左、右所能找到的海洋都是第一层海洋
		// 3) 第一层海洋继续bfs，每一块海洋的上、下、左、右所能找到的海洋都是第二层海洋
		// 4) 第二层海洋继续bfs，每一块海洋的上、下、左、右所能找到的海洋都是第三层海洋
		// ...
		// 也就是说，以陆地做起点，每一层bfs都只找海洋！
		// 看看最深能找到多少层海洋
		int distance = 0; // 这个变量就是最深的海洋层数
		while (l < r && find < seas) { // find < seas说明所有的海洋块没有找全，继续找！
			int size = r - l;
			for (int i = 0; i < size && find < seas; i++, l++) {
				int row = queue[l][0];
				int col = queue[l][1];
				add(row - 1, col, n, m, grid);
				add(row + 1, col, n, m, grid);
				add(row, col - 1, n, m, grid);
				add(row, col + 1, n, m, grid);
			}
			distance++;
		}
		return find == 0 ? -1 : distance;
	}

	public static void add(int i, int j, int n, int m, int[][] grid) {
		if (i >= 0 && i < n && j >= 0 && j < m && grid[i][j] == 0 && !visited[i][j]) {
			find++;
			visited[i][j] = true;
			queue[r][0] = i;
			queue[r++][1] = j;
		}
	}

}

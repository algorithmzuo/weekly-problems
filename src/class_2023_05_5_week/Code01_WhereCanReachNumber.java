package class_2023_05_5_week;

// 来自字节
// 给定一个n*m的二维矩阵，每个位置都是字符
// U、D、L、R表示传送带的位置，会被传送到 : 上、下、左、右
// . 、O分别表示空地、目标，一定只有一个目标点
// 可以在空地上选择上、下、左、右四个方向的一个
// 到达传送带的点会被强制移动到其指向的下一个位置
// 如果越界直接结束，返回有几个点可以到达O点
public class Code01_WhereCanReachNumber {

	// 暴力方法
	// 为了测试
	public static int number1(char[][] map) {
		int ans = 0;
		int n = map.length;
		int m = map[0].length;
		boolean[][] visited = new boolean[n][m];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (dfs(map, i, j, visited)) {
					ans++;
				}
			}
		}
		return ans;
	}

	// 暴力方法
	// 为了测试
	public static boolean dfs(char[][] map, int i, int j, boolean[][] visited) {
		if (i < 0 || i == map.length || j < 0 || j == map[0].length || visited[i][j]) {
			return false;
		}
		visited[i][j] = true;
		boolean ans = false;
		if (map[i][j] == 'O') {
			ans = true;
		} else {
			if (map[i][j] == 'U') {
				ans = dfs(map, i - 1, j, visited);
			} else if (map[i][j] == 'D') {
				ans = dfs(map, i + 1, j, visited);
			} else if (map[i][j] == 'L') {
				ans = dfs(map, i, j - 1, visited);
			} else if (map[i][j] == 'R') {
				ans = dfs(map, i, j + 1, visited);
			} else {
				ans = dfs(map, i - 1, j, visited) || dfs(map, i + 1, j, visited) || dfs(map, i, j - 1, visited)
						|| dfs(map, i, j + 1, visited);
			}
		}
		visited[i][j] = false;
		return ans;
	}

	// 正式方法
	// 时间复杂度O(n*m)
	public static int number2(char[][] map) {
		int n = map.length;
		int m = map[0].length;
		boolean[][] visited = new boolean[n][m];
		// queue[i] = {行坐标、列坐标}
		int[][] queue = new int[n * m][2];
		int l = 0;
		int r = 0;
		int ans = 0;
		// O在哪，目的地
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 'O') {
					visited[i][j] = true;
					queue[r][0] = i;
					queue[r++][1] = j;
					break;
				}
			}
		}
		// [] [] [] [] [] ...  
		// l ...... r
		while (l < r) { // 队列里还有位置！
			ans++;
			int[] cur = queue[l++];
			int row = cur[0];
			int col = cur[1];
			if (row - 1 >= 0 && !visited[row - 1][col] && (map[row - 1][col] == 'D' || map[row - 1][col] == '.')) {
				visited[row - 1][col] = true;
				queue[r][0] = row - 1;
				queue[r++][1] = col;
			}
			if (row + 1 < n && !visited[row + 1][col] && (map[row + 1][col] == 'U' || map[row + 1][col] == '.')) {
				visited[row + 1][col] = true;
				queue[r][0] = row + 1;
				queue[r++][1] = col;
			}
			if (col - 1 >= 0 && !visited[row][col - 1] && (map[row][col - 1] == 'R' || map[row][col - 1] == '.')) {
				visited[row][col - 1] = true;
				queue[r][0] = row;
				queue[r++][1] = col - 1;
			}
			if (col + 1 < m && !visited[row][col + 1] && (map[row][col + 1] == 'L' || map[row][col + 1] == '.')) {
				visited[row][col + 1] = true;
				queue[r][0] = row;
				queue[r++][1] = col + 1;
			}
		}
		return ans;
	}

	// 生成随机地图
	// 为了测试
	public static char[][] genarateRandomMap(int n, int m) {
		char[][] map = new char[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int r = (int) (Math.random() * 5);
				if (r == 0) {
					map[i][j] = 'U';
				} else if (r == 1) {
					map[i][j] = 'D';
				} else if (r == 2) {
					map[i][j] = 'L';
				} else if (r == 3) {
					map[i][j] = 'R';
				} else {
					map[i][j] = '.';
				}
			}
		}
		map[(int) (Math.random() * n)][(int) (Math.random() * m)] = 'O';
		return map;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 10;
		int m = 10;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			char[][] map = genarateRandomMap(n, m);
			int ans1 = number1(map);
			int ans2 = number2(map);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

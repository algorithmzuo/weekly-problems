package class_2022_04_1_week;

// 来自小红书
// 小红书第一题：
// 薯队长从北向南穿过一片红薯地（南北长M，东西宽N），红薯地被划分为1x1的方格，
// 他可以从北边的任何一个格子出发，到达南边的任何一个格子，
// 但每一步只能走到东南、正南、西南方向的三个格子之一，
// 而且不能跨出红薯地，他可以获得经过的格子上的所有红薯，请问他可以获得最多的红薯个数。
public class Code04_MaxScoreMoveInBoard {

//	// 目前来到(row, col)的位置
//	// 只能往不越界的三个方向移动：西南、正南、东南
//	// 一旦遇到最南格子，停
//	// 返回这个过程中，最大的收成
//	public static int f(int[][] board, int row, int col) {
//		if (col < 0 || col == board[0].length) {
//			return 0;
//		}
//		if (row == board.length - 1) {
//			return board[row][col];
//		}
//		int p1 = f(board, row + 1, col - 1);
//		int p2 = f(board, row + 1, col);
//		int p3 = f(board, row + 1, col + 1);
//		return board[row][col] + Math.max(p1, Math.max(p2, p3));
//	}

	public static int maxScore(int[][] map) {
		int ans = 0;
		for (int col = 0; col < map[0].length; col++) {
			ans = Math.max(ans, process(map, 0, col));
		}
		return ans;
	}

	public static int process(int[][] map, int row, int col) {
		if (col < 0 || col == map[0].length) {
			return -1;
		}
		if (row == map.length - 1) {
			return map[row][col];
		}
		int cur = map[row][col];
		int next1 = process(map, row + 1, col - 1);
		int next2 = process(map, row + 1, col);
		int next3 = process(map, row + 1, col + 1);
		return cur + Math.max(Math.max(next1, next2), next3);
	}

	public static int maxScore2(int[][] map) {
		int ans = 0;
		int n = map.length;
		int m = map[0].length;
		int[][] dp = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dp[i][j] = -2; // -2表示，这个格子没算过！
			}
		}
		for (int col = 0; col < map[0].length; col++) {
			ans = Math.max(ans, process2(map, 0, col, dp));
		}
		return ans;
	}

	public static int process2(int[][] map, int row, int col, int[][] dp) {
		if (col < 0 || col == map[0].length) {
			return -1;
		}
		if (dp[row][col] != -2) {
			return dp[row][col];
		}
		// 继续算！
		int ans = 0;
		if (row == map.length - 1) {
			ans = map[row][col];
		} else {
			int cur = map[row][col];
			int next1 = process2(map, row + 1, col - 1, dp);
			int next2 = process2(map, row + 1, col, dp);
			int next3 = process2(map, row + 1, col + 1, dp);
			ans = cur + Math.max(Math.max(next1, next2), next3);
		}
		dp[row][col] = ans;
		return ans;
	}

}

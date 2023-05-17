package class_2023_05_3_week;

// 在一个 n x n 的国际象棋棋盘上，一个骑士从单元格 (row, column) 开始
// 并尝试进行 k 次移动。行和列是 从 0 开始 的，所以左上单元格是 (0,0)
// 右下单元格是 (n - 1, n - 1)，象棋骑士有8种可能的走法
// 每次移动在基本方向上是两个单元格，然后在正交方向上是一个单元格，类似马走日
// 每次骑士要移动时，它都会随机从8种可能的移动中选择一种(即使棋子会离开棋盘)，然后移动到那里。
// 骑士继续移动，直到它走了 k 步或离开了棋盘
// 返回 骑士在棋盘停止移动后仍留在棋盘上的概率
// 测试链接 : https://leetcode.cn/problems/knight-probability-in-chessboard/
public class Code03_KnightProbabilityInChessboard {

	public double knightProbability(int n, int k, int row, int column) {
		double[][][] dp = new double[n][n][k + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int t = 0; t <= k; t++) {
					dp[i][j][t] = -1;
				}
			}
		}
		return process2(n, k, row, column, dp);
	}

	// (r,c) , 还剩rest步去走
	// 走的过程中，出棋盘就算死，走完之后还活的概率
	public double process2(int n, int rest, int r, int c, double[][][] dp) {
		if (r < 0 || r >= n || c < 0 || c >= n) {
			return 0;
		}
		if (dp[r][c][rest] != -1) {
			return dp[r][c][rest];
		}
		double ans = 0;
		if (rest == 0) {
			ans = 1;
		} else {
			ans += (process2(n, rest - 1, r - 2, c + 1, dp) / 8);
			ans += (process2(n, rest - 1, r - 1, c + 2, dp) / 8);
			ans += (process2(n, rest - 1, r + 1, c + 2, dp) / 8);
			ans += (process2(n, rest - 1, r + 2, c + 1, dp) / 8);
			ans += (process2(n, rest - 1, r + 2, c - 1, dp) / 8);
			ans += (process2(n, rest - 1, r + 1, c - 2, dp) / 8);
			ans += (process2(n, rest - 1, r - 1, c - 2, dp) / 8);
			ans += (process2(n, rest - 1, r - 2, c - 1, dp) / 8);
		}
		dp[r][c][rest] = ans;
		return ans;
	}

}

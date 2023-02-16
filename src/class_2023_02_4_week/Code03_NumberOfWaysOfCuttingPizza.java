package class_2023_02_4_week;

// 给你一个 rows * cols 大小的矩形披萨和一个整数 k
// 矩形包含两种字符： 'A' （表示苹果）和 '.' （表示空白格子）
// 你需要切披萨 k-1 次，得到 k 块披萨并送给别人
// 切披萨的每一刀，先要选择是向垂直还是水平方向切，再在矩形的边界上选一个切的位置
// 将披萨一分为二。如果垂直地切披萨，那么需要把左边的部分送给一个人
// 如果水平地切，那么需要把上面的部分送给一个人
// 在切完最后一刀后，需要把剩下来的一块送给最后一个人
// 请你返回确保每一块披萨包含 至少 一个苹果的切披萨方案数
// 由于答案可能是个很大的数字，请你返回它对 10^9 + 7 取余的结果
// 测试链接 : https://leetcode.cn/problems/number-of-ways-of-cutting-a-pizza/
public class Code03_NumberOfWaysOfCuttingPizza {

	// 暴力方法
	public static int ways1(String[] pizza, int k) {
		int n = pizza.length;
		int m = pizza[0].length();
		int[][] sum = new int[n + 1][m + 1];
		setAppleMatrix(pizza, sum, n, m);
		return process(sum, n, m, 1, 1, k);
	}

	// 暴力方法
	public static int process(int[][] sum, int n, int m, int row, int col, int rest) {
		if (apple(sum, row, col, n, m) == 0) {
			return 0;
		}
		if (rest == 1) {
			return 1;
		}
		int ways = 0;
		for (int i = col; i < m; i++) {
			if (apple(sum, row, col, n, i) > 0) {
				ways += process(sum, n, m, row, i + 1, rest - 1);
				ways %= mod;
			}
		}
		for (int i = row; i < n; i++) {
			if (apple(sum, row, col, i, m) > 0) {
				ways += process(sum, n, m, i + 1, col, rest - 1);
				ways %= mod;
			}
		}
		return ways;
	}

	// 暴力方法改动态规划 + 小优化
	// 时间复杂度O(N * M * K * (N + M))
	public static int ways2(String[] pizza, int k) {
		int n = pizza.length;
		int m = pizza[0].length();
		int[][] sum = new int[n + 1][m + 1];
		setAppleMatrix(pizza, sum, n, m);
		int[][][] dp = new int[k + 1][n + 1][m + 1];
		for (int r = 1; r <= n; r++) {
			for (int c = 1; c <= m; c++) {
				if (apple(sum, r, c, n, m) > 0) {
					dp[1][r][c] = 1;
				}
			}
		}
		for (int level = 2; level <= k; level++) {
			for (int row = n; row >= 1; row--) {
				for (int col = m; col >= 1; col--) {
					int ways = 0;
					for (int c = col; c < m; c++) {
						if (apple(sum, row, col, n, c) > 0) {
							for (int s = c + 1; s <= m; s++) {
								ways += dp[level - 1][row][s];
								ways %= mod;
							}
							break;
						}
					}
					for (int r = row; r < n; r++) {
						if (apple(sum, row, col, r, m) > 0) {
							for (int s = r + 1; s <= n; s++) {
								ways += dp[level - 1][s][col];
								ways %= mod;
							}
							break;
						}
					}
					dp[level][row][col] = ways;
				}
			}
		}
		return dp[k][1][1];
	}

	// 动态规划 + 观察位置依赖的大优化
	// 时间复杂度O(N * M * K)
	public static int ways3(String[] pizza, int k) {
		int n = pizza.length;
		int m = pizza[0].length();
		int[][] sum = new int[n + 1][m + 1];
		setAppleMatrix(pizza, sum, n, m);
		int[][] nearr = new int[n + 1][m + 1];
		int[][] nearc = new int[n + 1][m + 1];
		setNear(sum, nearr, nearc, n, m);
		int[][] dp = new int[n + 1][m + 1];
		int[][] rs = new int[n + 1][m + 1];
		int[][] cs = new int[n + 1][m + 1];
		for (int r = 1; r <= n; r++) {
			for (int c = 1; c <= m; c++) {
				if (apple(sum, r, c, n, m) > 0) {
					dp[r][c] = 1;
				}
			}
		}
		setRowColSums(dp, rs, cs, n, m);
		for (int level = 2; level <= k; level++) {
			for (int r = 1; r <= n; r++) {
				for (int c = 1; c <= m; c++) {
					dp[r][c] = nearr[r][c] < n ? (rs[nearr[r][c] + 1][c]) : 0;
					dp[r][c] = (dp[r][c] + (nearc[r][c] < m ? cs[r][nearc[r][c] + 1] : 0)) % mod;
				}
			}
			setRowColSums(dp, rs, cs, n, m);
		}
		return dp[1][1];
	}

	public static int mod = 1000000007;

	public static void setAppleMatrix(String[] pizza, int[][] sum, int n, int m) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				sum[i + 1][j + 1] = pizza[i].charAt(j) == 'A' ? 1 : 0;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				sum[i][j] += sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
			}
		}
	}

	public static int apple(int[][] sum, int a, int b, int c, int d) {
		return sum[c][d] - sum[c][b - 1] - sum[a - 1][d] + sum[a - 1][b - 1];
	}

	public static void setNear(int[][] sum, int[][] nearr, int[][] nearc, int n, int m) {
		for (int r = 1; r <= n; r++) {
			int right = m + 1;
			int number = 0;
			for (int c = m; c >= 1; c--) {
				int curApple = apple(sum, r, c, n, m);
				if (curApple > number) {
					number = curApple;
					right = c;
				}
				nearc[r][c] = right;
			}
		}
		for (int c = 1; c <= m; c++) {
			int down = n + 1;
			int number = 0;
			for (int r = n; r >= 1; r--) {
				int curApple = apple(sum, r, c, n, m);
				if (curApple > number) {
					number = curApple;
					down = r;
				}
				nearr[r][c] = down;
			}
		}
	}

	public static void setRowColSums(int[][] dp, int[][] rs, int[][] cs, int n, int m) {
		rs[n][m] = dp[n][m];
		cs[n][m] = dp[n][m];
		for (int r = n - 1; r >= 1; r--) {
			cs[r][m] = dp[r][m];
			rs[r][m] = (dp[r][m] + rs[r + 1][m]) % mod;
		}
		for (int c = m - 1; c >= 1; c--) {
			rs[n][c] = dp[n][c];
			cs[n][c] = (dp[n][c] + cs[n][c + 1]) % mod;
		}
		for (int r = n - 1; r >= 1; r--) {
			for (int c = m - 1; c >= 1; c--) {
				rs[r][c] = (dp[r][c] + rs[r + 1][c]) % mod;
				cs[r][c] = (dp[r][c] + cs[r][c + 1]) % mod;
			}
		}
	}

}

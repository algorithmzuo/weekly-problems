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
public class Code02_NumberOfWaysOfCuttingPizza {

	public static int mod = 1000000007;

	// 用sum来判断左上角(a,b)到右下角(c,d)的区域是否有苹果
	public static boolean apple(int[][] sum, int a, int b, int c, int d) {
		return sum[c][d] - sum[c][b - 1] - sum[a - 1][d] + sum[a - 1][b - 1] > 0;
	}

	public static int ways(String[] pizza, int k) {
		int n = pizza.length;
		int m = pizza[0].length();
		// sum[i][j]表示 : 左上角(0,0)到右下角(i,j)整个区域的苹果数量累加和
		// 生成这个是为了方便得到：任意的左上角(a,b)到右下角(c,d)区域的苹果累加和
		int[][] sum = new int[n + 1][m + 1];
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
		// 上面两个for循环，就把sum生成好了
		// 注意apple方法，用sum来判断左上角(a,b)到右下角(c,d)的区域是否有苹果
		// 从此下标从1开始，不从0开始

		// return process1(sum, n, m, 1, 1, k);
		// 如下就是process1方法改动态规划而已
		int[][][] dp = new int[n + 1][m + 1][k + 1];
		for (int a = 0; a <= n; a++) {
			for (int b = 0; b <= m; b++) {
				for (int c = 0; c <= k; c++) {
					dp[a][b][c] = -1;
				}
			}
		}
		return process2(sum, n, m, 1, 1, k, dp);
	}

	// 暴力递归方法
	// 因为水平切的时候给上块、竖直切的时候给左块
	// 所以剩余的区域的右下角总是(n,m)位置
	// 所以表示一个区域的时候只需要给定(row, col)即可
	// process(row, col, rest)表示 :
	// 左上角(row, col)右下角(n,m)的区域上，一定要切出rest份
	// 方法数是多少
	public static int process1(int[][] sum, int n, int m, int row, int col, int rest) {
		if (!apple(sum, row, col, n, m)) {
			return 0;
		}
		if (rest == 1) {
			return 1;
		}
		// 有苹果且不止切成1块
		int ways = 0;
		// 竖直切
		for (int i = col; i < m; i++) {
			if (apple(sum, row, col, n, i)) {
				ways += process1(sum, n, m, row, i + 1, rest - 1);
				ways %= mod;
			}
		}
		// 水平切
		for (int i = row; i < n; i++) {
			if (apple(sum, row, col, i, m)) {
				ways += process1(sum, n, m, i + 1, col, rest - 1);
				ways %= mod;
			}
		}
		return ways;
	}

	// process1改成动态规划的方法
	// 具体看体系学习班，动态规划章节
	// process2毫无灵魂，就是process1的改进而已
	// 核心就是process1
	public static int process2(int[][] sum, int n, int m, int row, int col, int rest, int[][][] dp) {
		if (!apple(sum, row, col, n, m)) {
			return 0;
		}
		if (rest == 1) {
			return 1;
		}
		if (dp[row][col][rest] != -1) {
			return dp[row][col][rest];
		}
		int ways = 0;
		for (int i = col; i < m; i++) {
			if (apple(sum, row, col, n, i)) {
				ways += process2(sum, n, m, row, i + 1, rest - 1, dp);
				ways %= mod;
			}
		}
		for (int i = row; i < n; i++) {
			if (apple(sum, row, col, i, m)) {
				ways += process2(sum, n, m, i + 1, col, rest - 1, dp);
				ways %= mod;
			}
		}
		dp[row][col][rest] = ways;
		return ways;
	}

}

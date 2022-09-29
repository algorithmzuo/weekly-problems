package class_2022_09_4_week;

// 给你两个整数 m 和 n 。构造一个 m x n 的网格，其中每个单元格最开始是白色
// 请你用 红、绿、蓝 三种颜色为每个单元格涂色。所有单元格都需要被涂色
// 涂色方案需要满足：不存在相邻两个单元格颜色相同的情况
// 返回网格涂色的方法数。因为答案可能非常大
// 返回 对 109 + 7 取余 的结果。
// 1 <= n <= 1000
// 1 <= m <= 5
// 测试链接 : https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/
public class Code04_PaintingGridWithThreeDifferentColors {

//	public static int zuo(int i, int j, int s, int n, int m) {
//		if (i == n) {
//			return 1;
//		}
//		if (j == m) {
//			return zuo(i + 1, 0, s, n, m);
//		}
//
//		// j列
//		int up = (s >> (j * 2)) & 3;
//		int left = j == 0 ? 0 : ((s >> ((j - 1) * 2)) & 3);
//		int ans = 0;
//		if (up != 1 && left != 1) { // 当前格子就涂1这个颜色
//			int exp = statu
//			ans += zuo( i, j + 1, ?, n,m);
//			
//		}
//		if (up != 2 && left != 2) {
//
//		}
//		if (up != 3 && left != 3) {
//
//		}
//		return ans;
//	}

	public static final int mod = 1000000007;

	public static int colorTheGrid(int m, int n) {
		int status = 1 << (m << 1);
		int[][][] dp = new int[n][m][status];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s < status; s++) {
					dp[i][j][s] = -1;
				}
			}
		}
		return process(0, 0, 0, n, m, dp);
	}

	public static int process(int i, int j, int s, int n, int m, int[][][] dp) {
		if (i == n) {
			return 1;
		}
		if (j == m) {
			return process(i + 1, 0, s, n, m, dp);
		}
		if (dp[i][j][s] != -1) {
			return dp[i][j][s];
		}
		int up = (s >> (j * 2)) & 3;
		int left = j == 0 ? 0 : ((s >> ((j - 1) << 1)) & 3);
		int ans = 0;
		if (up != 1 && left != 1) {
			ans += process(i, j + 1,(s ^ (up << (j * 2))) | (1 << (j * 2)), n, m, dp);
			ans %= mod;
		}
		if (up != 2 && left != 2) {
			ans += process(i, j + 1, (s ^ (up << (j << 1))) | (2 << (j << 1)), n, m, dp);
			ans %= mod;
		}
		if (up != 3 && left != 3) {
			ans += process(i, j + 1, (s ^ (up << (j << 1))) | (3 << (j << 1)), n, m, dp);
			ans %= mod;
		}
		dp[i][j][s] = ans;
		return ans;
	}

}

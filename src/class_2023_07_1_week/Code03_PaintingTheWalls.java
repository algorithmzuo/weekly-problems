package class_2023_07_1_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/painting-the-walls/
public class Code03_PaintingTheWalls {

	// 暴力递归
	// 展示了主要的思路
	public static int paintWalls1(int[] cost, int[] time) {
		int n = cost.length;
		return process(cost, time, 0, n);
	}

	public static int process(int[] cost, int[] time, int i, int s) {
		if (s <= 0) {
			return 0;
		}
		// s > 0
		if (i == cost.length) {
			return Integer.MAX_VALUE;
		} else {
			int p1 = process(cost, time, i + 1, s);
			int p2 = Integer.MAX_VALUE;
			int next2 = process(cost, time, i + 1, s - 1 - time[i]);
			if (next2 != Integer.MAX_VALUE) {
				p2 = cost[i] + next2;
			}
			return Math.min(p1, p2);
		}
	}

	// 暴力递归改记忆化搜索
	public static int paintWalls2(int[] cost, int[] time) {
		int n = cost.length;
		int[][] dp = new int[n + 1][n + 1];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				dp[i][j] = -1;
			}
		}
		return process2(cost, time, 0, n, dp);
	}

	public static int process2(int[] cost, int[] time, int i, int s, int[][] dp) {
		if (s <= 0) {
			return 0;
		}
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		int ans;
		if (i == cost.length) {
			ans = Integer.MAX_VALUE;
		} else {
			int p1 = process2(cost, time, i + 1, s, dp);
			int p2 = Integer.MAX_VALUE;
			int next2 = process2(cost, time, i + 1, s - 1 - time[i], dp);
			if (next2 != Integer.MAX_VALUE) {
				p2 = cost[i] + next2;
			}
			ans = Math.min(p1, p2);
		}
		dp[i][s] = ans;
		return ans;
	}

	// 严格位置依赖的动态规划 + 空间压缩
	public static int paintWalls3(int[] cost, int[] time) {
		int n = cost.length;
		int[] dp = new int[n + 1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		for (int i = n - 1; i >= 0; i--) {
			for (int s = n; s >= 0; s--) {
				if (s - 1 - time[i] <= 0) {
					dp[s] = Math.min(dp[s], cost[i]);
				} else if (dp[s - 1 - time[i]] != Integer.MAX_VALUE) {
					dp[s] = Math.min(dp[s], cost[i] + dp[s - 1 - time[i]]);
				}
			}
		}
		return dp[n];
	}

}

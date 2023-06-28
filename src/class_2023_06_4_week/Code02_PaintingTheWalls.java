package class_2023_06_4_week;

import java.util.Arrays;

// 给你两个长度为 n 下标从 0 开始的整数数组 cost 和 time
// 分别表示给 n 堵不同的墙刷油漆需要的开销和时间。你有两名油漆匠
// 一位需要 付费 的油漆匠，刷第 i 堵墙需要花费 time[i] 单位的时间
// 开销为 cost[i] 单位的钱。
// 一位 免费 的油漆匠，刷 任意 一堵墙的时间为 1 单位，开销为 0
// 但是必须在付费油漆匠 工作 时，免费油漆匠才会工作
// 请你返回刷完 n 堵墙最少开销为多少
// 测试链接 : https://leetcode.cn/problems/painting-the-walls/
public class Code02_PaintingTheWalls {

	// 暴力递归
	// 展示了主要的思路
	public static int paintWalls1(int[] cost, int[] time) {
		return process1(cost, time, 0, cost.length);
	}

	
	
	
	
	// 来到i位置的墙，整体还有s面墙需要刷
	// 认为只需要选s面墙即可，剩下的都刷完了
	// 返回刷完所有墙的最少花费
	public static int process1(int[] cost, int[] time, int i, int s) {
		if (s <= 0) {
			return 0;
		}
		// s > 0
		if (i == cost.length) {
			return Integer.MAX_VALUE;
		} else {
			// 付费的人，就是不选第i面墙
			int p1 = process1(cost, time, i + 1, s);
			// 付费的人，就是选第i面墙
			int p2 = Integer.MAX_VALUE;
			int next2 = process1(cost, time, i + 1, s - 1 - time[i]);
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
			Arrays.fill(dp[i], -1);
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
			for (int s = n; s >= 1; s--) {
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

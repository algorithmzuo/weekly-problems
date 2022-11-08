package class_2022_11_5_week;

import java.util.Arrays;
import java.util.List;

// 测试链接 : https://leetcode.cn/problems/minimum-total-distance-traveled/
public class Code03_MinimumTotalDistanceTraveled {

	public static long minimumTotalDistance1(List<Integer> robot, int[][] factory) {
		int n = robot.size();
		int m = factory.length;
		robot.sort((a, b) -> a - b);
		Arrays.sort(factory, (a, b) -> a[0] - b[0]);
		long[][] dp = new long[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dp[i][j] = -1;
			}
		}
		return process1(robot, factory, n - 1, m - 1, dp);
	}

	public static long process1(List<Integer> robot, int[][] factory, int i, int j, long[][] dp) {
		if (i < 0) {
			return 0;
		}
		if (j < 0) {
			return Long.MAX_VALUE;
		}
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		long ans = process1(robot, factory, i, j - 1, dp);
		long distance = 0;
		for (int l = i, num = 1; l >= 0 && num <= factory[j][1]; l--, num++) {
			long curAns = process1(robot, factory, l - 1, j - 1, dp);
			distance += Math.abs(robot.get(l) - factory[j][0]);
			if (curAns != Long.MAX_VALUE) {
				ans = Math.min(ans, curAns + distance);
			}
		}
		dp[i][j] = ans;
		return ans;
	}

	public static long minimumTotalDistance2(List<Integer> robot, int[][] factory) {
		int n = robot.size();
		int m = factory.length;
		robot.sort((a, b) -> a - b);
		Arrays.sort(factory, (a, b) -> a[0] - b[0]);
		long[][] dp = new long[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				long ans = j - 1 >= 0 ? dp[i][j - 1] : Long.MAX_VALUE;
				long distance = 0;
				for (int l = i, num = 1; l >= 0 && num <= factory[j][1]; l--, num++) {
					long curAns = l - 1 < 0 ? 0 : (j - 1 < 0 ? Long.MAX_VALUE : dp[l - 1][j - 1]);
					distance += Math.abs(robot.get(l) - factory[j][0]);
					if (curAns != Long.MAX_VALUE) {
						ans = Math.min(ans, curAns + distance);
					}
				}
				dp[i][j] = ans;
			}
		}
		return dp[n - 1][m - 1];
	}

	// 最优解O(N*M)，目前所有题解都没有达到这个程度的
	public static long minimumTotalDistance3(List<Integer> robot, int[][] factory) {
		int n = robot.size();
		int m = factory.length;
		robot.sort((a, b) -> a - b);
		Arrays.sort(factory, (a, b) -> a[0] - b[0]);
		long[][] dp = new long[n][m];
		long[][] deque = new long[n + 1][2];
		int l = 0;
		int r = 0;
		for (int j = 0; j < m; j++) {
			long add = 0;
			long limit = factory[j][1];
			l = 0;
			r = 1;
			deque[l][0] = -1;
			deque[l][1] = 0;
			for (int i = 0; i < n; i++) {
				long p1 = j - 1 >= 0 ? dp[i][j - 1] : Long.MAX_VALUE;
				add += Math.abs((long) robot.get(i) - (long) factory[j][0]);
				if (deque[l][0] == i - limit - 1) {
					l++;
				}
				long p2 = Long.MAX_VALUE;
				if (l < r) {
					long best = deque[l][1];
					if (best != Long.MAX_VALUE) {
						p2 = add + best;
					}
				}
				dp[i][j] = Math.min(p1, p2);
				long fill = p1 == Long.MAX_VALUE ? p1 : (p1 - add);
				while (l < r && deque[r - 1][1] >= fill) {
					r--;
				}
				deque[r][0] = i;
				deque[r++][1] = fill;
			}
		}
		return dp[n - 1][m - 1];
	}

}

package class_2022_12_1_week;

import java.util.Arrays;
import java.util.List;

// X轴上有一些机器人和工厂。给你一个整数数组robot，其中robot[i]是第i个机器人的位置
// 再给你一个二维整数数组factory，其中 factory[j] = [positionj, limitj]
// 表示第 j 个工厂的位置在 positionj ，且第 j 个工厂最多可以修理 limitj 个机器人
// 每个机器人所在的位置 互不相同。每个工厂所在的位置也互不相同
// 注意一个机器人可能一开始跟一个工厂在相同的位置
// 所有机器人一开始都是坏的，他们会沿着设定的方向一直移动
// 设定的方向要么是 X 轴的正方向，要么是 X 轴的负方向
// 当一个机器人经过一个没达到上限的工厂时，这个工厂会维修这个机器人，且机器人停止移动
// 任何时刻，你都可以设置 部分 机器人的移动方向
// 你的目标是最小化所有机器人总的移动距离
// 请你返回所有机器人移动的最小总距离
// 注意：
// 所有机器人移动速度相同
// 如果两个机器人移动方向相同，它们永远不会碰撞
// 如果两个机器人迎面相遇，它们也不会碰撞，它们彼此之间会擦肩而过
// 如果一个机器人经过了一个已经达到上限的工厂，机器人会当作工厂不存在，继续移动
// 机器人从位置 x 到位置 y 的移动距离为 |y - x|
// 1 <= robot.length, factory.length <= 100
// factory[j].length == 2
// -10^9 <= robot[i], positionj <= 10^9
// 0 <= limitj <= robot.length
// 测试数据保证所有机器人都可以被维修
// 测试链接 : https://leetcode.cn/problems/minimum-total-distance-traveled/
public class Code04_MinimumTotalDistanceTraveled {

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

package class_2022_11_5_week;

import java.util.Arrays;
import java.util.List;

// 来自谷歌
// 给定一个长度为N的数组，值一定在0~N-1范围，且每个值不重复
// 比如，arr = [4, 2, 0, 3, 1]
// 把0想象成洞，任何非0数字都可以来到这个洞里，然后在原本的位置留下洞
// 比如4这个数字，来到0所代表的洞里，那么数组变成 : 
// arr = [0, 2, 4, 3, 1]
// 也就是原来的洞被4填满，4走后留下了洞
// 任何数字只能搬家到洞里，并且走后留下洞
// 通过搬家的方式，想变成有序的，有序有两种形式
// 比如arr = [4, 2, 0, 3, 1]，变成
// [0, 1, 2, 3, 4]或者[1, 2, 3, 4, 0]都叫有序
// 返回变成任何一种有序的情况都可以，最少的数字搬动次数
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

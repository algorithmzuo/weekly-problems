package class_2022_12_4_week;

import java.util.PriorityQueue;

// 你的赛车可以从位置 0 开始，并且速度为 +1 ，在一条无限长的数轴上行驶
// 赛车也可以向负方向行驶
// 赛车可以按照由加速指令 'A' 和倒车指令 'R' 组成的指令序列自动行驶。
// 当收到指令 'A' 时，赛车这样行驶：
// position += speed
// speed *= 2
// 当收到指令 'R' 时，赛车这样行驶：
// 如果速度为正数，那么speed = -1
// 否则 speed = 1
// 当前所处位置不变。
// 例如，在执行指令 "AAR" 后，赛车位置变化为 0 --> 1 --> 3 --> 3
// 速度变化为 1 --> 2 --> 4 --> -1
// 给你一个目标位置 target ，返回能到达目标位置的最短指令序列的长度。
// 测试链接 : https://leetcode.cn/problems/race-car/
public class Code04_RaceCar {

	// Dijkstra算法
	public static int racecar1(int target) {
		int maxp = 0;
		int maxs = 1;
		while (maxp <= target) {
			maxp += 1 << (maxs - 1);
			maxs++;
		}
		// 0 : 几倍速
		// 1 : 花费了几步
		// 2 : 当前位置
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		boolean[][] positive = new boolean[maxs + 1][maxp + 1];
		boolean[][] negative = new boolean[maxs + 1][maxp + 1];
		heap.add(new int[] { 1, 0, 0 });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int speed = cur[0];
			int cost = cur[1];
			int position = cur[2];
			if (position == target) {
				return cost;
			}
			if (speed > 0) {
				if (positive[speed][position]) {
					continue;
				}
				positive[speed][position] = true;
				add(speed + 1, cost + 1, position + (1 << (speed - 1)), maxp, heap, positive);
				add(-1, cost + 1, position, maxp, heap, negative);
			} else {
				speed = -speed;
				if (negative[speed][position]) {
					continue;
				}
				negative[speed][position] = true;
				add(-speed - 1, cost + 1, position - (1 << (speed - 1)), maxp, heap, negative);
				add(1, cost + 1, position, maxp, heap, positive);
			}
		}
		return -1;
	}

	public static void add(int speed, int cost, int position, int limit, PriorityQueue<int[]> heap,
			boolean[][] visited) {
		if (position >= 0 && position <= limit && !visited[Math.abs(speed)][position]) {
			heap.add(new int[] { speed, cost, position });
		}
	}

	// 动态规划 + 数学
	public static int racecar2(int target) {
		int[] dp = new int[target + 1];
		return process(target, dp);
	}

	public static int process(int target, int[] dp) {
		if (dp[target] > 0) {
			return dp[target];
		}
		int steps = 0;
		int speed = 1;
		while (speed <= target) {
			speed <<= 1;
			steps++;
		}
		int ans = 0;
		int beyond = speed - 1 - target;
		if (beyond == 0) {
			ans = steps;
		} else {
			ans = steps + 1 + process(beyond, dp);
			steps--;
			speed >>= 1;
			int lack = target - (speed - 1);
			int offset = 1;
			for (int back = 0; back < steps; back++) {
				ans = Math.min(ans, steps + 1 + back + 1 + process(lack, dp));
				lack += offset;
				offset <<= 1;
			}
		}
		dp[target] = ans;
		return ans;
	}

}

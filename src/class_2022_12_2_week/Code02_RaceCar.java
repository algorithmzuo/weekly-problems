package class_2022_12_2_week;

import java.util.PriorityQueue;

// 测试链接 : https://leetcode.cn/problems/race-car/
public class Code02_RaceCar {

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
		PriorityQueue<int[]> heap = new PriorityQueue<>(
				(a, b) -> a[1] != b[1] ? (a[1] - b[1]) : (Math.abs(target - a[2]) - Math.abs(target - b[2])));
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

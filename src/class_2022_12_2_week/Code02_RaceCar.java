package class_2022_12_2_week;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

// 测试链接 : https://leetcode.cn/problems/race-car/
public class Code02_RaceCar {

	public static class Point {
		public int position;
		public int speed;
		public int distance;

		public Point(int p, int s, int d) {
			position = p;
			speed = s;
			distance = d;
		}
	}

	public static int racecar1(int target) {
		int max = target * 2;
		PriorityQueue<Point> heap = new PriorityQueue<>((a, b) -> a.distance - b.distance);
		HashMap<Integer, HashSet<Integer>> used = new HashMap<>();
		heap.add(new Point(0, 1, 0));
		while (!heap.isEmpty()) {
			Point cur = heap.poll();
			if (cur.position == target) {
				return cur.distance;
			}
			if (isUsed(cur.position, cur.speed, used)) {
				continue;
			}
			addToSet(cur.position, cur.speed, used);
			addToQueue(cur.position + cur.speed, cur.speed * 2, cur.distance + 1, max, heap, used);
			addToQueue(cur.position, cur.speed > 0 ? -1 : 1, cur.distance + 1, max, heap, used);
		}
		return -1;
	}

	public static void addToSet(int position, int speed, HashMap<Integer, HashSet<Integer>> used) {
		if (!used.containsKey(position)) {
			used.put(position, new HashSet<>());
		}
		used.get(position).add(speed);
	}

	public static boolean isUsed(int position, int speed, HashMap<Integer, HashSet<Integer>> used) {
		HashSet<Integer> set = used.getOrDefault(position, null);
		return set != null ? set.contains(speed) : false;
	}

	public static void addToQueue(int position, int speed, int distance, int max, PriorityQueue<Point> heap,
			HashMap<Integer, HashSet<Integer>> used) {
		if (position >= 0 && position <= max && !isUsed(position, speed, used)) {
			heap.add(new Point(position, speed, distance));
		}
	}

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

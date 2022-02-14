package class_2022_02_3_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.com/problems/cheapest-flights-within-k-stops/
public class Code01_CheapestFlightsWithinKStops {

	// Bellman Ford
	public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
		int[] cost = new int[n];
		Arrays.fill(cost, Integer.MAX_VALUE);
		cost[src] = 0;
		for (int i = 0; i <= k; i++) {
			int[] next = Arrays.copyOf(cost, n);
			for (int[] f : flights) {
				if (cost[f[0]] != Integer.MAX_VALUE) {
					next[f[1]] = Math.min(next[f[1]], cost[f[0]] + f[2]);
				}
			}
			cost = next;
		}
		return cost[dst] == Integer.MAX_VALUE ? -1 : cost[dst];
	}

}

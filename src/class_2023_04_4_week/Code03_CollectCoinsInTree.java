package class_2023_04_4_week;

import java.util.ArrayList;

// 测试链接 : https://leetcode.cn/problems/collect-coins-in-a-tree/
public class Code03_CollectCoinsInTree {

	public static int collectTheCoins(int[] coins, int[][] edges) {
		int n = coins.length;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		int[] inDegree = new int[n];
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
			inDegree[edge[0]]++;
			inDegree[edge[1]]++;
		}
		int[] queue = new int[n];
		int l = 0, r = 0;
		for (int i = 0; i < n; ++i) {
			if (inDegree[i] == 1 && coins[i] == 0) {
				queue[r++] = i;
			}
		}
		while (l < r) {
			int cur = queue[l++];
			for (int next : graph.get(cur)) {
				if (--inDegree[next] == 1 && coins[next] == 0) {
					queue[r++] = next;
				}
			}
		}
		for (int i = 0; i < n; ++i) {
			if (inDegree[i] == 1 && coins[i] == 1) {
				queue[r++] = i;
			}
		}
		int[] rank = new int[n];
		while (l < r) {
			int cur = queue[l++];
			for (int next : graph.get(cur)) {
				if (--inDegree[next] == 1) {
					rank[next] = rank[cur] + 1;
					queue[r++] = next;
				}
			}
		}
		int ans = 0;
		for (int[] edge : edges)
			if (rank[edge[0]] >= 2 && rank[edge[1]] >= 2) {
				ans += 2;
			}
		return ans;
	}

}

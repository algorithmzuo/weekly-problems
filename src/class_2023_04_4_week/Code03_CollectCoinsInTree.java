package class_2023_04_4_week;

import java.util.ArrayList;

// 给你一个 n 个节点的无向无根树，节点编号从 0 到 n - 1
// 给你整数 n 和一个长度为 n - 1 的二维整数数组 edges ，
// 其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间有一条边。
// 再给你一个长度为 n 的数组 coins ，其中 coins[i] 可能为 0 也可能为 1 ，
// 1 表示节点 i 处有一个金币。
// 一开始，你需要选择树中任意一个节点出发。你可以执行下述操作任意次：
// 收集距离当前节点距离为 2 以内的所有金币，或者 移动到树中一个相邻节点。
// 你需要收集树中所有的金币，并且回到出发节点，请你返回最少经过的边数。
// 如果你多次经过一条边，每一次经过都会给答案加一。
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

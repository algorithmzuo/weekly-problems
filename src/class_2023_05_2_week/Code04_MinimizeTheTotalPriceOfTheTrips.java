package class_2023_05_2_week;

import java.util.ArrayList;

// 现有一棵无向、无根的树，树中有 n 个节点，按从 0 到 n - 1 编号
// 给你一个整数 n 和一个长度为 n - 1 的二维整数数组 edges ，
// 其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条边。
// 每个节点都关联一个价格。给你一个整数数组 price ，其中 price[i] 是第 i 个节点的价格。
// 给定路径的 价格总和 是该路径上所有节点的价格之和。
// 另给你一个二维整数数组 trips ，其中 trips[i] = [starti, endi] 表示
// 从节点 starti 开始第 i 次旅行，并通过任何你喜欢的路径前往节点 endi 。
// 在执行第一次旅行之前，你可以选择一些 非相邻节点 并将价格减半。
// 返回执行所有旅行的最小价格总和。
// 测试链接 : https://leetcode.cn/problems/minimize-the-total-price-of-the-trips/
public class Code04_MinimizeTheTotalPriceOfTheTrips {

	public static int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		ArrayList<ArrayList<int[]>> queries = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
			queries.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		int m = trips.length;
		int[] lcs = new int[m];
		for (int i = 0; i < m; i++) {
			if (trips[i][0] == trips[i][1]) {
				lcs[i] = trips[i][0];
			} else {
				queries.get(trips[i][0]).add(new int[] { trips[i][1], i });
				queries.get(trips[i][1]).add(new int[] { trips[i][0], i });
			}
		}
		UnionFind uf = new UnionFind(n);
		int[] fathers = new int[n];
		tarjan(graph, 0, -1, uf, queries, fathers, lcs);
		int[] cnts = new int[n];
		for (int i = 0; i < m; i++) {
			cnts[trips[i][0]]++;
			cnts[trips[i][1]]++;
			cnts[lcs[i]]--;
			if (fathers[lcs[i]] != -1) {
				cnts[fathers[lcs[i]]]--;
			}
		}
		dfs(graph, 0, -1, cnts);
		int[] ans = dp(graph, 0, -1, cnts, price);
		return Math.min(ans[0], ans[1]);
	}

	public static void tarjan(ArrayList<ArrayList<Integer>> graph, int cur, int father, UnionFind uf,
			ArrayList<ArrayList<int[]>> queries, int[] fathers, int[] lcs) {
		fathers[cur] = father;
		for (int next : graph.get(cur)) {
			if (next != father) {
				tarjan(graph, next, cur, uf, queries, fathers, lcs);
				uf.union(cur, next);
				uf.setTag(cur, cur);
			}
		}
		for (int[] query : queries.get(cur)) {
			int tag = uf.getTag(query[0]);
			if (tag != -1) {
				lcs[query[1]] = tag;
			}
		}
	}

	public static void dfs(ArrayList<ArrayList<Integer>> graph, int cur, int father, int[] cnts) {
		for (int next : graph.get(cur)) {
			if (next != father) {
				dfs(graph, next, cur, cnts);
				cnts[cur] += cnts[next];
			}
		}
	}

	public static int[] dp(ArrayList<ArrayList<Integer>> graph, int cur, int father, int[] cnts, int[] price) {
		int no = price[cur] * cnts[cur];
		int yes = (price[cur] / 2) * cnts[cur];
		for (int next : graph.get(cur)) {
			if (next != father) {
				int[] nextAns = dp(graph, next, cur, cnts, price);
				no += Math.min(nextAns[0], nextAns[1]);
				yes += nextAns[0];
			}
		}
		return new int[] { no, yes };
	}

	public static class UnionFind {
		public int[] father;
		public int[] size;
		public int[] tag;
		public int[] help;

		public UnionFind(int n) {
			father = new int[n];
			size = new int[n];
			tag = new int[n];
			help = new int[n];
			for (int i = 0; i < n; i++) {
				father[i] = i;
				size[i] = 1;
				tag[i] = -1;
			}
		}

		public int find(int i) {
			int size = 0;
			while (i != father[i]) {
				help[size++] = i;
				i = father[i];
			}
			while (size > 0) {
				father[help[--size]] = i;
			}
			return i;
		}

		public void union(int i, int j) {
			int fi = find(i);
			int fj = find(j);
			if (fi != fj) {
				if (size[fi] >= size[fj]) {
					father[fj] = fi;
					size[fi] += size[fj];
				} else {
					father[fi] = fj;
					size[fj] += size[fi];
				}
			}
		}

		public void setTag(int i, int t) {
			tag[find(i)] = t;
		}

		public int getTag(int i) {
			return tag[find(i)];
		}

	}

}

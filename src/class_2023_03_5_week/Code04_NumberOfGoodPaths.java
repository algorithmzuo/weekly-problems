package class_2023_03_5_week;

import java.util.ArrayList;
import java.util.Arrays;

// 来自谷歌
// 给你一棵 n 个节点的树（连通无向无环的图）
// 节点编号从 0 到 n - 1 且恰好有 n - 1 条边
// 给你一个长度为 n 下标从 0 开始的整数数组 vals
// 分别表示每个节点的值
// 同时给你一个二维整数数组 edges
// 其中 edges[i] = [ai, bi] 表示节点 ai 和 bi 之间有一条 无向 边
// 一条 好路径 需要满足以下条件：
// 开始节点和结束节点的值 相同 。
// 开始节点和结束节点中间的所有节点值都 小于等于 开始节点的值
//（也就是说开始节点的值应该是路径上所有节点的最大值）
// 请你返回不同好路径的数目
// 注意，一条路径和它反向的路径算作 同一 路径
// 比方说， 0 -> 1 与 1 -> 0 视为同一条路径。单个节点也视为一条合法路径
// 测试链接 : https://leetcode.cn/problems/number-of-good-paths/
public class Code04_NumberOfGoodPaths {

	public static int numberOfGoodPaths(int[] vals, int[][] edges) {
		int n = vals.length;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] e : edges) {
			graph.get(e[0]).add(e[1]);
			graph.get(e[1]).add(e[0]);
		}
		int[][] nodes = new int[n][2];
		for (int i = 0; i < n; i++) {
			nodes[i][0] = i;
			nodes[i][1] = vals[i];
		}
		Arrays.sort(nodes, (a, b) -> a[1] - b[1]);
		int[] maxIndex = new int[n];
		for (int i = 0; i < n; i++) {
			maxIndex[i] = i;
		}
		int[] maxNumber = new int[n];
		Arrays.fill(maxNumber, 1);
		UnionFind uf = new UnionFind(n);
		int ans = n;
		for (int[] node : nodes) {
			int curi = node[0];
			int curv = vals[curi];
			int curFather = uf.find(curi);
			int curMaxIndex = maxIndex[curFather];
			for (int nexti : graph.get(curi)) {
				int nextv = vals[nexti];
				int nextFather = uf.find(nexti);
				if (curFather != nextFather && curv >= nextv) {
					int nextMaxIndex = maxIndex[nextFather];
					if (curv == vals[nextMaxIndex]) {
						ans += maxNumber[curMaxIndex] * maxNumber[nextMaxIndex];
						maxNumber[curMaxIndex] += maxNumber[nextMaxIndex];
					}
					int father = uf.union(curi, nexti);
					maxIndex[father] = curMaxIndex;
				}
			}
		}
		return ans;
	}

	public static class UnionFind {
		private int[] parent;
		private int[] size;
		private int[] help;

		public UnionFind(int n) {
			parent = new int[n];
			size = new int[n];
			help = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
				size[i] = 1;
			}
		}

		public int find(int i) {
			int hi = 0;
			while (i != parent[i]) {
				help[hi++] = i;
				i = parent[i];
			}
			for (hi--; hi >= 0; hi--) {
				parent[help[hi]] = i;
			}
			return i;
		}

		public int union(int i, int j) {
			int f1 = find(i);
			int f2 = find(j);
			if (f1 != f2) {
				if (size[f1] >= size[f2]) {
					size[f1] += size[f2];
					parent[f2] = f1;
					return f1;
				} else {
					size[f2] += size[f1];
					parent[f1] = f2;
					return f2;
				}
			}
			return f1;
		}

	}

}

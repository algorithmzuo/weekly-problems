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
public class Code05_NumberOfGoodPaths {

	// [1,2]
	// 1点 -> 7
	// 2点 -> 3
	public static int numberOfGoodPaths(int[] vals, int[][] edges) {
		int n = vals.length;
		// 1) 当前这一种，最经典的建图方式
		// 2) 邻接矩阵， N * N, 非常废空间，用于点的数量N不大的时候
		// 3) 链式前向星，固定数组就可以建图，省空间，不用动态结构，实现麻烦！
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		// a -> b  b -> a
		// a {b}
		// b {a}
		for (int[] e : edges) {
			graph.get(e[0]).add(e[1]);
			graph.get(e[1]).add(e[0]);
		}
		// 所有节点
		int[][] nodes = new int[n][2];
		for (int i = 0; i < n; i++) {
			// 编号，4号点
			nodes[i][0] = i;
			// 值，4号点的值
			nodes[i][1] = vals[i];
		}
		Arrays.sort(nodes, (a, b) -> a[1] - b[1]);
		UnionFind uf = new UnionFind(n);
		// 标签只有maxIndex数组
		int[] maxIndex = new int[n];
		for (int i = 0; i < n; i++) {
			maxIndex[i] = i;
		}
		// maxCnts不是标签
		// 单纯的最大值次数统计
		int[] maxCnts = new int[n];
		Arrays.fill(maxCnts, 1);
		int ans = n;
		// 已经根据值排序了！一定是从值小的点，遍历到值大的点
		for (int[] node : nodes) {
			int curi = node[0];
			int curv = vals[curi];
			int curCandidate = uf.find(curi);
			int curMaxIndex = maxIndex[curCandidate];
			// 遍历邻居
			for (int nexti : graph.get(curi)) {
				// 邻居值
				int nextv = vals[nexti];
				// 邻居的集合代表点
				int nextCandidate = uf.find(nexti);
				if (curCandidate != nextCandidate && curv >= nextv) {
					// 邻居集合最大值的下标
					int nextMaxIndex = maxIndex[nextCandidate];
					if (curv == vals[nextMaxIndex]) {
						ans += maxCnts[curMaxIndex] * maxCnts[nextMaxIndex];
						maxCnts[curMaxIndex] += maxCnts[nextMaxIndex];
					}
					int candidate = uf.union(curi, nexti);
					maxIndex[candidate] = curMaxIndex;
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

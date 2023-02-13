package class_2023_03_1_week;

import java.util.ArrayList;
import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/number-of-good-paths/
public class Code02_NumberOfGoodPaths {

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

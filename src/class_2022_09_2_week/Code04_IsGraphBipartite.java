package class_2022_09_2_week;

// 来自微软面试
// 给定一个长度为n的二维数组graph，代表一张图
// graph[i] = {a,b,c,d} 表示i讨厌(a,b,c,d)，讨厌关系为双向的
// 一共有n个人，编号0~n-1
// 讨厌的人不能一起开会
// 返回所有人能不能分成两组开会
// 测试链接 : https://leetcode.cn/problems/is-graph-bipartite/
public class Code04_IsGraphBipartite {

	public boolean isBipartite(int[][] graph) {
		int n = graph.length;
		UnionFind uf = new UnionFind(n);
		for (int[] neighbours : graph) {
			for (int i = 1; i < neighbours.length; i++) {
				uf.union(neighbours[i - 1], neighbours[i]);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j : graph[i]) {
				if (uf.same(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	public static class UnionFind {
		private int[] f;
		private int[] s;
		private int[] h;

		public UnionFind(int n) {
			f = new int[n];
			s = new int[n];
			h = new int[n];
			for (int i = 0; i < n; i++) {
				f[i] = i;
				s[i] = 1;
			}
		}

		private int find(int i) {
			int hi = 0;
			while (i != f[i]) {
				h[hi++] = i;
				i = f[i];
			}
			while (hi > 0) {
				f[h[--hi]] = i;
			}
			return i;
		}

		public boolean same(int i, int j) {
			return find(i) == find(j);
		}

		public void union(int i, int j) {
			int fi = find(i);
			int fj = find(j);
			if (fi != fj) {
				if (s[fi] >= s[fj]) {
					f[fj] = fi;
					s[fi] = s[fi] + s[fj];
				} else {
					f[fi] = fj;
					s[fj] = s[fi] + s[fj];
				}
			}
		}

	}

}

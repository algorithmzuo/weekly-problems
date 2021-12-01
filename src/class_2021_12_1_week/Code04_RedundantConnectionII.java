package class_2021_12_1_week;

// 来自微软，真正考的时候阉割了难度
// 测试链接 : https://leetcode.com/problems/redundant-connection-ii/
public class Code04_RedundantConnectionII {

	public static int[] findRedundantDirectedConnection(int[][] edges) {
		int N = edges.length;
		UnionFind uf = new UnionFind(N);
		int[] pre = new int[N + 1];
		int[] first = null;
		int[] second = null;
		int[] circle = null;
		for (int i = 0; i < N; i++) {
			int from = edges[i][0];
			int to = edges[i][1];
			if (pre[to] != 0) { // 不止一次到达
				first = new int[] { pre[to], to };
				second = edges[i];
			} else { // 就是第一次到达
				pre[to] = from;
				if (uf.same(from, to)) {
					circle = edges[i];
				} else {
					uf.union(from, to);
				}
			}
		}
		// 重点解析！这是啥？？？
		return first != null ? (circle != null ? first : second) : circle;
	}

	public static class UnionFind {
		private int[] f;
		private int[] s;
		private int[] h;

		public UnionFind(int N) {
			f = new int[N + 1];
			s = new int[N + 1];
			h = new int[N + 1];
			for (int i = 0; i <= N; i++) {
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

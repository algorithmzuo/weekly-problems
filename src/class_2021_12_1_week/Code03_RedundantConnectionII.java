package class_2021_12_1_week;

// 来自微软，真正考的时候阉割了难度
// 测试链接 : https://leetcode.com/problems/redundant-connection-ii/
public class Code03_RedundantConnectionII {

	// [
	//    [1 , 5]   1 > 5
	//    [7 , 3]   7 > 3
	
	
	// ]
	// 边的数量 = 点的数量！
	public static int[] findRedundantDirectedConnection(int[][] edges) {
		// N是点的数量
		// 点的编号，1~N，没有0
		int N = edges.length;
		// 并查集！N个点，去初始化，每个点各自是一个集合
		UnionFind uf = new UnionFind(N);
		// pre[i] = 0 来到i节点是第一次
		// pre[i] = 6 之前来过i，是从6来的！
		int[] pre = new int[N + 1];
		
		// 如果，没有入度为2的点，
		// first second 都维持是null
		// 如果，有入度为2的点，那么也只可能有一个
		// 比如入度为2的点，是5
		// first = [3,5]
		// second = [12,5]
		int[] first = null;
		int[] second = null;
		// 有没有环！非常不单纯！含义复杂！
		int[] circle = null;
		for (int i = 0; i < N; i++) { // 遍历每条边！
			int from = edges[i][0];
			int to = edges[i][1];
			if (pre[to] != 0) { // 不止一次来过to！
				first = new int[] { pre[to], to };
				second = edges[i];
			} else { // 第一次到达to，
				pre[to] = from;
				if (uf.same(from, to)) {
					circle = edges[i];
				} else {
					uf.union(from, to);
				}
			}
		}
		// 重点解析！这是啥？？？
		// first != null
		// 有入度为2的点！
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

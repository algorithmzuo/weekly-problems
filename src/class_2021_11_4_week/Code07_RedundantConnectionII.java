package class_2021_11_4_week;

// 测试链接 : https://leetcode.com/problems/redundant-connection-ii/
public class Code07_RedundantConnectionII {

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
		// circle表示，有没有第一次到达某个to节点，就发现环的情况，有的话记下来，circle = 此时产生环的边
		// 没有的话，circle维持null的状态
		// 也就是说，
		// 客观上如果就没有环，circle == null
		// 来到某个节点第二次的时候出现环，circle == null(因为只会在第一次来的时候赋值)
		// 只有第一次来到某个节点的时候发现了环，circle != null(被赋值)
		// first和second同时设置，要么一起为空，要么一起不为空，表示是否有某个节点入度为2。
		// first是第一次来到to的边，second是第二次来到to的边
		// 情况一：没有某个节点入度为2(first == null)。此时要删除的点就是circle。因为只有它多余。
		// 情况二：有某个节点入度为2(first != null)，且circle != null
		// 只有来到某个节点第一次的时候，发现了环，circle != null
		// 那就是first产生的环啊，删掉first
		// 情况三：有某个节点入度为2(first != null)，且circle == null
		// 客观上如果就没有环，circle == null，那删除掉second，因为它比first晚出现
		// 来到某个节点第二次的时候出现环，circle == null，那也删除掉second，因为环就是它产生的
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

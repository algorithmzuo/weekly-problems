package class_2022_11_2_week;

// 测试链接 : https://leetcode.cn/problems/couples-holding-hands/
public class Code03_CouplesHoldingHands {

	public int minSwapsCouples(int[] row) {
		int n = row.length;
		UnionFind uf = new UnionFind(n / 2);
		for (int i = 0; i < n; i += 2) {
			uf.union(row[i] / 2, row[i + 1] / 2);
		}
		return n / 2 - uf.sets();
	}

	public static class UnionFind {
		public int[] father;
		public int[] size;
		public int[] help;
		public int sets;

		public UnionFind(int n) {
			father = new int[n];
			size = new int[n];
			help = new int[n];
			for (int i = 0; i < n; i++) {
				father[i] = i;
				size[i] = 1;
			}
			sets = n;
		}

		private int find(int i) {
			int hi = 0;
			while (i != father[i]) {
				help[hi++] = i;
				i = father[i];
			}
			while (hi != 0) {
				father[help[--hi]] = i;
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
				sets--;
			}
		}

		public int sets() {
			return sets;
		}

	}

}

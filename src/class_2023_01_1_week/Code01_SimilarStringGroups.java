package class_2023_01_1_week;

// 测试链接 : https://leetcode.cn/problems/similar-string-groups/
public class Code01_SimilarStringGroups {

	public static int numSimilarGroups(String[] strs) {
		int n = strs.length;
		int m = strs[0].length();
		UnionFind uf = new UnionFind(n);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (uf.find(i) != uf.find(j)) {
					int diff = 0;
					for (int k = 0; k < m && diff < 3; k++) {
						if (strs[i].charAt(k) != strs[j].charAt(k)) {
							diff++;
						}
					}
					if (diff == 0 || diff == 2) {
						uf.union(i, j);
					}
				}
			}
		}
		return uf.sets;
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

		public int find(int i) {
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

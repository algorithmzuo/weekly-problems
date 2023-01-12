package class_2023_01_2_week;

// 如果交换字符串 X 中的两个不同位置的字母，使得它和字符串 Y 相等，
// 那么称 X 和 Y 两个字符串相似。如果这两个字符串本身是相等的，那它们也是相似的。
// 例如，"tars" 和 "rats" 是相似的 (交换 0 与 2 的位置)；
// "rats" 和 "arts" 也是相似的，但是 "star" 不与 "tars"，"rats"，或 "arts" 相似。
// 总之，它们通过相似性形成了两个关联组：{"tars", "rats", "arts"} 和 {"star"}。
// 注意，"tars" 和 "arts" 是在同一组中，即使它们并不相似。
// 形式上，对每个组而言，要确定一个单词在组中，只需要这个词和该组中至少一个单词相似。
// 给你一个字符串列表 strs。列表中的每个字符串都是 strs 中其它所有字符串的一个字母异位词。
// 请问 strs 中有多少个相似字符串组？
// 测试链接 : https://leetcode.cn/problems/similar-string-groups/
public class Code01_SimilarStringGroups {

	public static int numSimilarGroups(String[] strs) {
		int n = strs.length;
		int m = strs[0].length();
		UnionFind uf = new UnionFind(n);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				// [i] [j]
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

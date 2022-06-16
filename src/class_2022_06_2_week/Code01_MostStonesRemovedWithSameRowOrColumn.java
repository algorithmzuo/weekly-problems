package class_2022_06_2_week;

import java.util.HashMap;

// n块石头放置在二维平面中的一些整数坐标点上
// 每个坐标点上最多只能有一块石头
// 如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头。
// 给你一个长度为 n 的数组 stones ，
// 其中 stones[i] = [xi, yi] 表示第 i 块石头的位置，
// 返回 可以移除的石子 的最大数量。
// 测试链接 : https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
public class Code01_MostStonesRemovedWithSameRowOrColumn {

	public static int removeStones(int[][] stones) {
		int n = stones.length;
		HashMap<Integer, Integer> rowPre = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> colPre = new HashMap<Integer, Integer>();
		UnionFind uf = new UnionFind(n);
		for (int i = 0; i < n; i++) {
			int x = stones[i][0];
			int y = stones[i][1];
			if (!rowPre.containsKey(x)) {
				rowPre.put(x, i);
			} else {
				uf.union(i, rowPre.get(x));
			}
			if (!colPre.containsKey(y)) {
				colPre.put(y, i);
			} else {
				uf.union(i, colPre.get(y));
			}
		}
		return n - uf.sets();
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

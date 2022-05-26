package class_2022_06_2_week;

import java.util.ArrayList;
import java.util.HashMap;

// 测试链接 : https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
public class Code01_MostStonesRemovedWithSameRowOrColumn {

	public static int removeStones(int[][] stones) {
		int N = stones.length;
		int row = 0;
		int col = 0;
		for (int i = 0; i < N; i++) {
			row = Math.max(row, stones[i][0]);
			col = Math.max(col, stones[i][1]);
		}
		int[] rowFirst = new int[row + 1];
		for (int i = 0; i <= row; i++) {
			rowFirst[i] = -1;
		}
		int[] colFirst = new int[col + 1];
		for (int i = 0; i <= col; i++) {
			colFirst[i] = -1;
		}
		UnionFind uf = new UnionFind();
		for (int i = 0; i < N; i++) {
			uf.finger(stones[i][0], stones[i][1]);
			if (rowFirst[stones[i][0]] == -1) {
				rowFirst[stones[i][0]] = i;
			} else {
				int[] pre = stones[rowFirst[stones[i][0]]];
				uf.union(pre[0], pre[1], stones[i][0], stones[i][1]);
			}
			if (colFirst[stones[i][1]] == -1) {
				colFirst[stones[i][1]] = i;
			} else {
				int[] pre = stones[colFirst[stones[i][1]]];
				uf.union(pre[0], pre[1], stones[i][0], stones[i][1]);
			}
		}
		return N - uf.sets();
	}

	public static class UnionFind {
		private HashMap<String, String> parent;
		private HashMap<String, Integer> size;
		private ArrayList<String> help;
		private int sets;

		public UnionFind() {
			parent = new HashMap<>();
			size = new HashMap<>();
			help = new ArrayList<>();
			sets = 0;
		}

		private String find(String cur) {
			while (!cur.equals(parent.get(cur))) {
				help.add(cur);
				cur = parent.get(cur);
			}
			for (String str : help) {
				parent.put(str, cur);
			}
			help.clear();
			return cur;
		}

		public void finger(int r, int c) {
			String key = String.valueOf(r + "_" + c);
			if (!parent.containsKey(key)) {
				parent.put(key, key);
				size.put(key, 1);
				sets++;
			}
		}

		public void union(int r1, int c1, int r2, int c2) {
			String s1 = String.valueOf(r1 + "_" + c1);
			String s2 = String.valueOf(r2 + "_" + c2);
			if (parent.containsKey(s1) && parent.containsKey(s2)) {
				String f1 = find(s1);
				String f2 = find(s2);
				if (!f1.equals(f2)) {
					int size1 = size.get(f1);
					int size2 = size.get(f2);
					String big = size1 >= size2 ? f1 : f2;
					String small = big == f1 ? f2 : f1;
					parent.put(small, big);
					size.put(big, size1 + size2);
					sets--;
				}
			}
		}

		public int sets() {
			return sets;
		}

	}

}

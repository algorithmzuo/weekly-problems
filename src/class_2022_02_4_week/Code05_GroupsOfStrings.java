package class_2022_02_4_week;

import java.util.HashMap;

// 测试链接 : https://leetcode.com/problems/groups-of-strings/
public class Code05_GroupsOfStrings {

	// 可能会超时，或者打败比例很低
	// 因为常数优化不到位
	public static int[] groupStrings1(String[] words) {
		int n = words.length;
		UnionFind uf = new UnionFind(n);
		int[] strs = new int[n];
		HashMap<Integer, Integer> stands = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int status = 0;
			for (char c : words[i].toCharArray()) {
				status |= 1 << (c - 'a');
			}
			strs[i] = status;
			if (stands.containsKey(status)) {
				uf.union(stands.get(status), i);
			} else {
				stands.put(status, i);
			}
		}
		for (int i = 0; i < n; i++) {
			int status = strs[i];
			for (int j = 0; j < 26; j++) {
				uf.union(i, stands.get(status | (1 << j)));
			}
			for (int j = 0; j < 26; j++) {
				if ((status & (1 << j)) != 0) {
					uf.union(i, stands.get(status ^ (1 << j)));
				}
			}
			for (int has = 0; has < 26; has++) {
				if ((status & (1 << has)) != 0) {
					status ^= 1 << has;
					for (int replace = 0; replace < 26; replace++) {
						uf.union(i, stands.get(status | (1 << replace)));
					}
					status |= 1 << has;
				}
			}
		}
		return new int[] { uf.sets(), uf.maxSize() };
	}

	// 肯定通过
	// 打败比例达标
	// 优化了常数时间
	public static int[] groupStrings2(String[] words) {
		int n = words.length;
		UnionFind uf = new UnionFind(n);
		int[] strs = new int[n];
		HashMap<Integer, Integer> stands = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int status = 0;
			for (char c : words[i].toCharArray()) {
				status |= 1 << (c - 'a');
			}
			strs[i] = status;
			if (stands.containsKey(status)) {
				uf.union(stands.get(status), i);
			} else {
				stands.put(status, i);
			}
		}
		for (int i = 0; i < n; i++) {
			int yes = strs[i];
			int no = (~yes) & ((1 << 26) - 1);
			int tmp1 = yes;
			int tmp2 = no;
			int rightOne1 = 0;
			int rightOne2 = 0;
			while (tmp1 != 0) {
				rightOne1 = tmp1 & (-tmp1);
				uf.union(i, stands.get(yes ^ rightOne1));
				tmp1 ^= rightOne1;
			}
			while (tmp2 != 0) {
				rightOne2 = tmp2 & (-tmp2);
				uf.union(i, stands.get(yes | rightOne2));
				tmp2 ^= rightOne2;
			}
			tmp1 = yes;
			while (tmp1 != 0) {
				rightOne1 = tmp1 & (-tmp1);
				tmp2 = no;
				while (tmp2 != 0) {
					rightOne2 = tmp2 & (-tmp2);
					uf.union(i, stands.get((yes ^ rightOne1) | rightOne2));
					tmp2 ^= rightOne2;
				}
				tmp1 ^= rightOne1;
			}
		}
		return new int[] { uf.sets(), uf.maxSize() };
	}

	public static class UnionFind {
		private int[] parent;
		private int[] size;
		private int[] help;

		public UnionFind(int N) {
			parent = new int[N];
			size = new int[N];
			help = new int[N];
			for (int i = 0; i < N; i++) {
				parent[i] = i;
				size[i] = 1;
			}
		}

		private int find(int i) {
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

		public void union(Integer i, Integer j) {
			if (i == null || j == null) {
				return;
			}
			int f1 = find(i);
			int f2 = find(j);
			if (f1 != f2) {
				if (size[f1] >= size[f2]) {
					size[f1] += size[f2];
					parent[f2] = f1;
				} else {
					size[f2] += size[f1];
					parent[f1] = f2;
				}
			}
		}

		public int sets() {
			int ans = 0;
			for (int i = 0; i < parent.length; i++) {
				ans += parent[i] == i ? 1 : 0;
			}
			return ans;
		}

		public int maxSize() {
			int ans = 0;
			for (int s : size) {
				ans = Math.max(ans, s);
			}
			return ans;
		}
	}

}

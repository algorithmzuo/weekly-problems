package class_2021_12_2_week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 链接测试 : https://leetcode.com/problems/find-all-people-with-secret/
public class Code02_FindAllPeopleWithSecret {

	public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
		UnionFind uf = new UnionFind(n, firstPerson);
		int m = meetings.length;
		Arrays.sort(meetings, (a, b) -> a[2] - b[2]);
		int[] help = new int[m << 1];
		help[0] = meetings[0][0];
		help[1] = meetings[0][1];
		int size = 2;
		for (int i = 1; i < m; i++) {
			if (meetings[i][2] != meetings[i - 1][2]) {
				setSecrets(help, size, uf);
				help[0] = meetings[i][0];
				help[1] = meetings[i][1];
				size = 2;
			} else {
				help[size++] = meetings[i][0];
				help[size++] = meetings[i][1];
			}
		}
		setSecrets(help, size, uf);
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (uf.know(i)) {
				ans.add(i);
			}
		}
		return ans;
	}

	public static void setSecrets(int[] help, int size, UnionFind uf) {
		for (int i = 0; i < size; i += 2) {
			uf.union(help[i], help[i + 1]);
		}
		for (int i = 0; i < size; i++) {
			if (!uf.know(help[i])) {
				uf.isolate(help[i]);
			}
		}
	}

	public static class UnionFind {
		public int[] father;
		public boolean[] sect;
		public int[] help;

		public UnionFind(int n, int first) {
			father = new int[n];
			sect = new boolean[n];
			help = new int[n];
			for (int i = 1; i < n; i++) {
				father[i] = i;
			}
			father[first] = 0;
			sect[0] = true;
		}

		private int find(int i) {
			int hi = 0;
			while (i != father[i]) {
				help[hi++] = i;
				i = father[i];
			}
			for (hi--; hi >= 0; hi--) {
				father[help[hi]] = i;
			}
			return i;
		}

		public void union(int i, int j) {
			int fatheri = find(i);
			int fatherj = find(j);
			if (fatheri != fatherj) {
				father[fatherj] = fatheri;
				sect[fatheri] |= sect[fatherj];
			}
		}

		public boolean know(int i) {
			return sect[find(i)];
		}

		public void isolate(int i) {
			father[i] = i;
		}

	}

}

package class_2022_03_2_week;

import java.util.ArrayList;
import java.util.HashMap;

// 来自字节飞书团队
// 假设数组a和数组b为两组信号
// 1) length(b) <= length(a)
// 2) 对于任意0<=i<length(b), 有b[i+1] - b[i] == a[i+1] - a[i]
// 那么就称信号b和信号a一致，记为b==a
// 给你好多b数组，假设有m个: b0数组、b1数组...
// 给你好多a数组，假设有n个: a0数组、a1数组...
// 返回一个长度为m的结果数组ans，ans[i]表示 : bi数组和多少个a数组一致
public class Code04_SameTeams {

	public static class TrieNode {
		public ArrayList<Integer> indices;
		public HashMap<Integer, TrieNode> nexts;

		public TrieNode() {
			indices = new ArrayList<>();
			nexts = new HashMap<>();
		}

	}

	public static int[] sameTeamsArray(int[][] bs, int[][] as) {
		int m = bs.length;
		TrieNode root = new TrieNode();
		TrieNode cur = null;
		for (int i = 0; i < m; i++) {
			int k = bs[i].length;
			cur = root;
			for (int j = 1; j < k; j++) {
				int diff = bs[i][j] - bs[i][j - 1];
				if (!cur.nexts.containsKey(diff)) {
					cur.nexts.put(diff, new TrieNode());
				}
				cur = cur.nexts.get(diff);
			}
			cur.indices.add(i);
		}
		int[] ans = new int[m];
		int n = as.length;
		for (int i = 0; i < n; i++) {
			int k = as[i].length;
			cur = root;
			for (int j = 1; j < k; j++) {
				int diff = as[i][j] - as[i][j - 1];
				if (!cur.nexts.containsKey(diff)) {
					break;
				}
				cur = cur.nexts.get(diff);
				for (int index : cur.indices) {
					ans[index]++;
				}
			}
		}
		return ans;
	}

}

package class_2023_08_1_week;

import java.util.LinkedList;

// 测试链接 : https://leetcode.cn/problems/stream-of-characters/
public class Code03_StreamOfCharacters {

	// 就是AC自动机阉割版
	class StreamChecker {

		class Node {
			public boolean end;
			public Node fail;
			public Node[] nexts;

			public Node() {
				end = false;
				fail = null;
				nexts = new Node[26];
			}
		}

		public Node root;

		public Node now;

		private void insert(String s) {
			char[] str = s.toCharArray();
			Node cur = root;
			int index = 0;
			for (int i = 0; i < str.length; i++) {
				index = str[i] - 'a';
				if (cur.nexts[index] == null) {
					cur.nexts[index] = new Node();
				}
				cur = cur.nexts[index];
			}
			cur.end = true;
		}

		private void build() {
			LinkedList<Node> queue = new LinkedList<>();
			queue.add(root);
			Node cur = null;
			Node cfail = null;
			while (!queue.isEmpty()) {
				cur = queue.poll();
				for (int i = 0; i < 26; i++) {
					if (cur.nexts[i] != null) {
						cur.nexts[i].fail = root;
						cfail = cur.fail;
						while (cfail != null) {
							if (cfail.nexts[i] != null) {
								cur.nexts[i].fail = cfail.nexts[i];
								break;
							}
							cfail = cfail.fail;
						}
						queue.add(cur.nexts[i]);
					}
				}
			}
		}

		public StreamChecker(String[] words) {
			root = new Node();
			for (String w : words) {
				insert(w);
			}
			build();
			now = root;
		}

		public boolean query(char letter) {
			int index = 0;
			index = letter - 'a';
			while (now.nexts[index] == null && now != root) {
				now = now.fail;
			}
			now = now.nexts[index] != null ? now.nexts[index] : root;
			Node follow = now;
			while (follow != root) {
				if (follow.end) {
					return true;
				}
				follow = follow.fail;
			}
			return false;
		}

	}

}

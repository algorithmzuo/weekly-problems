package class_2022_12_4_week;

import java.util.HashSet;
import java.util.PriorityQueue;

// 测试链接 : https://leetcode.cn/problems/k-similar-strings/
public class Code05_KSimilarStrings {

	public static int len;

	public static int kSimilarity(String s1, String s2) {
		len = s1.length();
		PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> (a.cost + a.guess) - (b.cost + b.guess));
		HashSet<String> visited = new HashSet<>();
		heap.add(new Node(0, 0, 0, s1));
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			if (visited.contains(cur.str)) {
				continue;
			}
			if (cur.str.equals(s2)) {
				return cur.cost;
			}
			visited.add(cur.str);
			int firstDiff = cur.compare;
			while (cur.str.charAt(firstDiff) == s2.charAt(firstDiff)) {
				firstDiff++;
			}
			char[] curStr = cur.str.toCharArray();
			for (int i = firstDiff + 1; i < len; i++) {
				if (curStr[i] == s2.charAt(firstDiff) && curStr[i] != s2.charAt(i)) {
					swap(curStr, firstDiff, i);
					add(String.valueOf(curStr), s2, cur.cost + 1, firstDiff + 1, heap, visited);
					swap(curStr, firstDiff, i);
				}
			}
		}
		return -1;
	}

	public static void swap(char[] s, int i, int j) {
		char tmp = s[i];
		s[i] = s[j];
		s[j] = tmp;
	}

	// 估值函数
	// 看每周有营养的大厂算法面试题，2022年1月第3周
	// 估值函数的估计值要绝对 <= 真实距离
	// 但又要确保估计值足够大足够接近真实距离，这样效果最好
	public static int evaluate(String s1, String s2, int index) {
		int diff = 0;
		for (int i = index; i < len; i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				diff++;
			}
		}
		return (diff + 1) / 2;
	}

	public static void add(String add, String s2, int cost, int index, PriorityQueue<Node> heap,
			HashSet<String> visited) {
		if (!visited.contains(add)) {
			heap.add(new Node(cost, evaluate(add, s2, index), index, add));
		}
	}

	public static class Node {
		public int cost;
		public int guess;
		public int compare;
		public String str;

		public Node(int r, int g, int i, String s) {
			cost = r;
			guess = g;
			compare = i;
			str = s;
		}
	}

}

package class_2022_12_4_week;

import java.util.HashSet;
import java.util.PriorityQueue;

// 对于某些非负整数 k ，如果交换 s1 中两个字母的位置恰好 k 次
// 能够使结果字符串等于 s2 ，则认为字符串 s1 和 s2 的 相似度为 k
// 给你两个字母异位词 s1 和 s2 ，返回 s1 和 s2 的相似度 k 的最小值
// 测试链接 : https://leetcode.cn/problems/k-similar-strings/
public class Code06_KSimilarStrings {

	public static class Node {
		public int cost; // 代价，已经换了几回了！
		public int guess;// 猜测还要换几回，能变对！
		public int where;// 有必须去比对的下标，左边不再换了！
		public String str; // 当前的字符

		public Node(int r, int g, int i, String s) {
			cost = r;
			guess = g;
			where = i;
			str = s;
		}
	}

	public static int kSimilarity(String s1, String s2) {
		int len = s1.length();
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
			int firstDiff = cur.where;
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

	public static void add(String add, String s2, int cost, int index, PriorityQueue<Node> heap,
			HashSet<String> visited) {
		if (!visited.contains(add)) {
			heap.add(new Node(cost, evaluate(add, s2, index), index, add));
		}
	}

	// 估值函数
	// 看每周有营养的大厂算法面试题，2022年1月第3周
	// 估值函数的估计值要绝对 <= 真实距离
	// 但又要确保估计值足够大足够接近真实距离，这样效果最好
	public static int evaluate(String s1, String s2, int index) {
		int diff = 0;
		for (int i = index; i < s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				diff++;
			}
		}
		return (diff + 1) / 2;
	}

}

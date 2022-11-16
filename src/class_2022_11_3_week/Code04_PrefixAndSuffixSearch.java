package class_2022_11_3_week;

import java.util.ArrayList;

// 设计一个包含一些单词的特殊词典，并能够通过前缀和后缀来检索单词。
// 实现 WordFilter 类：
// WordFilter(string[] words) 使用词典中的单词 words 初始化对象
// f(string pref, string suff) 
// 返回词典中具有前缀 prefix 和后缀 suff 的单词的下标
// 如果存在不止一个满足要求的下标，返回其中 最大的下标
// 如果不存在这样的单词，返回 -1 。
// 测试链接 : https://leetcode.cn/problems/prefix-and-suffix-search/
public class Code04_PrefixAndSuffixSearch {

	// 提交以下这个类
	class WordFilter {

		class TrieNode {
			TrieNode[] nexts;
			ArrayList<Integer> indies;

			public TrieNode() {
				nexts = new TrieNode[26];
				indies = new ArrayList<>();
			}
		}

		TrieNode preHead;

		TrieNode sufHead;

		public WordFilter(String[] words) {
			preHead = new TrieNode();
			sufHead = new TrieNode();
			for (int i = 0; i < words.length; i++) {
				String word = words[i];
				TrieNode cur = preHead;
				for (int j = 0; j < word.length(); j++) {
					int path = word.charAt(j) - 'a';
					if (cur.nexts[path] == null) {
						cur.nexts[path] = new TrieNode();
					}
					cur = cur.nexts[path];
					cur.indies.add(i);
				}
				cur = sufHead;
				for (int j = word.length() - 1; j >= 0; j--) {
					int path = word.charAt(j) - 'a';
					if (cur.nexts[path] == null) {
						cur.nexts[path] = new TrieNode();
					}
					cur = cur.nexts[path];
					cur.indies.add(i);
				}
			}
		}

		public int f(String pref, String suff) {
			ArrayList<Integer> preList = null;
			TrieNode cur = preHead;
			for (int i = 0; i < pref.length() && cur != null; i++) {
				cur = cur.nexts[pref.charAt(i) - 'a'];
			}
			if (cur != null) {
				preList = cur.indies;
			}
			if (preList == null) {
				return -1;
			}
			ArrayList<Integer> sufList = null;
			cur = sufHead;
			for (int i = suff.length() - 1; i >= 0 && cur != null; i--) {
				cur = cur.nexts[suff.charAt(i) - 'a'];
			}
			if (cur != null) {
				sufList = cur.indies;
			}
			if (sufList == null) {
				return -1;
			}
			ArrayList<Integer> small = preList.size() <= sufList.size() ? preList : sufList;
			ArrayList<Integer> big = small == preList ? sufList : preList;
			for (int i = small.size() - 1; i >= 0; i--) {
				if (bs(big, small.get(i))) {
					return small.get(i);
				}
			}
			return -1;
		}

		private boolean bs(ArrayList<Integer> sorted, int num) {
			int l = 0;
			int r = sorted.size() - 1;
			int m = 0;
			int midValue = 0;
			while (l <= r) {
				m = (l + r) / 2;
				midValue = sorted.get(m);
				if (midValue == num) {
					return true;
				} else if (midValue < num) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			return false;
		}
	}

}

package class_2022_08_1_week;

import java.util.ArrayList;
import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/number-of-matching-subsequences/
public class Code04_NumberOfMatchingSubsequences {

	public static class Word {
		public char[] s;
		public int i;

		public Word(String str) {
			s = str.toCharArray();
			i = 0;
		}

		public char want() {
			return i == s.length ? 0 : s[i];
		}
	}

	public static int numMatchingSubseq1(String s, String[] words) {
		ArrayList<ArrayList<Word>> buckets = new ArrayList<>();
		ArrayList<ArrayList<Word>> nexts = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			buckets.add(new ArrayList<>());
			nexts.add(new ArrayList<>());
		}
		for (String word : words) {
			Word cur = new Word(word);
			buckets.get(cur.want() - 'a').add(cur);
		}
		int ans = 0;
		for (char x : s.toCharArray()) {
			for (Word w : buckets.get(x - 'a')) {
				w.i++;
				if (w.want() == 0) {
					ans++;
				} else {
					nexts.get(w.want() - 'a').add(w);
				}
			}
			buckets.get(x - 'a').clear();
			for (int i = 0; i < 26; i++) {
				for (Word w : nexts.get(i)) {
					buckets.get(i).add(w);
				}
				nexts.get(i).clear();
			}
		}
		return ans;
	}

	public static int[][] buckets = new int[26][5001];
	public static int[] lefts = new int[26];
	public static int[] rights = new int[26];
	public static int[] forwards = new int[5001];

	public static int numMatchingSubseq2(String s, String[] words) {
		Arrays.fill(lefts, 0);
		Arrays.fill(rights, 0);
		for (int i = 0; i < words.length; i++) {
			int first = words[i].charAt(0) - 'a';
			buckets[first][rights[first]] = i;
			rights[first]++;
			forwards[i] = 0;
		}
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			int cur = s.charAt(i) - 'a';
			int tmp = rights[cur];
			for (; lefts[cur] < tmp; lefts[cur]++) {
				int wi = buckets[cur][lefts[cur]];
				++forwards[wi];
				if (forwards[wi] == words[wi].length()) {
					ans++;
				} else {
					int next = words[wi].charAt(forwards[wi]) - 'a';
					buckets[next][rights[next]] = wi;
					rights[next]++;
				}
			}
		}
		return ans;
	}

}

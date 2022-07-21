package class_2022_08_1_week;

import java.util.ArrayList;
import java.util.Arrays;

public class Code03_NumberOfMatchingSubsequences {

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

	public static int[][] wbuckets = new int[26][5001];
	public static int[][] ibuckets = new int[26][5001];
	public static int[] l = new int[26];
	public static int[] r = new int[26];

	public static int numMatchingSubseq2(String s, String[] words) {
		Arrays.fill(l, 0);
		Arrays.fill(r, 0);
		for (int i = 0; i < words.length; i++) {
			int first = words[i].charAt(0) - 'a';
			wbuckets[first][r[first]] = i;
			ibuckets[first][r[first]] = 0;
			r[first]++;
		}
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			int cur = s.charAt(i) - 'a';
			int tmp = r[cur];
			for (; l[cur] < tmp; l[cur]++) {
				int wi = wbuckets[cur][l[cur]];
				int ci = ibuckets[cur][l[cur]] + 1;
				if (ci == words[wi].length()) {
					ans++;
				} else {
					int next = words[wi].charAt(ci) - 'a';
					wbuckets[next][r[next]] = wi;
					ibuckets[next][r[next]] = ci;
					r[next]++;
				}
			}
		}
		return ans;
	}

}

package class_2023_07_1_week;

// 测试链接 : https://leetcode.cn/problems/sentence-similarity-iii/
public class Code01_SentenceSimilarityIII {

	public static boolean areSentencesSimilar(String s1, String s2) {
		String[] w1 = s1.split(" ");
		String[] w2 = s2.split(" ");
		int i = 0, j = 0, n1 = w1.length, n2 = w2.length;
		while (i < n1 && i < n2 && w1[i].equals(w2[i])) {
			i++;
		}
		while (n1 - j > i && n2 - j > i && w1[n1 - 1 - j].equals(w2[n2 - 1 - j])) {
			j++;
		}
		return i + j == Math.min(n1, n2);
	}

}

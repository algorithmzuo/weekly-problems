package class_2023_06_1_week;

// 测试链接 : https://leetcode.cn/problems/remove-duplicate-letters/
// 大厂刷题班讲过，不过那时没有讲出最优解，安排一下重讲
public class Code04_RemoveDuplicateLetters {

	public static String removeDuplicateLetters(String s) {
		int[] cnts = new int[26];
		boolean[] enter = new boolean[26];
		for (int i = 0; i < s.length(); i++) {
			cnts[s.charAt(i) - 'a']++;
		}
		char[] stack = new char[26];
		int size = 0;
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (!enter[cur - 'a']) {
				enter[cur - 'a'] = true;
				while (size > 0 && stack[size - 1] > cur && cnts[stack[size - 1] - 'a'] > 0) {
					enter[stack[size - 1] - 'a'] = false;
					size--;
				}
				stack[size++] = cur;
			}
			cnts[cur - 'a']--;
		}
		return String.valueOf(stack, 0, size);
	}

}

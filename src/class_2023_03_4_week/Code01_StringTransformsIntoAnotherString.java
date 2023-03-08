package class_2023_03_4_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/string-transforms-into-another-string/
public class Code01_StringTransformsIntoAnotherString {

	public static boolean canConvert(String str1, String str2) {
		if (str1.equals(str2)) {
			return true;
		}
		int[] map = new int[26];
		int kinds = 0;
		for (int i = 0; i < str2.length(); i++) {
			if (map[str2.charAt(i) - 'a']++ == 0) {
				kinds++;
			}
		}
		if (kinds == 26) {
			return false;
		}
		Arrays.fill(map, -1);
		for (int i = 0; i < str1.length(); i++) {
			int cur = str1.charAt(i) - 'a';
			if (map[cur] != -1 && str2.charAt(map[cur]) != str2.charAt(i)) {
				return false;
			}
			map[cur] = i;
		}
		return true;
	}

}

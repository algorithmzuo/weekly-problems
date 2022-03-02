package class_2022_03_3_week;

import java.util.Arrays;

// 来自字节飞书团队
// 小歪每次会给你两个字符串：
// 笔记s1和关键词s2，请你写一个函数
// 判断s2的排列之一是否是s1的子串
// 如果是，返回true
// 否则，返回false
public class Code02_StringCheck {

	public static boolean check1(String s1, String s2) {
		if (s1.length() < s2.length()) {
			return false;
		}
		char[] str2 = s2.toCharArray();
		Arrays.sort(str2);
		s2 = String.valueOf(str2);
		for (int L = 0; L < s1.length(); L++) {
			for (int R = L; R < s1.length(); R++) {
				char[] cur = s1.substring(L, R + 1).toCharArray();
				Arrays.sort(cur);
				String curSort = String.valueOf(cur);
				if (curSort.equals(s2)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean check2(String s1, String s2) {
		if (s1.length() < s2.length()) {
			return false;
		}
		char[] str2 = s2.toCharArray();
		int[] count = new int[256];
		for (int i = 0; i < str2.length; i++) {
			count[str2[i]]++;
		}
		int M = str2.length;
		char[] st1 = s1.toCharArray();
		int inValidTimes = 0;
		int R = 0;
		for (; R < M; R++) {
			if (count[st1[R]]-- <= 0) {
				inValidTimes++;
			}
		}
		for (; R < st1.length; R++) {
			if (inValidTimes == 0) {
				return true;
			}
			if (count[st1[R]]-- <= 0) {
				inValidTimes++;
			}
			if (count[st1[R - M]]++ < 0) {
				inValidTimes--;
			}
		}
		return inValidTimes == 0;
	}

}

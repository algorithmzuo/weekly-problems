package class_2022_07_3_week;

import java.util.ArrayList;

// 测试链接 : https://leetcode.com/problems/special-binary-string/
public class Code04_SpecialBinaryString {

	public static String makeLargestSpecial(String s) {
		ArrayList<String> arr = new ArrayList<>();
		for (int index = 0; index < s.length();) {
			Info info = process(s, index + 1);
			arr.add(info.ans);
			index = info.end + 1;
		}
		StringBuilder builder = new StringBuilder();
		arr.sort((a, b) -> b.compareTo(a));
		for (String cur : arr) {
			builder.append(cur);
		}
		return builder.toString();
	}

	public static class Info {
		public String ans;
		public int end;

		public Info(String a, int e) {
			ans = a;
			end = e;
		}
	}

	public static Info process(String s, int index) {
		ArrayList<String> arr = new ArrayList<>();
		while (index != s.length() && s.charAt(index) != '0') {
			Info info = process(s, index + 1);
			arr.add(info.ans);
			index = info.end + 1;
		}
		StringBuilder builder = new StringBuilder();
		arr.sort((a, b) -> b.compareTo(a));
		for (String cur : arr) {
			builder.append(cur);
		}
		return new Info("1" + builder.toString() + "0", index);
	}

}

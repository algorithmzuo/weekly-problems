package class_2022_07_3_week;

import java.util.ArrayList;

// 特殊的二进制序列是具有以下两个性质的二进制序列：
// 0 的数量与 1 的数量相等。
// 二进制序列的每一个前缀码中 1 的数量要大于等于 0 的数量。
// 给定一个特殊的二进制序列 S，以字符串形式表示。
// 定义一个操作 为首先选择 S 的两个连续且非空的特殊的子串，然后将它们交换。
// (两个子串为连续的当且仅当第一个子串的最后一个字符恰好为第二个子串的第一个字符的前一个字符)
// 在任意次数的操作之后，交换后的字符串按照字典序排列的最大的结果是什么？
// 测试链接 : https://leetcode.cn/problems/special-binary-string/
public class Code04_SpecialBinaryString {

	public static String makeLargestSpecial(String s) {
		ArrayList<String> arr = new ArrayList<>();
		// 主！
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

	// process(i)
	public static Info process(String s, int index) {
		ArrayList<String> arr = new ArrayList<>();
		// index 不能是 ) -> 0
		while (s.charAt(index) != '0') {
			// index ( -> 1
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

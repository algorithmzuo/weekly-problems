package class_2022_12_1_week;

// 将一个给定字符串 s 根据给定的行数 numRows
// 以从上往下、从左到右进行 Z 字形排列
// 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下
// P   A   H   N
// A P L S I I G
// Y   I   R
// 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串
// "PAHNAPLSIIGYIR"
// 请你实现这个将字符串进行指定行数变换的函数
// string convert(string s, int numRows)
// 测试链接 : https://leetcode.cn/problems/zigzag-conversion/
public class Code02_ZigZagConversion {

	public static String convert(String s, int m) {
		int n = s.length();
		if (m == 1 || m >= n) {
			return s;
		}
		int t = m * 2 - 2;
		char[] ans = new char[n];
		int fill = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j + i < n; j += t) {
				ans[fill++] = s.charAt(j + i);
				if (i >= 1 && i <= m - 2 && j + t - i < n) {
					ans[fill++] = s.charAt(j + t - i);
				}
			}
		}
		return String.valueOf(ans);
	}

}

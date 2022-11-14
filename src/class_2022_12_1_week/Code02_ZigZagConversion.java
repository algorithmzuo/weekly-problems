package class_2022_12_1_week;

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

package class_2022_08_5_week;

// 测试链接 : https://leetcode.cn/problems/construct-smallest-number-from-di-string/
public class Code03_CreateMinNumberFromPattern {

	public static String smallestNumber(String pattern) {
		return String.valueOf(create(pattern.toCharArray(), 0, 0, 0));
	}

	public static int create(char[] p, int i, int s, int n) {
		if (i == p.length + 1) {
			return n;
		}
		int cur = 0;
		while ((cur = next(s, cur)) != -1) {
			if (i == 0 || (p[i - 1] == 'I' && n % 10 < cur) || (p[i - 1] == 'D' && n % 10 > cur)) {
				int ans = create(p, i + 1, s | (1 << cur), n * 10 + cur);
				if (ans != -1) {
					return ans;
				}
			}
		}
		return -1;
	}

	public static int next(int s, int num) {
		for (int i = num + 1; i <= 9; i++) {
			if ((s & (1 << i)) == 0) {
				return i;
			}
		}
		return -1;
	}

}

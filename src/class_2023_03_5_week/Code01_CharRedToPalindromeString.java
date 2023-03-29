package class_2023_03_5_week;

// 来百度
// 用r、e、d三种字符，拼出一个回文子串数量等于x的字符串
// 1 <= x <= 10^5
public class Code01_CharRedToPalindromeString {

	public static String palindromeX(int x) {
		StringBuilder builder = new StringBuilder();
		char cur = 'r';
		while (x > 0) {
			int number = near(x);
			for (int i = 0; i < number; i++) {
				builder.append(cur);
			}
			x -= number * (number + 1) / 2;
			cur = cur == 'r' ? 'e' : (cur == 'e' ? 'd' : 'r');
		}
		return builder.toString();
	}

	public static int near(int x) {
		int l = 1;
		int r = x;
		int m, ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (ok(m, x)) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static boolean ok(int n, int x) {
		return ((long) n * (n + 1) / 2) <= x;
	}

	public static void main(String[] args) {
		int x = 27380;
		System.out.println(palindromeX(x));
	}

}

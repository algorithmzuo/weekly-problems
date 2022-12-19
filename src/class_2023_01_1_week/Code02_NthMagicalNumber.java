package class_2023_01_1_week;

// 测试链接 : https://leetcode.cn/problems/nth-magical-number/
public class Code02_NthMagicalNumber {

	public static int nthMagicalNumber(int n, int a, int b) {
		long lcm = (long)a / gcd(a, b) * b;
		long ans = 0;
		for (long l = 0, r = (long) n * Math.min(a, b), m = 0; l <= r;) {
			m = (l + r) / 2;
			if (m / a + m / b - m / lcm >= n) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return (int) (ans % 1000000007);
	}

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

}

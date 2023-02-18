package class_2023_02_4_week;

// 测试链接 : https://leetcode.cn/problems/numbers-with-repeated-digits/
public class Code03_NumbersWithRepeatedDigits {

	public static int numDupDigitsAtMostN(int n) {
		if (n <= 10) {
			return 0;
		}
		int len = 1;
		int offset = 1;
		int tmp = n;
		tmp /= 10;
		while (tmp > 0) {
			len++;
			offset *= 10;
			tmp /= 10;
		}
		int noRepeat = 0;
		for (int i = 1; i < len; i++) {
			noRepeat += numAllLength(i);
		}
		if (len <= 10) {
			int status = 0b1111111111;
			noRepeat += ((n / offset) - 1) * numberRest(offset / 10, status ^ 1);
			noRepeat += process(offset / 10, status ^ (1 << (n / offset)), n);
		}
		return n + 1 - noRepeat;
	}

	// 10进制长度必须为len的所有数中
	// 每一位数字都不重复的数有多少
	public static int numAllLength(int len) {
		if (len > 10) {
			return 0;
		}
		if (len == 1) {
			return 10;
		}
		int ans = 9;
		int cur = 9;
		while (--len > 0) {
			ans *= cur;
			cur--;
		}
		return ans;
	}

	public static int process(int offset, int status, int n) {
		if (offset == 0) {
			return 1;
		}
		int ans = 0;
		int first = (n / offset) % 10;
		for (int cur = 0; cur < Math.min(9, first); cur++) {
			if ((status & (1 << cur)) != 0) {
				ans += numberRest(offset / 10, status ^ (1 << cur));
			}
		}
		if ((status & (1 << first)) != 0) {
			ans += process(offset / 10, status ^ (1 << first), n);
		}
		return ans;
	}

	public static int numberRest(int offset, int status) {
		int c = hammingWeight(status);
		int ans = 1;
		while (offset > 0) {
			ans *= c;
			c--;
			offset /= 10;
		}
		return ans;
	}

	public static int hammingWeight(int n) {
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}

}

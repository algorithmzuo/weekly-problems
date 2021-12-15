package class_2021_12_3_week;

// 测试链接 : https://www.nowcoder.com/test/33701596/summary
// 本题目为第3题
// 核心方法，在大厂刷题班19节，第3题
public class Code03_OneCountsInKSystem {

	public static long minM(int n, int k) {
		int len = bits(n, k);
		long l = 1;
		long r = power(k, len + 1);
		long ans = r;
		while (l <= r) {
			long m = l + ((r - l) >> 1);
			if (ones(m, k) >= n) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static int bits(long num, int k) {
		int len = 0;
		while (num != 0) {
			len++;
			num /= k;
		}
		return len;
	}

	public static long power(long base, int power) {
		long ans = 1;
		while (power != 0) {
			if ((power & 1) != 0) {
				ans *= base;
			}
			base *= base;
			power >>= 1;
		}
		return ans;
	}

	public static long ones(long num, int k) {
		int len = bits(num, k);
		if (len <= 1) {
			return len;
		}
		long offset = power(k, len - 1);
		long first = num / offset;
		long curOne = first == 1 ? (num % offset) + 1 : offset;
		long restOne = first * (len - 1) * (offset / k);
		return curOne + restOne + ones(num % offset, k);
	}

}

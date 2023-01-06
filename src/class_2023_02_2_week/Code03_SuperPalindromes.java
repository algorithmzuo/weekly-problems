package class_2023_02_2_week;

// 测试链接 : https://leetcode.cn/problems/super-palindromes/
public class Code03_SuperPalindromes {

	public static int superpalindromesInRange(String left, String right) {
		long l = Long.valueOf(left);
		long r = Long.valueOf(right);
		long sqrtR = (long) Math.sqrt((double) r);
		int cnt = 0;
		long seed = 1;
		long enlarge = 0;
		do {
			enlarge = enlarge2(seed);
			if (isValid(enlarge * enlarge, l, r)) {
				cnt++;
			}
			enlarge = enlarge1(seed);
			if (isValid(enlarge * enlarge, l, r)) {
				cnt++;
			}
			seed++;
		} while (enlarge < sqrtR);
		return cnt;
	}

	public static long enlarge1(long seed) {
		long ans = seed;
		seed /= 10;
		while (seed != 0) {
			ans = ans * 10 + seed % 10;
			seed /= 10;
		}
		return ans;
	}

	public static long enlarge2(long seed) {
		long ans = seed;
		while (seed != 0) {
			ans = ans * 10 + seed % 10;
			seed /= 10;
		}
		return ans;
	}

	public static boolean isValid(long ans, long l, long r) {
		return isPalindrome(ans) && ans >= l && ans <= r;
	}

	public static boolean isPalindrome(long n) {
		long help = 1;
		while (n / help >= 10) {
			help *= 10;
		}
		while (n != 0) {
			if (n / help != n % 10) {
				return false;
			}
			n = (n % help) / 10;
			help /= 100;
		}
		return true;
	}

}

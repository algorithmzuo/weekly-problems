package class_2023_03_2_week;

// 测试链接 : https://leetcode.cn/problems/jian-sheng-zi-ii-lcof/
public class Code03_SplitNumberTimesMax {

	public static int mod = 1000000007;

	// x的n次方，% mod之后，是多少？
	public static long power(long x, int n) {
		long ans = 1;
		while (n > 0) {
			if ((n & 1) == 1) {
				ans = (ans * x) % mod;
			}
			x = (x * x) % mod;
			n >>= 1;
		}
		return ans;
	}

	public static int cuttingRope(int n) {
		if (n == 2) {
			return 1;
		}
		if (n == 3) {
			return 2;
		}
		int rest = n % 3 == 0 ? n : (n % 3 == 1 ? (n - 4) : (n - 2));
		int last = n % 3 == 0 ? 1 : (n % 3 == 1 ? 4 : 2);
		return (int) ((power(3, rest / 3) * last) % mod);
	}

}

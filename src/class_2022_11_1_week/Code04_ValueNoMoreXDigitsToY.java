package class_2022_11_1_week;

import java.util.Arrays;

// 来自CISCO
// 给定两个正整数x、y，都是int类型
// 返回0 ~ x以内，每位数字加起来是y的数字个数
// 比如，x = 20、y = 5，返回2
// 因为0 ~ x以内，每位数字加起来是5的数字有：5、14
// x、y范围是java里正整数的范围
public class Code04_ValueNoMoreXDigitsToY {

	// 暴力方法
	// 为了测试
	public static int num1(int x, int y) {
		int ans = 0;
		for (int i = 0; i <= x; i++) {
			if (check1(i, y)) {
				ans++;
			}
		}
		return ans;
	}

	public static boolean check1(int num, int y) {
		int sum = 0;
		while (num != 0) {
			sum += num % 10;
			num /= 10;
		}
		return sum == y;
	}

	// 正式方法
	// 时间复杂度O(整数有几个十进制位的平方 * 9)
	public static int num2(int x, int y) {
		if (x < 0 || y > 90) {
			return 0;
		}
		if (x == 0) {
			return y == 0 ? 1 : 0;
		}
		int offset = 1;
		int len = 1;
		while (offset <= x / 10) {
			offset *= 10;
			len++;
		}
		int[][] dp = new int[len + 1][y + 1];
		for (int i = 0; i <= len; i++) {
			Arrays.fill(dp[i], -1);
		}
		return count(x, offset, len, y, dp);
	}

	public static int count(int x, int offset, int len, int rest, int[][] dp) {
		if (len == 0) {
			return rest == 0 ? 1 : 0;
		}
		if (dp[len][rest] != -1) {
			return dp[len][rest];
		}
		int ans = 0;
		int cur = (x / offset) % 10;
		for (int i = 0; i < cur && i <= rest; i++) {
			ans += form[len - 1][rest - i];
		}
		if (cur <= rest) {
			ans += count(x, offset / 10, len - 1, rest - cur, dp);
		}
		dp[len][rest] = ans;
		return ans;
	}

	public static int[][] form = new int[11][91];
	static {
		form[0][0] = 1;
		for (int len = 1; len <= 10; len++) {
			for (int sum = 0; sum <= len * 9; sum++) {
				for (int cur = 0; cur <= 9 && cur <= sum; cur++) {
					form[len][sum] += form[len - 1][sum - cur];
				}
			}
		}
	}

	// 为了测试
	public static void main(String[] args) {
		int x = 88739128;
		int y = 37;
		System.out.println(num1(x, y));
		System.out.println(num2(x, y));
	}

}

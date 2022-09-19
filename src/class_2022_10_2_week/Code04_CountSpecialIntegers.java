package class_2022_10_2_week;

// 如果一个正整数每一个数位都是 互不相同 的，我们称它是 特殊整数 。
// 给你一个正整数 n ，请你返回区间 [1, n] 之间特殊整数的数目。
// 测试链接 : https://leetcode.cn/problems/count-special-integers/
public class Code04_CountSpecialIntegers {

	public static int[] offset = { 0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };

	public static int countSpecialNumbers(int n) {
		int len = len(n);
		int ans = 0;
		for (int i = 1; i < len; i++) {
			ans += all(i);
		}
		int firstNumber = n / offset[len];
		ans += (firstNumber - 1) * small(len - 1, 9);
		ans += process(n, len, len - 1, 1 << firstNumber);
		return ans;
	}

	// 返回n这个数字有几位
	public static int len(int n) {
		int ans = 0;
		while (n != 0) {
			ans++;
			n /= 10;
		}
		return ans;
	}

	// 返回所有bits位数，有几个特殊的
	public static int all(int bits) {
		int ans = 9;
		int cur = 9;
		while (--bits != 0) {
			ans *= cur--;
		}
		return ans;
	}

	public static int small(int bits, int candidates) {
		int ans = 1;
		for (int i = 0; i < bits; i++, candidates--) {
			ans *= candidates;
		}
		return ans;
	}

	// 哪些数字选了都在status里，用一个status变量表示数字选没选(位信息)
	public static int process(int num, int len, int rest, int status) {
		if (rest == 0) {
			return 1;
		}
		int cur = (num / offset[rest]) % 10;
		int cnt = 0;
		for (int i = 0; i < cur; i++) {
			if ((status & (1 << i)) == 0) {
				cnt++;
			}
		}
		int ans = cnt * small(rest - 1, 10 - len + rest - 1);
		if ((status & (1 << cur)) == 0) {
			ans += process(num, len, rest - 1, status | (1 << cur));
		}
		return ans;
	}

}

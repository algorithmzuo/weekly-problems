package class_2022_11_5_week;

// 给你一个整数 n ，
// 请你在无限的整数序列 [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...]
// 中找出并返回第 n 位上的数字。
// 测试链接 : https://leetcode.cn/problems/nth-digit/
// 1 <= n <= 2^31 - 1
public class Code02_NthDigit {

	public static long[] help1 = {
			0L,
			9L,
			189L,
			2889L,
			38889L,
			488889L,
			5888889L,
			68888889L,
			788888889L,
			8888888889L,
			98888888889L };

	public static int[] help2 = {
			0,
			1,
			10,
			100,
			1000,
			10000,
			100000,
			1000000,
			10000000,
			100000000,
			1000000000 };

	public static int findNthDigit(int n) {
		int bits = 0;
		for (int i = 1; i < help1.length; i++) {
			if (help1[i] >= n) {
				bits = i;
				break;
			}
		}
		return number(0, help2[bits], bits, help2[bits], (int) (n - help1[bits - 1]));
	}

	public static int number(int path, int rest, int bits, int offset, int nth) {
		if (rest == 0) {
			return (path / help2[nth]) % 10;
		} else {
			int cur = 0;
			int minus = 0;
			for (int i = rest == offset ? 1 : 0, j = 1; i <= 9; i++, j++) {
				long under = (long) j * bits * rest;
				if (under >= nth) {
					cur = i;
					break;
				}
				minus = (int) under;
			}
			return number(cur * (offset / rest) + path, rest / 10, bits, offset, nth - minus);
		}
	}

}

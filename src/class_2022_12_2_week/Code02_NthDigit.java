package class_2022_12_2_week;

// 测试链接 : https://leetcode.cn/problems/nth-digit/
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
			int curNumber = 0;
			int minus = 0;
			if (rest == offset) {
				for (int i = 1; i <= 9; i++) {
					long under = (long) i * bits * rest;
					if (under >= nth) {
						curNumber = i;
						break;
					}
					minus = (int) under;
				}
			} else {
				for (int i = 0; i <= 9; i++) {
					long under = (long) (i + 1) * bits * rest;
					if (under >= nth) {
						curNumber = i;
						break;
					}
					minus = (int) under;
				}
			}
			return number(curNumber * (offset / rest) + path, rest / 10, bits, offset, nth - minus);
		}
	}

}

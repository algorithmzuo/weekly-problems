package class_2022_11_5_week;

// 给你一个整数 n ，
// 请你在无限的整数序列 [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...]
// 中找出并返回第 n 位上的数字。
// 测试链接 : https://leetcode.cn/problems/nth-digit/
// 1 <= n <= 2^31 - 1
public class Code04_NthDigit {

	public static final long[] under = {
			0L,// 0位数，一共能解决几个位
			9L,// 1位数，一共能解决几个位
			189L,// 1~2位数，一共能解决几个位
			2889L,// 1~3位数，一共能解决几个位
			38889L,
			488889L,
			5888889L,
			68888889L,
			788888889L,
			8888888889L,
			98888888889L };

	public static final int[] help = {
			0,
			1, // 1
			10,// 2
			100,// 3
			1000, // 4
			10000,
			100000,
			1000000,
			10000000,
			100000000,
			1000000000 };

	public static int findNthDigit(int n) {
		int len = 0;
		for (int i = 1; i < under.length; i++) {
			if (under[i] >= n) {
				len = i;
				break;
			}
		}
		// 算出几位够用！
		// 5位数够用！
		// nth - 1~4位所有的整数，帮忙搞定的数字个数
		return number(0, len, help[len], help[len], (int) (n - under[len - 1]));
	}

	// path : 路径    左(低) <- 右(高)
	// len  : n -> 5位数 len = 5 固定！
	// offset : 10000 目前要决定的是高1位
	//           1000 目前要决定的是高2位
	//             10 目前要决定的是高2位
	//          可变
	// all    : 10000  固定
	// nth : 第几个
	public static int number(int path, int len, int offset, int all, int nth) {
		if (offset == 0) {
			// 5 3 2 1 6
			// path : 6 1 2 3 5
			//        5 4 3 2 1（高）
			return (path / help[nth]) % 10;
		} else {
			// 3 _ _ _ _
			int cur = 0;
			int minus = 0;
			// i是开始尝试的数字！  最高位  i 1 2 3 4...
			//                  不是最高位  0 1 2 3 4
			// 股数j = 1 2 3 4
			for (int i = offset == all ? 1 : 0, j = 1; i <= 9; i++, j++) {
				// 搞定了多少
				long under = (long) j * len * offset;
				if (under >= nth) {
					cur = i;
					break;
				}
				// < i不要
				minus = (int) under;
			}
			// 1458 - 1200
			// cur !
			// n - minus
			// 10000
			// !1000
			// !!100
			// !!!10
			return number(cur * (all / offset) + path, len, offset / 10, all, nth - minus);
		}
	}

}

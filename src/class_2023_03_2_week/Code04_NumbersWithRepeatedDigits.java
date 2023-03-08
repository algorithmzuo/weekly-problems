package class_2023_03_2_week;

// 给定正整数 n
// 返回在 [1, n] 范围内具有 至少 1 位 重复数字的正整数的个数。
// 测试链接 : https://leetcode.cn/problems/numbers-with-repeated-digits/
public class Code04_NumbersWithRepeatedDigits {

	public static int numDupDigitsAtMostN(int n) {
		if (n <= 10) {
			return 0;
		}
		// n   = 23645
		// len = 5长度
		// n       = 723645
		// offset  = 100000
		int len = 1;
		int offset = 1;
		int tmp = n / 10;
		while (tmp > 0) {
			len++;
			offset *= 10;
			tmp /= 10;
		}
		// n = 23645  5长度
		// 1长度的有几个、2长度的有几个、3长度的有几个、4长度的有几个
		// 直接公式决定
		int noRepeat = 0;
		for (int i = 1; i < len; i++) {
			noRepeat += numAllLength(i);
		}
		// num = 723645
		// 单独求6长度的，数字不重复的有几个
		if (len <= 10) {
			// int a = 0b 11
			int status = 0b1111111111;
			// n =    732645
			// offset 100000
			// n / offset = 7
			// 1 2 3 ... 6  7-1
			// ====
			// n =    732645
			// offset 100000
			//         10000
			noRepeat += ((n / offset) - 1) * numberRest(offset / 10, status ^ 1);
			// n =    732645
			// offset 100000
			//        7.....
			noRepeat += process(offset / 10, status ^ (1 << (n / offset)), n);
		}
		return n + 1 - noRepeat;
	}

	// 10进制长度必须为len的所有数中
	// 每一位数字都不重复的数有多少
	// 10
	// 9 * 9
	// 9 * 9 * 8
	// 9 * 9 * 8 * 7
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

	// n = 732645
	//      10000
	// status : 还剩哪些数字可以选
	// 返回值 : 前面的数字都和n一样，剩下的位里有多少数字，剩下的数字都不同？
	public static int process(int offset, int status, int n) {
		if (offset == 0) {
			// n = 732645
			//           0
			return 1;
		}
		int ans = 0;
		// n = 732645
		// off    100
		//     732....
		//     7320...
		//     7321...
		//     7322... X 
		//     7323... X
		//     7324...
		//     7325...
		// 
		// n = 732645
		// off    100
		//        6
		// 7326 % 10 = 6
		int first = (n / offset) % 10;
		for (int cur = 0; cur < first; cur++) {
			if ((status & (1 << cur)) != 0) {
				ans += numberRest(offset / 10, status ^ (1 << cur));
			}
		}
		// n = 732645
		// off     10
		//     7326 ..
		if ((status & (1 << first)) != 0) {
			ans += process(offset / 10, status ^ (1 << first), n);
		}
		return ans;
	}

	// offset : 1000, 还剩4长度
	// offset : 100000, 还剩7长度
	// status : 还有哪些数字可以选
	//          状态!
	//          9 8 7 6 5 4 3 2 1 0
	//          0 1 1 1 0 0 0 1 1 1
	public static int numberRest(int offset, int status) {
		// c 还有几种数字可以选！
		// offset = 1000; 100 10 1 0
		// c = 7  6  5 4 3
		// ans = 1 * 7 * 6 * 5 * 4
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

package class_2022_12_1_week;

// 如果一个正整数每一个数位都是 互不相同 的，我们称它是 特殊整数 。
// 给你一个正整数 n ，请你返回区间 [1, n] 之间特殊整数的数目。
// 测试链接 : https://leetcode.cn/problems/count-special-integers/
public class Code01_CountSpecialIntegers {

	public static int[] offset = { 
			0,   // 0
			1,   // 1
			10,  // 2
			100, // 3
			1000, 
			10000, 
			100000, 1000000, 10000000, 100000000, 1000000000 };

	public static int countSpecialNumbers(int n) {
		int len = len(n);
		int ans = 0;
		for (int i = 1; i < len; i++) {
			ans += all(i);
		}
		// n -> 9位数  3456789124
		//            1 .....
		//            2 .....
		//            3 ______
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

	// bits : 8 7 6 5  4位
	// candidates : 8 可能性
	
	// bits : _ _ _ 3位
	// candidates : 5 可能性
	public static int small(int bits, int candidates) {
		int ans = 1;
		for (int i = 0; i < bits; i++, candidates--) {
			ans *= candidates;
		}
		return ans;
	}

	// num : 原始数 46531 固定
	// len : 原始数有几位，5位，固定
	// rest : 还剩几位没决定，可变参数
	// num : 46531
	//       4 _ _ _ _ 
	//       4 0~5 
	//       4 6 _ _ _
	// status : 4 6 _ _ _
	// 哪些数字使用了，状态！在status里：
	// 体系学习班，状态压缩的动态规划！
	// int status 32位
	// 9 8 7 6 5 4 3 2 1 0
	//       1 0 1 0 0 0 0
	// 4 6 _ _ _ 还有几个达标的！
	// 哪些数字选了都在status里，用一个status变量表示数字选没选(位信息)
	public static int process(int num, int len, int rest, int status) {
		if (rest == 0) {
			return 1;
		}
		// 46531
		//   ___
		//   5
		// 46531 / 100 -> 465 % 10 -> 5
		
		// 比5小的有几股？0 1 2 3 4
		// 
		// n : 454012
		//     45_
		//       0...
		//       1...
		//       2...
		//       3...
		//       4 _ _ _
		int cur = (num / offset[rest]) % 10;
		int cnt = 0;
		for (int i = 0; i < cur; i++) {
			if ((status & (1 << i)) == 0) {
				cnt++;
			}
		}
		int ans = cnt * small(rest - 1, 9 - (len - rest));
		if ((status & (1 << cur)) == 0) {
			ans += process(num, len, rest - 1, status | (1 << cur));
		}
		return ans;
	}

}

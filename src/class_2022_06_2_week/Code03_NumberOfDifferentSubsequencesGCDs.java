package class_2022_06_2_week;

// 给你一个由正整数组成的数组 nums 。
// 数字序列的 最大公约数 定义为序列中所有整数的共有约数中的最大整数。
// 例如，序列 [4,6,16] 的最大公约数是 2 。
// 数组的一个 子序列 本质是一个序列，可以通过删除数组中的某些元素（或者不删除）得到。
// 例如，[2,5,10] 是 [1,2,1,2,4,1,5,10] 的一个子序列。
// 计算并返回 nums 的所有 非空 子序列中 不同 最大公约数的 数目 。
// 测试链接 : https://leetcode.com/problems/number-of-different-subsequences-gcds/
public class Code03_NumberOfDifferentSubsequencesGCDs {

	// n不是数字的个数，是数组中的最大值
	// 体系学习班，
	// 根据数据量猜解法，
	// 要想通过测试，一定要让计算量不超过10的7次方~10的8次方
	// n/1 + n/2 + n/3 + n/4 + ... + n/n -> O(N * logN)
	public static int countDifferentSubsequenceGCDs(int[] nums) {
		// 找到数组中的最大数！max
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			max = Math.max(max, num);
		}
		// 1~max，哪个数有哪个数没有
		boolean[] set = new boolean[max + 1];
		for (int num : nums) {
			set[num] = true;
		}
		int ans = 0;
		// a是当前想确定，是不是某个子序列的最大公约数，有a！
		for (int a = 1; a <= max; a++) {
			// 1)找到，离a最近的，a的倍数！1 2 3 ... g就是
			int g = a;
			for (; g <= max; g += a) {
				if (set[g]) {
					break;
				}
			}
			// 2) 找到了a最近的倍数，g
			// g + 0 , g ?= a
			// g + a , g ?= a
			// g + 2a , g ?= a
			// g + 3a , g ?= a
			for (int b = g; b <= max; b += a) {
				if (set[b]) {
					g = gcd(g, b);
					if (g == a) {
						ans++;
						break;
					}
				}
			}
		}
		return ans;
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

}

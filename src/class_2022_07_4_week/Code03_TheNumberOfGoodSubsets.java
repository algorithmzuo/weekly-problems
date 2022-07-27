package class_2022_07_4_week;

import java.util.Arrays;

// 给你一个整数数组 nums 。如果 nums 的一个子集中，
// 所有元素的乘积可以表示为一个或多个 互不相同的质数 的乘积，那么我们称它为 好子集 。
// 比方说，如果 nums = [1, 2, 3, 4] ：
// [2, 3] ，[1, 2, 3] 和 [1, 3] 是 好 子集，乘积分别为 6 = 2*3 ，6 = 2*3 和 3 = 3 。
// [1, 4] 和 [4] 不是 好 子集，因为乘积分别为 4 = 2*2 和 4 = 2*2 。
// 请你返回 nums 中不同的 好 子集的数目对 109 + 7 取余 的结果。
// nums 中的 子集 是通过删除 nums 中一些（可能一个都不删除，也可能全部都删除）
// 元素后剩余元素组成的数组。
// 如果两个子集删除的下标不同，那么它们被视为不同的子集。
// 测试链接 : https://leetcode.cn/problems/the-number-of-good-subsets/
public class Code03_TheNumberOfGoodSubsets {

	// 2, 3, 5, 6, 7, 10, 11, 13, 14,
	// 15, 17, 19, 21, 22, 23, 26, 29, 30
	public static int[] primes = {
			//        11 7 5 3 2
			// 2       0 0 0 0 1
			// 2 5     0 0 1 0 1
			0, // 0 00000000
			0, // 1 00000000
			1, // 2 00000001
			2, // 3 00000010
			0, // 4 00000000
			4, // 5 00000100
			3, // 6 00000011
			8, // 7 00001000
			0, // 8 00000000
			0, // 9 00000000
			5, // 10 00000101
			16, 0, 32, 9, 6, 0, 64, 0, 128, 0, 10, 17, 256, 0, 0, 33, 0, 0, 
			512,// 29  10000000
			7   // 30  2 * 3 * 5   111
			};

	public static int[] counts = new int[31];

	public static int[] status = new int[1 << 10];

	public static int mod = 1000000007;

	public static int numberOfGoodSubsets(int[] nums) {
		Arrays.fill(counts, 0);
		Arrays.fill(status, 0);
		for (int num : nums) {
			counts[num]++;
		}
		status[0] = 1;
		for (int i = 0; i < counts[1]; i++) {
			status[0] = (status[0] << 1) % mod;
		}
		for (int i = 2; i <= 30; i++) {
			// 2 几次 3 几次 4几次 5几次 30 几次
			int curPrimesStatus = primes[i];
			if (curPrimesStatus != 0 && counts[i] != 0) {
				// curPrimesStatus K次
				for (int from = 0; from < (1 << 10); from++) {
					// from 11111111
					// 枚举所有的状态 from
					// from & curPrimesStatus == 0
					if ((from & curPrimesStatus) == 0) {
						// to
						int to = from | curPrimesStatus;
						status[to] = (int) (((long) status[to] + ((long) status[from] * counts[i])) % mod);
//						// status[to] += status[from] * counts[i];
					}
				}
			}
		}
		int ans = 0;
		for (int s = 1; s < (1 << 10); s++) {
			ans = (ans + status[s]) % mod;
		}
		return ans;
	}

}

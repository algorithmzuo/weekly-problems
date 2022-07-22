package class_2022_08_1_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/the-number-of-good-subsets/
public class Code02_TheNumberOfGoodSubsets {

	// 2,3,5,6,7,10,11,13,14,15,17,19,21,22,23,26,29,30
	public static int[] numberPrimes = { 0, 0, 1, 2, 0, 4, 3, 8, 0, 0, 5, 16, 0, 32, 9, 6, 0, 64, 0, 128, 0, 10, 17,
			256, 0, 0, 33, 0, 0, 512, 7 };
	// v = 0110 0100 1110 1010 1110 1100 1110 1100;
	public static int okNumbers = 0x64eaecec;
	public static int valueMax = 30;
	public static int statusMax = 1 << 10;
	public static int[] cnt = new int[valueMax + 1];
	public static int[] status = new int[statusMax];
	public static int mod = 1000000007;

	public static int numberOfGoodSubsets(int[] nums) {
		Arrays.fill(cnt, 0);
		Arrays.fill(status, 0);
		for (int num : nums) {
			cnt[num]++;
		}
		status[0] = 1;
		for (int i = 0; i < cnt[1]; i++) {
			status[0] = (status[0] << 1) % mod;
		}
		for (int i = 2; i <= valueMax; i++) {
			if ((okNumbers & (1 << i)) == 0 || cnt[i] == 0) {
				continue;
			}
			int cur = numberPrimes[i];
			for (int mask = 0; mask < statusMax; mask++) {
				if ((mask & cur) == 0) {
					status[mask | cur] = (int) ((status[mask | cur] + ((long) status[mask] * cnt[i])) % mod);
				}
			}
		}
		int ans = 0;
		for (int mask = 1; mask < statusMax; mask++) {
			ans = (ans + status[mask]) % mod;
		}
		return ans;
	}

}

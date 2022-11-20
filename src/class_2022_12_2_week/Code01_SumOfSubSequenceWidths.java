package class_2022_12_2_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/sum-of-subsequence-widths/
public class Code01_SumOfSubSequenceWidths {

	public static int sumSubseqWidths(int[] nums) {
		Arrays.sort(nums);
		int mod = 1000000007;
		long ans = 0;
		long A = 0;
		long B = 0;
		long C = 1;
		long D = C;
		for (int i = 1; i < nums.length; i++) {
			A = (D * nums[i]) % mod;
			B = (B * 2 + nums[i - 1]) % mod;
			ans = (ans + A - B + mod) % mod;
			C = (C * 2) % mod;
			D = (D + C) % mod;
		}
		return (int) (ans);
	}

}

package class_2022_07_2_week;

// 测试链接 : https://leetcode.cn/problems/number-of-people-aware-of-a-secret/
public class Code05_NumberOfPeopleAwareOfASecret {

	public static int peopleAwareOfSecret(int n, int delay, int forget) {
		long mod = 1000000007;
		// dpKnow[i], 第i天知道秘密的人
		// dpForget[i], 第i天将要忘记秘密的人
		// dpShare[i], 第i天可以分享秘密的人
		long[] dpKnow = new long[n + 1];
		long[] dpForget = new long[n + 1];
		long[] dpShare = new long[n + 1];
		dpKnow[1] = 1;
		if (1 + forget <= n) {
			dpForget[1 + forget] = 1;
		}
		if (1 + delay <= n) {
			dpShare[1 + delay] = 1;
		}
		for (int i = 2; i <= n; i++) {
			dpKnow[i] = (mod + dpKnow[i - 1] - dpForget[i] + dpShare[i]) % mod;
			if (i + forget <= n) {
				dpForget[i + forget] = dpShare[i];
			}
			if (i + delay <= n) {
				dpShare[i + delay] = (mod + dpShare[i + delay - 1] + dpShare[i] - dpForget[i + delay]) % mod;
			}
		}
		return (int) dpKnow[n];
	}

}

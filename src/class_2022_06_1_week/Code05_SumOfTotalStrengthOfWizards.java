package class_2022_06_1_week;

// 测试链接 : https://leetcode.cn/problems/sum-of-total-strength-of-wizards/
public class Code05_SumOfTotalStrengthOfWizards {

	public static final long mod = 1000000007;

	public static int totalStrength(int[] arr) {
		int n = arr.length;
		long preSum = arr[0];
		long[] sumSum = new long[n];
		sumSum[0] = arr[0];
		for (int i = 1; i < n; i++) {
			preSum += arr[i];
			sumSum[i] = (sumSum[i - 1] + preSum) % mod;
		}
		int[] stack = new int[n];
		int size = 0;
		long ans = 0;
		for (int i = 0; i < n; i++) {
			while (size > 0 && arr[stack[size - 1]] >= arr[i]) {
				int m = stack[--size];
				int r = i - 1;
				int l = (size > 0 ? stack[size - 1] : -1) + 1;
				ans += magicSum(arr, sumSum, l, m, r);
				ans %= mod;
			}
			stack[size++] = i;
		}
		while (size > 0) {
			int m = stack[--size];
			int r = n - 1;
			int l = (size > 0 ? stack[size - 1] : -1) + 1;
			ans += magicSum(arr, sumSum, l, m, r);
			ans %= mod;
		}
		return (int) ans;
	}

	public static long magicSum(int[] arr, long[] sumSum, int L, int M, int R) {
		long rightSum = (long) (M - L + 1) * (sumSum[R] - (M - 1 >= 0 ? sumSum[M - 1] : 0) + mod) % mod;
		long leftSum = (long) (R - M + 1) * ((M - 1 >= 0 ? sumSum[M - 1] : 0) - (L - 2 >= 0 ? sumSum[L - 2] : 0) + mod)% mod;
		return (long) arr[M] * ((rightSum - leftSum + mod) % mod);
	}

}

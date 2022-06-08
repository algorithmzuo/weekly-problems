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
				int l = size > 0 ? stack[size - 1] : -1;
				ans += magicSum(arr, sumSum, l, m, i);
				ans %= mod;
			}
			stack[size++] = i;
		}
		while (size > 0) {
			int m = stack[--size];
			int l = size > 0 ? stack[size - 1] : -1;
			ans += magicSum(arr, sumSum, l, m, n);
			ans %= mod;
		}
		return (int) ans;
	}

	public static long magicSum(int[] arr, long[] sumSum, int l, int m, int r) {
		long left = (long) (m - l) * (sumSum[r - 1] - (m - 1 >= 0 ? sumSum[m - 1] : 0) + mod) % mod;
		long right = (long) (r - m) * ((m - 1 >= 0 ? sumSum[m - 1] : 0) - (l - 1 >= 0 ? sumSum[l - 1] : 0) + mod) % mod;
		return (long) arr[m] * ((left - right + mod) % mod);
	}

}

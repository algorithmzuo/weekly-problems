package class_2022_06_1_week;

// 作为国王的统治者，你有一支巫师军队听你指挥。
// 给你一个下标从 0 开始的整数数组 strength ，
// 其中 strength[i] 表示第 i 位巫师的力量值。
// 对于连续的一组巫师（也就是这些巫师的力量值是 strength 的 子数组），
// 总力量 定义为以下两个值的 乘积 ：
// 巫师中 最弱 的能力值 * 组中所有巫师的个人力量值 之和 。
// 请你返回 所有 巫师组的 总 力量之和。由于答案可能很大，请将答案对 109 + 7 取余 后返回。
// 子数组 是一个数组里 非空 连续子序列。
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

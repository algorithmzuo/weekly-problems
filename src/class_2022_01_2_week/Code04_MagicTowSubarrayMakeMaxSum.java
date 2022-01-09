package class_2022_01_2_week;

// 来自美团
// 小美有一个长度为n的数组，为了使得这个数组的和尽量大，她向会魔法的小团进行求助
// 小团可以选择数组中至多两个不相交的子数组，并将区间里的数全都变为原来的10倍
// 小团想知道他的魔法最多可以帮助小美将数组的和变大到多少?
public class Code04_MagicTowSubarrayMakeMaxSum {

	// 比较暴力的方法
	// 用于对数器
	public static int maxSum1(int[] arr) {
		int n = arr.length;
		int[] presum = presum(arr);
		int ans = maxSumAtMostOneRangeMagic(presum, 0, n - 1);
		for (int split = 0; split < n - 1; split++) {
			ans = Math.max(ans,
					maxSumAtMostOneRangeMagic(presum, 0, split) + maxSumAtMostOneRangeMagic(presum, split + 1, n - 1));
		}
		return ans;
	}

	public static int[] presum(int[] arr) {
		int n = arr.length;
		int[] presum = new int[n];
		presum[0] = arr[0];
		for (int i = 1; i < n; i++) {
			presum[i] = presum[i - 1] + arr[i];
		}
		return presum;
	}

	public static int sum(int[] presum, int l, int r) {
		return l > r ? 0 : (l == 0 ? presum[r] : (presum[r] - presum[l - 1]));
	}

	public static int maxSumAtMostOneRangeMagic(int[] presum, int l, int r) {
		if (l > r) {
			return 0;
		}
		int ans = sum(presum, l, r);
		for (int s = l; s <= r; s++) {
			for (int e = s; e <= r; e++) {
				ans = Math.max(ans, sum(presum, l, s - 1) + sum(presum, s, e) * 10 + sum(presum, e + 1, r));
			}
		}
		return ans;
	}

	// 正式方法，时间复杂度O(N)
	public static int maxSum2(int[] arr) {
		int n = arr.length;
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return Math.max(arr[0], arr[0] * 10);
		}
		// dp[i] : arr[0..i]范围上，可以没有10倍区域、或者有10倍区域但是最多有一个的情况下，最大累加和是多少？
		// 可能性1：就是没有10倍区域，那就是arr[0..i]的累加和
		// 可能性2：有一个10倍区域
		// a : arr[i]不在10倍区域里，但是之前可能有，那么就是dp[i-1] + arr[i]
		// b : arr[i]在10倍区域里
		// 甲：arr[0..i-1]没有10倍区域，arr[i]自己10倍
		// 乙：arr[0..i-1]中i-1位置在10倍区域里，arr[i]也在10倍区域里
		// 对于乙，要求知道magic[j]的信息
		// magic[j]：arr[0..j]范围上，j一定要在10倍区域里，并且只有一个10倍区域的情况下，最大累加和
		// 可能性1：只有arr[j]是10倍，arr[0..j-1]没有10倍
		// 可能性2：magic[j-1] + 10 * arr[j]
		int sum = arr[n - 1];
		int magic = sum * 10;
		int[] right = new int[n];
		right[n - 1] = Math.max(sum, sum * 10);
		for (int i = n - 2; i >= 0; i--) {
			magic = 10 * arr[i] + Math.max(sum, magic);
			sum += arr[i];
			right[i] = Math.max(Math.max(sum, right[i + 1] + arr[i]), magic);
		}
		int ans = right[0];
		sum = arr[0];
		magic = sum * 10;
		int dp = Math.max(sum, sum * 10);
		ans = Math.max(ans, dp + right[1]);
		for (int i = 1; i < n - 1; i++) {
			magic = 10 * arr[i] + Math.max(sum, magic);
			sum += arr[i];
			dp = Math.max(Math.max(sum, dp + arr[i]), magic);
			ans = Math.max(ans, dp + right[i + 1]);
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 30;
		int value = 100;
		int testTime = 500000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, value);
			int ans1 = maxSum1(arr);
			int ans2 = maxSum2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

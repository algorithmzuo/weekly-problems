package class_2022_04_3_week;

import java.util.Arrays;

// 来自腾讯音乐
// 原本数组中都是大于0、小于等于k的数字，是一个单调不减的数组
// 其中可能有相等的数字，总体趋势是递增的
// 但是其中有些位置的数被替换成了0，我们需要求出所有的把0替换的方案数量：
// 1）填充的每一个数可以大于等于前一个数，小于等于后一个数
// 2）填充的每一个数不能大于k
public class Code03_ValidSortedArrayWays {

	// 动态规划
	public static long ways1(int[] nums, int k) {
		int n = nums.length;
		// dp[i][j] : 一共i个格子，随意填，但是不能降序，j种数可以选
		long[][] dp = new long[n + 1][k + 1];
		for (int i = 1; i <= n; i++) {
			dp[i][1] = 1;
		}
		for (int i = 1; i <= k; i++) {
			dp[1][i] = i;
		}
		for (int i = 2; i <= n; i++) {
			for (int j = 2; j <= k; j++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			}
		}
		long res = 1;
		for (int i = 0, j = 0; i < nums.length; i++) {
			if (nums[i] == 0) {
				j = i + 1;
				while (j < nums.length && nums[j] == 0) {
					j++;
				}
				int leftValue = i - 1 >= 0 ? nums[i - 1] : 1;
				int rightValue = j < nums.length ? nums[j] : k;
				res *= dp[j - i][rightValue - leftValue + 1];
				i = j;
			}
		}
		return res;
	}

	// 数学方法
	// a ~ b范围的数字随便选，可以选重复的数，一共选m个
	// 选出有序序列的方案数：C ( m, b - a + m )
	public static long ways2(int[] nums, int k) {
		long res = 1;
		for (int i = 0, j = 0; i < nums.length; i++) {
			if (nums[i] == 0) {
				j = i + 1;
				while (j < nums.length && nums[j] == 0) {
					j++;
				}
				int leftValue = i - 1 >= 0 ? nums[i - 1] : 1;
				int rightValue = j < nums.length ? nums[j] : k;
				int numbers = j - i;
				res *= c(rightValue - leftValue + numbers, numbers);
				i = j;
			}
		}
		return res;
	}

	// 从一共a个数里，选b个数，方法数是多少
	public static long c(int a, int b) {
		if (a == b) {
			return 1;
		}
		long x = 1;
		long y = 1;
		for (int i = b + 1, j = 1; i <= a; i++, j++) {
			x *= i;
			y *= j;
			long gcd = gcd(x, y);
			x /= gcd;
			y /= gcd;
		}
		return x / y;
	}

	public static long gcd(long m, long n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	// 为了测试
	public static int[] randomArray(int n, int k) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * k) + 1;
		}
		Arrays.sort(ans);
		for (int i = 0; i < n; i++) {
			ans[i] = Math.random() < 0.5 ? 0 : ans[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 20;
		int K = 30;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int k = (int) (Math.random() * K) + 1;
			int[] arr = randomArray(n, k);
			long ans1 = ways1(arr, k);
			long ans2 = ways2(arr, k);
			if (ans1 != ans2) {
				System.out.println("出错了");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");
	}

}

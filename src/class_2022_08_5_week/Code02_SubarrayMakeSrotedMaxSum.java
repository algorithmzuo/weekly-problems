package class_2022_08_5_week;

// 来自Amazon
// 定义一个概念叫"变序最大和"
// "变序最大和"是说一个数组中，每个值都可以减小或者不变，
// 在必须把整体变成严格升序的情况下，得到的最大累加和
// 比如，[1,100,7]变成[1,6,7]时，就有变序最大和为14
// 比如，[5,4,9]变成[3,4,9]时，就有变序最大和为16
// 比如，[1,4,2]变成[0,1,2]时，就有变序最大和为3
// 给定一个数组arr，其中所有的数字都是>=0的
// 求arr所有子数组的变序最大和中，最大的那个并返回
// 1 <= arr长度 <= 10^6
// 0 <= arr[i] <= 10^6
public class Code02_SubarrayMakeSrotedMaxSum {

	// 时间复杂度O(N * V)的方法
	// 为了验证
	public static long maxSum1(int[] arr) {
		int n = arr.length;
		int max = 0;
		for (int num : arr) {
			max = Math.max(max, num);
		}
		long ans = 0;
		long[][] dp = new long[n][max + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= max; j++) {
				dp[i][j] = -1;
			}
		}
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, process1(arr, i, arr[i], dp));
		}
		return ans;
	}

	public static long process1(int[] arr, int i, int p, long[][] dp) {
		if (p <= 0 || i == -1) {
			return 0;
		}
		if (dp[i][p] != -1) {
			return dp[i][p];
		}
		int cur = Math.min(arr[i], p);
		long next = process1(arr, i - 1, cur - 1, dp);
		long ans = (long) cur + next;
		dp[i][p] = ans;
		return cur + next;
	}

	// 正式方法
	// 时间复杂度O(N)
	public static long maxSum2(int[] arr) {
		int n = arr.length;
		int[] stack = new int[n];
		int size = 0;
		long[] dp = new long[n];
		long ans = 0;
		for (int i = 0; i < n; i++) {
			int curVal = arr[i];
			int curIdx = i;
			while (curVal > 0 && size > 0) {
				int leftIdx = stack[size - 1];
				int leftVal = arr[leftIdx];
				if (leftVal >= curVal) {
					size--;
				} else {
					int idxDiff = curIdx - leftIdx;
					int valDiff = curVal - leftVal;
					if (valDiff >= idxDiff) {
						dp[i] += sum(curVal, idxDiff) + dp[leftIdx];
						curVal = 0;
						curIdx = 0;
						break;
					} else {
						dp[i] += sum(curVal, idxDiff);
						curVal -= idxDiff;
						curIdx = leftIdx;
						size--;
					}
				}
			}
			if (curVal > 0) {
				dp[i] += sum(curVal, curIdx + 1);
			}
			stack[size++] = i;
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	public static long sum(int max, int n) {
		n = Math.min(max, n);
		return (((long) max * 2 - n + 1) * n) / 2;
	}

	// 为了验证
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int testTimes = 50000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			long ans1 = maxSum1(arr);
			long ans2 = maxSum2(arr);
			if (ans1 != ans2) {
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 1000000;
		int v = 1000000;
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : " + v);
		int[] arr = randomArray(n, v);
		long start = System.currentTimeMillis();
		maxSum2(arr);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + "毫秒");
		System.out.println("性能测试结束");

	}

}

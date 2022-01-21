package class_2022_02_3_week;

import java.util.Arrays;

public class Problem_1043_PartitionArrayForMaximumSum {

	public static int maxSumAfterPartitioning1(int[] arr, int k) {
		int[] dp = new int[arr.length];
		Arrays.fill(dp, -1);
		return process1(arr, k, arr.length - 1, dp);
	}

	// 永远不变的固定参数 : arr, k
	// 可变参数 : index
	// 缓存 : dp
	// process含义 : arr[0...index]最优划分搞出的最大和是多少，返回
	public static int process1(int[] arr, int k, int index, int[] dp) {
		if (index == -1) {
			return 0;
		}
		if (dp[index] != -1) {
			return dp[index];
		}
		int max = Integer.MIN_VALUE;
		int ans = Integer.MIN_VALUE;
		for (int i = index, j = 1; i >= 0 && j <= k; i--, j++) {
			max = Math.max(max, arr[i]);
			ans = Math.max(ans, process1(arr, k, i - 1, dp) + (index - i + 1) * max);
		}
		dp[index] = ans;
		return ans;
	}

	public static int maxSumAfterPartitioning2(int[] arr, int k) {
		int n = arr.length;
		int[] dp = new int[n];
		for (int index = 0; index < n; index++) {
			int max = arr[index];
			for (int i = index, j = 1; i >= 0 && j <= k; i--, j++) {
				max = Math.max(max, arr[i]);
				dp[index] = Math.max(dp[index], (i - 1 >= 0 ? dp[i - 1] : 0) + (index - i + 1) * max);
			}
		}
		return dp[n - 1];
	}

}

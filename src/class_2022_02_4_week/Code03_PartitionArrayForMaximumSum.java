package class_2022_02_4_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.com/problems/partition-array-for-maximum-sum/
public class Code03_PartitionArrayForMaximumSum {

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
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int n = arr.length;
		int[] dp = new int[n];
		dp[0] = arr[0];
		for (int i = 1; i < n; i++) {
			dp[i] = arr[i] + dp[i - 1];
			int max = arr[i];
			for (int j = i - 1; j >= 0 && (i - j + 1) <= k; j--) {
				max = Math.max(max, arr[j]);
				dp[i] = Math.max(dp[i], max * (i - j + 1) + (j - 1 >= 0 ? dp[j - 1] : 0));
			}
		}
		return dp[n - 1];
	}

}

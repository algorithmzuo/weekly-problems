package class_2022_03_3_week;

import java.util.Arrays;

// 来自美团
// 给定一个数组arr，你可以随意挑选其中的数字
// 但是你挑选的数中，任何两个数a和b，不能让Math.abs(a - b) <= 1
// 返回你最多能挑选几个数
public class Code03_LongestUncontinuousSet {

	public static int longestUncontinuous(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Arrays.sort(arr);
		int n = arr.length;
		int size = 1;
		for (int i = 1; i < n; i++) {
			if (arr[i] != arr[size - 1]) {
				arr[size++] = arr[i];
			}
		}
		int[] dp = new int[size];
		dp[0] = 1;
		int ans = 1;
		for (int i = 1; i < size; i++) {
			dp[i] = 1;
			if (arr[i] - arr[i - 1] > 1) {
				dp[i] = 1 + dp[i - 1];
			}
			if (i - 2 >= 0 && arr[i] - arr[i - 2] > 1) {
				dp[i] = Math.max(dp[i], 1 + dp[i - 2]);
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

}

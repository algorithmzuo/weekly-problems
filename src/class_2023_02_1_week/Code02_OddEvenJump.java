package class_2023_02_1_week;

import java.util.TreeMap;

// 测试链接 : https://leetcode.cn/problems/odd-even-jump/
public class Code02_OddEvenJump {

	public static int oddEvenJumps(int[] arr) {
		int n = arr.length;
		int[] odd = new int[n];
		int[] even = new int[n];
		TreeMap<Integer, Integer> orderMap = new TreeMap<>();
		for (int i = n - 1; i >= 0; i--) {
			Integer to = orderMap.ceilingKey(arr[i]);
			odd[i] = to == null ? -1 : orderMap.get(to);
			to = orderMap.floorKey(arr[i]);
			even[i] = to == null ? -1 : orderMap.get(to);
			orderMap.put(arr[i], i);
		}
		boolean[][] dp = new boolean[n][2];
		dp[n - 1][0] = true;
		dp[n - 1][1] = true;
		int ans = 1;
		for (int i = n - 2; i >= 0; i--) {
			dp[i][0] = odd[i] != -1 && dp[odd[i]][1];
			dp[i][1] = even[i] != -1 && dp[even[i]][0];
			ans += dp[i][0] ? 1 : 0;
		}
		return ans;
	}

}

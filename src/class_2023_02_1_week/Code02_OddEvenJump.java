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
		int ans = 0;
		int[][] dp = new int[n][2];
		for (int i = 0; i < n; i++) {
			if (jump(i, 1, n - 1, odd, even, dp)) {
				ans++;
			}
		}
		return ans;
	}

	public static boolean jump(int cur, int status, int target, int[] odd, int[] even, int[][] dp) {
		if (cur == target) {
			return true;
		}
		if (dp[cur][status] != 0) {
			return dp[cur][status] == 1;
		}
		boolean ans = false;
		if (status == 1 && odd[cur] != -1) {
			ans = jump(odd[cur], 0, target, odd, even, dp);
		}
		if (status == 0 && even[cur] != -1) {
			ans = jump(even[cur], 1, target, odd, even, dp);
		}
		dp[cur][status] = ans ? 1 : -1;
		return ans;
	}

}

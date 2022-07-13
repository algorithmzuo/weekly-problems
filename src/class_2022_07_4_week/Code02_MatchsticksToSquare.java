package class_2022_07_4_week;

// 测试链接 : https://leetcode.cn/problems/matchsticks-to-square/
public class Code02_MatchsticksToSquare {

	public static boolean makesquare(int[] matchsticks) {
		int sum = 0;
		for (int num : matchsticks) {
			sum += num;
		}
		if ((sum & 3) != 0) {
			return false;
		}
		int[] dp = new int[1 << matchsticks.length];
		return process(matchsticks, 0, 0, sum >> 2, 4, dp);
	}

	public static boolean process(int[] arr, int status, int cur, int len, int edges, int[] dp) {
		if (dp[status] != 0) {
			return dp[status] == 1;
		}
		boolean ans = false;
		if (edges == 0) {
			ans = (status == (1 << arr.length) - 1) ? true : false;
		} else {
			for (int i = 0; i < arr.length && !ans; i++) {
				if (((1 << i) & status) == 0 && cur + arr[i] <= len) {
					if (cur + arr[i] == len) {
						ans |= process(arr, status | (1 << i), 0, len, edges - 1, dp);
					} else {
						ans |= process(arr, status | (1 << i), cur + arr[i], len, edges, dp);
					}
				}
			}
		}
		dp[status] = ans ? 1 : -1;
		return ans;
	}

}

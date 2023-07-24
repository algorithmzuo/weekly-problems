package class_2023_07_4_week;

import java.util.List;

// 测试链接 : https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/
public class Code02_MaximumValueOfKcoinsFromPiles {

	public int maxValueOfCoins(List<List<Integer>> piles, int k) {
		int[] dp = new int[k + 1];
		for (List<Integer> stack : piles) {
			for (int w = k; w > 0; w--) {
				for (int i = 1, sum = 0; i <= Math.min(stack.size(), w); i++) {
					sum += stack.get(i - 1);
					dp[w] = Math.max(dp[w], sum + dp[w - i]);
				}
			}
		}
		return dp[k];
	}

}

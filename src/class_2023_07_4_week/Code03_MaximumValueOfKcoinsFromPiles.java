package class_2023_07_4_week;

import java.util.List;

// 一张桌子上总共有 n 个硬币 栈 。每个栈有 正整数 个带面值的硬币
// 每一次操作中，你可以从任意一个栈的 顶部 取出 1 个硬币，从栈中移除它，并放入你的钱包里
// 给你一个列表 piles ，其中 piles[i] 是一个整数数组
// 分别表示第 i 个栈里 从顶到底 的硬币面值。同时给你一个正整数 k
// 请你返回在 恰好 进行 k 次操作的前提下，你钱包里硬币面值之和 最大为多少
// 测试链接 : https://leetcode.cn/problems/maximum-value-of-k-coins-from-piles/
public class Code03_MaximumValueOfKcoinsFromPiles {

	public int maxValueOfCoins(List<List<Integer>> piles, int k) {
		int[] dp = new int[k + 1];
		// 物品总量，n  2000内
		// 组的数量，m  1000内
		// 挑选的次数，k 2000内
		// O( k * n)
		// O( m * k^2)
		// min (  O( k * n) ,   O( m * k^2)  )
		for (List<Integer> stack : piles) { // 组
			for (int w = k; w > 0; w--) { // 背包容量
				// i = 1 sum = 0
				// i = 2 sum = arr[0]
				// i = 3 sum = arr[0] + arr[1]
				for (int i = 1, sum = 0; i <= Math.min(stack.size(), w); i++) {
					sum += stack.get(i - 1);
					dp[w] = Math.max(dp[w], sum + dp[w - i]);
				}
			}
		}
		return dp[k];
	}

}

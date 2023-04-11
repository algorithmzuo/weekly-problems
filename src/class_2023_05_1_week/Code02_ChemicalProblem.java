package class_2023_05_1_week;

import java.util.Arrays;

// 来自小红书
// 实验室需要配制一种溶液，现在研究员面前有n种该物质的溶液，
// 每一种有无限多瓶，第i种的溶液体积为v[i]，里面含有w[i]单位的该物质
// 研究员每次可以选择一瓶溶液，
// 将其倒入另外一瓶（假设瓶子的容量无限），即可以看作将两个瓶子内的溶液合并
// 此时合并的溶液体积和物质含量都等于之前两个瓶子内的之和。
// 特别地，如果瓶子A与B的溶液体积相同，那么A与B合并之后
// 该物质的含量会产生化学反应，使得该物质含量增加x单位
// 研究员的任务是配制溶液体积恰好等于c的，且尽量浓的溶液(即物质含量尽量多）
// 研究员想要知道物质含量最多是多少
// 对于所有数据，1 <= n, v[i], w[i], x, c <= 1000
public class Code02_ChemicalProblem {

	public static int maxValue(int[] v, int[] w, int x, int c) {
		int n = v.length;
		int[] dp = new int[c + 1];
		Arrays.fill(dp, -1);
		for (int i = 0; i < n; i++) {
			if (v[i] <= c) {
				dp[v[i]] = Math.max(dp[v[i]], w[i]);
			}
		}
		for (int i = 1; i <= c; i++) {
			for (int j = 1; j <= i / 2; j++) {
				if (dp[j] != -1 && dp[i - j] != -1) {
					dp[i] = Math.max(dp[i], dp[j] + dp[i - j] + (j == i - j ? x : 0));
				}
			}
		}
		return dp[c];
	}

	public static void main(String[] args) {
		int[] v = { 5, 3, 4 };
		int[] w = { 2, 4, 1 };
		int x = 4;
		int c = 16;
		System.out.println(maxValue(v, w, x, c));
	}

}

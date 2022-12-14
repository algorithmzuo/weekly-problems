package class_2022_12_2_week;

import java.util.Arrays;

// 石子游戏中，爱丽丝和鲍勃轮流进行自己的回合，爱丽丝先开始 。
// 有 n 块石子排成一排。
// 每个玩家的回合中，可以从行中 移除 最左边的石头或最右边的石头，
// 并获得与该行中剩余石头值之 和 相等的得分。当没有石头可移除时，得分较高者获胜。
// 鲍勃发现他总是输掉游戏（可怜的鲍勃，他总是输），
// 所以他决定尽力 减小得分的差值 。爱丽丝的目标是最大限度地 扩大得分的差值 。
// 给你一个整数数组 stones ，其中 stones[i] 表示 从左边开始 的第 i 个石头的值，
// 如果爱丽丝和鲍勃都 发挥出最佳水平 ，请返回他们 得分的差值 。
// 测试链接 : https://leetcode.cn/problems/stone-game-vii/
public class Code04_StoneGameVII {

	// 会超时但是思路是对的，如果想通过就把这个暴力递归改成下面的动态规划
	// 改法课上都讲了
	public static int stoneGameVII1(int[] stones) {
		int sum = 0;
		for (int num : stones) {
			sum += num;
		}
		int alice = f(stones, sum, 0, stones.length - 1);
		int bob = s(stones, sum, 0, stones.length - 1);
		return Math.abs(alice - bob);
	}

	// 先手
	public static int f(int[] stones, int sum, int L, int R) {
		if (L == R) { // 只能一块儿了！
			return 0;
		}
		// p1
		// L
		int p1 = sum - stones[L] + s(stones, sum - stones[L], L + 1, R);
		int against1 = f(stones, sum - stones[L], L + 1, R);
		//          p2
		//          R
		int p2 = sum - stones[R] + s(stones, sum - stones[R], L, R - 1);
		int against2 = f(stones, sum - stones[R], L, R - 1);
		return (p1 - against1) > (p2 - against2) ? p1 : p2;
	}

	// 后手！
	public static int s(int[] stones, int sum, int L, int R) {
		if (L == R) {
			return 0;
		}
		// 当前的是后手
		// 对手，先手！
		int against1 = sum - stones[L] + s(stones, sum - stones[L], L + 1, R);
		// 当前用户的得分！后手！是对手决定的！
		int get1 = f(stones, sum - stones[L], L + 1, R);
		int against2 = sum - stones[R] + s(stones, sum - stones[R], L, R - 1);
		int get2 = f(stones, sum - stones[R], L, R - 1);
		return (against1 - get1) > (against2 - get2) ? get1 : get2;
	}

	// 动态规划版
	public static int stoneGameVII2(int[] stones) {
		int N = stones.length;
		int[] presum = new int[N + 1];
		for (int i = 0; i < N; i++) {
			presum[i + 1] = presum[i] + stones[i];
		}
		int[][] dpf = new int[N][N];
		int[][] dps = new int[N][N];
		for (int L = N - 2; L >= 0; L--) {
			for (int R = L + 1; R < N; R++) {
				int sumLR = presum[R + 1] - presum[L];
				int a = sumLR - stones[L] + dps[L + 1][R];
				int b = dpf[L + 1][R];
				int c = sumLR - stones[R] + dps[L][R - 1];
				int d = dpf[L][R - 1];
				dpf[L][R] = (a - b > c - d) ? a : c;
				dps[L][R] = (a - b > c - d) ? b : d;
			}
		}
		return Math.abs(dpf[0][N - 1] - dps[0][N - 1]);
	}

	// 另一种尝试 + static动态规划表 + 空间压缩 + 尽量优化
	// dp[len][i] : 从i出发，当长度为len的情况下，Alice能比Bob多多少分？
	// 要注意结算时机！这是这种尝试的核心！
	public static int[] dp = new int[1000];

	// 时间复杂度和刚才讲的一样！
	public int stoneGameVII3(int[] s) {
		int n = s.length;
		Arrays.fill(dp, 0, n, 0);
		if (n % 2 == 0) {
			for (int i = 0; i < n; i++) {
				dp[i] = s[i];
			}
		}
		boolean alicePick = n % 2 == 0;
		for (int len = 2; len <= n; len++, alicePick = !alicePick) {
			for (int i = 0, j = len - 1; j < n; i++, j++) {
				dp[i] = alicePick ? Math.max(dp[i], dp[i + 1]) : Math.min(dp[i] + s[j], s[i] + dp[i + 1]);
			}
		}
		return dp[0];
	}

}

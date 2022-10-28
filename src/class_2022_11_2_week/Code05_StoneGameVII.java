package class_2022_11_2_week;

// 石子游戏中，爱丽丝和鲍勃轮流进行自己的回合，爱丽丝先开始 。
// 有 n 块石子排成一排。
// 每个玩家的回合中，可以从行中 移除 最左边的石头或最右边的石头，
// 并获得与该行中剩余石头值之 和 相等的得分。当没有石头可移除时，得分较高者获胜。
// 鲍勃发现他总是输掉游戏（可怜的鲍勃，他总是输），
// 所以他决定尽力 减小得分的差值 。爱丽丝的目标是最大限度地 扩大得分的差值 。
// 给你一个整数数组 stones ，其中 stones[i] 表示 从左边开始 的第 i 个石头的值，
// 如果爱丽丝和鲍勃都 发挥出最佳水平 ，请返回他们 得分的差值 。
// 测试链接 : https://leetcode.cn/problems/stone-game-vii/
public class Code05_StoneGameVII {

	// 暴力尝试版本，提交的时候请把名字改成stoneGameVII
	// 会超时但是思路是对的，如果想通过就把这个暴力递归改成下面的动态规划
	// 改法课上都讲了
	public static int stoneGameVII1(int[] stones) {
		int sum = 0;
		for (int num : stones) {
			sum += num;
		}
		int f = f(stones, sum, 0, stones.length - 1);
		int s = s(stones, sum, 0, stones.length - 1);
		return Math.abs(f - s);
	}

	public static int f(int[] stones, int sum, int L, int R) {
		if (L == R) {
			return 0;
		}
		int get1 = sum - stones[L] + s(stones, sum - stones[L], L + 1, R);
		int against1 = f(stones, sum - stones[L], L + 1, R);
		int get2 = sum - stones[R] + s(stones, sum - stones[R], L, R - 1);
		int against2 = f(stones, sum - stones[R], L, R - 1);
		return (get1 - against1) > (get2 - against2) ? get1 : get2;
	}

	public static int s(int[] stones, int sum, int L, int R) {
		if (L == R) {
			return 0;
		}
		int against1 = sum - stones[L] + s(stones, sum - stones[L], L + 1, R);
		int get1 = f(stones, sum - stones[L], L + 1, R);
		int against2 = sum - stones[R] + s(stones, sum - stones[R], L, R - 1);
		int get2 = f(stones, sum - stones[R], L, R - 1);
		return (against1 - get1) > (against2 - get2) ? get1 : get2;
	}

	// 动态规划版，提交的时候请把名字改成stoneGameVII
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

}

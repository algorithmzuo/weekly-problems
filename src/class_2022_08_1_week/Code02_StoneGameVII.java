package class_2022_08_1_week;

// 测试链接 : https://leetcode.cn/problems/stone-game-vii/
public class Code02_StoneGameVII {

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

	// 极致优化的版本
	public static int[] dp = new int[1000];

	public int stoneGameVII(int[] stones) {
		int n = stones.length;
		boolean unfair = n % 2 == 0;
		for (int i = 0; i < n; i++) {
			dp[i] = unfair ? stones[i] : 0;
		}
		for (int s = 1; s < n; s++) {
			for (int i = 0; i < n - s; i++) {
				dp[i] = unfair ? Math.max(dp[i + 1], dp[i]) : Math.min(dp[i + 1] + stones[i], dp[i] + stones[i + s]);
			}
			unfair = !unfair;
		}
		return dp[0];
	}

}

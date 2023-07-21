package class_2023_08_1_week;

// 测试链接 : https://leetcode.cn/problems/paint-house-iii/
public class Code05_PaintHouseIII {

	public static int minCost1(int[] houses, int[][] cost, int m, int n, int target) {
		int[][][] dp = new int[m][target + 1][n + 1];
		for (int i = 0; i < m; i++) {
			for (int k = 0; k <= target; k++) {
				for (int c = 0; c <= n; c++) {
					dp[i][k][c] = -1;
				}
			}
		}
		int ans = process1(houses, cost, n, 0, target, 0, dp);
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	public static int process1(int[] houses, int[][] cost, int n, int i, int k, int c, int[][][] dp) {
		if (k < 0) {
			return Integer.MAX_VALUE;
		}
		if (i == houses.length) {
			return k == 0 ? 0 : Integer.MAX_VALUE;
		}
		if (dp[i][k][c] != -1) {
			return dp[i][k][c];
		}
		int ans = Integer.MAX_VALUE;
		if (houses[i] != 0) {
			if (houses[i] != c) {
				ans = process1(houses, cost, n, i + 1, k - 1, houses[i], dp);
			} else {
				ans = process1(houses, cost, n, i + 1, k, houses[i], dp);
			}
		} else {
			for (int fill = 1, next; fill <= n; fill++) {
				if (fill == c) {
					next = process1(houses, cost, n, i + 1, k, fill, dp);
				} else {
					next = process1(houses, cost, n, i + 1, k - 1, fill, dp);
				}
				if (next != Integer.MAX_VALUE) {
					ans = Math.min(ans, cost[i][fill - 1] + next);
				}
			}
		}
		dp[i][k][c] = ans;
		return ans;
	}

	public static int minCost2(int[] houses, int[][] cost, int m, int n, int target) {
		int[][] dp = new int[target + 1][n + 1];
		for (int c = 0; c <= n; c++) {
			dp[0][c] = 0;
		}
		for (int k = 1; k <= target; k++) {
			for (int c = 0; c <= n; c++) {
				dp[k][c] = Integer.MAX_VALUE;
			}
		}
		int[] memo = new int[n + 1];
		for (int i = m - 1; i >= 0; i--) {
			if (houses[i] != 0) {
				int houseColor = houses[i];
				for (int k = target; k >= 0; k--) {
					int memory = dp[k][houseColor];
					for (int c = 0; c <= n; c++) {
						if (houseColor != c) {
							dp[k][c] = k == 0 ? Integer.MAX_VALUE : dp[k - 1][houseColor];
						} else {
							dp[k][c] = memory;
						}
					}
				}
			} else {
				for (int k = target; k >= 0; k--) {
					for (int c = 0; c <= n; c++) {
						memo[c] = dp[k][c];
					}
					for (int c = 0; c <= n; c++) {
						int ans = Integer.MAX_VALUE;
						for (int fill = 1, next; fill <= n; fill++) {
							if (fill == c) {
								next = memo[fill];
							} else {
								next = k == 0 ? Integer.MAX_VALUE : dp[k - 1][fill];
							}
							if (next != Integer.MAX_VALUE) {
								ans = Math.min(ans, cost[i][fill - 1] + next);
							}
						}
						dp[k][c] = ans;
					}
				}
			}
		}
		return dp[target][0] == Integer.MAX_VALUE ? -1 : dp[target][0];
	}

	public static int minCost3(int[] houses, int[][] cost, int m, int n, int target) {
		int[][] dp = new int[target + 1][n + 1];
		for (int c = 0; c <= n; c++) {
			dp[0][c] = 0;
		}
		for (int k = 1; k <= target; k++) {
			for (int c = 0; c <= n; c++) {
				dp[k][c] = Integer.MAX_VALUE;
			}
		}
		int[] memo = new int[n + 1];
		int[] minl = new int[n + 2];
		int[] minr = new int[n + 2];
		minl[0] = minr[0] = minl[n + 1] = minr[n + 1] = Integer.MAX_VALUE;
		for (int i = m - 1; i >= 0; i--) {
			if (houses[i] != 0) {
				for (int k = target, memory; k >= 0; k--) {
					memory = dp[k][houses[i]];
					for (int c = 0; c <= n; c++) {
						if (houses[i] != c) {
							dp[k][c] = k == 0 ? Integer.MAX_VALUE : dp[k - 1][houses[i]];
						} else {
							dp[k][c] = memory;
						}
					}
				}
			} else {
				for (int k = target; k >= 0; k--) {
					for (int c = 0; c <= n; c++) {
						memo[c] = dp[k][c];
					}
					for (int fill = 1; fill <= n; fill++) {
						if (k == 0 || dp[k - 1][fill] == Integer.MAX_VALUE) {
							minl[fill] = minl[fill - 1];
						} else {
							minl[fill] = Math.min(minl[fill - 1], cost[i][fill - 1] + dp[k - 1][fill]);
						}
					}
					for (int fill = n; fill >= 1; fill--) {
						if (k == 0 || dp[k - 1][fill] == Integer.MAX_VALUE) {
							minr[fill] = minr[fill + 1];
						} else {
							minr[fill] = Math.min(minr[fill + 1], cost[i][fill - 1] + dp[k - 1][fill]);
						}
					}
					for (int c = 0, ans; c <= n; c++) {
						if (c == 0 || memo[c] == Integer.MAX_VALUE) {
							ans = Integer.MAX_VALUE;
						} else {
							ans = cost[i][c - 1] + memo[c];
						}
						if (c > 0) {
							ans = Math.min(ans, minl[c - 1]);
						}
						if (c < n) {
							ans = Math.min(ans, minr[c + 1]);
						}
						dp[k][c] = ans;
					}
				}
			}
		}
		return dp[target][0] != Integer.MAX_VALUE ? dp[target][0] : -1;
	}

}

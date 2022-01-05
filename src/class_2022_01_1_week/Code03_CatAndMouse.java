package class_2022_01_1_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.com/problems/cat-and-mouse/
public class Code03_CatAndMouse {

	public static int catMouseGame(int[][] graph) {
		int n = graph.length;
		int[][][] dp = new int[n][n][n << 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}
		return process(graph, 2, 1, 1, dp);
	}

	public static int process(int[][] graph, int cat, int mouse, int turn, int[][][] dp) {
		if (turn == (graph.length << 1)) {
			return 0;
		}
		if (dp[cat][mouse][turn] != -1) {
			return dp[cat][mouse][turn];
		}
		int ans = 0;
		if (cat == mouse) {
			ans = 2;
		} else if (mouse == 0) {
			ans = 1;
		} else {
			if ((turn & 1) == 1) { // 老鼠回合
				ans = 2;
				for (int next : graph[mouse]) {
					int p = process(graph, cat, next, turn + 1, dp);
					ans = p == 1 ? 1 : (p == 0 ? 0 : ans);
					if (ans == 1) {
						break;
					}
				}
			} else { // 猫回合
				ans = 1;
				for (int next : graph[cat]) {
					if (next != 0) {
						int p = process(graph, next, mouse, turn + 1, dp);
						ans = p == 2 ? 2 : (p == 0 ? 0 : ans);
						if (ans == 2) {
							break;
						}
					}
				}
			}
		}
		dp[cat][mouse][turn] = ans;
		return ans;
	}

}

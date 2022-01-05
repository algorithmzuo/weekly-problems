package class_2022_01_1_week;

import java.util.ArrayList;
import java.util.Arrays;

// 测试链接 : https://leetcode.com/problems/cat-and-mouse/
public class Code02_CatAndMouse {

	// 不贪心，就递归 + 记忆化搜索
	public static int catMouseGame1(int[][] graph) {
		int n = graph.length;
		int limit = ((n * (n - 1)) << 1) + 1;
		int[][][] dp = new int[n][n][limit];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}
		return process(graph, 2, 1, 1, dp);
	}

	// 贪心 + 递归 + 记忆化搜索
	// 但是不对！
	// 再次强调！不要去证明！
	public static int catMouseGame2(int[][] graph) {
		int n = graph.length;
		// 这里！
		// int limit = (n << 1) + 2; 还会出错，但是概率很小，需要多跑几次
		// int limit = (n << 1) + 3; 就没错了，或者说，概率小到很难重现
		// 为啥？我屌你为啥！
		int limit = (n << 1) + 2;
		int[][][] dp = new int[n][n][limit];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}
		return process(graph, 2, 1, 1, dp);
	}

	public static int process(int[][] graph, int cat, int mouse, int turn, int[][][] dp) {
		if (turn == dp[0][0].length) {
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

	// 为了测试
	// 暴力尝试
	public static int right(int[][] graph) {
		int n = graph.length;
		boolean[][][] path = new boolean[n][n][2];
		return win(graph, 2, 1, 1, path);
	}

	// 暴力尝试
	public static int win(int[][] graph, int cat, int mouse, int turn, boolean[][][] path) {
		if (path[cat][mouse][turn]) {
			return 0;
		}
		path[cat][mouse][turn] = true;
		int ans = 0;
		if (cat == mouse) {
			ans = 2;
		} else if (mouse == 0) {
			ans = 1;
		} else {
			if ((turn & 1) == 1) { // 老鼠回合
				ans = 2;
				for (int next : graph[mouse]) {
					int p = win(graph, cat, next, turn ^ 1, path);
					ans = p == 1 ? 1 : (p == 0 ? 0 : ans);
					if (ans == 1) {
						break;
					}
				}
			} else { // 猫回合
				ans = 1;
				for (int next : graph[cat]) {
					if (next != 0) {
						int p = win(graph, next, mouse, turn ^ 1, path);
						ans = p == 2 ? 2 : (p == 0 ? 0 : ans);
						if (ans == 2) {
							break;
						}
					}
				}
			}
		}
		path[cat][mouse][turn] = false;
		return ans;
	}

	// 为了测试
	public static int[][] randomGraph(int n) {
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<>());
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (Math.random() > 0.5) {
					g.get(i).add(j);
					g.get(j).add(i);
				}
			}
		}
		int[][] graph = new int[n][];
		for (int i = 0; i < n; i++) {
			int m = g.get(i).size();
			graph[i] = new int[m];
			for (int j = 0; j < m; j++) {
				graph[i][j] = g.get(i).get(j);
			}
		}
		return graph;
	}

	// 为了测试
	public static void main(String[] args) {
		System.out.println("证什么证！？对数器万岁!");
		int N = 7;
		int testTime = 3000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 3;
			int[][] graph = randomGraph(n);
			int ans1 = catMouseGame1(graph);
			int ans2 = catMouseGame2(graph);
			if (ans1 != ans2) {
				for (int row = 0; row < graph.length; row++) {
					System.out.print(row + " : ");
					for (int next : graph[row]) {
						System.out.print(next + " ");
					}
					System.out.println();
				}
				System.out.println("ans1 : " + ans1);
				System.out.println("ans2 : " + ans2);
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");

		// 给你记录一个错误的例子
		int[][] graph = {
				// 0 :
				{ 2, 6, 7 },
				// 1 :
				{ 3, 4, 5, 7 },
				// 2 :
				{ 0, 3, 4, 7 },
				// 3 :
				{ 1, 2, 5, 6 },
				// 4 :
				{ 1, 2, 5, 7 },
				// 5 :
				{ 1, 3, 4, 6 },
				// 6 :
				{ 0, 3, 5, 7 },
				// 7 :
				{ 0, 1, 2, 4, 6 } };
		System.out.println(catMouseGame1(graph));
		System.out.println(catMouseGame2(graph));

	}

}

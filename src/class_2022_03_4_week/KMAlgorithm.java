// 注意本文件中，graph不是邻接矩阵的含义，而是一个二部图
// 在长度为N的邻接矩阵matrix中，所有的点有N个，matrix[i][j]表示点i到点j的距离或者权重
// 而在二部图graph中，所有的点有2*N个，行所对应的点有N个，列所对应的点有N个
// 而且认为，行所对应的点之间是没有路径的，列所对应的点之间也是没有路径的！

package class_2022_03_4_week;

import java.util.Arrays;

public class KMAlgorithm {

	// 暴力解
	public static int right(int[][] graph) {
		int N = graph.length;
		int[] to = new int[N];
		for (int i = 0; i < N; i++) {
			to[i] = 1;
		}
		return process(0, to, graph);
	}

	public static int process(int from, int[] to, int[][] graph) {
		if (from == graph.length) {
			return 0;
		}
		int ans = 0;
		for (int i = 0; i < to.length; i++) {
			if (to[i] == 1) {
				to[i] = 0;
				ans = Math.max(ans, graph[from][i] + process(from + 1, to, graph));
				to[i] = 1;
			}
		}
		return ans;
	}

	public static int km(int[][] graph) {
		int N = graph.length;
		int[] match = new int[N];
		int[] lx = new int[N];
		int[] ly = new int[N];
		boolean[] x = new boolean[N];
		boolean[] y = new boolean[N];
		int[] slack = new int[N];
		int invalid = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			match[i] = -1;
			lx[i] = -invalid;
			for (int j = 0; j < N; j++) {
				lx[i] = Math.max(lx[i], graph[i][j]);
			}
			ly[i] = 0;
		}
		for (int f = 0; f < N; f++) {
			Arrays.fill(x, false);
			Arrays.fill(y, false);
			for (int i = 0; i < N; i++) {
				slack[i] = invalid;
			}
			while (!dfs(f, x, y, lx, ly, match, slack, graph)) {
				int d = invalid;
				for (int i = 0; i < N; i++) {
					if (!y[i] && slack[i] < d) {
						d = slack[i];
					}
				}
				for (int i = 0; i < N; i++) {
					if (x[i]) {
						lx[i] = lx[i] - d;
						x[i] = false;
					}
					if (y[i]) {
						ly[i] = ly[i] + d;
						y[i] = false;
					}
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans += (lx[i] + ly[i]);
		}
		return ans;
	}

	public static boolean dfs(int f, boolean[] x, boolean[] y, int[] lx, int[] ly,
			int[] match, int[] slack, int[][] map) {
		int N = map.length;
		x[f] = true;
		for (int t = 0; t < N; t++) {
			int d = lx[f] + ly[t] - map[f][t];
			if (y[t] || d != 0) {
				slack[t] = Math.min(slack[t], d);
			} else {
				y[t] = true;
				if (match[t] == -1 || dfs(match[t], x, y, lx, ly, match, slack, map)) {
					match[t] = f;
					return true;
				}
			}
		}
		return false;
	}

	// 为了测试
	public static int[][] randomGraph(int N, int V) {
		int[][] graph = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				int num = (int) (Math.random() * V);
				graph[i][j] = num;
				graph[j][i] = num;
			}
		}
		return graph;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int V = 20;
		int testTime = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[][] graph = randomGraph(N, V);
			int ans1 = right(graph);
			int ans2 = km(graph);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				for (int r = 0; r < graph.length; r++) {
					for (int c = 0; c < graph.length; c++) {
						System.out.print(graph[r][c] + " ");
					}
					System.out.println();
				}
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");

	}

}

// 注意本文件中，graph不是邻接矩阵的含义，而是一个二部图
// 在长度为N的邻接矩阵matrix中，所有的点有N个，matrix[i][j]表示点i到点j的距离或者权重
// 而在二部图graph中，所有的点有2*N个，行所对应的点有N个，列所对应的点有N个
// 而且认为，行所对应的点之间是没有路径的，列所对应的点之间也是没有路径的！

package class_2022_03_5_week;

import java.util.Arrays;

// km算法
// O(N^3)，最大匹配问题，最优的解！
public class Code01_KMAlgorithm {

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
		// dfs过程中，碰过的点！
		boolean[] x = new boolean[N];
		boolean[] y = new boolean[N];
		// 降低的预期！
		// 公主上，打一个，降低预期的值，只维持最小！
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
		for (int from = 0; from < N; from++) {
			for (int i = 0; i < N; i++) {
				slack[i] = invalid;
			}
			Arrays.fill(x, false);
			Arrays.fill(y, false);
			// dfs() : from王子，能不能不降预期，匹配成功！
			// 能：dfs返回true！
			// 不能：dfs返回false！
			while (!dfs(from, x, y, lx, ly, match, slack, graph)) {
				// 刚才的dfs，失败了！
				// 需要拿到，公主的slack里面，预期下降幅度的最小值！
				int d = invalid;
				for (int i = 0; i < N; i++) {
					if (!y[i] && slack[i] < d) {
						d = slack[i];
					}
				}
				// 按照最小预期来调整预期
				for (int i = 0; i < N; i++) {
					if (x[i]) {
						lx[i] = lx[i] - d;
					}
					if (y[i]) {
						ly[i] = ly[i] + d;
					}
				}
				Arrays.fill(x, false);
				Arrays.fill(y, false);
				// 然后回到while里，再次尝试
			}
		}
		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans += (lx[i] + ly[i]);
		}
		return ans;
	}

	// from, 当前的王子
	// x，王子碰没碰过
	// y, 公主碰没碰过
	// lx，所有王子的预期
	// ly, 所有公主的预期
	// match，所有公主，之前的分配，之前的爷们！
	// slack，连过，但没允许的公主，最小下降的幅度
	// map，报价，所有王子对公主的报价
	// 返回，from号王子，不降预期能不能配成！
	public static boolean dfs(int from, boolean[] x, boolean[] y, int[] lx, int[] ly, int[] match, int[] slack,
			int[][] map) {
		int N = map.length;
		x[from] = true;
		for (int to = 0; to < N; to++) {
			if (!y[to]) { // 只有没dfs过的公主，才会去尝试
				int d = lx[from] + ly[to] - map[from][to];
				if (d != 0) {// 如果当前的路不符合预期，更新公主的slack值
					slack[to] = Math.min(slack[to], d);
				} else { // 如果当前的路符合预期，尝试直接拿下，或者抢夺让之前的安排倒腾去
					y[to] = true;
					if (match[to] == -1 || dfs(match[to], x, y, lx, ly, match, slack, map)) {
						match[to] = from;
						return true;
					}
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

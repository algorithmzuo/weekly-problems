package class_2022_06_2_week;

// 来自网易
// 题目出处 : https://leetcode-cn.com/circle/discuss/uOnnUA/
// 已知一个n*n的01矩阵，
// 只能通过通过行交换、或者列交换的方式调整矩阵，
// 判断这个矩阵的对角线是否能全为1，如果能返回true，不能返回false
// 我们升级一下:
// 已知一个n*n的01矩阵，
// 只能通过通过行交换、或者列交换的方式调整矩阵，
// 判断这个矩阵的对角线是否能全为1，如果不能打印-1
// 如果能，打印需要交换的次数，并且打印怎么交换
// 在线测试链接 : http://acm.hdu.edu.cn/showproblem.php?pid=2819
// 提交如下代码，并把主类名改成"Main", 
// 可以直接通过
import java.util.Arrays;
import java.util.Scanner;

public class Code05_SwapRowOrColMakeDiagonalAllOne {

	public static int[][] out = new int[1000][2];

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int[][] graph = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					graph[i][j] = sc.nextInt();
				}
			}
			int t = km(graph);
			System.out.println(t);
			for (int i = 0; i < t; i++) {
				System.out.println("R " + (out[i][0] + 1) + " " + (out[i][1] + 1));
			}
		}
		sc.close();
	}

	// 改写的km算法
	// 如果达成的最优匹配收益 < N，返回-1;
	// 如果达成的最优匹配收益 == N，加工好如何交换的结果(全局out数组)，
	// 最终返回交换的次数(>=0);
	public static int km(int[][] graph) {
		int N = graph.length;
		int[] lx = new int[N];
		int[] ly = new int[N];
		int[] match = new int[N];
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
		for (int from = 0; from < N; from++) {
			for (int i = 0; i < N; i++) {
				slack[i] = invalid;
			}
			Arrays.fill(x, false);
			Arrays.fill(y, false);
			while (!dfs(from, x, y, lx, ly, match, slack, graph)) {
				int d = invalid;
				for (int i = 0; i < N; i++) {
					if (!y[i] && slack[i] < d) {
						d = slack[i];
					}
				}
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
			}
		}
		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans += (lx[i] + ly[i]);
		}
		if (ans < N) {
			return -1;
		}
		int t = 0;
		for (int i = 0; i < N; i++) {
			int u = match[i], v = i;
			if (u != v) {
				out[t][0] = v;
				out[t++][1] = u;
				for (int j = i + 1; j < N; j++) {
					if (match[j] == v) {
						match[j] = u;
					}
				}
			}
		}
		return t;
	}

	public static boolean dfs(int from, boolean[] x, boolean[] y, int[] lx, int[] ly, int[] match, int[] slack,
			int[][] map) {
		int N = map.length;
		x[from] = true;
		for (int to = 0; to < N; to++) {
			if (!y[to]) {
				int d = lx[from] + ly[to] - map[from][to];
				if (d != 0) {
					slack[to] = Math.min(slack[to], d);
				} else {
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

}
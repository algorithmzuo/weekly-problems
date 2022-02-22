package class_2022_03_2_week;

// 来自微软
// 在N*N的正方形棋盘中，有N*N个棋子，那么每个格子正好可以拥有一个棋子
// 但是现在有些棋子聚集到一个格子上了，比如：
// 2 0 3
// 0 1 0
// 3 0 0
// 如上的二维数组代表，一共3*3个格子
// 但是有些格子有2个棋子、有些有3个、有些有1个、有些没有
// 请你用棋子移动的方式，让每个格子都有一个棋子
// 每个棋子可以上、下、左、右移动，每移动一步算1的代价
// 返回最小的代价
public class Code01_ToAllSpace {

	// 暴力解
	// 作为对数器
	public static int minDistance1(int[][] map) {
		int n = 0;
		int m = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				n += Math.max(0, map[i][j] - 1);
				m += map[i][j] == 0 ? 1 : 0;
			}
		}
		if (n != m || n == 0) {
			return 0;
		}
		int[][] nodes = new int[n][2];
		int[][] space = new int[m][2];
		n = 0;
		m = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				for (int k = 2; k <= map[i][j]; k++) {
					nodes[n][0] = i;
					nodes[n++][1] = j;
				}
				if (map[i][j] == 0) {
					space[m][0] = i;
					space[m++][1] = j;
				}
			}
		}
		return process1(nodes, 0, space);
	}

	public static int process1(int[][] nodes, int index, int[][] space) {
		int ans = 0;
		if (index == nodes.length) {
			for (int i = 0; i < nodes.length; i++) {
				ans += distance(nodes[i], space[i]);
			}
		} else {
			ans = Integer.MAX_VALUE;
			for (int i = index; i < nodes.length; i++) {
				swap(nodes, index, i);
				ans = Math.min(ans, process1(nodes, index + 1, space));
				swap(nodes, index, i);
			}
		}
		return ans;
	}

	public static void swap(int[][] nodes, int i, int j) {
		int[] tmp = nodes[i];
		nodes[i] = nodes[j];
		nodes[j] = tmp;
	}

	public static int distance(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}

	// 正式方法
	// KM算法
	public static int minDistance2(int[][] map) {
		int n = 0;
		int m = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				n += Math.max(0, map[i][j] - 1);
				m += map[i][j] == 0 ? 1 : 0;
			}
		}
		if (n != m || n == 0) {
			return 0;
		}
		int[][] nodes = new int[n][2];
		int[][] space = new int[m][2];
		n = 0;
		m = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				for (int k = 2; k <= map[i][j]; k++) {
					nodes[n][0] = i;
					nodes[n++][1] = j;
				}
				if (map[i][j] == 0) {
					space[m][0] = i;
					space[m++][1] = j;
				}
			}
		}
		int[][] graph = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				graph[i][j] = -distance(nodes[i], space[j]);
			}
		}
		return -km(graph);
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
		for (int t = 0; t < N; t++) {
			clean(x);
			clean(y);
			for (int i = 0; i < N; i++) {
				slack[i] = invalid;
			}
			while (!dfs(t, x, y, lx, ly, match, slack, graph)) {
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

	public static void clean(boolean[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = false;
		}
	}

	public static boolean dfs(int t, boolean[] x, boolean[] y, int[] lx, int[] ly, int[] match, int[] slack,
			int[][] map) {
		int N = map.length;
		x[t] = true;
		for (int u = 0; u < N; u++) {
			int d = lx[t] + ly[u] - map[t][u];
			if (!y[u] && d == 0) {
				y[u] = true;
				if (match[u] == -1 || dfs(match[u], x, y, lx, ly, match, slack, map)) {
					match[u] = t;
					return true;
				}
			} else {
				slack[u] = Math.min(slack[u], d);
			}
		}
		return false;
	}

	// 为了测试
	public static int[][] randomValidMatrix(int len) {
		int[][] ans = new int[len][len];
		int all = len * len;
		for (int i = 1; i <= all; i++) {
			ans[(int) (Math.random() * len)][(int) (Math.random() * len)]++;
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 4;
		int testTimes = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int[][] map = randomValidMatrix(len);
			int ans1 = minDistance1(map);
			int ans2 = minDistance2(map);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

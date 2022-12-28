package class_2022_12_4_week;

// Floyd算法应用
// 存在一个由 n 个节点组成的无向连通图，图中的节点按从 0 到 n - 1 编号
// 给你一个数组 graph 表示这个图
// 其中，graph[i] 是一个列表，由所有与节点 i 直接相连的节点组成
// 返回能够访问所有节点的最短路径的长度
// 你可以在任一节点开始和停止，也可以多次重访节点，并且可以重用边
// 测试链接 : https://leetcode.cn/problems/shortest-path-visiting-all-nodes/
public class Code03_ShortestPathVisitingAllNodes {

	public static int shortestPathLength(int[][] graph) {
		int n = graph.length;
		int[][] distance = floyd(n, graph);
		int ans = Integer.MAX_VALUE;
		int[][] dp = new int[1 << n][n];
		for (int i = 0; i < (1 << n); i++) {
			for (int j = 0; j < n; j++) {
				dp[i][j] = -1;
			}
		}
		for (int i = 0; i < n; i++) {
			ans = Math.min(ans, process(1 << i, i, n, distance, dp));
		}
		return ans;
	}

	public static int[][] floyd(int n, int[][] graph) {
		int[][] distance = new int[n][n];
		// 初始化认为都没路
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
		}
		// 自己到自己的距离为0
		for (int i = 0; i < n; i++) {
			distance[i][i] = 0;
		}
		// 支持任意有向图，把直接边先填入
		for (int cur = 0; cur < n; cur++) {
			for (int next : graph[cur]) {
				distance[cur][next] = 1;
				distance[next][cur] = 1;
			}
		}
		// O(N^3)的过程
		// 枚举每个跳板
		// 注意! 跳板要最先枚举，然后是from和to
		for (int jump = 0; jump < n; jump++) {
			for (int from = 0; from < n; from++) {
				for (int to = 0; to < n; to++) {
					if (distance[from][jump] != Integer.MAX_VALUE && distance[jump][to] != Integer.MAX_VALUE
							&& distance[from][to] > distance[from][jump] + distance[jump][to]) {
						distance[from][to] = distance[from][jump] + distance[jump][to];
					}
				}
			}
		}
		return distance;
	}

	// status : 所有已经走过点的状态
	// 0 1 2 3 4 5 
	// int 
	//        5 4 3 2 1 0
	//        0 0 1 1 0 1
	// cur : 当前所在哪个城市！
	// n : 一共有几座城
	// 返回值 : 从此时开始，逛掉所有的城市，至少还要走的路，返回！
	public static int process(int status, int cur, int n, int[][] distance, int[][] dp) {
		// 5 4 3 2 1 0
		// 1 1 1 1 1 1
		// 1 << 6 - 1
		if (status == (1 << n) - 1) {
			return 0;
		}
		if (dp[status][cur] != -1) {
			return dp[status][cur];
		}
		int ans = Integer.MAX_VALUE;
		// status:
		// 5 4 3 2 1 0
		// 0 0 1 0 1 1
		// cur : 0
		// next : 2 4 5
		for (int next = 0; next < n; next++) {
			if ((status & (1 << next)) == 0 && distance[cur][next] != Integer.MAX_VALUE) {
				int nextAns = process(status | (1 << next), next, n, distance, dp);
				if (nextAns != Integer.MAX_VALUE) {
					ans = Math.min(ans, distance[cur][next] + nextAns);
				}
			}
		}
		dp[status][cur] = ans;
		return ans;
	}

}

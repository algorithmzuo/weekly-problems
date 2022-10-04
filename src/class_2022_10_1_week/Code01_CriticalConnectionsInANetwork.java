package class_2022_10_1_week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 力扣数据中心有 n 台服务器，分别按从 0 到 n-1 的方式进行了编号
// 它们之间以「服务器到服务器」点对点的形式相互连接组成了一个内部集群
// 其中连接 connections 是无向的
// 从形式上讲，connections[i] = [a, b] 表示服务器 a 和 b 之间形成连接
// 任何服务器都可以直接或者间接地通过网络到达任何其他服务器。
// "关键连接"是在该集群中的重要连接，也就是说，假如我们将它移除
// 便会导致某些服务器无法访问其他服务器。
// 请你以任意顺序返回该集群内的所有"关键连接"
// 测试链接 : https://leetcode.cn/problems/critical-connections-in-a-network/
public class Code01_CriticalConnectionsInANetwork {

	public static int[] dfn = new int[100010];

	public static int[] low = new int[100010];

	public static int dfnCnt = 0;

	public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (List<Integer> edge : connections) {
			graph.get(edge.get(0)).add(edge.get(1));
			graph.get(edge.get(1)).add(edge.get(0));
		}
		Arrays.fill(dfn, 0, n, 0);
		Arrays.fill(low, 0, n, 0);
		dfnCnt = 0;
		List<List<Integer>> ans = new ArrayList<>();
		tarjan(0, -1, graph, ans);
		return ans;
	}

	public static void tarjan(int cur, int father, ArrayList<ArrayList<Integer>> graph, List<List<Integer>> ans) {
		dfn[cur] = low[cur] = ++dfnCnt;
		for (Integer next : graph.get(cur)) {
			if (next != father) {
				if (dfn[next] == 0) {
					tarjan(next, cur, graph, ans);
					low[cur] = Math.min(low[cur], low[next]);
				} else {
					low[cur] = Math.min(low[cur], dfn[next]);
				}
			}
		}
		if (low[cur] == dfn[cur] && cur != 0) {
			ans.add(Arrays.asList(father, cur));
		}
	}

}

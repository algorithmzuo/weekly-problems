package class_2021_12_2_week;

import java.util.HashSet;

// 来自美团
// 给定一个无向图
// 从任何一个点x出发，比如有一条路径: x -> a -> b -> c -> y
// 这条路径上有5个点并且5个点都不一样的话，我们说(x,a,b,c,y)是一条合法路径
// 这条合法路径的代表，就是x,a,b,c,y所组成的集合，我们叫做代表集合
// 如果从b到y，还有一条路径叫(b,a,c,x,y)，那么(x,a,b,c,y)和(b,a,c,x,y)是同一个代表集合
// 返回这个无向图中所有合法路径的代表集合数量
// 题目给定点的数量n <= 15，边的数量m <= 60
// 所有的点编号都是从0~n-1的
public class Code03_FiveNodesListNumbers {

	// graph[i] = { a, b, c} 代表：点i直接相邻的节点有a,b,c
	// graph[j] = { d } 代表：点j直接相邻的节点有d
	// 所以二维数组graph可以表示无向图
	public static int validPathSets(int[][] graph) {
		int n = graph.length;
		HashSet<Integer> set = new HashSet<>();
		for (int from = 0; from < n; from++) {
			dfs(0, 0, from, graph, set);
		}
		return set.size();
	}

	public static void dfs(int status, int len, int cur, int[][] graph, HashSet<Integer> set) {
		if ((status & (1 << cur)) == 0) {
			len++;
			status |= 1 << cur;
			if (len == 5) {
				set.add(status);
			} else {
				for (int next : graph[cur]) {
					dfs(status, len, next, graph, set);
				}
			}
		}
	}

}

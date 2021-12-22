package class_2021_12_4_week;

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
public class Code01_FiveNodesListNumbers {

	// graph[i] = { a, b, c} 代表：点i直接相邻的节点有a,b,c
	// graph[j] = { d } 代表：点j直接相邻的节点有d
	// 所以二维数组graph可以表示无向图
	// 0 : 
	// 1 : 
	// 2 : 
	// n-1 : 
	public static int validPathSets(int[][] graph) {
		int n = graph.length;
		// 任何一个合法路径的集合，都被弄成了整数形式
		// 0010010011 -> int
		// 甲 ： 0011010011 
		// 乙 ： 0011010011
        // 丙 ： 0011010011
		HashSet<Integer> set = new HashSet<>();
		// 下面的过程：从每个点出发，0、1、2、3、。。。
		// 从x点出发，往外最多迈5步，所产生的所有路径，都要！
		for (int from = 0; from < n; from++) {
			dfs(0, 0, from, graph, set);
		}
		return set.size();
	}

	// int status -> 已经走过了哪些点的集合 ->  00001101
	// int len -> 已经往外几步了！
	// int cur -> 当前来到的是几号点！
	// int[][] graph -> 图
	// HashSet<Integer> set -> 收集所有合法路径的点集合！
	public static void dfs(int status, int len, int cur, int[][] graph, HashSet<Integer> set) {
		if ((status & (1 << cur)) == 0) { // 之前走过的点，不包括cur，迈上去！
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

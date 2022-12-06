package class_2022_12_1_week;

import java.util.ArrayList;

// 给定一个无向、连通的树
// 树中有 n 个标记为 0...n-1 的节点以及 n-1 条边 。
// 给定整数 n 和数组 edges ，
// edges[i] = [ai, bi]表示树中的节点 ai 和 bi 之间有一条边。
// 返回长度为 n 的数组 answer ，其中 answer[i] : 
// 树中第 i 个节点与所有其他节点之间的距离之和。
// 测试链接 : https://leetcode.cn/problems/sum-of-distances-in-tree/
public class Code03_SumOfDistancesInTree {

	public int N = 30001;
	public int[] size = new int[N];
	public int[] distance = new int[N];

	public int[] sumOfDistancesInTree(int n, int[][] edges) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		collect(0, -1, graph);
		int[] ans = new int[n];
		setAns(0, -1, 0, graph, ans);
		return ans;
	}

	public void collect(int cur, int father, ArrayList<ArrayList<Integer>> graph) {
		size[cur] = 1;
		distance[cur] = 0;
		for (int next : graph.get(cur)) {
			if (next != father) {
				collect(next, cur, graph);
				distance[cur] += distance[next] + size[next];
				size[cur] += size[next];
			}
		}
	}

	public void setAns(int cur, int father, int upDistance, ArrayList<ArrayList<Integer>> graph, int[] ans) {
		ans[cur] = upDistance + distance[cur];
		for (int next : graph.get(cur)) {
			if (next != father) {
				setAns(next, cur, ans[cur] + size[0] - distance[next] - (size[next] << 1), graph, ans);
			}
		}
	}

}

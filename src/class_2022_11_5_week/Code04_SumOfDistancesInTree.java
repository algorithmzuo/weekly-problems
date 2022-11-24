package class_2022_11_5_week;

import java.util.ArrayList;

// 给定一个无向、连通的树
// 树中有 n 个标记为 0...n-1 的节点以及 n-1 条边 。
// 给定整数 n 和数组 edges ，
// edges[i] = [ai, bi]表示树中的节点 ai 和 bi 之间有一条边。
// 返回长度为 n 的数组 answer ，其中 answer[i] : 
// 树中第 i 个节点与所有其他节点之间的距离之和。
// 测试链接 : https://leetcode.cn/problems/sum-of-distances-in-tree/
public class Code04_SumOfDistancesInTree {

	public int[] sumOfDistancesInTree(int n, int[][] edges) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		int[] size = new int[n];
		int[] distance = new int[n];
		collect(0, -1, graph, size, distance);
		int[] ans = new int[n];
		setAns(0, -1, graph, size, distance, ans);
		return ans;
	}

	public void collect(int cur, int father, ArrayList<ArrayList<Integer>> graph, int[] size, int[] distance) {
		size[cur] = 1;
		distance[cur] = 0;
		for (int next : graph.get(cur)) {
			if (next != father) {
				collect(next, cur, graph, size, distance);
				distance[cur] += distance[next] + size[next];
				size[cur] += size[next];
			}
		}
	}

	public void setAns(int cur, int father, ArrayList<ArrayList<Integer>> graph, int[] size, int[] distance,
			int[] ans) {
		ans[cur] = distance[cur];
		for (int next : graph.get(cur)) {
			if (next != father) {
				int saveCurDistance = distance[cur];
				int saveNextDistance = distance[next];
				int saveCurSize = size[cur];
				int saveNextSize = size[next];

				distance[cur] -= distance[next] + size[next];
				size[cur] -= size[next];
				distance[next] += distance[cur] + size[cur];
				size[next] += size[cur];

				setAns(next, cur, graph, size, distance, ans);

				distance[cur] = saveCurDistance;
				distance[next] = saveNextDistance;
				size[cur] = saveCurSize;
				size[next] = saveNextSize;
			}
		}
	}

}

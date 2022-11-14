package class_2022_12_1_week;

import java.util.ArrayList;

// 测试链接 : https://leetcode.cn/problems/sum-of-distances-in-tree/
public class Code03_SumOfDistancesInTree {

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

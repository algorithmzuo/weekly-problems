package class_2022_07_1_week;

import java.util.ArrayList;

// 给定一个棵树
// 树上每个节点都有自己的值，记录在数组nums里
// 比如nums[4] = 10，表示4号点的值是10
// 给定树上的每一条边，记录在二维数组edges里
// 比如edges[8] = {4, 9}表示4和9之间有一条无向边
// 可以保证输入一定是一棵树，只不过边是无向边
// 那么我们知道，断掉任意两条边，都可以把整棵树分成3个部分。
// 假设是三个部分为a、b、c
// a部分的值是：a部分所有点的值异或起来
// b部分的值是：b部分所有点的值异或起来
// c部分的值是：c部分所有点的值异或起来
// 请问怎么分割，能让最终的：三个部分中最大的异或值 - 三个部分中最小的异或值，最小
// 返回这个最小的差值
// 测试链接 : https://leetcode.cn/problems/minimum-score-after-removals-on-a-tree/
public class Code02_MinimumScoreAfterRemovalsOnATree {

	public static int cnt;

	public static int minimumScore(int[] nums, int[][] edges) {
		int n = nums.length;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		int[] dfn = new int[n];
		int[] xor = new int[n];
		int[] size = new int[n];
		cnt = 1;
		dfs(nums, graph, 0, dfn, xor, size);
		int ans = Integer.MAX_VALUE, m = edges.length, cut1, cut2, pre, pos, part1, part2, part3, max, min;
		for (int i = 0; i < m; i++) {
			cut1 = dfn[edges[i][0]] < dfn[edges[i][1]] ? edges[i][1] : edges[i][0];
			for (int j = i + 1; j < m; j++) {
				cut2 = dfn[edges[j][0]] < dfn[edges[j][1]] ? edges[j][1] : edges[j][0];
				pre = dfn[cut1] < dfn[cut2] ? cut1 : cut2;
				pos = pre == cut1 ? cut2 : cut1;
				part1 = xor[pos];
				if (dfn[pos] < dfn[pre] + size[pre]) {
					part2 = xor[pre] ^ xor[pos];
					part3 = xor[0] ^ xor[pre];
				} else {
					part2 = xor[pre];
					part3 = xor[0] ^ part1 ^ part2;
				}
				max = Math.max(Math.max(part1, part2), part3);
				min = Math.min(Math.min(part1, part2), part3);
				ans = Math.min(ans, max - min);
			}
		}
		return ans;
	}

	public static void dfs(int[] nums, ArrayList<ArrayList<Integer>> graph, int cur, int[] dfn, int[] xor, int[] size) {
		dfn[cur] = cnt++;
		xor[cur] = nums[cur];
		size[cur] = 1;
		for (int next : graph.get(cur)) {
			if (dfn[next] == 0) {
				dfs(nums, graph, next, dfn, xor, size);
				xor[cur] ^= xor[next];
				size[cur] += size[next];
			}
		}
	}

}

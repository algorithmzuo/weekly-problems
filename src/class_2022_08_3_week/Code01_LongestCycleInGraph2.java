package class_2022_08_3_week;

// 给你一个 n 个节点的 有向图 ，节点编号为 0 到 n - 1 ，
// 其中每个节点 至多 有一条出边。
// 图用一个大小为 n 下标从 0 开始的数组 edges 表示，
// 节点 i 到节点 edges[i] 之间有一条有向边。
// 如果节点 i 没有出边，那么 edges[i] == -1 。
// 请你返回图中的 最长 环，如果没有任何环，请返回 -1 。
// 一个环指的是起点和终点是 同一个 节点的路径。
// 测试链接 : https://leetcode.cn/problems/longest-cycle-in-a-graph/
public class Code01_LongestCycleInGraph2 {

	public int longestCycle(int[] edges) {
		// edges[i] = j  i -> j
		// edges[i] = -1  i X
		int n = edges.length;
		int[] ids = new int[n];
		// 发现的最大环，有几个点！
		int ans = -1;
		for (int from = 0, cnt = 1; from < n; from++) {
			if (ids[from] == 0) {
				for (int cur = from, fromId = cnt; cur != -1;
						cur = edges[cur]) {
					// from -> -> cur ->
					if (ids[cur] > 0) {
						// 访问过，此时的环，之前遍历过的点
						if (ids[cur] >= fromId) { // 新的环
							ans = Math.max(ans, cnt - ids[cur]);
						}
						// 如果上面的if不成立，老的点，直接跳出
						break;
					}
					ids[cur] = cnt++;
				}
			}
		}
		return ans;
	}

}

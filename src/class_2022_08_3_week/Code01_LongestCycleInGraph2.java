package class_2022_08_3_week;

// 测试链接 : https://leetcode.cn/problems/longest-cycle-in-a-graph/
public class Code01_LongestCycleInGraph2 {

	public int longestCycle(int[] edges) {
		int n = edges.length;
		int[] ids = new int[n];
		int ans = -1;
		for (int from = 0, cnt = 1; from < n; from++) {
			if (ids[from] == 0) {
				for (int cur = from, fromId = cnt; cur != -1; cur = edges[cur]) {
					if (ids[cur] > 0) {
						if (ids[cur] >= fromId) {
							ans = Math.max(ans, cnt - ids[cur]);
						}
						break;
					}
					ids[cur] = cnt++;
				}
			}
		}
		return ans;
	}

}

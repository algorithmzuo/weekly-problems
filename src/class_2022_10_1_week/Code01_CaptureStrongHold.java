package class_2022_10_1_week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 来自Leetcode周赛
// 魔物了占领若干据点，这些据点被若干条道路相连接，
// roads[i] = [x, y] 表示编号 x、y 的两个据点通过一条道路连接。
// 现在勇者要将按照以下原则将这些据点逐一夺回：
// 在开始的时候，勇者可以花费资源先夺回一些据点，
// 初始夺回第 j 个据点所需消耗的资源数量为 cost[j]
// 接下来，勇者在不消耗资源情况下，
// 每次可以夺回一个和「已夺回据点」相连接的魔物据点，
// 并对其进行夺回
// 为了防止魔物暴动，勇者在每一次夺回据点后（包括花费资源夺回据点后），
// 需要保证剩余的所有魔物据点之间是相连通的（不经过「已夺回据点」）。
// 请返回勇者夺回所有据点需要消耗的最少资源数量。
// 输入保证初始所有据点都是连通的，且不存在重边和自环
// 测试链接 : https://leetcode.cn/problems/s5kipK/
public class Code01_CaptureStrongHold {

	public static long minimumCost(int[] cost, int[][] roads) {
		int n = cost.length;
		if (n == 1) {
			return cost[0];
		}
		int m = roads.length;
		DoubleConnectedComponents dc = new DoubleConnectedComponents(n, m, roads);
		List<List<Integer>> dcc = dc.getDcc();
		boolean[] cut = dc.getCuts();
		long ans = 0;
		if (dcc.size() == 1) {
			ans = Integer.MAX_VALUE;
			for (int num : cost) {
				ans = Math.min(ans, num);
			}
		} else {
			ArrayList<Integer> arr = new ArrayList<>();
			for (List<Integer> set : dcc) {
				int cutCnt = 0;
				int curCost = Integer.MAX_VALUE;
				for (int nodes : set) {
					if (cut[nodes]) {
						cutCnt++;
					} else {
						curCost = Math.min(curCost, cost[nodes]);
					}
				}
				if (cutCnt == 1) {
					arr.add(curCost);
				}
			}
			arr.sort((a, b) -> a - b);
			for (int i = 0; i < arr.size() - 1; i++) {
				ans += arr.get(i);
			}
		}
		return ans;
	}

	public static class DoubleConnectedComponents {
		public int[] head;
		public int[] next;
		public int[] to;
		public int[] dfn;
		public int[] low;
		public int[] stack;
		public List<List<Integer>> dcc;
		public boolean[] cut;
		public static int edgeCnt;
		public static int dfnCnt;
		public static int top;
		public static int root;

		public DoubleConnectedComponents(int n, int m, int[][] roads) {
			init(n, m);
			createGraph(roads);
			creatDcc(n);
		}

		private void init(int n, int m) {
			head = new int[n];
			Arrays.fill(head, -1);
			next = new int[m << 1];
			to = new int[m << 1];
			dfn = new int[n];
			low = new int[n];
			stack = new int[n];
			dcc = new ArrayList<>();
			cut = new boolean[n];
			edgeCnt = 0;
			dfnCnt = 0;
			top = 0;
			root = 0;
		}

		private void createGraph(int[][] roads) {
			for (int[] edges : roads) {
				add(edges[0], edges[1]);
				add(edges[1], edges[0]);
			}
		}

		private void add(int u, int v) {
			to[edgeCnt] = v;
			next[edgeCnt] = head[u];
			head[u] = edgeCnt++;
		}

		private void creatDcc(int n) {
			for (int i = 0; i < n; i++) {
				if (dfn[i] == 0) {
					root = i;
					tarjan(i);
				}
			}
		}

		private void tarjan(int x) {
			dfn[x] = low[x] = ++dfnCnt;
			stack[top++] = x;
			int flag = 0;
			if (x == root && head[x] == -1) {
				dcc.add(new ArrayList<>());
				dcc.get(dcc.size() - 1).add(x);
			} else {
				for (int i = head[x]; i >= 0; i = next[i]) {
					int y = to[i];
					if (dfn[y] == 0) {
						tarjan(y);
						low[x] = Math.min(low[x], low[y]);
						if (low[y] >= dfn[x]) {
							flag++;
							if (x != root || flag > 1) {
								cut[x] = true;
							}
							List<Integer> curAns = new ArrayList<>();
							for (int z = stack[--top]; z != y; z = stack[--top]) {
								curAns.add(z);
							}
							curAns.add(y);
							curAns.add(x);
							dcc.add(curAns);
						}
					} else {
						low[x] = Math.min(low[x], dfn[y]);
					}
				}
			}

		}

		public List<List<Integer>> getDcc() {
			return dcc;
		}

		public boolean[] getCuts() {
			return cut;
		}

	}

}

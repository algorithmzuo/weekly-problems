package class_2022_03_1_week;

import java.util.ArrayList;

// tarjan算法求有向图的强连通分量
public class Code01_StronglyConnectedComponents {
	
	public static class StronglyConnectedComponents {
		public ArrayList<ArrayList<Integer>> nexts;
		public int n;
		public int[] stack;
		public int stackSize;
		public int[] dfn;
		public int[] low;
		public int cnt;
		public int[] scc;
		public int sccn;

		// 请保证点的编号从1开始，不从0开始
		// 注意：
		// 如果edges里有0、1、2...n这些点，那么容器edges的大小为n+1
		// 但是0点是弃而不用的，所以1..n才是有效的点，所以有效大小是n
		public StronglyConnectedComponents(ArrayList<ArrayList<Integer>> edges) {
			nexts = edges;
			init();
			scc();
		}

		private void init() {
			n = nexts.size();
			stack = new int[n];
			stackSize = 0;
			dfn = new int[n];
			low = new int[n];
			cnt = 0;
			scc = new int[n];
			sccn = 0;
			n--;
		}

		private void scc() {
			for (int i = 1; i <= n; i++) {
				if (dfn[i] == 0) {
					tarjan(i);
				}
			}
		}

		// low[]
		// dfn[]
		// stack[]
		// int stackSize
		// boolean isStack[]
		// int cnt;
		// int sccn;
		// scc[]
		private void tarjan(int p) {
			low[p] = dfn[p] = ++cnt;
			stack[stackSize++] = p;
			for (int q : nexts.get(p)) {
				// q 当前p的每一个孩子
				if (dfn[q] == 0) {
					tarjan(q);
				}
				// q 肯定遍历过  1) 遍历过，结算了！2）遍历过，没结算
				if (scc[q] == 0) { // scc[q]!=0 q已经属于某个集团了！不能用来更新
					low[p] = Math.min(low[p], low[q]);
				}
			}
			if (low[p] == dfn[p]) {
				sccn++;
				int top = 0;
				do {
					top = stack[--stackSize];
					scc[top] = sccn;
				} while (top != p);
			}
		}

		public int[] getScc() {
			return scc;
		}

		public int getSccn() {
			return sccn;
		}

		public ArrayList<ArrayList<Integer>> getShortGraph() {
			ArrayList<ArrayList<Integer>> shortGraph = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i <= sccn; i++) {
				shortGraph.add(new ArrayList<Integer>());
			}
			for (int u = 1; u <= n; u++) {
				for (int v : nexts.get(u)) {
					if (scc[u] != scc[v]) {
						shortGraph.get(scc[u]).add(scc[v]);
					}
				}
			}
			return shortGraph;
		}

	}

}

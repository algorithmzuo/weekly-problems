package class_2022_03_1_week;

import java.util.ArrayList;

public class Code01_StronglyConnectedComponents {

	public static class StronglyConnectedComponents {
		public ArrayList<ArrayList<Integer>> nexts;
		public int n;
		public int[] stack;
		public int stackSize;
		public boolean[] isInStack;
		public int[] dfsn;
		public int[] low;
		public int cnt;
		public int[] scc;
		public int sccn;

		// 请保证点的编号从1开始，不从0开始
		// 注意：
		// 如果edges里，有0、1、2、3、4、5这些点，大小为6
		// 但是，0点是弃而不用的，所以1、2、3、4、5才是有效点所以有效大小是5
		public StronglyConnectedComponents(ArrayList<ArrayList<Integer>> edges) {
			nexts = edges;
			init();
			scc();
		}

		private void init() {
			n = nexts.size();
			stack = new int[n];
			stackSize = 0;
			isInStack = new boolean[n];
			dfsn = new int[n];
			low = new int[n];
			cnt = 0;
			scc = new int[n];
			sccn = 0;
			n--;
		}

		private void scc() {
			for (int i = 1; i <= n; i++) {
				if (dfsn[i] == 0) {
					tarjan(i);
				}
			}
		}

		private void tarjan(int p) {
			low[p] = dfsn[p] = ++cnt;
			isInStack[p] = true;
			stack[stackSize++] = p;
			for (int q : nexts.get(p)) {
				if (dfsn[q] == 0) {
					tarjan(q);
					low[p] = Math.min(low[p], low[q]);
				} else {
					if (isInStack[q]) {
						low[p] = Math.min(low[p], dfsn[q]);
					}
				}
			}
			if (low[p] == dfsn[p]) {
				sccn++;
				int top = 0;
				do {
					top = stack[--stackSize];
					isInStack[top] = false;
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

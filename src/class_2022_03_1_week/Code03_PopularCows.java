package class_2022_03_1_week;

// 强连通分量练习题目
// A -> B，表示A认为B是红人
// A -> B -> C，表示A认为B是红人，B认为C是红人，规定“认为”关系有传递性，所以A也认为C是红人
// 给定一张有向图，方式是给定M个有序对(A, B)
// (A, B)表示A认为B是红人，该关系具有传递性
// 给定的有序对中可能包含(A, B)和(B, C)，但不包含(A,C)
// 求被其他所有人认为是红人的总数。
// 测试链接 : http://poj.org/problem?id=2186
// 注册一下 -> 页面上点击"submit" -> 语言选择java
// 然后把如下代码粘贴进去, 把主类名改成"Main", 可以直接通过
import java.util.ArrayList;
import java.util.Scanner;

public class Code03_PopularCows {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i <= n; i++) {
				edges.add(new ArrayList<Integer>());
			}
			for (int i = 0; i < m; i++) {
				int from = sc.nextInt();
				int to = sc.nextInt();
				edges.get(from).add(to);
			}
			StronglyConnectedComponents connectedComponents = new StronglyConnectedComponents(edges);
			int sccn = connectedComponents.getSccn();
			if (sccn == 1) {
				System.out.println(n);
			} else {
				ArrayList<ArrayList<Integer>> dag = connectedComponents.getShortGraph();
				int zeroOut = 0;
				int outScc = 0;
				for (int i = 1; i <= sccn; i++) {
					if (dag.get(i).size() == 0) {
						zeroOut++;
						outScc = i;
					}
				}
				if (zeroOut > 1) {
					System.out.println(0);
				} else {
					int[] scc = connectedComponents.getScc();
					int ans = 0;
					for (int i = 1; i <= n; i++) {
						if (scc[i] == outScc) {
							ans++;
						}
					}
					System.out.println(ans);
				}
			}
		}
		sc.close();
	}

	public static class StronglyConnectedComponents {
		public ArrayList<ArrayList<Integer>> nexts;
		public int n;
		public int[] stack;
		public int stackSize;
		public boolean[] isInStack;
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
			isInStack = new boolean[n];
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

		private void tarjan(int p) {
			low[p] = dfn[p] = ++cnt;
			isInStack[p] = true;
			stack[stackSize++] = p;
			for (int q : nexts.get(p)) {
				if (dfn[q] == 0) {
					tarjan(q);
				}
				if (scc[q] == 0) {
					low[p] = Math.min(low[p], low[q]);
				}
			}
			if (low[p] == dfn[p]) {
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

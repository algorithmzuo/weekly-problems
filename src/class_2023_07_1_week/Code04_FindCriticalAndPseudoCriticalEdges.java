package class_2023_07_1_week;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// 给你一个 n 个点的带权无向连通图，节点编号为 0 到 n-1
// 同时还有一个数组 edges ，其中 edges[i] = [fromi, toi, weighti]
// 表示在 fromi 和 toi 节点之间有一条带权无向边
// 最小生成树 (MST) 是给定图中边的一个子集
// 它连接了所有节点且没有环，而且这些边的权值和最小
// 请你找到给定图中最小生成树的所有关键边和伪关键边
// 如果从图中删去某条边，会导致最小生成树的权值和增加，那么我们就说它是一条关键边
// 伪关键边则是可能会出现在某些最小生成树中但不会出现在所有最小生成树中的边
// 请注意，你可以分别以任意顺序返回关键边的下标和伪关键边的下标
// 测试链接 : https://leetcode.cn/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/
// 本题用到并查集、最小生成树、求联通图中的桥
// 并查集、最小生成树，在体系学习班
// 求桥，在每周有营养的大厂算法面试题，2022年10月第1周
// 务必打好基础，再来看这个题的解析
// 这个实现打败100%的提交者
public class Code04_FindCriticalAndPseudoCriticalEdges {

	public static int MAXN = 101;

	public static int MAXM = 201;

	// 边状态的记录
	// status[ei] = 0，代表ei这个边既不是关键边也不是伪关键边
	// status[ei] = 1，代表ei这个边是伪关键边
	// status[ei] = 2，代表ei这个边是关键边
	public static int[] status = new int[MAXM];

	// 并查集相关
	public static int[] father = new int[MAXN];
	public static int[] size = new int[MAXN];
	public static int[] help = new int[MAXN];
	public static int sets = 0;

	// 并查集初始化
	public static void buildUnoinSet(int n) {
		for (int i = 0; i < n; i++) {
			father[i] = i;
			size[i] = 1;
		}
		sets = n;
	}

	// 并查集向上找代表节点
	public static int find(int i) {
		int r = 0;
		while (i != father[i]) {
			help[r++] = i;
			i = father[i];
		}
		while (r > 0) {
			father[help[--r]] = i;
		}
		return i;
	}

	// 并查集合并集合
	public static void union(int i, int j) {
		int fi = find(i);
		int fj = find(j);
		if (fi != fj) {
			if (size[fi] >= size[fj]) {
				father[fj] = fi;
				size[fi] += size[fj];
			} else {
				father[fi] = fj;
				size[fj] += size[fi];
			}
			sets--;
		}
	}

	// 边相关
	public static int[][] edges = new int[MAXM][4];

	public static int m;

	public static void buildEdges(int[][] e) {
		for (int i = 0; i < m; i++) {
			edges[i][0] = i;
			edges[i][1] = e[i][0];
			edges[i][2] = e[i][1];
			edges[i][3] = e[i][2];
		}
		Arrays.sort(edges, 0, m, (a, b) -> a[3] - b[3]);
	}

	// 通过集合编号建图相关
	// 链式前向星建图
	// 为啥用这玩意儿建图？没啥，就是想秀
	public static int[] head = new int[MAXN];
	public static int[][] info = new int[MAXM][3];
	public static int[] next = new int[MAXM];
	public static int edgeSize;

	public static void buildGraph(int k) {
		for (int i = 0; i < k; i++) {
			head[i] = -1;
			edgeSize = 0;
		}
	}

	public static void addEdge(int a, int b, int ei) {
		next[edgeSize] = head[a];
		info[edgeSize][0] = ei;
		info[edgeSize][1] = a;
		info[edgeSize][2] = b;
		head[a] = edgeSize++;
	}

	// 哈希表相关
	public static int[] id = new int[MAXN];

	// 找桥相关
	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cnt;

	public static void findBridge(int k) {
		Arrays.fill(dfn, 0, k, 0);
		Arrays.fill(low, 0, k, 0);
		cnt = 0;
		for (int init = 0; init < k; init++) {
			if (dfn[init] == 0) {
				tarjan(init, init, -1, -1);
			}
		}
	}

	public static void tarjan(int init, int cur, int father, int ei) {
		dfn[cur] = low[cur] = ++cnt;
		for (int i = head[cur]; i != -1; i = next[i]) {
			int edgei = info[i][0];
			int nodei = info[i][2];
			if (nodei != father) {
				if (dfn[nodei] == 0) {
					tarjan(init, nodei, cur, edgei);
					low[cur] = Math.min(low[cur], low[nodei]);
				} else {
					low[cur] = Math.min(low[cur], dfn[nodei]);
				}
			}
		}
		if (low[cur] == dfn[cur] && cur != init) {
			status[ei] = 2;
		}
	}

	public static List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] e) {
		buildUnoinSet(n);
		m = e.length;
		buildEdges(e);
		Arrays.fill(status, 0, m, 0);
		List<Integer> bridge = new ArrayList<>();
		List<Integer> pseudo = new ArrayList<>();
		int teamStart = 0;
		while (sets != 1) {
			int teamEnd = teamStart;
			while (teamEnd + 1 < m && edges[teamEnd + 1][3] == edges[teamStart][3]) {
				teamEnd++;
			}
			connect(teamStart, teamEnd);
			for (int i = teamStart; i <= teamEnd; i++) {
				int ei = edges[i][0];
				if (status[ei] == 2) {
					bridge.add(ei);
				} else if (status[ei] == 1) {
					pseudo.add(ei);
				}
				union(edges[i][1], edges[i][2]);
			}
			teamStart = teamEnd + 1;
		}
		return Arrays.asList(bridge, pseudo);
	}

	public static void connect(int start, int end) {
		for (int i = start; i <= end; i++) {
			id[find(edges[i][1])] = -1;
			id[find(edges[i][2])] = -1;
		}
		int k = 0;
		for (int i = start; i <= end; i++) {
			if (id[find(edges[i][1])] == -1) {
				id[find(edges[i][1])] = k++;
			}
			if (id[find(edges[i][2])] == -1) {
				id[find(edges[i][2])] = k++;
			}
		}
		buildGraph(k);
		for (int i = start; i <= end; i++) {
			int index = edges[i][0];
			int a = id[find(edges[i][1])];
			int b = id[find(edges[i][2])];
			if (a != b) {
				status[index] = 1;
				addEdge(a, b, index);
				addEdge(b, a, index);
			}
		}
		findBridge(k);
		// 处理重复连接
		// 什么是重复连接？不是自己指向自己，那叫自环
		// 重复连接指的是:
		// 集合a到集合b有一条边，边的序号是p
		// 于是，a的邻接表里有(p,b)，b的邻接表里有(p,a)
		// 集合a到集合b又有一条边，边的序号是t
		// 于是，a的邻接表里有(t,b)，b的邻接表里有(t,a)
		// 那么p和t都是重复链接，因为单独删掉p或者t，不会影响联通性
		// 而在求桥的模版中，是默认没有重复链接的
		// 如果有重复链接就直接用模版，那么会出现重复链接被认为是桥的情况
		// 所以这里要单独判断，如果有重复链接被设置成了桥，要把它改成伪关键边的状态
		Arrays.sort(info, 0, edgeSize, (a, b) -> a[1] != b[1] ? (a[1] - b[1]) : (a[2] - b[2]));
		int right, left = 0;
		while (left < edgeSize) {
			right = left + 1;
			while (right < edgeSize && info[left][1] == info[right][1]) {
				right++;
			}
			for (int i = left + 1; i < right; i++) {
				if (info[i][2] == info[i - 1][2]) {
					status[info[i][0]] = 1;
					status[info[i - 1][0]] = 1;
				}
			}
			left = right;
		}
	}

}

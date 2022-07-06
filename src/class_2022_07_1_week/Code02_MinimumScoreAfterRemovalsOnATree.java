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

//	public static int minDiff(int[] nums, int[][] edges) {
//		int ans = Integer.MAX_VALUE;
//		// 计算量，不能超过10的8次方！
//		// 体系学习班，根据数据量猜解法
//		// 边 1000   边^平方 ： 10的6次方
//		for (int i = 0; i < edges.length; i++) {
//			for (int j = i + 1; j < edges.length; j++) {
//				// 在删掉i号边，和删掉j号边的情况下
//				// 一定会有三个部分！
//				// 请告诉我，三个部分 max(异或和) - min(异或和) 差值是多少？
//				// O(1)
//				int curDiff = ????? ;
//				ans = Math.min(ans, curDiff);	
//			}
//		}
//		return ans;
//	}

	public static int cnt;

	public static int minimumScore(int[] nums, int[][] edges) {
		int n = nums.length;
		// 先建立图
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		// 4个点，0、1、2、3
		// 0 : {}
		// 1 : {}
		// 2 : {}
		// 3 : {}
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			// a,b
			// graph.get(a).add(b);
			// graph.get(b).add(a);
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		// 无向边组成的无环图
		// 为了方便，就认为0是头
		// dfn[i] = ?
		int[] dfn = new int[n];
		// xor[i] 以i为头的整棵树，整体异或的结果是多少？
		int[] xor = new int[n];
		// size[i] 以i为头的整棵树，一共几个点？
		int[] size = new int[n];
		cnt = 1;
		dfs(nums, graph, 0, dfn, xor, size);
		int ans = Integer.MAX_VALUE, m = edges.length, cut1, cut2, pre, pos, part1, part2, part3, max, min;
		for (int i = 0; i < m; i++) {
			// i，要删掉的第一条边，i号边
			// edges[i][0]   edges[i][1]  dfn 谁大，谁就是删掉之后的树的头！cut1
			//      a            b                cut1
			// { a, b}
			//   0  1
			int a = edges[i][0];
			int b = edges[i][1];
			cut1 = dfn[a] < dfn[b] ? b : a;
			for (int j = i + 1; j < m; j++) {
				// j, 要删掉的第二条边，j号边
				// { c, d}
				//   0  1
				int c = edges[j][0];
				int d = edges[j][1];
				cut2 = dfn[c] < dfn[d] ? d : c;
				// cut1，cut2
				pre = dfn[cut1] < dfn[cut2] ? cut1 : cut2;
				pos = pre == cut1 ? cut2 : cut1;
				// 早 pre  晚 pos
				part1 = xor[pos];
				// pos为头的树，是pre为头的树的子树！
				if (dfn[pos] < dfn[pre] + size[pre]) {
					part2 = xor[pre] ^ xor[pos];
					part3 = xor[0] ^ xor[pre];
				} else { // pos为头的树，不是pre为头的树的子树！
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

	// 所有节点的值，存在nums数组里
	// 整个图结构，存在graph里
	// 当前来到的是cur号点
	// 请把cur为头，整棵树，所有节点的dfn、size、xor填好！
	// 返回！
	public static void dfs(int[] nums, 
			ArrayList<ArrayList<Integer>> graph, 
			int cur, 
			int[] dfn, int[] xor, int[] size) {
		// 当前节点了！，
		dfn[cur] = cnt++;
		// 只是来到了cur的头部！
		xor[cur] = nums[cur];
		size[cur] = 1;
		// 遍历所有的孩子！
		for (int next : graph.get(cur)) {
			// 只有dfn是0的孩子，才是cur在树中的下级！！！！
			if (dfn[next] == 0) {
				// cur某个孩子是next
				dfs(nums, graph, next, dfn, xor, size);
				// next整棵树的异或和，
				xor[cur] ^= xor[next];
				// next整棵树的size
				size[cur] += size[next];
			}
		}
	}

}

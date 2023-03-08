package class_2023_03_4_week;

import java.util.Arrays;

// 这道题很高频，引起注意
// 本身也不难，转化一下变成最小生成树的问题即可
// 测试链接 : https://leetcode.cn/problems/optimize-water-distribution-in-a-village/
public class Code03_OptimizeWaterDistributionInVillage {

	public static int MAXN = 10010;

	public static int[][] edges = new int[MAXN << 1][3];

	public static int esize;

	public static int[] father = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] help = new int[MAXN];

	public static void build(int n) {
		for (int i = 0; i <= n; i++) {
			father[i] = i;
			size[i] = 1;
		}
	}

	public static int find(int i) {
		int s = 0;
		while (i != father[i]) {
			help[s++] = i;
			i = father[i];
		}
		while (s > 0) {
			father[help[--s]] = i;
		}
		return i;
	}

	public static boolean union(int i, int j) {
		int f1 = find(i);
		int f2 = find(j);
		if (f1 != f2) {
			if (size[f1] >= size[f2]) {
				father[f2] = f1;
				size[f1] += size[f2];
			} else {
				father[f1] = f2;
				size[f2] += size[f1];
			}
			return true;
		} else {
			return false;
		}
	}

	public static int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
		esize = 0;
		for (int i = 0; i < n; i++, esize++) {
			edges[esize][0] = 0;
			edges[esize][1] = i + 1;
			edges[esize][2] = wells[i];
		}
		for (int i = 0; i < pipes.length; i++, esize++) {
			edges[esize][0] = pipes[i][0];
			edges[esize][1] = pipes[i][1];
			edges[esize][2] = pipes[i][2];
		}
		Arrays.sort(edges, 0, esize, (a, b) -> a[2] - b[2]);
		build(n);
		int ans = 0;
		for (int i = 0; i < esize; i++) {
			if (union(edges[i][0], edges[i][1])) {
				ans += edges[i][2];
			}
		}
		return ans;
	}

}

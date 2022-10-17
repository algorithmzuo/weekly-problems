package class_2022_10_4_week;

import java.util.Arrays;

// 来自拼多多
// 第一行有一个正整数n(3<=n<=100000)，代表小A拟定的路线数量
// 第二行有n个正整数，第i个代表第i条路线的起始日期
// 第三行有n个正整数，第i个代表第i条路线的终止日期
// 输入保证起始日期小于终止日期
// 日期最小是1，最大不超过1000000000
// 小A打算选三个路线进行旅游，比如 A -> B -> C
// 要求A的结束日期要小于B的开始日期，B的结束日期要小于C的开始日期
// 输出一个非负整数，代表线路的方案数量
// 例子
// 输入
// 6
// 4 1 3 2 1 2 
// 4 1 3 3 2 2
// 输出
// 6
// 解释
// [1,1] -> [2,2] -> [3,3]
// [1,1] -> [2,2] -> [4,4]
// [1,1] -> [2,3] -> [4,4]
// [1,2] -> [3,3] -> [4,4]
// [1,1] -> [3,3] -> [4,4]
// [2,2] -> [3,3] -> [4,4]
public class Code04_NumberOfIncreasingRoadsThree {

	// 暴力方法
	// 为了验证
	public static int num1(int[][] roads) {
		int n = roads.length;
		int max = 0;
		for (int i = 0; i < n; i++) {
			max = Math.max(max, roads[i][1]);
		}
		Arrays.sort(roads, (a, b) -> a[0] - b[0]);
		int[][][] dp = new int[n][4][max + 1];
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < 4; b++) {
				for (int c = 0; c <= max; c++) {
					dp[a][b][c] = -1;
				}
			}
		}
		return process1(roads, 0, 3, 0, dp);
	}

	public static int process1(int[][] roads, int i, int rest, int end, int[][][] dp) {
		if (rest == 0) {
			return 1;
		}
		if (i == roads.length) {
			return 0;
		}
		if (dp[i][rest][end] != -1) {
			return dp[i][rest][end];
		}
		int p1 = process1(roads, i + 1, rest, end, dp);
		int p2 = roads[i][0] > end ? process1(roads, i + 1, rest - 1, roads[i][1], dp) : 0;
		int ans = p1 + p2;
		dp[i][rest][end] = ans;
		return ans;
	}

	public static int num2(int[][] roads) {
		int n = roads.length;
		int[] sorted = new int[n << 1];
		for (int i = 0; i < n; i++) {
			sorted[i << 1] = roads[i][0];
			sorted[i << 1 | 1] = roads[i][1];
		}
		Arrays.sort(roads, (a, b) -> a[0] - b[0]);
		Arrays.sort(sorted);
		IndexTree it1 = new IndexTree(n << 1);
		IndexTree it2 = new IndexTree(n << 1);
		IndexTree it3 = new IndexTree(n << 1);
		for (int[] road : roads) {
			int l = rank(sorted, road[0]);
			int r = rank(sorted, road[1]);
			it1.add(r, 1);
			it2.add(r, it1.sum(l - 1));
			it3.add(r, it2.sum(l - 1));
		}
		return it3.sum(n << 1);
	}

	public static int rank(int[] sorted, int num) {
		int l = 0;
		int r = sorted.length - 1;
		int m = 0;
		int ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (sorted[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans + 1;
	}

	// 下标从1开始！
	public static class IndexTree {

		private int[] tree;

		private int N;

		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		public int sum(int index) {
			int ret = 0;
			while (index > 0) {
				ret += tree[index];
				index -= index & -index;
			}
			return ret;
		}

		public void add(int index, int d) {
			while (index <= N) {
				tree[index] += d;
				index += index & -index;
			}
		}
	}

	// 为了测试
	public static int[][] randomRoads(int n, int v) {
		int[][] roads = new int[n][2];
		for (int i = 0; i < n; i++) {
			int a = (int) (Math.random() * v) + 1;
			int b = (int) (Math.random() * v) + 1;
			int start = Math.min(a, b);
			int end = Math.max(a, b);
			if (start == end) {
				end++;
			}
			roads[i][0] = start;
			roads[i][1] = end;
		}
		return roads;
	}

	public static void main(String[] args) {
		int N = 50;
		int V = 50;
		int testTime = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[][] roads = randomRoads(n, V);
			int ans1 = num1(roads);
			int ans2 = num2(roads);
			if (ans1 != ans2) {
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 100000;
		int v = 1000000000;
		int[][] roads = randomRoads(n, v);
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : " + v);
		long start = System.currentTimeMillis();
		num2(roads);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}

package class_2022_05_2_week;

import java.util.Arrays;

// 来自字节
// 5.6笔试
// 给定N件物品，每个物品有重量(w[i])、有价值(v[i])
// 只能最多选两件商品，重量不超过bag，返回价值最大能是多少？
// N <= 10^5, w[i] <= 10^5, v[i] <= 10^5, bag <= 10^5
// 本题的关键点：什么数据范围都很大，唯独只需要最多选两件商品，这个可以利用一下
public class Code01_TwoObjectMaxValue {

	// 暴力方法
	// 为了验证而写的方法
	public static int max1(int[] w, int[] v, int bag) {
		return process1(w, v, 0, 2, bag);
	}

	public static int process1(int[] w, int[] v, int index, int restNumber, int restWeight) {
		if (restNumber < 0 || restWeight < 0) {
			return -1;
		}
		if (index == w.length) {
			return 0;
		}
		int p1 = process1(w, v, index + 1, restNumber, restWeight);
		int p2 = -1;
		int next = process1(w, v, index + 1, restNumber - 1, restWeight - w[index]);
		if (next != -1) {
			p2 = v[index] + next;
		}
		return Math.max(p1, p2);
	}

	// 正式方法
	// 时间复杂度O(N * logN)
	public static int max2(int[] w, int[] v, int bag) {
		int n = w.length;
		int[][] arr = new int[n][2];
		for (int i = 0; i < n; i++) {
			arr[i][0] = w[i];
			arr[i][1] = v[i];
		}
		// O(N * logN)
		Arrays.sort(arr, (a, b) -> (a[0] - b[0]));
		// 重量从轻到重，依次标号1、2、3、4....
		// 价值依次被构建成了RMQ结构
		// O(N * logN)
		RMQ rmq = new RMQ(arr);
		int ans = 0;
		// N * logN
		for (int i = 0, j = 1; i < n && arr[i][0] <= bag; i++, j++) {
			// 当前来到0号货物，RMQ结构1号
			// 当前来到i号货物，RMQ结构i+1号
			// 查询重量的边界，重量 边界 <= bag - 当前货物的重量
			// 货物数组中，找到 <= 边界，最右的位置i
			// RMQ，位置 i + 1
			int right = right(arr, bag - arr[i][0]) + 1;
			int rest = 0;
			// j == i + 1，当前的货物，在RMQ里的下标
			if (right == j) {
				rest = rmq.max(1, right - 1);
			} else if (right < j) {
				rest = rmq.max(1, right);
			} else { // right > j
				rest = Math.max(rmq.max(1, j - 1), rmq.max(j + 1, right));
			}
			ans = Math.max(ans, arr[i][1] + rest);
		}
		return ans;
	}

	public static int right(int[][] arr, int limit) {
		int l = 0;
		int r = arr.length - 1;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr[m][0] <= limit) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static class RMQ {
		public int[][] max;

		public RMQ(int[][] arr) {
			int n = arr.length;
			int k = power2(n);
			max = new int[n + 1][k + 1];
			for (int i = 1; i <= n; i++) {
				max[i][0] = arr[i - 1][1];
			}
			for (int j = 1; (1 << j) <= n; j++) {
				for (int i = 1; i + (1 << j) - 1 <= n; i++) {
					max[i][j] = Math.max(max[i][j - 1], max[i + (1 << (j - 1))][j - 1]);
				}
			}
		}

		public int max(int l, int r) {
			if (r < l) {
				return 0;
			}
			int k = power2(r - l + 1);
			return Math.max(max[l][k], max[r - (1 << k) + 1][k]);
		}

		private int power2(int m) {
			int ans = 0;
			while ((1 << ans) <= (m >> 1)) {
				ans++;
			}
			return ans;
		}

	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v);
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 12;
		int V = 20;
		int testTimes = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] w = randomArray(n, V);
			int[] v = randomArray(n, V);
			int bag = (int) (Math.random() * V * 3);
			int ans1 = max1(w, v, bag);
			int ans2 = max2(w, v, bag);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

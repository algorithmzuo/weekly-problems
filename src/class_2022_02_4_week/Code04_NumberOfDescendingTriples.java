package class_2022_02_4_week;

import java.util.Arrays;

// 返回一个数组中，所有降序三元组的数量
// 比如 : {5, 3, 4, 2, 1}
// 所有降序三元组为 : 
// {5, 3, 2}、{5, 3, 1}、{5, 4, 2}、{5, 4, 1}
// {5, 2, 1}、{3, 2, 1}、{4, 2, 1}
// 所以返回数量7
public class Code04_NumberOfDescendingTriples {

	// 暴力方法
	// 对数器
	public static int num1(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		return process(arr, 0, new int[3], 0);
	}

	public static int process(int[] arr, int index, int[] path, int size) {
		if (size == 3) {
			return path[0] > path[1] && path[1] > path[2] ? 1 : 0;
		}
		int ans = 0;
		if (index < arr.length) {
			ans = process(arr, index + 1, path, size);
			path[size] = arr[index];
			ans += process(arr, index + 1, path, size + 1);
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N * logN)
	// 利用index tree
	public static int num2(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int[] sorted = Arrays.copyOf(arr, n);
		Arrays.sort(sorted);
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			arr[i] = rank(sorted, arr[i]);
			max = Math.max(max, arr[i]);
		}
		IndexTree it2 = new IndexTree(max);
		IndexTree it3 = new IndexTree(max);
		int ans = 0;
		for (int i = n - 1; i >= 0; i--) {
			ans += arr[i] == 1 ? 0 : it3.sum(arr[i] - 1);
			it2.add(arr[i], 1);
			it3.add(arr[i], arr[i] == 1 ? 0 : it2.sum(arr[i] - 1));
		}
		return ans;
	}

	// 返回>=num, 最左位置
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

	// 下标从1开始
	public static class IndexTree {

		private int[] tree;
		private int N;

		// 0位置弃而不用
		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		// 1~index 累加和是多少？
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
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
		}
		return arr;
	}

	public static void main(String[] args) {
		int len = 20;
		int value = 100;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = randomArray(len, value);
			int ans1 = num1(arr);
			int ans2 = num2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

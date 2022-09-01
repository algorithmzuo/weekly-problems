package class_2022_09_3_week;

// 给你一个长度为n的数组，并询问q次
// 每次询问区间[l,r]之间是否存在小于等于k个数的和大于等于x
// 每条查询返回true或者false
// 1 <= n, q <= 10^5
// k <= 10
// 1 <= x <= 10^8
import java.util.PriorityQueue;

public class Code02_QueryTopKSum {

	public static class SegmentTree {

		private int n;
		private int k;
		private int[][] max;
		private int[][] query;

		public SegmentTree(int[] arr, int K) {
			n = arr.length;
			k = K;
			max = new int[(n + 1) << 2][k];
			query = new int[(n + 1) << 2][k];
			for (int i = 0; i < n; i++) {
				update(i, arr[i]);
			}
		}

		public int topKSum(int l, int r) {
			collect(l + 1, r + 1, 1, n, 1);
			int sum = 0;
			for (int num : query[1]) {
				sum += num;
			}
			return sum;
		}

		private void update(int i, int v) {
			update(i + 1, i + 1, v, 1, n, 1);
		}

		private void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				max[rt][0] = C;
				return;
			}
			int mid = (l + r) >> 1;
			if (L <= mid) {
				update(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			merge(max[rt], max[rt << 1], max[rt << 1 | 1]);
		}

		private void merge(int[] father, int[] left, int[] right) {
			for (int i = 0, p1 = 0, p2 = 0; i < k; i++) {
				if (left == null || p1 == k) {
					father[i] = right[p2++];
				} else if (right == null || p2 == k) {
					father[i] = left[p1++];
				} else {
					father[i] = left[p1] >= right[p2] ? left[p1++] : right[p2++];
				}
			}
		}

		private void collect(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				for (int i = 0; i < k; i++) {
					query[rt][i] = max[rt][i];
				}
			} else {
				int mid = (l + r) >> 1;
				boolean leftUpdate = false;
				boolean rightUpdate = false;
				if (L <= mid) {
					leftUpdate = true;
					collect(L, R, l, mid, rt << 1);
				}
				if (R > mid) {
					rightUpdate = true;
					collect(L, R, mid + 1, r, rt << 1 | 1);
				}
				merge(query[rt], leftUpdate ? query[rt << 1] : null, rightUpdate ? query[rt << 1 | 1] : null);
			}
		}

	}

	// 暴力实现的结构
	// 为了验证
	public static class Right {
		public int[] arr;
		public int k;

		public Right(int[] nums, int K) {
			k = K;
			arr = new int[nums.length];
			for (int i = 0; i < nums.length; i++) {
				arr[i] = nums[i];
			}
		}

		public int topKSum(int l, int r) {
			PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
			for (int i = l; i <= r; i++) {
				heap.add(arr[i]);
			}
			int sum = 0;
			for (int i = 0; i < k && !heap.isEmpty(); i++) {
				sum += heap.poll();
			}
			return sum;
		}

	}

	// 为了验证
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	// 为了验证
	public static void main(String[] args) {
		int N = 100;
		int K = 10;
		int V = 100;
		int testTimes = 5000;
		int queryTimes = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int k = (int) (Math.random() * K) + 1;
			int[] arr = randomArray(n, V);
			SegmentTree st = new SegmentTree(arr, k);
			Right right = new Right(arr, k);
			for (int j = 0; j < queryTimes; j++) {
				int a = (int) (Math.random() * n);
				int b = (int) (Math.random() * n);
				int l = Math.min(a, b);
				int r = Math.max(a, b);
				int ans1 = st.topKSum(l, r);
				int ans2 = right.topKSum(l, r);
				if (ans1 != ans2) {
					System.out.println("出错了!");
					System.out.println(ans1);
					System.out.println(ans2);
				}
			}
		}
		System.out.println("测试结束");

		System.out.println("性能测试开始");
		int n = 100000;
		int k = 10;
		int[] arr = randomArray(n, n);
		int[][] queries = new int[n][2];
		for (int i = 0; i < n; i++) {
			int a = (int) (Math.random() * n);
			int b = (int) (Math.random() * n);
			queries[i][0] = Math.min(a, b);
			queries[i][1] = Math.max(a, b);
		}
		System.out.println("数据规模 : " + n);
		System.out.println("数值规模 : " + n);
		System.out.println("查询规模 : " + n);
		System.out.println("k规模 : " + k);
		long start, end1, end2;
		start = System.currentTimeMillis();
		SegmentTree st = new SegmentTree(arr, k);
		end1 = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			st.topKSum(queries[i][0], queries[i][1]);
		}
		end2 = System.currentTimeMillis();
		System.out.println("初始化运行时间 : " + (end1 - start) + " 毫秒");
		System.out.println("执行查询运行时间 : " + (end2 - end1) + " 毫秒");
		System.out.println("总共运行时间 : " + (end2 - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}

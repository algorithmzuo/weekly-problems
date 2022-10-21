package class_2022_10_3_week;

// 来自阿里
// 给定一个长度n的数组，每次可以选择一个数x
// 让这个数组中所有的x都变成x+1，问你最少的操作次数
// 使得这个数组变成一个非降数组
// n <= 3 * 10^5
// 0 <= 数值 <= 10^9
public class Code03_AddToSorted {

	// 方法1
	// 暴力方法
	// 为了验证
	public static int minOp1(int[] arr) {
		int max = 0;
		for (int num : arr) {
			max = Math.max(max, num);
		}
		return process1(0, max, new boolean[max + 1], arr);
	}

	public static int process1(int num, int max, boolean[] op, int[] arr) {
		if (num == max + 1) {
			int cnt = 0;
			int[] help = new int[arr.length];
			for (int i = 0; i < arr.length; i++) {
				help[i] = arr[i];
			}
			for (int i = 0; i <= max; i++) {
				if (op[i]) {
					cnt++;
					add(help, i);
				}
			}
			for (int i = 1; i < arr.length; i++) {
				if (help[i - 1] > help[i]) {
					return Integer.MAX_VALUE;
				}
			}
			return cnt;
		} else {
			op[num] = true;
			int p1 = process1(num + 1, max, op, arr);
			op[num] = false;
			int p2 = process1(num + 1, max, op, arr);
			return Math.min(p1, p2);
		}
	}

	public static void add(int[] arr, int num) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == num) {
				arr[i]++;
			}
		}
	}

	// 方法2
	// 最优解的思路
	// 但依然不能通过考试数据量
	// 为了验证
	public static int minOp2(int[] arr) {
		if (arr.length < 2) {
			return 0;
		}
		int n = arr.length;
		int[] min = new int[n];
		min[n - 1] = arr[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			min[i] = Math.min(min[i + 1], arr[i]);
		}
		int max = 0;
		for (int num : arr) {
			max = Math.max(max, num);
		}
		boolean[] add = new boolean[max + 1];
		for (int i = 0; i < n - 1; i++) {
			if (arr[i] > min[i + 1]) {
				for (int j = min[i + 1]; j < arr[i]; j++) {
					add[j] = true;
				}
			}
		}
		int ans = 0;
		for (boolean is : add) {
			ans += is ? 1 : 0;
		}
		return ans;
	}

	// 方法3
	// 最优解 + 动态开点线段树
	// 时间复杂度O(N*logN)
	public static int minOp3(int[] arr) {
		int n = arr.length;
		int m = 0;
		for (int num : arr) {
			m = Math.max(m, num);
		}
		// 0 ~ 1000000000
		DynamicSegmentTree dst = new DynamicSegmentTree(m);
		for (int i = 1, max = arr[0]; i < n; i++) {
			// max左边最大值
			// 10    4
			// 4...9
			if (max > arr[i]) {
				dst.set(arr[i], max - 1);
			}
			max = Math.max(max, arr[i]);
		}
		return dst.sum();
	}

	public static class Node {
		public int sum;
		public boolean set;
		public Node left;
		public Node right;
	}

	// 动态增加
	public static class DynamicSegmentTree {
		public Node root;
		public int size;

		public DynamicSegmentTree(int max) {
			root = new Node();
			size = max;
		}

		private void pushDown(Node p, int ln, int rn) {
			if (p.left == null) {
				p.left = new Node();
			}
			if (p.right == null) {
				p.right = new Node();
			}
			if (p.set) {
				p.left.set = true;
				p.right.set = true;
				p.left.sum = ln;
				p.right.sum = rn;
				p.set = false;
			}
		}

		public void set(int s, int e) {
			update(root, 0, size, s, e);
		}

		private void update(Node c, int l, int r, int s, int e) {
			if (s <= l && r <= e) {
				c.set = true;
				c.sum = (r - l + 1);
			} else {
				int mid = (l + r) >> 1;
				pushDown(c, mid - l + 1, r - mid);
				if (s <= mid) {
					update(c.left, l, mid, s, e);
				}
				if (e > mid) {
					update(c.right, mid + 1, r, s, e);
				}
				c.sum = c.left.sum + c.right.sum;
			}
		}

		public int sum() {
			return root.sum;
		}
	}

	// 方法4
	// 最优解 + 固定数组的动态开点线段树(多次运行更省空间)
	// 时间复杂度O(N*logN)
	public static int minOp4(int[] arr) {
		int n = arr.length;
		int m = 0;
		for (int num : arr) {
			m = Math.max(m, num);
		}
		for (int i = 0; i < cnt; i++) {
			lchild[i] = -1;
			rchild[i] = -1;
		}
		cnt = 0;
		sum[cnt] = 0;
		set[cnt] = false;
		left[cnt] = 0;
		right[cnt++] = m;
		for (int i = 1, max = arr[0]; i < n; i++) {
			if (max > arr[i]) {
				set(arr[i], max - 1, 0);
			}
			max = Math.max(max, arr[i]);
		}
		return sum();
	}

	// 空间固定！
	public static final int MAXM = 8000000;
	public static int[] sum = new int[MAXM];
	public static boolean[] set = new boolean[MAXM];
	public static int[] left = new int[MAXM];
	public static int[] right = new int[MAXM];
	public static int[] lchild = new int[MAXM];
	public static int[] rchild = new int[MAXM];

	static {
		for (int i = 0; i < MAXM; i++) {
			lchild[i] = -1;
			rchild[i] = -1;
		}
	}

	public static int cnt = 0;

	public static void set(int s, int e, int i) {
		int l = left[i];
		int r = right[i];
		if (s <= l && r <= e) {
			set[i] = true;
			sum[i] = (r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(i, l, mid, mid + 1, r, mid - l + 1, r - mid);
			if (s <= mid) {
				set(s, e, lchild[i]);
			}
			if (e > mid) {
				set(s, e, rchild[i]);
			}
			sum[i] = sum[lchild[i]] + sum[rchild[i]];
		}
	}

	public static void down(int i, int l1, int r1, int l2, int r2, int ln, int rn) {
		if (lchild[i] == -1) {
			sum[cnt] = 0;
			set[cnt] = false;
			left[cnt] = l1;
			right[cnt] = r1;
			lchild[i] = cnt++;
		}
		if (rchild[i] == -1) {
			sum[cnt] = 0;
			set[cnt] = false;
			left[cnt] = l2;
			right[cnt] = r2;
			rchild[i] = cnt++;
		}
		if (set[i]) {
			set[lchild[i]] = true;
			set[rchild[i]] = true;
			sum[lchild[i]] = ln;
			sum[rchild[i]] = rn;
			set[i] = false;
		}
	}

	public static int sum() {
		return sum[0];
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int V = 12;
		int testTime = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int ans1 = minOp1(arr);
			int ans2 = minOp2(arr);
			int ans3 = minOp3(arr);
			int ans4 = minOp4(arr);
			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		N = 300000;
		V = 1000000000;
		testTime = 10;
		System.out.println("数组长度 : " + N);
		System.out.println("数值范围 : " + V);
		System.out.println("测试次数 : " + testTime);
		long runTime = 0;
		for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(N, V);
			long start = System.currentTimeMillis();
			minOp4(arr);
			long end = System.currentTimeMillis();
			runTime += end - start;
		}
		System.out.println(testTime + "次测试总运行时间 : " + runTime + " 毫秒");
		System.out.println("性能测试结束");
	}

}

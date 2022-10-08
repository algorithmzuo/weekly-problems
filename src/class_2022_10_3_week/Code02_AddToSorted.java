package class_2022_10_3_week;

// 来自阿里
// 给定一个长度n的数组，每次可以选择一个数x
// 让这个数组中所有的x都变成x+1，问你最少的操作次数
// 使得这个数组变成一个非降数组
// n <= 3 * 10^5
// 数值 <= 10^9
public class Code02_AddToSorted {

	// 暴力方法
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

	// 最优解的思路
	// 但依然不能通过考试数据量
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

	// 最优解 + 线段树
	// 时间复杂度O(N*logN)
	public static int minOp3(int[] arr) {
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
		DynamicSegmentTree dst = new DynamicSegmentTree(max);
		for (int i = 0; i < n - 1; i++) {
			if (arr[i] > min[i + 1]) {
				dst.set(min[i + 1], arr[i] - 1);
			}
		}
		return dst.sum();
	}

	public static class Node {
		public int sum;
		public boolean set;
		public Node left;
		public Node right;
	}

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
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 300000;
		int v = 1000000000;
		int[] arr = randomArray(n, v);
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : " + v);
		long start = System.currentTimeMillis();
		minOp3(arr);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}

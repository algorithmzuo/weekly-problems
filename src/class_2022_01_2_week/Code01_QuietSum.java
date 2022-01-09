package class_2022_01_2_week;

// 给定一个非负数组arr，学生依次坐在0~N-1位置，每个值表示学生的安静值
// 如果在i位置安置插班生，那么i位置的安静值变成0，同时任何同学都会被影响到而减少安静值
// 同学安静值减少的量: N - 这个同学到插班生的距离
// 但是减到0以下的话，当做0处理
// 返回一个和arr等长的ans数组，ans[i]表示如果把插班生安排在i位置，所有学生的安静值的和
// 比如 : arr = {3,4,2,1,5}，应该返回{4,3,2,3,4}
// 比如 : arr = {10,1,10,10,10}，应该返回{24,27,20,20,22}
// arr长度 <= 10^5
// arr中值 <= 2 * 10^5
public class Code01_QuietSum {

	// 为了测试
	// 彻底暴力的方法
	public static long[] quiet1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new long[0];
		}
		int n = arr.length;
		long[] ans = new long[n];
		for (int i = 0; i < n; i++) {
			long sum = 0;
			for (int j = 0; j < i; j++) {
				sum += Math.max(0, arr[j] - (n - Math.abs(i - j)));
			}
			for (int j = i + 1; j < n; j++) {
				sum += Math.max(0, arr[j] - (n - Math.abs(i - j)));
			}
			ans[i] = sum;
		}
		return ans;
	}

	// 时间复杂度O(N * logN)的方法
	public static long[] quiet2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new long[0];
		}
		int n = arr.length;
		SBTree sbt = new SBTree();
		long[] ans = new long[n];
		sbt.add(arr[n - 1] - 1);
		for (int i = n - 2; i >= 0; i--) {
			long moreCount = sbt.moreCount(i);
			long moreSum = sbt.moreSum(i);
			ans[i] = moreSum - (moreCount * i);
			sbt.add(arr[i] + i - n);
		}
		sbt = new SBTree();
		sbt.add(arr[0] - 1);
		for (int i = 1, j = n - 2; i < n; i++, j--) {
			long moreCount = sbt.moreCount(j);
			long moreSum = sbt.moreSum(j);
			ans[i] += moreSum - (moreCount * j);
			sbt.add(arr[i] + j - n);
		}
		return ans;
	}

	public static class SBTNode {
		public int value;
		// 和业务无关
		// 不同key的size，纯粹为了树的平衡调整
		public int size;
		// 和业务有关
		// 加过几个数字
		public int all;
		// 和业务有关
		// 子树的累加和
		public long sum;
		public SBTNode l;
		public SBTNode r;

		public SBTNode(int v) {
			value = v;
			size = 1;
			all = 1;
			sum = v;
		}
	}

	public static class SBTree {

		private SBTNode root;

		private SBTNode rightRotate(SBTNode cur) {
			int curCount = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
			long curSum = cur.sum - (cur.l != null ? cur.l.sum : 0) - (cur.r != null ? cur.r.sum : 0);
			SBTNode leftNode = cur.l;
			cur.l = leftNode.r;
			leftNode.r = cur;
			leftNode.size = cur.size;
			leftNode.all = cur.all;
			leftNode.sum = cur.sum;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + curCount;
			cur.sum = (cur.l != null ? cur.l.sum : 0) + (cur.r != null ? cur.r.sum : 0) + curSum;
			return leftNode;
		}

		private SBTNode leftRotate(SBTNode cur) {
			int curCount = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
			long curSum = cur.sum - (cur.l != null ? cur.l.sum : 0) - (cur.r != null ? cur.r.sum : 0);
			SBTNode rightNode = cur.r;
			cur.r = rightNode.l;
			rightNode.l = cur;
			rightNode.size = cur.size;
			rightNode.all = cur.all;
			rightNode.sum = cur.sum;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + curCount;
			cur.sum = (cur.l != null ? cur.l.sum : 0) + (cur.r != null ? cur.r.sum : 0) + curSum;
			return rightNode;
		}

		private SBTNode maintain(SBTNode cur) {
			if (cur == null) {
				return null;
			}
			long leftSize = cur.l != null ? cur.l.size : 0;
			long leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
			long leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
			long rightSize = cur.r != null ? cur.r.size : 0;
			long rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
			long rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
			if (leftLeftSize > rightSize) {
				cur = rightRotate(cur);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			} else if (leftRightSize > rightSize) {
				cur.l = leftRotate(cur.l);
				cur = rightRotate(cur);
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			} else if (rightRightSize > leftSize) {
				cur = leftRotate(cur);
				cur.l = maintain(cur.l);
				cur = maintain(cur);
			} else if (rightLeftSize > leftSize) {
				cur.r = rightRotate(cur.r);
				cur = leftRotate(cur);
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			return cur;
		}

		public void add(int v) {
			root = add(root, v, contains(root, v));
		}

		private boolean contains(SBTNode cur, int v) {
			if (cur == null) {
				return false;
			} else if (cur.value == v) {
				return true;
			} else if (cur.value > v) {
				return contains(cur.l, v);
			} else {
				return contains(cur.r, v);
			}
		}

		private SBTNode add(SBTNode cur, int v, boolean contains) {
			if (cur == null) {
				return new SBTNode(v);
			} else {
				if (!contains) {
					cur.size++;
				}
				cur.all++;
				cur.sum += v;
				if (cur.value == v) {
					return cur;
				} else if (cur.value > v) {
					cur.l = add(cur.l, v, contains);
				} else {
					cur.r = add(cur.r, v, contains);
				}
				return maintain(cur);
			}
		}

		public long moreCount(int num) {
			return moreCount(root, num);
		}

		private long moreCount(SBTNode cur, int num) {
			if (cur == null) {
				return 0;
			}
			if (cur.value <= num) {
				return moreCount(cur.r, num);
			} else { // cur.value > num
				return cur.all - (cur.l != null ? cur.l.all : 0) + moreCount(cur.l, num);
			}
		}

		public long moreSum(int num) {
			return moreSum(root, num);
		}

		private long moreSum(SBTNode cur, int num) {
			if (cur == null) {
				return 0;
			}
			if (cur.value <= num) {
				return moreSum(cur.r, num);
			} else { // cur.value > num
				return cur.sum - (cur.l != null ? cur.l.sum : 0) + moreSum(cur.l, num);
			}
		}

	}

	// 为了测试
	public static int[] randomArray(int len, int v) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * (v + 1));
		}
		return ans;
	}

	public static boolean sameArray(long[] arr1, long[] arr2) {
		if (arr1.length != arr2.length) {
			return false;
		}
		int n = arr1.length;
		for (int i = 0; i < n; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 1000;
		int V = 5000;
		int testTime = 10000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(size, V);
			long[] ans1 = quiet1(arr);
			long[] ans2 = quiet2(arr);
			if (!sameArray(ans1, ans2)) {
				System.out.println("出错了！");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				for (long num : ans1) {
					System.out.print(num + " ");
				}
				System.out.println();
				for (long num : ans2) {
					System.out.print(num + " ");
				}
				System.out.println();
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		N = 200000;
		V = 500000;
		long start;
		long end;
		int[] arr = randomArray(N, V);
		start = System.currentTimeMillis();
		quiet2(arr);
		end = System.currentTimeMillis();
		System.out.println("测试数组长度 : " + N);
		System.out.println("测试数组数值最大值 : " + V);
		System.out.println("运行时间（毫秒） : " + (end - start));
		System.out.println("性能测试结束");
	}

}

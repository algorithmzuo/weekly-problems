package class_2022_11_4_week;

import java.util.Arrays;

// 来自第四届全国大学生算法设计与编程挑战赛（秋季赛）
// 给定两个长度为N的数组，a[]和b[]
// 也就是对于每个位置i来说，有a[i]和b[i]两个属性
// 现在想为了i，选一个最好的j位置，搭配能得到最小的如下值: 
// (a[i] + a[j]) ^ 2 + b[i] + b[j]
// 我们把这个最小的值，定义为i的最in值
// 比如 : 
// a = {   2,  3,  6,  5,   1 }
// b = { 100, 70, 20, 40, 150 }
//         0   1   2   3    4
// 0位置和2位置搭配，可以得到最in值 : 184
// 1位置和2位置搭配，可以得到最in值 : 171
// 2位置和1位置搭配，可以得到最in值 : 171
// 3位置和1位置搭配，可以得到最in值 : 174
// 4位置和2位置搭配，可以得到最in值 : 219
// 注意 : i位置可以和i位置(自己)搭配，并不是说i和j一定要是不同的位置
// 返回每个位置i的最in值
// 比如上面的例子，最后返回[184, 171, 171, 174, 219]
// 1 <= N <= 10^5
// 1 <= a[i]、b[i] <= 10^9
public class Code05_PickBestToGetInValue {

	// 暴力方法
	// 时间复杂度O(N^2)
	// 为了测试
	public static long[] inValues1(int[] a, int[] b) {
		int n = a.length;
		long[] ans = new long[n];
		for (int i = 0; i < n; i++) {
			long curAns = Long.MAX_VALUE;
			for (int j = 0; j < n; j++) {
				long cur = (long) (a[i] + a[j]) * (a[i] + a[j]) + b[i] + b[j];
				curAns = Math.min(curAns, cur);
			}
			ans[i] = curAns;
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N*logN)
	// (a[i] + a[j]) ^ 2 + b[i] + b[j]
	// a[i]^2 + b[i] + 2a[i]a[j] + a[j]^2 + b[j]
	// a[i] * ( a[i] + b[i]/a[i] + 2a[j] + (a[j]^2 + b[j])/a[i])
	// 令S(j) = 2a[j]
	// 令T(j) = a[j]^2 + b[j]
	// 那么对于i来说，就是选择j，让下面得到最小值
	// a[i] * ( a[i] + b[i]/a[i] + S(j) + T(j)/a[i])
	// 选择最小的S(j) + T(j)/a[i]，就得到了答案
	// 剩下的一切都是围绕这个
	public static long[] inValues2(int[] a, int[] b) {
		int n = a.length;
		long[][] ST = new long[n][2];
		for (int i = 0; i < n; i++) {
			ST[i][0] = 2L * a[i];
			ST[i][1] = (long) a[i] * a[i] + b[i];
		}
		// 只需要根据S值从大到小排序即可
		// 下面的比较器定义稍复杂，因为java里排序会严格检查传递性
		// 所以策略参考了S和T，其实只需要根据S值从大到小排序即可
		Arrays.sort(ST, (x, y) -> x[0] != y[0] ? (x[0] > y[0] ? -1 : 1) : (x[1] <= y[1] ? -1 : 1));
		int[] deque = new int[n];
		int l = 0;
		int r = 0;
		for (int i = 0; i < n; i++) {
			long curS = ST[i][0];
			long curT = ST[i][1];
			while (l != r && tail(ST, deque, l, r) >= better(ST[deque[r - 1]][0], ST[deque[r - 1]][1], curS, curT)) {
				r--;
			}
			deque[r++] = i;
		}
		int[][] arr = new int[n][3];
		for (int i = 0; i < n; i++) {
			arr[i][0] = i;
			arr[i][1] = a[i];
			arr[i][2] = b[i];
		}
		// 只需要根据a值从小到大排序即可
		// 下面的比较器定义稍复杂，因为java里排序会严格检查传递性
		// 所以策略参考了a和位置i，其实只需要根据a值从小到大排序即可
		Arrays.sort(arr, (x, y) -> x[1] != y[1] ? (x[1] < y[1] ? -1 : 1) : (x[0] - y[0]));
		long[] ans = new long[n];
		for (int k = 0; k < n; k++) {
			int i = arr[k][0];
			int ai = arr[k][1];
			int bi = arr[k][2];
			while (head(ST, deque, l, r) <= ai) {
				l++;
			}
			long Sj = ST[deque[l]][0];
			long Tj = ST[deque[l]][1];
			// a[i] * ( a[i] + b[i]/a[i] + S(j) + T(j)/a[i])
			long curAns = Sj * ai + Tj + (long) ai * ai + bi;
			ans[i] = curAns;
		}
		return ans;
	}

	public static int tail(long[][] ST, int[] deque, int l, int r) {
		if (r - l == 1) {
			return 1;
		}
		return better(ST[deque[r - 2]][0], ST[deque[r - 2]][1], ST[deque[r - 1]][0], ST[deque[r - 1]][1]);
	}

	public static int head(long[][] ST, int[] deque, int l, int r) {
		if (r - l == 1) {
			return Integer.MAX_VALUE;
		}
		return better(ST[deque[l]][0], ST[deque[l]][1], ST[deque[l + 1]][0], ST[deque[l + 1]][1]);
	}

	// 入参时候s1>=s2，这是一定的
	// 返回当ai大到什么值的时候，(s2+t2/ai) <= (s1+t1/ai)
	// 即 : ai大到什么值的时候，后者更好
	public static int better(long s1, long t1, long s2, long t2) {
		if (s1 == s2) {
			return t1 <= t2 ? Integer.MAX_VALUE : 1;
		}
		// s1 > s2
		if (t1 >= t2) {
			return 1;
		}
		// s1 > s2
		// t1 < t2
		long td = t2 - t1;
		long sd = s1 - s2;
		return (int) ((td + sd - 1) / sd);
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	// 为了测试
	public static boolean isSameArray(long[] arr1, long[] arr2) {
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int A = 100;
		int B = 50000;
		int testTime = 50000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] a = randomArray(n, A);
			int[] b = randomArray(n, B);
			long[] ans1 = inValues1(a, b);
			long[] ans2 = inValues2(a, b);
			if (!isSameArray(ans1, ans2)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 100000;
		int v = 1000000000;
		int[] a = randomArray(n, v);
		int[] b = randomArray(n, v);
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : " + v);
		long start = System.currentTimeMillis();
		inValues2(a, b);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}

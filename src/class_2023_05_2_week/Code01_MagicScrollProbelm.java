package class_2023_05_2_week;

// 来自微众银行
// 两个魔法卷轴问题
// 给定一个数组arr，其中可能有正、负、0，
// 一个魔法卷轴可以把arr中连续的一段全变成0，你希望数组整体的累加和尽可能大
// 你有两个魔法卷轴，请返回数组尽可能大的累加和
// 1 <= arr长度 <= 100000
// -100000 <= arr里的值 <= 100000
public class Code01_MagicScrollProbelm {

	// 暴力方法
	// 为了测试
	public static int maxSum1(int[] arr) {
		int p1 = 0;
		for (int num : arr) {
			p1 += num;
		}
		int n = arr.length;
		int p2 = mustOneScroll(arr, 0, n - 1);
		int p3 = Integer.MIN_VALUE;
		for (int i = 1; i < n; i++) {
			p3 = Math.max(p3, mustOneScroll(arr, 0, i - 1) + mustOneScroll(arr, i, n - 1));
		}
		return Math.max(p1, Math.max(p2, p3));
	}

	// 为了测试
	public static int mustOneScroll(int[] arr, int L, int R) {
		int ans = Integer.MIN_VALUE;
		for (int a = L; a <= R; a++) {
			for (int b = a; b <= R; b++) {
				int curAns = 0;
				for (int i = L; i < a; i++) {
					curAns += arr[i];
				}
				for (int i = b + 1; i <= R; i++) {
					curAns += arr[i];
				}
				ans = Math.max(ans, curAns);
			}
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int maxSum2(int[] arr) {
		if (arr.length == 0) {
			return 0;
		}
		int p1 = 0;
		for (int num : arr) {
			p1 += num;
		}
		int n = arr.length;
		int[] left = new int[n];
		int sum = arr[0];
		int maxSum = Math.max(0, sum);
		for (int i = 1; i < n; i++) {
			left[i] = Math.max(left[i - 1] + arr[i], maxSum);
			sum += arr[i];
			maxSum = Math.max(maxSum, sum);
		}
		int p2 = left[n - 1];
		int[] right = new int[n];
		sum = arr[n - 1];
		maxSum = Math.max(0, sum);
		for (int i = n - 2; i >= 0; i--) {
			right[i] = Math.max(arr[i] + right[i + 1], maxSum);
			sum += arr[i];
			maxSum = Math.max(maxSum, sum);
		}
		int p3 = Integer.MIN_VALUE;
		for (int i = 1; i < n; i++) {
			p3 = Math.max(p3, left[i - 1] + right[i]);
		}
		return Math.max(p1, Math.max(p2, p3));
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * (v * 2 + 1)) - v;
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 50;
		int V = 100;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N);
			int[] arr = randomArray(n, V);
			int ans1 = maxSum1(arr);
			int ans2 = maxSum2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

package class_2023_06_2_week;

// 来自华为od
// 给定一个数组arr，长度为n，表示n个格子的分数，并且这些格子首尾相连
// 孩子不能选相邻的格子，不能回头选，不能选超过一圈
// 但是孩子可以决定从任何位置开始选，也可以什么都不选
// 返回孩子能获得的最大分值
// 1 <= n <= 10^6
// 0 <= arr[i] <= 10^6
public class Code02_JumpGameOnLoop {

	// 暴力方法
	// 为了测试
	public static int max1(int[] arr) {
		if (arr.length == 1) {
			return arr[0];
		}
		return process(arr, 0, new boolean[arr.length]);
	}

	public static int process(int[] arr, int i, boolean[] path) {
		if (i == arr.length) {
			if (path[0] && path[arr.length - 1]) {
				return Integer.MIN_VALUE;
			}
			for (int j = 1; j < arr.length; j++) {
				if (path[j - 1] && path[j]) {
					return Integer.MIN_VALUE;
				}
			}
			int ans = 0;
			for (int j = 0; j < arr.length; j++) {
				if (path[j]) {
					ans += arr[j];
				}
			}
			return ans;
		} else {
			path[i] = true;
			int ans1 = process(arr, i + 1, path);
			path[i] = false;
			int ans2 = process(arr, i + 1, path);
			return Math.max(ans1, ans2);
		}
	}

	// 时间复杂度O(N)，记忆化搜索
	public static int max2(int[] arr) {
		if (arr.length == 1) {
			return arr[0];
		}
		int n = arr.length;
		int[][] dp = new int[n][4];
		for (int i = 0; i < n; i++) {
			dp[i][0] = Integer.MIN_VALUE;
			dp[i][1] = Integer.MIN_VALUE;
			dp[i][2] = Integer.MIN_VALUE;
			dp[i][3] = Integer.MIN_VALUE;
		}
		int ans = arr[0] + process2(arr, 1, 1, 1, dp);
		ans = Math.max(ans, process2(arr, 1, 0, 0, dp));
		return ans;
	}

	public static int process2(int[] arr, int i, int pre, int end, int[][] dp) {
		if (i == arr.length - 1) {
			return (pre == 1 || end == 1) ? 0 : Math.max(0, arr[i]);
		} else {
			if (dp[i][(pre << 1) | end] != Integer.MIN_VALUE) {
				return dp[i][(pre << 1) | end];
			}
			int p1 = process2(arr, i + 1, 0, end, dp);
			int p2 = Integer.MIN_VALUE;
			if (pre != 1) {
				p2 = arr[i] + process2(arr, i + 1, 1, end, dp);
			}
			int ans = Math.max(p1, p2);
			dp[i][(pre << 1) | end] = ans;
			return ans;
		}
	}

	// 正式方法
	// 严格位置依赖的动态规划 + 空间压缩
	// 时间复杂度O(N)
	public static int max3(int[] arr) {
		if (arr.length == 1) {
			return arr[0];
		}
		int n = arr.length;
		int[] next = new int[4];
		int[] cur = new int[4];
		next[0] = Math.max(0, arr[n - 1]);
		for (int i = n - 2; i >= 1; i--) {
			for (int pre = 0; pre < 2; pre++) {
				for (int end = 0; end < 2; end++) {
					cur[(pre << 1) | end] = next[end];
					if (pre != 1) {
						cur[(pre << 1) | end] = Math.max(cur[(pre << 1) | end], arr[i] + next[2 + end]);
					}
				}
			}
			next[0] = cur[0];
			next[1] = cur[1];
			next[2] = cur[2];
			next[3] = cur[3];
		}
		return Math.max(arr[0] + next[3], next[0]);
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
		int N = 16;
		int V = 100;
		int testTimes = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int ans1 = max1(arr);
			int ans2 = max2(arr);
			int ans3 = max3(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

}

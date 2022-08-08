package class_2022_08_2_week;

// 来自猿辅导
// 2022.8.7笔试第三道
// 给定一个数组arr，和一个正数k
// 如果arr[i] == 0，表示i这里既可以是左括号也可以是右括号，而且可以涂上1~k每一种颜色
// 如果arr[i] != 0，表示i这里已经确定是左括号，颜色就是arr[i]的值
// 那么arr整体就可以变成某个括号字符串，并且每个括号字符都带有颜色
// 返回在括号字符串合法的前提下，有多少种不同的染色方案
// 不管是排列、还是颜色，括号字符串任何一点不一样，就算不同的染色方案
// 最后的结果%10001，为了方便，我们不处理mod，就管核心思路
// 2 <= arr长度 <= 5000
// 1 <= k <= 1000
// 0 <= arr[i] <= k
public class Code01_ParenthesesDye {

	// 暴力方法
	// 为了验证
	public static int ways1(int[] arr, int k) {
		if ((arr.length & 1) != 0) {
			return 0;
		}
		return process1(arr, 0, k);
	}

	public static int process1(int[] arr, int index, int k) {
		if (index == arr.length) {
			int n = arr.length;
			int[] stack = new int[n];
			int size = 0;
			for (int i = 0; i < n; i++) {
				if (arr[i] > 0) {
					stack[size++] = arr[i];
				} else {
					if (size == 0 || stack[--size] != -arr[i]) {
						return 0;
					}
				}
			}
			return size == 0 ? 1 : 0;
		} else if (arr[index] != 0) {
			return process1(arr, index + 1, k);
		} else {
			int ans = 0;
			for (int color = 1; color <= k; color++) {
				arr[index] = color;
				ans += process1(arr, index + 1, k);
				arr[index] = -color;
				ans += process1(arr, index + 1, k);
				arr[index] = 0;
			}
			return ans;
		}
	}

	// 正式方法
	// 时间复杂度O(N^2), N是数组长度
	// 首先求合法的括号组合数量（忽略染色这件事），
	// 就是combines方法，看注释
	// 当括号数量求出来，再看染色能有几种
	// 比如忽略颜色，某个合法的括号结合 长度为n，
	// 如果已经有b个涂上了颜色，而且是左括号
	// 那么，因为该结合是合法的，
	// 所以这b个涂上了颜色的左括号，和哪些右括号结合，
	// 其实是确定的，这些右括号颜色也是确定的
	// 那么还剩n-(b*2)个字符
	// 这n-(b*2)个字符，就是(n-(b*2))/2对括号
	// 每对括号都可以自由发挥，所以，任何一个合法的组合，涂色方案为k^((n-(b*2))/2)
	// 最终答案 : 合法括号组合数量 * k^((n-(b*2))/2)
	public static int ways2(int[] arr, int k) {
		int n = arr.length;
		if ((n & 1) != 0) {
			return 0;
		}
		int a = combines(arr);
		int b = 0;
		for (int num : arr) {
			if (num != 0) {
				b++;
			}
		}
		return a * ((int) Math.pow((double) k, (double) ((n - (b << 1)) >> 1)));
	}

	// 忽略染色这件事，求合法的括号结合数量
	public static int combines(int[] arr) {
		int n = arr.length;
		int[][] dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dp[i][j] = -1;
			}
		}
		return f(arr, 0, 0, dp);
	}

	// 在arr[i...]范围上做决定
	// 之前在arr[0...i-1]上的决定，使得左括号比右括号多了j个
	// 最终合法的括号结合是多少
	public static int f(int[] arr, int i, int j, int[][] dp) {
		int n = arr.length;
		if (i == n) {
			return j == 0 ? 1 : 0;
		}
		if (j < 0) {
			return 0;
		}
		if (n - i < j) {
			return 0;
		}
		// 如果缓存命中，直接返回答案
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		int ans = 0;
		if (arr[i] > 0) {
			ans = f(arr, i + 1, j + 1, dp);
		} else {
			ans = f(arr, i + 1, j + 1, dp) + f(arr, i + 1, j - 1, dp);
		}
		dp[i][j] = ans;
		return ans;
	}

	// 生成长度随机的数组
	// 值在0~K之间，但是50%的概率值是0，50%的概率值是1~k中的一个
	public static int[] randomArray(int n, int k) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = Math.random() < 0.5 ? 0 : ((int) (Math.random() * k) + 1);
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 5;
		int K = 4;
		int testTimes = 1000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = ((int) (Math.random() * N) + 1) << 1;
			int k = (int) (Math.random() * K) + 1;
			int[] arr = randomArray(n, k);
			int ans1 = ways1(arr, k);
			int ans2 = ways2(arr, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 5000;
		int k = 1000;
		System.out.println("数组长度 : " + n);
		System.out.println("颜色数量 : " + k);
		int[] arr = randomArray(n, k);
		long start = System.currentTimeMillis();
		ways2(arr, k);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + "毫秒");
		System.out.println("性能测试结束");

		System.out.println("注意 : 这个解答没有取mod，只展示了核心思路");
	}

}

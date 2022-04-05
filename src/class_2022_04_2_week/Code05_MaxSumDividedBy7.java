package class_2022_04_2_week;

// 来自美团
// 3.36笔试
// 给定一个非负数组，任意选择数字，使累加和最大且为7的倍数
public class Code05_MaxSumDividedBy7 {

	public static int maxSum1(int[] arr) {
		return process1(arr, 0, 0);
	}

	public static int process1(int[] arr, int index, int pre) {
		if (index == arr.length) {
			return pre % 7 == 0 ? pre : 0;
		}
		int p1 = process1(arr, index + 1, pre);
		int p2 = process1(arr, index + 1, pre + arr[index]);
		return Math.max(p1, p2);
	}

	public static int maxSum2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int n = arr.length;
		int[][] dp = new int[n][7];
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < 7; j++) {
				dp[i][j] = -1;
			}
		}
		dp[0][arr[0] % 7] = arr[0];
		for (int i = 1; i < n; i++) {
			int curMod = arr[i] % 7;
			for (int j = 0; j < 7; j++) {
				dp[i][j] = dp[i - 1][j];
				int findMod = (7 - curMod + j) % 7;
				if (dp[i - 1][findMod] != -1) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][findMod] + arr[i]);
				}
			}
		}
		return dp[n - 1][0] == -1 ? 0 : dp[n - 1][0];
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 12;
		int value = 30;
		int testTime = 2000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, value);
			int ans1 = maxSum1(arr);
			int ans2 = maxSum2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

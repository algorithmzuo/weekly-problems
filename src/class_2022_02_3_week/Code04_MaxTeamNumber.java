package class_2022_02_3_week;

import java.util.Arrays;

// 来自微软
// 给定一个数组arr，一个正数num，一个正数k
// 可以把arr中的某些数字拿出来组成一组，要求该组中的最大值减去最小值<=num
// 且该组数字的个数一定要正好等于k
// 每个数字只能选择进某一组，不能进多个组
// 返回arr中最多有多少组
public class Code04_MaxTeamNumber {

	// 对数器方法
	public static int maxTeams1(int[] arr, int num, int k) {
		Arrays.sort(arr);
		return process1(arr, 0, new int[arr.length], 0, num, k);
	}

	public static int process1(int[] arr, int index, int[] path, int size, int num, int k) {
		if (index == arr.length) {
			if (size % k != 0) {
				return 0;
			} else {
				for (int start = 0; start < size; start += k) {
					if (path[start + k - 1] - path[start] > num) {
						return 0;
					}
				}
				return size / k;
			}
		} else {
			int p1 = process1(arr, index + 1, path, size, num, k);
			path[size] = arr[index];
			int p2 = process1(arr, index + 1, path, size + 1, num, k);
			return Math.max(p1, p2);
		}
	}

	// 正式方法
	// 时间复杂度O(N * logN)
	public static int maxTeams2(int[] arr, int num, int k) {
		int n = arr.length;
		if (k > n) {
			return 0;
		}
		Arrays.sort(arr);
		int[] dp = new int[n];
		dp[k - 1] = arr[k - 1] - arr[0] <= num ? 1 : 0;
		for (int i = k; i < n; i++) {
			int p1 = dp[i - 1];
			int p2 = (arr[i] - arr[i - k + 1] <= num ? 1 : 0) + dp[i - k];
			dp[i] = Math.max(p1, p2);
		}
		return dp[n - 1];
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 18;
		int v = 50;
		int testTimes = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[] arr = randomArray(len, v);
			int num = (int) (Math.random() * v) + 1;
			int k = (int) (Math.random() * len) + 1;
			int ans1 = maxTeams1(arr, num, k);
			int ans2 = maxTeams2(arr, num, k);
			if (ans1 != ans2) {
				for (int number : arr) {
					System.out.print(number + " ");
				}
				System.out.println();
				System.out.println("num : " + num);
				System.out.println("k : " + k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

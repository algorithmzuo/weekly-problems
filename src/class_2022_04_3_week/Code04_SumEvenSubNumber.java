package class_2022_04_3_week;

// 来自学员问题
// 总长度为n的数组中，所有长度为k的子序列里，有多少子序列的和为偶数
public class Code04_SumEvenSubNumber {

	public static int number1(int[] arr, int k) {
		if (arr == null || arr.length == 0 || k < 1 || k > arr.length) {
			return 0;
		}
		return process1(arr, 0, k, 0);
	}

	public static int process1(int[] arr, int index, int rest, int sum) {
		if (index == arr.length) {
			return rest == 0 && (sum & 1) == 0 ? 1 : 0;
		} else {
			return process1(arr, index + 1, rest, sum) + process1(arr, index + 1, rest - 1, sum + arr[index]);
		}
	}

	public static int number2(int[] arr, int k) {
		if (arr == null || arr.length == 0 || k < 1 || k > arr.length) {
			return 0;
		}
		int n = arr.length;
		// even[i][j] : 在前i个数的范围上(0...i-1)，一定选j个数，加起来是偶数的子序列个数
		// odd[i][j] : 在前i个数的范围上(0...i-1)，一定选j个数，加起来是奇数的子序列个数
		int[][] even = new int[n + 1][k + 1];
		int[][] odd = new int[n + 1][k + 1];
		for (int i = 0; i <= n; i++) {
			// even[0][0] = 1;
			// even[1][0] = 1;
			// even[2][0] = 1;
			// even[n][0] = 1;
			even[i][0] = 1;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= Math.min(i, k); j++) {
				even[i][j] = even[i - 1][j];
				odd[i][j] = odd[i - 1][j];
				even[i][j] += (arr[i - 1] & 1) == 0 ? even[i - 1][j - 1] : odd[i - 1][j - 1];
				odd[i][j] += (arr[i - 1] & 1) == 0 ? odd[i - 1][j - 1] : even[i - 1][j - 1];
			}
		}
		return even[n][k];
	}

	// 补充一个更数学的方法
	// 统计arr中的偶数个数、奇数个数
	// k个数加起来是偶数的方案 :
	// 1) 奇数选0个，偶数选k个
	// 2) 奇数选2个，偶数选k-2个
	// 3) 奇数选4个，偶数选k-4个
	// ...
	public static int number3(int[] arr, int k) {
		if (arr == null || arr.length == 0 || k < 1 || k > arr.length) {
			return 0;
		}
		int even = 0;
		int odd = 0;
		for (int num : arr) {
			if ((num & 1) == 0) {
				even++;
			} else {
				odd++;
			}
		}
		int ans = 0;
		for (int pick = 0, rest = k; pick <= k; pick += 2, rest -= 2) {
			ans += c(pick, odd) * c(rest, even);
		}
		return ans;
	}

	public static long c(long m, long n) {
		if (m > n) {
			return 0;
		}
		if (m == 0 && m == n) {
			return 1;
		}
		long up = 1;
		long down = 1;
		for (long i = m + 1, j = 1; i <= n; i++, j++) {
			up *= i;
			down *= j;
			long gcd = gcd(up, down);
			up /= gcd;
			down /= gcd;
		}
		return up;
	}

	public static long gcd(long m, long n) {
		return n == 0 ? m : gcd(n, m % n);
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
		int N = 20;
		int V = 30;
		int testTimes = 3000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int k = (int) (Math.random() * n) + 1;
			int ans1 = number1(arr, k);
			int ans2 = number2(arr, k);
			int ans3 = number3(arr, k);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
			}
		}
		System.out.println("测试结束");
	}

}

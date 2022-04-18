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
		int[][] even = new int[n + 1][k + 1];
		int[][] odd = new int[n + 1][k + 1];
		for (int i = 0; i <= n; i++) {
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
			if (ans1 != ans2) {
				System.out.println("出错了");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");
	}

}

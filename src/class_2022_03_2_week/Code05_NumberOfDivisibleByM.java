package class_2022_03_2_week;

// 来自微软
// 给定一个数组arr，给定一个正数M
// 如果arr[i] + arr[j]可以被M整除，并且i < j，那么(i,j)叫做一个M整除对
// 返回arr中M整除对的总数量
public class Code05_NumberOfDivisibleByM {

	public static int num1(int[] arr, int m) {
		int n = arr.length;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (Math.abs(arr[i] + arr[j]) % m == 0) {
					ans++;
				}
			}
		}
		return ans;
	}

	public static int num2(int[] arr, int m) {
		int n = arr.length;
		int[] cnts = new int[m];
		int ans = 0;
		for (int i = n - 1; i >= 0; i--) {
			int cur = (arr[i] % m + m) % m;
			ans += cnts[(m - cur) % m];
			cnts[cur]++;
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) - (int) (Math.random() * v);
		}
		return arr;
	}

	public static void main(String[] args) {
		int len = 50;
		int value = 50;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, value);
			int m = (int) (Math.random() * value) + 1;
			int ans1 = num1(arr, m);
			int ans2 = num2(arr, m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("m = " + m);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

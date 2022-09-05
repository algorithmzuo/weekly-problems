package class_2022_09_1_week;

// 来自学员问题
// 有一个数组包含0、1、2三种值，
// 有n次修改机会，第一种将所有连通的1变为0，修改次数-1
// 第二种将所有连通的2变为1或0，修改次数-2，
// 返回n次修改机会的情况下连通的0最长能是多少？
// 1 <= arr长度 <= 10^6
// 0 <= 修改机会 <= 10^6
public class Code05_RunThroughZero2 {

	// 时间复杂度O(N^3)的方法
	// 为了验证
	public static int maxZero1(int[] arr, int k) {
		int n = arr.length;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = n - 1; j >= i; j--) {
				if (cost1(arr, i, j) <= k) {
					ans = Math.max(ans, j - i + 1);
					break;
				}
			}
		}
		return ans;
	}

	// 为了验证
	public static int cost1(int[] arr, int l, int r) {
		int num0 = 0;
		int num2 = 0;
		int n = r - l + 1;
		for (int i = l; i <= r; i++) {
			num0 += arr[i] == 0 ? 1 : 0;
			num2 += arr[i] == 2 ? 1 : 0;
		}
		if (num0 == n) {
			return 0;
		}
		if (num2 == n) {
			return 2;
		}
		int area2 = arr[l] == 2 ? 1 : 0;
		for (int i = l; i < r; i++) {
			if (arr[i] != 2 && arr[i + 1] == 2) {
				area2++;
			}
		}
		boolean has1 = false;
		int areaHas1No0 = 0;
		for (int i = l; i <= r; i++) {
			if (arr[i] == 0) {
				if (has1) {
					areaHas1No0++;
				}
				has1 = false;
			}
			if (arr[i] == 1) {
				has1 = true;
			}
		}
		if (has1) {
			areaHas1No0++;
		}
		return 2 * area2 + areaHas1No0;
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int[] left10 = new int[1000001];
	public static int[] left2x = new int[1000001];
	public static int[] right10 = new int[1000001];
	public static int[] right2x = new int[1000001];
	public static int[] area2s = new int[1000001];
	public static int[] area1s = new int[1000001];

	public static int maxZero2(int[] arr, int k) {
		int n = arr.length;
		int last = -1;
		for (int i = 0; i < n; i++) {
			if (arr[i] == 0) {
				last = i;
			}
			if (arr[i] == 1) {
				left10[i] = last;
			}
		}
		last = -1;
		for (int i = 0; i < n; i++) {
			if (arr[i] != 2) {
				last = i;
			}
			if (arr[i] == 2) {
				left2x[i] = last;
			}
		}
		last = n;
		for (int i = n - 1; i >= 0; i--) {
			if (arr[i] == 0) {
				last = i;
			}
			if (arr[i] == 1) {
				right10[i] = last;
			}
		}
		last = n;
		for (int i = n - 1; i >= 0; i--) {
			if (arr[i] != 2) {
				last = i;
			}
			if (arr[i] == 2) {
				right2x[i] = last;
			}
		}
		int area2 = arr[0] == 2 ? 1 : 0;
		for (int i = 0; i < n - 1; i++) {
			if (arr[i] != 2) {
				area2s[i] = area2;
				if (arr[i + 1] == 2) {
					area2++;
				}
			}
		}
		if (arr[n - 1] != 2) {
			area2s[n - 1] = area2;
		}
		boolean has1 = false;
		int area1 = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] == 0) {
				if (has1) {
					area1++;
				}
				has1 = false;
				area1s[i] = area1;
			}
			if (arr[i] == 1) {
				has1 = true;
			}
		}
		int ans = 0;
		int right = 0;
		for (int left = 0; left < n; left++) {
			while (right < n && cost2(arr, left, right) <= k) {
				right++;
			}
			ans = Math.max(ans, right - left);
			right = Math.max(right, left + 1);
		}
		return ans;
	}

	public static int cost2(int[] arr, int left, int right) {
		if (arr[left] == 2 && right2x[left] > right) {
			return 2;
		}
		int area2 = arr[left] == 2 ? 1 : 0;
		area2 += arr[right] == 2 ? 1 : 0;
		left = arr[left] == 2 ? right2x[left] : left;
		right = arr[right] == 2 ? left2x[right] : right;
		area2 += area2s[right] - area2s[left];
		int area1 = 0;
		if (arr[left] == 0 && arr[right] == 0) {
			area1 = area1s[right] - area1s[left];
		} else if (arr[left] == 0) {
			area1++;
			right = left10[right];
			area1 += area1s[right] - area1s[left];
		} else if (arr[right] == 0) {
			area1++;
			left = right10[left];
			area1 += area1s[right] - area1s[left];
		} else {
			if (right10[left] > right) {
				area1++;
			} else {
				area1 += 2;
				left = right10[left];
				right = left10[right];
				area1 += area1s[right] - area1s[left];
			}
		}
		return 2 * area2 + area1;
	}

	// 为了测试
	public static int[] randomArray(int n) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * 3);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int K = 100;
		int testTimes = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int k = (int) (Math.random() * K);
			int[] arr = randomArray(n);
			int ans1 = maxZero1(arr, k);
			int ans2 = maxZero2(arr, k);
			if (ans1 != ans2) {
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("k : " + k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 1000000;
		int k = 1000000;
		System.out.println("数组长度 : " + n);
		System.out.println("修改次数 : " + k);
		int[] arr = randomArray(n);
		long start = System.currentTimeMillis();
		maxZero2(arr, k);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + "毫秒");
		System.out.println("性能测试结束");

	}

}

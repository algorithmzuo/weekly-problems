package class_2022_08_4_week;

// 本文件仅为贪心的验证文件
// 有一个数组包含0、1、2三种值，
// 有n次修改机会，第一种将所有连通的1变为0，修改次数-1
// 第二种将所有连通的2变为1或0，修改次数-2，
// 返回n次修改机会的情况下连通的0最长能是多少？
// 1<=arr长度<=10^6
// 0<=修改机会<=10^6
public class Code05_RunThroughZero1 {

	public static int best1(int[] arr) {
		int zero = 0;
		int two = 0;
		int n = arr.length;
		for (int num : arr) {
			zero += num == 0 ? 1 : 0;
			two += num == 2 ? 1 : 0;
		}
		if (zero == n) {
			return 0;
		}
		if (two == n) {
			return 2;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			if (arr[i] != 0 && (i == 0 || arr[i - 1] != arr[i])) {
				if (arr[i] == 2) {
					ans = Math.min(ans, 2 + Math.min(best1(change(arr, i, 1)), best1(change(arr, i, 0))));
				} else {
					ans = Math.min(ans, 1 + best1(change(arr, i, 0)));
				}
			}
		}
		return ans;
	}

	public static int[] change(int[] arr, int i, int to) {
		int l = i;
		int r = i;
		while (l >= 0 && arr[l] == arr[i]) {
			l--;
		}
		while (r < arr.length && arr[r] == arr[i]) {
			r++;
		}
		int[] ans = new int[arr.length];
		for (i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		for (i = l + 1; i < r; i++) {
			ans[i] = to;
		}
		return ans;
	}

	public static int cost(int[] arr, int l, int r) {
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

	public static int[] randomArray(int n) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * 3);
		}
		return ans;
	}

	public static void main(String[] args) {
		int n = 9;
		int testTimes = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = randomArray(n);
			int ans1 = best1(arr);
			int ans2 = cost(arr, 0, arr.length - 1);
			if (ans1 != ans2) {
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

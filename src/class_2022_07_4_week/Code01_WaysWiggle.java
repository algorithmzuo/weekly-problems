package class_2022_07_4_week;

// 一个数组如果满足 : 
// 升降升降升降... 或者 降升降升...都是满足的
// 给定一个数组， 
// 1，看有几种方法能够剔除一个元素，达成上述的要求
// 2，数组天然符合要求返回0
// 3，剔除1个元素达成不了要求，返回-1，
// 比如：
// 给定[3, 4, 5, 3, 7]，返回3
// 移除0元素，4 5 3 7 符合
// 移除1元素，3 5 3 7 符合
// 移除2元素，3 4 3 7 符合
// 再比如：给定[1, 2, 3, 4] 返回-1
// 因为达成不了要求
public class Code01_WaysWiggle {

	// 暴力方法
	// 为了验证
	public static int ways1(int[] arr) {
		if (isWiggle(arr, -1)) {
			return 0;
		}
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			if (isWiggle(arr, i)) {
				ans++;
			}
		}
		return ans == 0 ? -1 : ans;
	}

	public static boolean isWiggle(int[] arr, int removeIndex) {
		boolean ans = true;
		// 升
		boolean request = true;
		for (int i = 1; i < arr.length; i++) {
			if (i == removeIndex) {
				continue;
			}
			if (i - 1 == removeIndex && removeIndex == 0) {
				continue;
			}
			int last = i - 1 == removeIndex ? (i - 2) : (i - 1);
			if (request) {
				if (arr[last] >= arr[i]) {
					ans = false;
					break;
				}
			} else {
				if (arr[last] <= arr[i]) {
					ans = false;
					break;
				}
			}
			request = !request;
		}
		if (ans) {
			return true;
		}
		ans = true;
		// 降
		request = false;
		for (int i = 1; i < arr.length; i++) {
			if (i == removeIndex) {
				continue;
			}
			if (i - 1 == removeIndex && removeIndex == 0) {
				continue;
			}
			int last = i - 1 == removeIndex ? (i - 2) : (i - 1);
			if (request) {
				if (arr[last] >= arr[i]) {
					ans = false;
					break;
				}
			} else {
				if (arr[last] <= arr[i]) {
					ans = false;
					break;
				}
			}
			request = !request;
		}
		return ans;
	}

	// 时间复杂度O(N)
	public static int ways2(int[] arr) {
		int n = arr.length;
		if (n == 1) {
			return 0;
		}
		boolean[] upLeft = new boolean[n];
		boolean[] downLeft = new boolean[n];
		boolean[] upRight = new boolean[n];
		boolean[] downRight = new boolean[n];
		boolean request = true;
		upLeft[0] = true;
		for (int i = 1; i < n; i++) {
			if ((request && arr[i - 1] < arr[i]) || (!request && arr[i - 1] > arr[i])) {
				upLeft[i] = true;
			} else {
				break;
			}
			request = !request;
		}
		request = false;
		downLeft[0] = true;
		for (int i = 1; i < n; i++) {
			if ((request && arr[i - 1] < arr[i]) || (!request && arr[i - 1] > arr[i])) {
				downLeft[i] = true;
			} else {
				break;
			}
			request = !request;
		}
		if (upLeft[n - 1] || downLeft[n - 1]) {
			return 0;
		}
		upRight[n - 1] = true;
		downRight[n - 1] = true;
		for (int i = n - 2; i >= 0; i--) {
			if (arr[i] < arr[i + 1] && downRight[i + 1]) {
				upRight[i] = true;
			}
			if (arr[i] > arr[i + 1] && upRight[i + 1]) {
				downRight[i] = true;
			}
			if (!upRight[i] && !downRight[i]) {
				break;
			}
		}
		int ans = (upRight[1] || downRight[1]) ? 1 : 0;
		ans += (upLeft[n - 2] || downLeft[n - 2]) ? 1 : 0;
		for (int deleteIndex = 1; deleteIndex < n - 1; deleteIndex++) {
			int l = deleteIndex - 1;
			int r = deleteIndex + 1;
			if (arr[l] > arr[r] && upRight[r] && ((l % 2 == 1 && upLeft[l]) || (l % 2 == 0 && downLeft[l]))) {
				ans++;
			}
			if (arr[l] < arr[r] && downRight[r] && ((l % 2 == 1 && downLeft[l]) || (l % 2 == 0 && upLeft[l]))) {
				ans++;
			}
		}
		return ans == 0 ? -1 : ans;
	}

	// 对数器
	public static int[] randomArray(int len, int maxValue) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * maxValue) + 1;
		}
		return ans;
	}

	// 对数器
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// 对数器
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 100;
		int testTime = 3000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen) + 1;
			int[] arr = randomArray(len, maxValue);
			int ans1 = ways1(arr);
			int ans2 = ways2(arr);
			if (ans1 != ans2) {
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

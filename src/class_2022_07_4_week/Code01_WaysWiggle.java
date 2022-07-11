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
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int n = arr.length;
		boolean[] up = new boolean[n];
		boolean[] down = new boolean[n];
		up[n - 1] = true;
		down[n - 1] = true;
		for (int i = n - 2; i >= 0; i--) {
			up[i] = arr[i] < arr[i + 1] && down[i + 1];
			down[i] = arr[i] > arr[i + 1] && up[i + 1];
		}
		if (up[0] || down[0]) {
			return 0;
		}
		int ans = (up[1] || down[1]) ? 1 : 0;
		boolean leftUp = true;
		boolean leftDown = true;
		boolean tmp;
		for (int i = 1, l = 0, r = 2; i < n - 1; i++, l++, r++) {
			ans += (arr[l] > arr[r] && up[r] && leftDown) || (arr[l] < arr[r] && down[r] && leftUp) ? 1 : 0;
			tmp = leftUp;
			leftUp = arr[l] > arr[i] && leftDown;
			leftDown = arr[l] < arr[i] && tmp;
		}
		ans += leftUp || leftDown ? 1 : 0;
		return ans == 0 ? -1 : ans;
	}

	// 为了验证
	public static int[] randomArray(int len, int maxValue) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * maxValue) + 1;
		}
		return ans;
	}

	// 为了验证
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 100;
		int testTime = 30000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen) + 1;
			int[] arr = randomArray(len, maxValue);
			int ans1 = ways1(arr);
			int ans2 = ways2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

}

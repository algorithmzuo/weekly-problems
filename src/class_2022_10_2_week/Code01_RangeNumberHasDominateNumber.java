package class_2022_10_2_week;

import java.util.HashMap;

// 来自小红书
// 小A认为如果在数组中有一个数出现了至少k次
// 且这个数是该数组的众数，即出现次数最多的数之一
// 那么这个数组被该数所支配
// 显然当k比较大的时候，有些数组不被任何数所支配
// 现在小A拥有一个长度为n的数组，她想知道内部有多少个区间是被某个数支配的
// 2 <= k <= n <= 100000
// 1 <= 数组的值 <= n
public class Code01_RangeNumberHasDominateNumber {

	// 暴力方法
	// 为了验证
	// 时间复杂度O(N^3)
	public static int dominates1(int[] arr, int k) {
		int n = arr.length;
		int ans = 0;
		for (int l = 0; l < n; l++) {
			for (int r = l; r < n; r++) {
				if (ok(arr, l, r, k)) {
					ans++;
				}
			}
		}
		return ans;
	}

	public static boolean ok(int[] arr, int l, int r, int k) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = l; i <= r; i++) {
			map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
		}
		for (int times : map.values()) {
			if (times >= k) {
				return true;
			}
		}
		return false;
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int dominates2(int[] arr, int k) {
		int n = arr.length;
		int all = n * (n + 1) / 2;
		int except = 0;
		int[] cnt = new int[n + 1];
		for (int l = 0, r = 0; l < n; l++) {
			while (r < n && cnt[arr[r]] + 1 < k) {
				cnt[arr[r++]]++;
			}
			except += r - l;
			cnt[arr[l]]--;
		}
		return all - except;
	}

	// 为了测试
	public static int[] randomArray(int n) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * n) + 1;
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int testTimes = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n);
			int k = (int) (Math.random() * n) + 1;
			int ans1 = dominates1(arr, k);
			int ans2 = dominates2(arr, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

package class_2023_03_4_week;

import java.util.Arrays;
import java.util.HashSet;

// 最长可整合子数组的长度
// 数组中的数字排序之后，相邻两数的差值是1
// 这种数组就叫可整合数组
// 给定一个数组，求最长可整合子数组的长度
public class Code04_MaxLenOfIntegratedSubarray {

	// 返回最长的可整合子数组长度
	// 时间复杂度O(N^2)，确实无法更好
	public static int maxLen(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int ans = 1;
		HashSet<Integer> set = new HashSet<>();
		for (int start = 0; start < arr.length; start++) {
			set.clear();
			int min = arr[start];
			int max = arr[start];
			set.add(arr[start]);
			for (int end = start + 1; end < arr.length; end++) {
				if (!set.add(arr[end])) {
					break;
				}
				min = Math.min(min, arr[end]);
				max = Math.max(max, arr[end]);
				if (max - min == end - start) {
					// start...end 可整合！
					ans = Math.max(end - start + 1, ans);
				}
			}
		}
		return ans;
	}

	// 暴力方法
	// 为了验证
	public static int right(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int n = arr.length;
		int ans = 0;
		int[] help = new int[n];
		for (int l = 0; l < n; l++) {
			for (int r = l; r < n; r++) {
				help[r] = arr[r];
				Arrays.sort(help, l, r + 1);
				boolean ok = true;
				for (int i = l + 1; i <= r; i++) {
					if (help[i - 1] + 1 != help[i]) {
						ok = false;
						break;
					}
				}
				if (ok) {
					ans = Math.max(ans, r - l + 1);
				}
			}
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int V = 50;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N);
			int[] arr = randomArray(n, V);
			int ans1 = maxLen(arr);
			int ans2 = right(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

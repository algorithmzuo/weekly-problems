package class_2022_11_2_week;

import java.util.HashMap;
import java.util.LinkedList;

// 来自亚马逊
// 给定一个数组arr，和一个正数k
// 你可以随意删除arr中的数字，最多删除k个
// 目的是让连续出现一种数字的长度尽量长
// 返回这个尽量长的长度
// 比如数组arr = { 3, -2, 3, 3, 5, 6, 3, -2 }, k = 3
// 你可以删掉-2、5、6(最多3个)，这样数组arr = { 3, 3, 3, 3, -2 }
// 可以看到连续出现3的长度为4
// 这是所有删除方法里的最长结果，所以返回4
// 1 <= arr长度 <= 3 * 10^5
// -10^9 <= arr中的数值 <= 10^9
// 0 <= k <= 3 * 10^5
public class Code02_RemoveMostKContinuousSameLongest {

	// 暴力方法
	// 为了测试
	public static int longest1(int[] arr, int k) {
		return process1(arr, 0, new int[arr.length], 0, k);
	}

	public static int process1(int[] arr, int i, int[] path, int size, int k) {
		if (k < 0) {
			return 0;
		}
		if (i == arr.length) {
			if (size == 0) {
				return 0;
			}
			int ans = 0;
			int cnt = 1;
			for (int j = 1; j < size; j++) {
				if (path[j - 1] != path[j]) {
					ans = Math.max(ans, cnt);
					cnt = 1;
				} else {
					cnt++;
				}
			}
			ans = Math.max(ans, cnt);
			return ans;
		} else {
			path[size] = arr[i];
			int p1 = process1(arr, i + 1, path, size + 1, k);
			int p2 = process1(arr, i + 1, path, size, k - 1);
			return Math.max(p1, p2);
		}
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int longest2(int[] arr, int k) {
		HashMap<Integer, LinkedList<Integer>> valueIndies = new HashMap<>();
		int ans = 1;
		for (int i = 0; i < arr.length; i++) {
			int value = arr[i];
			if (!valueIndies.containsKey(value)) {
				valueIndies.put(value, new LinkedList<>());
			}
			LinkedList<Integer> indies = valueIndies.get(value);
			while (!indies.isEmpty() && i - indies.peekFirst() - indies.size() > k) {
				indies.pollFirst();
			}
			indies.addLast(i);
			ans = Math.max(ans, indies.size());
		}
		return ans;
	}

	// 为了测试
	// 生成长度为n的数组
	// 值在[-v,v]之间等概率随机
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * (2 * v + 1)) - v;
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 20;
		int V = 10;
		int K = 5;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int k = (int) (Math.random() * K);
			int ans1 = longest1(arr, k);
			int ans2 = longest2(arr, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

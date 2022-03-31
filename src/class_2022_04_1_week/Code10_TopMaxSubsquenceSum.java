package class_2022_04_1_week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

// 给定一个数组arr，含有n个数字，可能有正、有负、有0
// 给定一个正数k
// 返回所有子序列中，累加和最大的前k个子序列累加和
// 假设K不大，怎么算最快？
public class Code10_TopMaxSubsquenceSum {

	public static int[] topMaxSum1(int[] arr, int k) {
		ArrayList<Integer> allAns = new ArrayList<>();
		process(arr, 0, 0, allAns);
		allAns.sort((a, b) -> a.compareTo(b));
		int[] ans = new int[k];
		for (int i = allAns.size() - 1, j = 0; j < k; i--, j++) {
			ans[j] = allAns.get(i);
		}
		return ans;
	}

	public static void process(int[] arr, int index, int sum, ArrayList<Integer> ans) {
		if (index == arr.length) {
			ans.add(sum);
		} else {
			process(arr, index + 1, sum, ans);
			process(arr, index + 1, sum + arr[index], ans);
		}
	}

	public static int[] topMaxSum2(int[] arr, int k) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= 0) {
				sum += arr[i];
			} else {
				arr[i] = -arr[i];
			}
		}
		int[] ans = topMinSum(arr, k);
		for (int i = 0; i < ans.length; i++) {
			ans[i] = sum - ans[i];
		}
		return ans;
	}

	public static int[] topMinSum(int[] arr, int k) {
		Arrays.sort(arr);
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.add(new int[] { 0, arr[0] });
		int[] ans = new int[k];
		for (int i = 1; i < k; i++) {
			int[] cur = heap.poll();
			int last = cur[0];
			int sum = cur[1];
			ans[i] = sum;
			if (last + 1 < arr.length) {
				heap.add(new int[] { last + 1, sum - arr[last] + arr[last + 1] });
				heap.add(new int[] { last + 1, sum + arr[last + 1] });
			}
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * value) + 1;
		}
		return arr;
	}

	// 为了测试
	public static boolean equals(int[] ans1, int[] ans2) {
		if (ans1.length != ans2.length) {
			return false;
		}
		for (int i = 0; i < ans1.length; i++) {
			if (ans1[i] != ans2[i]) {
				return false;
			}
		}
		return true;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 10;
		int v = 40;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[] arr = randomArray(len, v);
			int k = (int) (Math.random() * ((1 << len) - 1)) + 1;
			int[] ans1 = topMaxSum1(arr, k);
			int[] ans2 = topMaxSum2(arr, k);
			if (!equals(ans1, ans2)) {
				System.out.println("出错了！");
				System.out.print("arr : ");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("k : " + k);
				for (int num : ans1) {
					System.out.print(num + " ");
				}
				System.out.println();
				for (int num : ans2) {
					System.out.print(num + " ");
				}
				System.out.println();
				break;
			}
		}
		System.out.println("测试结束");
	}

}

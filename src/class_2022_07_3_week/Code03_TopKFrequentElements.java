package class_2022_07_3_week;

// 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。
// 你可以按 任意顺序 返回答案。
// 要求时间复杂度O(N)
// 本题测试链接 : https://leetcode.cn/problems/top-k-frequent-elements/
// 提交时直接提交以下代码，并把主类名改成"Solution", 可以直接通过

import java.util.HashMap;
import java.util.Map.Entry;

public class Code03_TopKFrequentElements {

	public static int[] topKFrequent(int[] nums, int k) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int num : nums) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}
		int i = map.size();
		int[][] arr = new int[i][2];
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			arr[--i][0] = entry.getKey();
			arr[i][1] = entry.getValue();
		}
		moreLess(arr, 0, arr.length - 1, k);
		int[] ans = new int[k];
		for (; i < k; i++) {
			ans[i] = arr[i][0];
		}
		return ans;
	}

	public static void moreLess(int[][] arr, int l, int r, int k) {
		if (k == r - l + 1) {
			return;
		}
		swap(arr, r, l + (int) (Math.random() * (r - l + 1)));
		int pivot = partition(arr, l, r);
		if (pivot - l == k) {
			return;
		} else if (pivot - l > k) {
			moreLess(arr, l, pivot - 1, k);
		} else {
			moreLess(arr, pivot, r, k - pivot + l);
		}
	}

	public static int partition(int[][] arr, int l, int r) {
		int left = l - 1;
		int index = l;
		while (index < r) {
			if (arr[index][1] <= arr[r][1]) {
				index++;
			} else {
				swap(arr, ++left, index++);
			}
		}
		swap(arr, ++left, r);
		return left;
	}

	public static void swap(int[][] arr, int i, int j) {
		int[] tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}

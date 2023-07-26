package class_2023_08_3_week;

import java.util.PriorityQueue;

// https://leetcode.cn/problems/minimum-operations-to-halve-array-sum/
public class Code02_MinimumOperationsToHalveArraySum {

	public static int halveArray1(int[] nums) {
		PriorityQueue<Double> heap = new PriorityQueue<>((a, b) -> b.compareTo(a));
		double sum = 0;
		for (int num : nums) {
			heap.add((double) num);
			sum += num;
		}
		sum /= 2;
		int ans = 0;
		for (double minus = 0, cur; minus < sum; ans++, minus += cur) {
			cur = heap.poll() / 2;
			heap.add(cur);
		}
		return ans;
	}

	public static int MAXN = 100001;

	public static long[] help = new long[MAXN];

	public static int halveArray2(int[] nums) {
		int n = nums.length;
		long sum = 0;
		for (int i = n - 1; i >= 0; i--) {
			help[i] = (long) nums[i] << 20;
			sum += help[i];
			heapify(i, n);
		}
		sum /= 2;
		int ans = 0;
		for (long minus = 0, cur; minus < sum; ans++, minus += cur) {
			help[0] /= 2;
			cur = help[0];
			heapify(0, n);
		}
		return ans;
	}

	public static void heapify(int i, int size) {
		int l = i * 2 + 1;
		while (l < size) {
			int largest = l + 1 < size && help[l + 1] > help[l] ? l + 1 : l;
			largest = help[largest] > help[i] ? largest : i;
			if (largest == i) {
				break;
			}
			swap(largest, i);
			i = largest;
			l = i * 2 + 1;
		}
	}

	public static void swap(int i, int j) {
		long tmp = help[i];
		help[i] = help[j];
		help[j] = tmp;
	}

}

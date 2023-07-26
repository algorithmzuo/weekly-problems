package class_2023_07_4_week;

import java.util.PriorityQueue;

// 给你一个正整数数组 nums
// 每一次操作中，你可以从 nums 中选择 任意 一个数并将它减小到 恰好 一半
//（注意，在后续操作中你可以对减半过的数继续执行操作）
// 请你返回将 nums 数组和 至少 减少一半的 最少 操作数
// 测试链接 : https://leetcode.cn/problems/minimum-operations-to-halve-array-sum/
public class Code01_MinimumOperationsToHalveArraySum {

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

	public static long[] heap = new long[MAXN];

	public static int size;

	public static int halveArray2(int[] nums) {
		size = nums.length;
		long sum = 0;
		for (int i = size - 1; i >= 0; i--) {
			heap[i] = (long) nums[i] << 20;
			sum += heap[i];
			heapify(i);
		}
		sum /= 2;
		int ans = 0;
		for (long minus = 0; minus < sum; ans++) {
			heap[0] /= 2;
			minus += heap[0];
			heapify(0);
		}
		return ans;
	}

	public static void heapify(int i) {
		int l = i * 2 + 1;
		while (l < size) {
			int best = l + 1 < size && heap[l + 1] > heap[l] ? l + 1 : l;
			best = heap[best] > heap[i] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static void swap(int i, int j) {
		long tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

}

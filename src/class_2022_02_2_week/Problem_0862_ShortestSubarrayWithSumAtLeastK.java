package class_2022_02_2_week;

import java.util.LinkedList;

public class Problem_0862_ShortestSubarrayWithSumAtLeastK {

	public static int shortestSubarray(int[] arr, int K) {
		int N = arr.length;
		int[] preSums = new int[N + 1];
		for (int i = 0; i < N; i++) {
			preSums[i + 1] = preSums[i] + arr[i];
		}
		int ans = Integer.MAX_VALUE;
		LinkedList<Integer> dq = new LinkedList<>();
		for (int i = 0; i < N + 1; i++) {
			while (!dq.isEmpty() && preSums[dq.getLast()] >= preSums[i]) {
				dq.pollLast();
			}
			while (!dq.isEmpty() && preSums[i] - preSums[dq.getFirst()] >= K) {
				ans = Math.min(ans, i - dq.pollFirst());
			}
			dq.addLast(i);
		}
		return ans != Integer.MAX_VALUE ? ans : -1;
	}

}

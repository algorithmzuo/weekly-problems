package class_2023_01_1_week;

// 给你一个 严格升序排列 的正整数数组 arr 和一个整数 k 。
// 请你找到这个数组里第 k 个缺失的正整数。
// 测试链接 : https://leetcode.cn/problems/kth-missing-positive-number/
public class Code01_KthMissingPositiveNumber {

	public int findKthPositive(int[] arr, int k) {
		int l = 0;
		int r = arr.length - 1;
		int m = 0;
		int find = arr.length;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr[m] - (m + 1) >= k) {
				find = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		int preValue = find == 0 ? 0 : arr[find - 1];
		int under = preValue - find;
		return preValue + (k - under);
	}
}

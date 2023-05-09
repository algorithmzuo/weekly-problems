package class_2023_05_2_week;

import java.util.Arrays;
import java.util.TreeSet;

// 给你一个整数 n 和一个在范围 [0, n - 1] 以内的整数 p ，
// 它们表示一个长度为 n 且下标从 0 开始的数组 arr ，
// 数组中除了下标为 p 处是 1 以外，其他所有数都是 0 。
// 同时给你一个整数数组 banned ，它包含数组中的一些位置。
// banned 中第 i 个位置表示 arr[banned[i]] = 0 ，题目保证 banned[i] != p 。
// 你可以对 arr 进行 若干次 操作。一次操作中，你选择大小为 k 的一个 子数组 
// 并将它 翻转 。在任何一次翻转操作后，
// 你都需要确保 arr 中唯一的 1 不会到达任何 banned 中的位置。
// 换句话说，arr[banned[i]] 始终 保持 0 。
// 请你返回一个数组 ans ，对于 [0, n - 1] 之间的任意下标 i ，
// ans[i] 是将 1 放到位置 i 处的 最少 翻转操作次数，
// 如果无法放到位置 i 处，此数为 -1 。
// 子数组 指的是一个数组里一段连续 非空 的元素序列。
// 对于所有的 i ，ans[i] 相互之间独立计算。
// 将一个数组中的元素 翻转 指的是将数组中的值变成 相反顺序 。
// 测试链接 : https://leetcode.cn/problems/minimum-reverse-operations/
public class Code05_MinReverseOperations {

	public static int[] minReverseOperations(int n, int p, int[] banned, int k) {
		TreeSet<Integer> oddSet = new TreeSet<>();
		TreeSet<Integer> evenSet = new TreeSet<>();
		for (int i = 1; i < n; i += 2) {
			oddSet.add(i);
		}
		for (int i = 0; i < n; i += 2) {
			evenSet.add(i);
		}
		for (int ban : banned) {
			oddSet.remove(ban);
			evenSet.remove(ban);
		}
		oddSet.remove(p);
		evenSet.remove(p);
		int[] ans = new int[n];
		Arrays.fill(ans, -1);
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		queue[r++] = p;
		int level = 0;
		while (l < r) {
			int end = r;
			for (; l < end; l++) {
				int cur = queue[l];
				ans[cur] = level;
				// 核心的两句，举例子说明
				// 纯下标变换，没什么算法
				int left = Math.max(cur - k + 1, k - cur - 1);
				int right = Math.min(cur + k - 1, n * 2 - k - cur - 1);
				TreeSet<Integer> curSet = (left & 1) == 1 ? oddSet : evenSet;
				Integer ceilling = curSet.ceiling(left);
				while (ceilling != null && ceilling <= right) {
					queue[r++] = ceilling;
					curSet.remove(ceilling);
					ceilling = curSet.ceiling(left);
				}
			}
			level++;
		}
		return ans;
	}

}

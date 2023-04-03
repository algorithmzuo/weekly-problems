package class_2023_04_3_week;

import java.util.Arrays;
import java.util.TreeSet;

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
				for (Integer ceilling = curSet.ceiling(left); ceilling != null
						&& ceilling <= right; ceilling = curSet.ceiling(left)) {
					queue[r++] = ceilling;
					curSet.remove(ceilling);
				}
			}
			level++;
		}
		return ans;
	}

}

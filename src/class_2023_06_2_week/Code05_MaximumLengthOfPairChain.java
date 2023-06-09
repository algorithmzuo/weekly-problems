package class_2023_06_2_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/maximum-length-of-pair-chain/
public class Code05_MaximumLengthOfPairChain {

	public static int findLongestChain(int[][] pairs) {
		int n = pairs.length;
		Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
		int[] ends = new int[n];
		int size = 0;
		for (int[] pair : pairs) {
			int l = 0;
			int r = size - 1;
			int m = 0;
			int find = -1;
			while (l <= r) {
				m = (l + r) / 2;
				if (ends[m] >= pair[0]) {
					find = m;
					r = m - 1;
				} else {
					l = m + 1;
				}
			}
			if (find == -1) {
				ends[size++] = pair[1];
			} else {
				ends[find] = Math.min(ends[find], pair[1]);
			}
		}
		return size;
	}

}

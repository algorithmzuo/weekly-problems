package class_2022_08_1_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/shortest-impossible-sequence-of-rolls/
public class Code05_ShortestImpossibleSequenceOfRolls {

	public static int shortestSequence(int[] rolls, int k) {
		boolean[] set = new boolean[k + 1];
		int size = 0;
		int ans = 0;
		for (int num : rolls) {
			if (!set[num]) {
				set[num] = true;
				size++;
			}
			if (size == k) {
				ans++;
				Arrays.fill(set, false);
				size = 0;
			}
		}
		return ans + 1;
	}

}

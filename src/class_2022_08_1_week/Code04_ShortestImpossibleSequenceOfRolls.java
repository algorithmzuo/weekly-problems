package class_2022_08_1_week;

import java.util.Arrays;

// 给你一个长度为 n 的整数数组 rolls 和一个整数 k 。
// 你扔一个 k 面的骰子 n 次，骰子的每个面分别是 1 到 k ，
// 其中第 i 次扔得到的数字是 rolls[i] 。
// 请你返回 无法 从 rolls 中得到的 最短 骰子子序列的长度。
// 扔一个 k 面的骰子 len 次得到的是一个长度为 len 的 骰子子序列 。
// 注意 ，子序列只需要保持在原数组中的顺序，不需要连续。
// 测试链接 : https://leetcode.cn/problems/shortest-impossible-sequence-of-rolls/
public class Code04_ShortestImpossibleSequenceOfRolls {

	// 所有数字1~k
	public static int shortestSequence(int[] rolls, int k) {
		// 1~k上，某个数字是否收集到了！
		// set[i] == true
		// set[i] == false
		boolean[] set = new boolean[k + 1];
		// 当前这一套，收集了几个数字了？
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

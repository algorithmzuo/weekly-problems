package class_2023_02_3_week;

import java.util.Arrays;
import java.util.HashMap;

// 给你一份工作时间表 hours，上面记录着某一位员工每天的工作小时数。
// 我们认为当员工一天中的工作小时数大于 8 小时的时候，那么这一天就是「劳累的一天」。
// 所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 大于「不劳累的天数」。
// 请你返回「表现良好时间段」的最大长度。
// 测试链接 : https://leetcode.cn/problems/longest-well-performing-interval/
public class Code03_LongestWellPerformingInterval {

	// 哈希表
	public static int longestWPI1(int[] hours) {
		// key : 某个前缀和
		// value : 这个前缀和最早出现的位置
		HashMap<Integer, Integer> map = new HashMap<>();
		// 0这个前缀和，最早出现在哪？一个数也没有的时候
		map.put(0, -1);
		int ans = 0;
		int sum = 0;
		for (int i = 0; i < hours.length; i++) {
			sum += hours[i] > 8 ? 1 : -1;
			if (sum > 0) {
				// 0...i i+1
				ans = i + 1;
			} else {
				// sum = -4
				// -5最早出现在哪 j  j+1...i
				if (map.containsKey(sum - 1)) {
					ans = Math.max(ans, i - map.get(sum - 1));
				}
			}
			if (!map.containsKey(sum)) {
				map.put(sum, i);
			}
		}
		return ans;
	}

	// 数组替代哈希表
	public static int longestWPI2(int[] hours) {
		int n = hours.length;
		int[] early = new int[(n << 1) + 1];
		Arrays.fill(early, -2);
		early[0 + n] = -1;
		int ans = 0;
		int sum = 0;
		for (int i = 0; i < hours.length; i++) {
			sum += hours[i] > 8 ? 1 : -1;
			if (sum > 1) {
				ans = i + 1;
			} else {
				if (sum - 1 + n >= 0 && early[sum - 1 + n] != -2) {
					ans = Math.max(ans, i - early[sum - 1 + n]);
				}
			}
			if (early[sum + n] == -2) {
				early[sum + n] = i;
			}
		}
		return ans;
	}

}

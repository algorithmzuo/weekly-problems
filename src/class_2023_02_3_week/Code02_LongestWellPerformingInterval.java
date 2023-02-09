package class_2023_02_3_week;

import java.util.Arrays;
import java.util.HashMap;

// 测试链接 : https://leetcode.cn/problems/longest-well-performing-interval/
public class Code02_LongestWellPerformingInterval {

	// 哈希表
	public static int longestWPI1(int[] hours) {
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int ans = 0;
		int sum = 0;
		for (int i = 0; i < hours.length; i++) {
			sum += hours[i] > 8 ? 1 : -1;
			if (sum > 1) {
				ans = i + 1;
			} else {
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

package class_2023_02_1_week;

import java.util.TreeSet;

// 测试链接 : https://leetcode.cn/problems/minimize-deviation-in-array/
public class Code01_MinimizeDeviationInArray {

	public int minimumDeviation(int[] nums) {
		TreeSet<Integer> set = new TreeSet<>();
		for (int num : nums) {
			set.add(num % 2 == 0 ? num : num * 2);
		}
		int ans = set.last() - set.first();
		while (ans > 0 && set.last() % 2 == 0) {
			int max = set.last();
			set.remove(max);
			set.add(max / 2);
			ans = Math.min(ans, set.last() - set.first());
		}
		return ans;
	}

}

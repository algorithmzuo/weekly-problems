package class_2023_02_1_week;

import java.util.TreeSet;

// 给你一个由 n 个正整数组成的数组 nums
// 你可以对数组的任意元素执行任意次数的两类操作
// 如果元素是 偶数 ，除以 2
// 例如，如果数组是 [1,2,3,4]
// 那么你可以对最后一个元素执行此操作使其变成 [1,2,3,2]
// 如果元素是 奇数 ，乘上 2
// 例如，如果数组是 [1,2,3,4] ，那么你可以对第一个元素执行此操作，使其变成 [2,2,3,4]
// 数组的 偏移量 是数组中任意两个元素之间的 最大差值
// 返回数组在执行某些操作之后可以拥有的 最小偏移量
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

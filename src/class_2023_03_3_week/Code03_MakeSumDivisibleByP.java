package class_2023_03_3_week;

import java.util.HashMap;

// 给你一个正整数数组 nums，请你移除 最短 子数组（可以为 空）
// 使得剩余元素的 和 能被 p 整除。 不允许 将整个数组都移除。
// 请你返回你需要移除的最短子数组的长度，如果无法满足题目要求，返回 -1 。
// 子数组 定义为原数组中连续的一组元素。
// 测试链接 : https://leetcode.cn/problems/make-sum-divisible-by-p/
public class Code03_MakeSumDivisibleByP {

	public int minSubarray(int[] nums, int p) {
		int n = nums.length;
		// 求出整体的余数
		int allMod = 0;
		for (int num : nums) {
			allMod = (allMod + num) % p;
		}
		if (allMod == 0) {
			return 0;
		}
		// 记录前缀和的某个余数，最晚出现的位置
		// 看课！然后看接下来的代码
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int ans = Integer.MAX_VALUE;
		int curMod = 0, find;
		for (int i = 0; i < n; i++) {
			// 0...i 累加和的余数
			curMod = (curMod + nums[i]) % p;
			// 如果p = 7，整体余数2，当前余数5，那么找之前的部分余数是3
			// 如果p = 7，整体余数2，当前余数1，那么找之前的部分余数是6
			// 整体变成下面的公式，可以自己带入各种情况验证
			find = (curMod - allMod + p) % p;
			if (map.containsKey(find)) {
				if (i != n - 1 || map.get(find) != -1) {
					ans = Math.min(ans, i - map.get(find));
				}
			}
			map.put(curMod, i);
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

}

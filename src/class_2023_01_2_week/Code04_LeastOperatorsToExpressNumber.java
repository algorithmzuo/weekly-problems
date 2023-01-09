package class_2023_01_2_week;

import java.util.HashMap;

// 测试链接 : https://leetcode.cn/problems/least-operators-to-express-number/
public class Code04_LeastOperatorsToExpressNumber {

	public static int leastOpsExpressTarget(int x, int target) {
		return dp(0, target, x, new HashMap<>()) - 1;
	}

	public static int dp(int i, int target, int x, HashMap<Integer, HashMap<Integer, Integer>> dp) {
		if (dp.containsKey(i) && dp.get(i).containsKey(target)) {
			return dp.get(i).get(target);
		}
		int ans = 0;
		if (target > 0 && i < 39) {
			if (target == 1) {
				ans = cost(i);
			} else {
				int t = target / x;
				int r = target % x;
				ans = Math.min(r * cost(i) + dp(i + 1, t, x, dp), (x - r) * cost(i) + dp(i + 1, t + 1, x, dp));
			}
		}
		if (!dp.containsKey(i)) {
			dp.put(i, new HashMap<>());
		}
		dp.get(i).put(target, ans);
		return ans;
	}

	public static int cost(int x) {
		return x > 0 ? x : 2;
	}

}

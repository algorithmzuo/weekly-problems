package class_2023_01_2_week;

import java.util.HashMap;

// 给定一个正整数 x，我们将会写出一个形如 x (op1) x (op2) x (op3) x ... 的表达式
// 其中每个运算符 op1，op2，… 可以是加、减、乘、除之一
// 例如，对于 x = 3，我们可以写出表达式 3 * 3 / 3 + 3 - 3，该式的值为3
// 在写这样的表达式时，我们需要遵守下面的惯例：
// 除运算符（/）返回有理数
// 任何地方都没有括号
// 我们使用通常的操作顺序：乘法和除法发生在加法和减法之前
// 不允许使用一元否定运算符（-）。例如，“x - x” 是一个有效的表达
// 因为它只使用减法，但是 “-x + x” 不是，因为它使用了否定运算符
// 我们希望编写一个能使表达式等于给定的目标值 target 且运算符最少的表达式
// 返回所用运算符的最少数量
// 测试链接 : https://leetcode.cn/problems/least-operators-to-express-number/
public class Code03_LeastOperatorsToExpressNumber {

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

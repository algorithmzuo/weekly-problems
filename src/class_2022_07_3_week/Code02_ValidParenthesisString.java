package class_2022_07_3_week;

// 来自蔚来汽车
// 给定一个只包含三种字符的字符串：（ ，） 和 *，
// 写一个函数来检验这个字符串是否为有效字符串。有效字符串具有如下规则：
// 任何左括号 ( 必须有相应的右括号 )。
// 任何右括号 ) 必须有相应的左括号 ( 。
// 左括号 ( 必须在对应的右括号之前 )。
// * 可以被视为单个右括号 ) ，或单个左括号 ( ，或一个空字符串。
// 一个空字符串也被视为有效字符串。
// 测试链接 : https://leetcode.cn/problems/valid-parenthesis-string/
public class Code02_ValidParenthesisString {

	// 时间复杂度O(N平方)
	// 从左往右的尝试
	// 暴力递归改动态规划
	public static boolean checkValidString1(String s) {
		char[] str = s.toCharArray();
		int n = str.length;
		int[][] dp = new int[n][n];
		return f(str, 0, 0, dp);
	}

	public static boolean f(char[] s, int i, int c, int[][] dp) {
		if (i == s.length) {
			return c == 0;
		}
		if (c < 0) {
			return false;
		}
		if (c > s.length - i) {
			return false;
		}
		if (dp[i][c] != 0) {
			return dp[i][c] == 1;
		}
		boolean ans = false;
		if (s[i] == '(') {
			ans = f(s, i + 1, c + 1, dp);
		} else if (s[i] == ')') {
			ans = f(s, i + 1, c - 1, dp);
		} else {
			ans |= f(s, i + 1, c + 1, dp);
			ans |= f(s, i + 1, c - 1, dp);
			ans |= f(s, i + 1, c, dp);
		}
		dp[i][c] = ans ? 1 : -1;
		return ans;
	}

	// 贪心方法
	// 最优解
	// 时间复杂度O(N)，额外空间复杂度O(1)
	public static boolean checkValidString2(String s) {
		char[] str = s.toCharArray();
		int max = 0;
		int min = 0;
		for (char x : str) {
			if (x == '(') {
				max++;
				min++;
			} else {
				if (x == ')' && max == 0) {
					return false;
				}
				max += x == ')' ? -1 : 1;
				if (min > 0) {
					min--;
				}
			}
		}
		return min == 0;
	}

}

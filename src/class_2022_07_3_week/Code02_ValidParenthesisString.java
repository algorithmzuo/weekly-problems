package class_2022_07_3_week;

// 来自蔚来汽车
// 蔚来汽车考试的时候，数据量N才100
// 其实这个题可以做到时间复杂度O(N)，额外空间复杂度O(1)
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
		int down = 0;
		int up = 0;
		for (char x : str) {
			if (x == '(') {
				down++;
				up++;
			} else if (x == ')') {
				down = Math.max(down - 1, 0);
				up--;
			} else {
				down = Math.max(down - 1, 0);
				up++;
			}
			if (up < 0) {
				return false;
			}
		}
		return down == 0;
	}

}

package class_2022_04_2_week;

// 给定一个字符串str，和一个正数k
// 你可以随意的划分str成多个子串，
// 目的是找到在某一种划分方案中，有尽可能多的回文子串，长度>=k，并且没有重合
// 返回有几个回文子串
public class Code07_PalindromeStringNoLessKLenNoOverlapingMaxParts {

	// 暴力尝试
	// 为了测试
	// 可以改成动态规划，但不是最优解
	public static int max1(String s, int k) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		return process1(str, 0, k);
	}

	public static int process1(char[] str, int index, int k) {
		if (str.length - index < k) {
			return 0;
		}
		int ans = process1(str, index + 1, k);
		for (int i = index + k - 1; i < str.length; i++) {
			if (isPalindrome(str, index, i)) {
				ans = Math.max(ans, 1 + process1(str, i + 1, k));
			}
		}
		return ans;
	}

	public static boolean isPalindrome(char[] str, int L, int R) {
		while (L < R) {
			if (str[L++] != str[R--]) {
				return false;
			}
		}
		return true;
	}

	// 最优解
	// 时间复杂度O(N)
	public static int max2(String s, int k) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = manacherString(s);
		int[] radius = new int[str.length];
		int ans = 0;
		int next = 0;
		while ((next = manacherFind(str, radius, next, k)) != str.length) {
			ans++;
		}
		return ans;
	}

	public static char[] manacherString(String s) {
		char[] str = s.toCharArray();
		char[] ans = new char[s.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != ans.length; i++) {
			ans[i] = (i & 1) == 0 ? '#' : str[index++];
		}
		return ans;
	}

	// s[l...]字符串只在这个范围上，且s[l]一定是'#'
	// 从下标l开始，之前都不算，一旦有某个中心回文半径>k，马上返回右边界
	// 并且右边界一定要是'#'
	public static int manacherFind(char[] s, int[] r, int l, int k) {
		int C = l - 1;
		int R = l - 1;
		for (int i = l; i < s.length; i++) {
			r[i] = R > i ? Math.min(r[2 * C - i], R - i) : 1;
			while (i + r[i] < s.length && i - r[i] > l - 1) {
				if (s[i + r[i]] == s[i - r[i]]) {
					r[i]++;
					if (r[i] - 1 >= k) {
						return s[i + k] == '#' ? (i + k) : (i + k + 1);
					}
				} else {
					break;
				}
			}
			if (i + r[i] > R) {
				R = i + r[i];
				C = i;
			}
		}
		return s.length;
	}

	// 为了测试
	public static String randomString(int n, int r) {
		char[] str = new char[(int) (Math.random() * n)];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) ((int) (Math.random() * r) + 'a');
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 20;
		int r = 3;
		int testTime = 50000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			String str = randomString(n, r);
			int k = (int) (Math.random() * str.length()) + 1;
			int ans1 = max1(str, k);
			int ans2 = max2(str, k);
			if (ans1 != ans2) {
				System.out.println(str);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

package class_2022_10_3_week;

// 给定一个字符串str
// 如果删掉连续一段子串，剩下的字符串拼接起来是回文串
// 那么该删除叫做有效的删除
// 返回有多少种有效删除
// 字符串长度 <= 3000
public class Code03_RemoveSubstringRestPalindrome {

	// 暴力方法
	// 时间复杂度O(N^3)
	// 为了验证而写，不是正式方法
	// 删掉每一个可能的子串
	// 然后验证剩下的子串是不是回文串
	public static int good1(String str) {
		int n = str.length();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				if (isPalindrome(str.substring(0, i) + str.substring(j + 1))) {
					ans++;
				}
			}
		}
		return ans - 1;
	}

	public static boolean isPalindrome(String str) {
		for (int l = 0, r = str.length() - 1; l <= r; l++, r--) {
			if (str.charAt(l) != str.charAt(r)) {
				return false;
			}
		}
		return true;
	}

	// 正式方法
	// 时间复杂度O(N^2)
	public static int good2(String str) {
		if (str.length() == 1) {
			return 0;
		}
		int[] pArr = manacher(str);
		char[] s = str.toCharArray();
		int n = s.length;
		int range = 0;
		for (int l = 0, r = n - 1; l <= r && s[l] == s[r]; l++, r--) {
			range++;
		}
		int ans = 0;
		for (int l = 0; l < n; l++) {
			for (int r = l; r < n; r++) {
				if (l < n - r - 1) {
					if (range >= l && check(pArr, r + 1, n - l - 1)) {
						ans++;
					}
				} else if (l > n - r - 1) {
					if (range >= n - r - 1 && check(pArr, n - r - 1, l - 1)) {
						ans++;
					}
				} else {
					if (range >= l) {
						ans++;
					}
				}
			}
		}
		return ans - 1;
	}

	public static int[] manacher(String s) {
		char[] str = manacherString(s);
		int[] pArr = new int[str.length];
		int C = -1;
		int R = -1;
		for (int i = 0; i < str.length; i++) { // 0 1 2
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
		}
		return pArr;
	}

	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	// 根据原字符串的回文半径数组，判断原字符串l...r这一段是不是回文
	public static boolean check(int[] pArr, int l, int r) {
		int n = r - l + 1;
		l = l * 2 + 1;
		r = r * 2 + 1;
		return pArr[(l + r) / 2] - 1 >= n;
	}

	// 为了测试
	public static String randomString(int n, int v) {
		char[] str = new char[n];
		for (int i = 0; i < n; i++) {
			str[i] = (char) ((int) (Math.random() * v) + 'a');
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 50;
		int V = 3;
		int testTime = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			String str = randomString(n, V);
			int ans1 = good1(str);
			int ans2 = good2(str);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 3000;
		int v = 26;
		String str = randomString(n, v);
		System.out.println("字符串的长度 : " + n);
		System.out.println("小写字符种类 : " + v);
		long start;
		long end;
		start = System.currentTimeMillis();
		int ans1 = good1(str);
		end = System.currentTimeMillis();
		System.out.println("方法1(暴力方法)答案 : " + ans1);
		System.out.println("方法1(暴力方法)运行时间 : " + (end - start) + " 毫秒");
		start = System.currentTimeMillis();
		int ans2 = good2(str);
		end = System.currentTimeMillis();
		System.out.println("方法2(正式方法)答案 : " + ans2);
		System.out.println("方法2(正式方法)运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}

package class_2022_09_3_week;

// 来自字节
// 给定一个只由小写字母组成的字符串str，长度为N
// 给定一个只由0、1组成的数组arr，长度为N
// arr[i] == 0表示str中i位置的字符不许修改
// arr[i] == 1表示str中i位置的字符允许修改
// 给定一个正数m，表示在任意允许修改的位置，可以把该位置的字符变成a~z中的任何一个
// 可以修改m次
// 返回在最多修改m次的情况下，全是一种字符的最长子串是多长
// 1 <= N, M <= 10^5
// 所有字符都是小写
public class Code03_MaxLengthSameCharMChanges {

	// 暴力方法
	// 为了测试
	public static int maxLen1(String str, int[] arr, int m) {
		char[] s = str.toCharArray();
		int n = s.length;
		int ans = 0;
		for (char c = 'a'; c <= 'z'; c++) {
			for (int i = 0; i < n; i++) {
				for (int j = n - 1; j >= i; j--) {
					if (ok(s, i, j, c, arr, m)) {
						ans = Math.max(ans, j - i + 1);
						break;
					}
				}
			}
		}
		return ans;
	}

	// 为了测试
	public static boolean ok(char[] s, int l, int r, char c, int[] arr, int m) {
		for (int i = l; i <= r; i++) {
			if (s[i] == c) {
				continue;
			}
			if (arr[i] == 0 || m == 0) {
				return false;
			}
			m--;
		}
		return true;
	}

	// 正式方法
	public static int maxLen2(String str, int[] arr, int m) {
		char[] s = str.toCharArray();
		int n = s.length;
		int ans = 0;
		for (char c = 'a'; c <= 'z'; c++) {
			int r = 0;
			int change = 0;
			for (int i = 0; i < n; i++) {
				while (r < n) {
					if (s[r] == c) {
						r++;
						continue;
					}
					if (arr[r] == 0 || change == m) {
						break;
					}
					change++;
					r++;
				}
				ans = Math.max(ans, r - i);
				if (s[i] != c && arr[i] == 1) {
					change--;
				}
				r = Math.max(r, i + 1);
			}
		}
		return ans;
	}

	// 为了测试
	public static String randomString(int n, int r) {
		char[] ans = new char[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (char) ((int) (Math.random() * r) + 'a');
		}
		return String.valueOf(ans);
	}

	// 为了测试
	public static int[] randomArray(int n) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * 2);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int R = 5;
		int testTimes = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int m = (int) (Math.random() * n) + 1;
			String str = randomString(n, R);
			int[] arr = randomArray(n);
			int ans1 = maxLen1(str, arr, m);
			int ans2 = maxLen2(str, arr, m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

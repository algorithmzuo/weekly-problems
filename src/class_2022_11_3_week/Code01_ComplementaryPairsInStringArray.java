package class_2022_11_3_week;

import java.util.HashMap;

// 来自亚马逊
// 给定一个字符串数组strs，其中每个字符串都是小写字母组成的
// 如果i < j，并且strs[i]和strs[j]所有的字符随意去排列能组成回文串
// 那么说(i,j)叫做一个互补对(complementary)
// 求strs中有多少个互补对
// strs长度 <= 3 * 10^5
// 单个字符串长度 <= 10^5
// strs里所有字符串总长度 <= 10^6
public class Code01_ComplementaryPairsInStringArray {

	// 暴力方法
	// 为了测试
	public static int num1(String[] strs) {
		int ans = 0;
		for (int i = 0; i < strs.length; i++) {
			for (int j = i + 1; j < strs.length; j++) {
				if (complementary(strs[i], strs[j])) {
					ans++;
				}
			}
		}
		return ans;
	}

	public static boolean complementary(String a, String b) {
		int[] cnt = new int[26];
		for (int i = 0; i < a.length(); i++) {
			cnt[a.charAt(i) - 'a']++;
		}
		for (int i = 0; i < b.length(); i++) {
			cnt[b.charAt(i) - 'a']++;
		}
		int odd = 0;
		for (int num : cnt) {
			if ((num & 1) != 0) {
				odd++;
			}
		}
		return odd < 2;
	}

	public static int num2(String[] strs) {
		HashMap<Integer, Integer> status = new HashMap<>();
		int ans = 0;
		for (String str : strs) {
			int cur = 0;
			for (int i = 0; i < str.length(); i++) {
				cur ^= 1 << (str.charAt(i) - 'a');
			}
			ans += status.getOrDefault(cur, 0);
			for (int i = 0; i < 26; i++) {
				ans += status.getOrDefault(cur ^ (1 << i), 0);
			}
			status.put(cur, status.getOrDefault(cur, 0) + 1);
		}
		return ans;
	}

	// 为了验证
	public static String[] randomStringArray(int n, int m, int r) {
		String[] ans = new String[n];
		for (int i = 0; i < n; i++) {
			int len = (int) (Math.random() * m) + 1;
			char[] str = new char[len];
			for (int j = 0; j < len; j++) {
				str[j] = (char) ((int) (Math.random() * r) + 'a');
			}
			ans[i] = String.valueOf(str);
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 100;
		int M = 20;
		int R = 5;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			String[] strs = randomStringArray(n, M, R);
			int ans1 = num1(strs);
			int ans2 = num2(strs);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

package class_2022_11_2_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/cracking-the-safe/description/
public class Code04_CrackingTheSafe {

	public static String crackSafe(int n, int k) {
		int size = power(k, n);
		int offset = size / k;
		int[] ans = new int[size - 1 + n];
		int[] choose = new int[offset];
		Arrays.fill(choose, k - 1);
		for (int i = n - 1, first = 0, pre = 0; i < ans.length; i++, first++) {
			ans[i] = choose[pre]--;
			pre = pre * k - ans[first] * offset + ans[i];
		}
		StringBuilder builder = new StringBuilder();
		for (int num : ans) {
			builder.append(String.valueOf(num));
		}
		return builder.toString();
	}

	public static int power(int k, int n) {
		int ans = 1;
		for (; n > 0; n >>= 1) {
			if ((n & 1) != 0) {
				ans *= k;
			}
			k *= k;
		}
		return ans;
	}

}

package class_2023_06_3_week;

// 来自字节
// 密码是一串长度为n的小写字母，一则关于密码的线索纸条
// 首先将字母a到z编号为0到25编号
// 纸条上共有n个整数ai，其中a1表示密码里第一个字母的编号
// 若i>1的话就表示第i个字母和第i-1个字母编号的差值
// 例如，a2就代表密码中第1个字母和第2个字母编号的差值
// 若密码是acb，那么纸条上的数字就是[5, 2, 1]
// a b c d e f
// 0 1 2 3 4 5
// 返回可能的密码的个数，由于结果可能很大，
// 输出对1000000007取模的结果
// 1 <= n <= 10^5
// 0 <= ai <= 25
public class Code02_PasswordWays {

	// 暴力递归
	public static int ways1(int[] arr) {
		//
		// 0 -> a
		// 1 -> b
		return process1(arr, 1, arr[0]);
	}

	// arr : 字条 i现在来到的位置
	// pre : 前一个字符的编号
	// f ?...
	// 5
	// 3
	// 返回最终，有多少合法的字符串
	// -1 >=26
	// pre pre
	public static int process1(int[] arr, int i, int pre) {
		int ans = 0;
		if (pre < 0 || pre > 25) {
			ans = 0;
		} else {
			// pre 有效！
			if (i == arr.length) {
				// 之前的转化，一定走到尽头了
				ans = 1;
			} else {
				// pre f 2
				// f+2.....
				// f-2.....
				ans += process1(arr, i + 1, pre - arr[i]);
				ans += process1(arr, i + 1, pre + arr[i]);
			}
		}
		return ans;
	}

	// 动态规划
	public static int ways2(int[] arr) {
		int mod = 1000000007;
		int n = arr.length;
		int[][] dp = new int[n + 1][26];
		for (int j = 0; j < 26; j++) {
			dp[n][j] = 1;
		}
		for (int i = n - 1; i >= 1; i--) {
			for (int j = 0; j < 26; j++) {
				if (j - arr[i] >= 0) {
					dp[i][j] = dp[i + 1][j - arr[i]];
				}
				if (j + arr[i] < 26) {
					dp[i][j] = (dp[i][j] + dp[i + 1][j + arr[i]]) % mod;
				}
			}
		}
		return dp[1][arr[0]];
	}

}

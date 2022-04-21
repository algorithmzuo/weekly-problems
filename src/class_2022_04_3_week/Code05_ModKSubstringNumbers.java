package class_2022_04_3_week;

// 来自微众
// 4.11笔试
// 给定n位长的数字字符串和正数k，求该子符串能被k整除的子串个数
// (n<=1000，k<=100)
public class Code05_ModKSubstringNumbers {

	// 暴力方法
	// 为了验证
	public static int modWays1(String s, int k) {
		int n = s.length();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				if (Long.valueOf(s.substring(i, j + 1)) % k == 0) {
					ans++;
				}
			}
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N * k)
	public static int modWays2(String s, int k) {
		int[] cur = new int[k];
		// 帮忙迁移
		int[] next = new int[k];
		// 0...i 整体余几？
		int mod = 0;
		// 答案：统计有多少子串的值%k == 0
		int ans = 0;
		for (char cha : s.toCharArray()) {
			for (int i = 0; i < k; i++) {
				// i -> 10个
				// (i * 10) % k 
				next[(i * 10) % k] += cur[i];
				cur[i] = 0;
			}
			int[] tmp = cur;
			cur = next;
			next = tmp;
			mod = (mod * 10 + (cha - '0')) % k;
			ans += (mod == 0 ? 1 : 0) + cur[mod];
			cur[mod]++;
		}
		return ans;
	}

	// 为了测试
	public static String randomNumber(int n) {
		char[] ans = new char[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (char) ((int) (Math.random() * 10) + '0');
		}
		return String.valueOf(ans);
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 18;
		int K = 20;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			String str = randomNumber((int) (Math.random() * N) + 1);
			int k = (int) (Math.random() * K) + 1;
			int ans1 = modWays1(str, k);
			int ans2 = modWays2(str, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println(str);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

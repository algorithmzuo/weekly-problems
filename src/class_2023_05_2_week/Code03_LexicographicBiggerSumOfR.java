package class_2023_05_2_week;

// 来自学员问题，来自真实笔试
// 塔子哥最近在处理一些字符串相关的任务
// 他喜欢 R 字符，因为在某些任务中，这个字符通常表示“正确”的结果
// 另一方面，他不喜欢 B 字符，因为在某些任务中，这个字符通常表示“错误”的结果
// 为了解决他的任务，塔子哥定义了字符串的权值为字符串中 R 字符的出现次数
// 例如，对于字符串 BBRBRB，它的权值为 2，因为其中有 2 个 R 字符
// 现在，塔子哥面临一个问题，他有一个长度为 n 的字符串 s，它仅由 R 和 B 组成
// 他想知道，长度为 n 的仅由 R 和 B组成的字符串中，
// 字典序不小于 s 的字符串的权值之和是多少？
// 因此，他需要编写一个程序来解决这个问题
// 输入第一行为一个整数 n ，表示字符串的长度
// 输入第二行为一个长度为 n 的字符串 s ，字符串中元素组成仅为 R 和 B
// 输出一个整数，代表长度为 n 的、字典序不小于 s 的字符串权值之和
// 输入样例：
// 3
// RBR
// 输出：
// 7
// 解释：共有 3 个字符串字典序大于等于"RBR"，RBR权值为2，RRB为2，RRR为3
// 1 <= n <= 100000
// 结果可能很大，对1000000007取模
// 帖子链接 : https://www.mashibing.com/question/detail/67223
public class Code03_LexicographicBiggerSumOfR {

	// 为了测试
	// 暴力方法
	public static int sum1(String str) {
		return process1("", str);
	}

	// 为了测试
	// 暴力方法
	public static int process1(String path, String s) {
		if (path.length() == s.length()) {
			if (path.compareTo(s) >= 0) {
				int ans = 0;
				for (int i = 0; i < path.length(); i++) {
					if (path.charAt(i) == 'R') {
						ans++;
					}
				}
				return ans;
			} else {
				return 0;
			}
		} else {
			return process1(path + "R", s) + process1(path + "B", s);
		}
	}

	// 以下为正式方法
	public static int MAXN = 100001;

	// pow2[i] : 长度为i的所有RB串，一共有多少字符串，%mod的余数
	// 一定要求余数！
	public static int[] pow2 = new int[MAXN];

	// f[i] : 长度为i的所有RB串，一共有多少权值和，%mod的余数
	// 一定要求余数！
	public static int[] f = new int[MAXN];

	public static int mod = 1000000007;

	static {
		pow2[0] = 1;
		for (int i = 1; i < MAXN; i++) {
			pow2[i] = (pow2[i - 1] * 2) % mod;
		}
		f[1] = 1;
		for (int i = 2; i < MAXN; i++) {
			f[i] = (pow2[i - 1] + f[i - 1]) % mod;
			f[i] = (f[i] + f[i - 1]) % mod;
		}
	}

	// 普通递归版
	// 不是最优解，但是展示了大过程
	public static int sum2(String str) {
		int n = str.length();
		char[] s = str.toCharArray();
		int[] rnumber = new int[n];
		rnumber[0] = s[0] == 'R' ? 1 : 0;
		for (int i = 1; i < n; i++) {
			rnumber[i] = rnumber[i - 1] + (s[i] == 'R' ? 1 : 0);
		}
		return process2(s, rnumber, n, 0);
	}

	// 你依次填写字符串
	// 0...i-1范围上，你填写的东西，和s完全一样
	// 当前来到i位置，一共的长度是n
	// rnumber[i] : s[0...i]范围上有几个R
	// 返回 : 在[0...i-1]和s完全一样的情况下，后续所有字典序不小于s的字符串，整体的权值和是多少?
	public static int process2(char[] s, int[] rnumber, int n, int i) {
		int ans = 0;
		if (i == n) {
			ans = rnumber[n - 1];
		} else {
			if (s[i] == 'B') {
				// s当前位置是'B'，你可以填写R，也可以是B
				// 如果你当前填R，现在的情况是：
				// 0...i-1位置上你填写的和s一样；
				// i位置上s是'B'，你填写的是'R'；
				// i之后的剩余长度是 : n-i-1，你可以随便填了
				// n-i-1可以随便填，那么字符串个数为 : 2^(n-i-1)个
				// 这2^(n-i-1)个字符串，都是:
				// [0...i-1]和s一样，i位置比s多一个R，
				// 权值和 = 1) + 2)
				// 1) 是这2^(n-i-1)个字符串，前缀上的权值和
				// 2) 是这2^(n-i-1)个字符串，后缀上的权值和
				// 具体来说 :
				// 1) (s的[0...i]范围上R的数量 + 1) * 2^(n-i-1)
				// 2) 在[i+1....]范围上的权值和 : f[n-i-1]
				int p1 = (int) (((long) (rnumber[i] + 1) * pow2[n - i - 1]) % mod);
				p1 = (p1 + f[n - i - 1]) % mod;
				// 如果你当前填B，那么继续递归
				int p2 = process2(s, rnumber, n, i + 1);
				ans = (p1 + p2) % mod;
			} else {
				// s当前位置是'R'，你只能填写R，然后继续递归
				ans = process2(s, rnumber, n, i + 1);
			}
		}
		return ans;
	}

	// 彻底动态规划版
	// 正式版，时间复杂度O(N)
	public static int sum3(String str) {
		int n = str.length();
		char[] s = str.toCharArray();
		int[] rnumber = new int[n];
		rnumber[0] = s[0] == 'R' ? 1 : 0;
		for (int i = 1; i < n; i++) {
			rnumber[i] = rnumber[i - 1] + (s[i] == 'R' ? 1 : 0);
		}
		int[] dp = new int[n + 1];
		dp[n] = rnumber[n - 1];
		for (int i = n - 1; i >= 0; i--) {
			if (s[i] == 'B') {
				int p1 = (int) (((long) (rnumber[i] + 1) * pow2[n - i - 1]) % mod);
				p1 = (p1 + f[n - i - 1]) % mod;
				int p2 = dp[i + 1];
				dp[i] = (p1 + p2) % mod;
			} else {
				dp[i] = dp[i + 1];
			}
		}
		return dp[0];
	}

	// 为了测试
	public static String randomString(int n) {
		char[] s = new char[n];
		for (int i = 0; i < n; i++) {
			s[i] = Math.random() < 0.5 ? 'B' : 'R';
		}
		return String.valueOf(s);
	}

	public static void main(String[] args) {
		int N = 15;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			String s = randomString(n);
			int ans1 = sum1(s);
			int ans3 = sum3(s);
			if (ans1 != ans3) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

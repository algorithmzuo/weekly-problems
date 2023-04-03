package class_2023_04_3_week;

// 来自腾讯笔试
// 给定一个长度为N的正数数组，还有一个正数K
// 返回有多少子序列的最大公约数为K
// 结果可能很大，对1000000007取模
// 原题目简单转化就是如下的题目
// 测试链接 : https://www.luogu.com.cn/problem/CF803F
// 所以课上会讲怎么转化，然后就是讲测试链接里的题目
// 1 <= N <= 10^5
// 1 <= arr[i] <= 10^5
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的所有代码，并把主类名改成"Main"
// 可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_NumberOfSubsetGcdEqualK {

	public static int MAXN = 100001;

	public static int mod = 1000000007;

	public static long[] dp = new long[MAXN];

	public static long[] cnt = new long[MAXN];

	public static long[] pow2 = new long[MAXN];

	public static int n, v;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			pow2[0] = 1;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				v = (int) in.nval;
				cnt[v]++;
				pow2[i] = (pow2[i - 1] * 2) % mod;
			}
			for (int i = MAXN - 1; i >= 1; i--) {
				long counts = 0;
				for (int j = i; j < MAXN; j += i) {
					counts = (counts + cnt[j]) % mod;
				}
				dp[i] = (pow2[(int) counts] - 1 + mod) % mod;
				for (int j = 2 * i; j < MAXN; j += i) {
					dp[i] = (dp[i] - dp[j] + mod) % mod;
				}
			}
			out.println(dp[1]);
			out.flush();
		}
	}

}
package class_2023_02_1_week;

// 一共有 4 种硬币。面值分别为c1、c2、c3、c4
// 一开始就给定了，就是这些面值不再改变
// 某人去商店买东西，去了 n 次，
// 每次都是一次查询：
// 这个人每次代的每种硬币的数量不一样，都记录在d数组中
// 1) d[i]表示他带了的i种面值的数量
// 2) 他要购买s价值的东西
// 返回每次有多少种购买方法
// 1 <= c1、c2、c3、c4、d[i]、s <= 10^5
// 1 <= n <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P1450
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_FourKindsPaperQueryWays {

	public static int limit = 100000;
	public static long[] dp = new long[limit + 1];
	public static int[] c = new int[4];
	public static int[] d = new int[4];
	public static int n, s;

	public static void init() {
		dp[0] = 1;
		for (int i = 0; i <= 3; i++) {
			for (int j = c[i]; j <= limit; j++) {
				dp[j] += dp[j - c[i]];
			}
		}
	}

	public static long query() {
		long minus = 0;
		for (int status = 1; status <= 15; status++) {
			long t = s;
			int sign = -1;
			for (int j = 0; j <= 3; j++) {
				if (((status >> j) & 1) == 1) {
					t -= c[j] * (d[j] + 1);
					sign = -sign;
				}
			}
			if (t >= 0) {
				minus += dp[(int) t] * sign;
			}
		}
		return dp[s] - minus;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			c[0] = (int) in.nval;
			in.nextToken();
			c[1] = (int) in.nval;
			in.nextToken();
			c[2] = (int) in.nval;
			in.nextToken();
			c[3] = (int) in.nval;
			in.nextToken();
			n = (int) in.nval;
			init();
			for (int i = 0; i < n; i++) {
				in.nextToken();
				d[0] = (int) in.nval;
				in.nextToken();
				d[1] = (int) in.nval;
				in.nextToken();
				d[2] = (int) in.nval;
				in.nextToken();
				d[3] = (int) in.nval;
				in.nextToken();
				s = (int) in.nval;
				out.println(query());
				out.flush();
			}
		}
	}

}

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
	// 无限制情况下的方法数，得到dp
	// 可以用这个dp，去求解，2元 交上 3元的违规方法
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
		// 违规方法数！
		long minus = 0;
		//  d c b a
		//  0 0 0 1
		//  0 1 1 0
		for (int status = 1; status <= 15; status++) {
			// 100元，2元 4张  3元 9张
			// 100 - 2 * 5 - 3 * 10 => 60
			// 60元无限制的情况下，方法数是多少
			// 就是！100元，在2元面值和3元面值同时违规的情况下，违规方法数
			long t = s;
			int sign = -1;
			for (int j = 0; j <= 3; j++) {
				// j 0号货币有没有？
				//   1号货币有没有？
			    //   2号货币有没有？
				//   3号货币有没有？
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
			// 先给每一种面值
			// 存好，可能有多组查询，但是有哪些面值是不变的
			// 面值的种数也不变，4种面值
			c[0] = (int) in.nval;
			in.nextToken();
			c[1] = (int) in.nval;
			in.nextToken();
			c[2] = (int) in.nval;
			in.nextToken();
			c[3] = (int) in.nval;
			in.nextToken();
			// 有多少组查询
			n = (int) in.nval;
			init();
			for (int i = 0; i < n; i++) {
				// 一组查询
				// a 面值 给你多少张
				// b 面值 给你多少张
				// c 面值 给你多少张
				// d 面值 给你多少张
				in.nextToken();
				d[0] = (int) in.nval;
				in.nextToken();
				d[1] = (int) in.nval;
				in.nextToken();
				d[2] = (int) in.nval;
				in.nextToken();
				d[3] = (int) in.nval;
				in.nextToken();
				// 花钱总数
				// 不超过10的5次方的
				s = (int) in.nval;
				out.println(query());
				out.flush();
			}
		}
	}

}

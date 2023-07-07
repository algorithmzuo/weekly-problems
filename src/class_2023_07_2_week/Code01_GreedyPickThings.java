package class_2023_07_2_week;

// 阿里巴巴走进了装满宝藏的藏宝洞。藏宝洞里面有N堆金币
// 第i堆金币的总重量和总价值分别是m[i]、v[i]
// 阿里巴巴有一个承重量为T的背包，但并不一定有办法将全部的金币都装进去
// 他想装走尽可能多价值的金币
// 所有金币都可以随意分割，分割完的金币重量价值比（也就是单位价格）不变
// 请问阿里巴巴最多可以拿走多少价值的金币？
// 测试链接 : https://www.luogu.com.cn/problem/P2240
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_GreedyPickThings {

	public static int MAXN = 101;

	public static int[][] mv = new int[MAXN][2];

	public static int n, t;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			t = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				mv[i][0] = (int) in.nval;
				in.nextToken();
				mv[i][1] = (int) in.nval;
			}
			Arrays.sort(mv, 0, n, (a, b) -> (b[1] * a[0]) - (a[1] * b[0]));
			double ans = 0;
			int i = 0;
			int used = 0;
			for (; i < n && used + mv[i][0] <= t; i++) {
				used += mv[i][0];
				ans += mv[i][1];
			}
			if (i < n) {
				ans += (double) mv[i][1] * (t - used) / (double) mv[i][0];
			}
			out.println(String.format("%.2f", ans));
			out.flush();
		}
	}

}

package class_2023_07_2_week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

// 测试链接 : https://www.luogu.com.cn/problem/P2240
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

package class_2023_04_1_week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

// 来自华为社招笔试
// 测试链接 : https://www.luogu.com.cn/problem/P1052
public class Code04_FrogHateStoneMinTimes {

	public static int MAXN = 101;

	public static int MAXL = 100001;

	public static int[] stone = new int[MAXN];

	public static int[] far = new int[MAXN];

	public static int[] dp = new int[MAXL];

	public static boolean[] visited = new boolean[MAXL];

	public static int l, s, t, m, base;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			l = (int) in.nval;
			in.nextToken();
			s = (int) in.nval;
			in.nextToken();
			t = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			base = s * t;
			for (int i = 1; i <= m; ++i) {
				in.nextToken();
				stone[i] = (int) in.nval;
			}
			Arrays.sort(stone, 1, m + 1);
			if (s == t) {
				int ans = 0;
				for (int i = 1; i <= m; ++i) {
					if (stone[i] % s == 0) {
						ans++;
					}
				}
				out.println(ans);
			} else {
				for (int i = 1; i <= m; i++) {
					int distance = Math.min(stone[i] - stone[i - 1], base);
					far[i] = far[i - 1] + distance;
					visited[far[i]] = true;
				}
				l = far[m] + base;
				Arrays.fill(dp, 1, l + 1, MAXN);
				dp[0] = 0;
				for (int i = 1; i <= l; i++) {
					for (int j = s; j <= t; j++) {
						if (i - j >= 0) {
							if (visited[i]) {
								dp[i] = Math.min(dp[i - j] + 1, dp[i]);
							} else {
								dp[i] = Math.min(dp[i - j], dp[i]);
							}
						}
					}
				}
				int ans = MAXN;
				for (int i = far[m]; i <= l; i++) {
					ans = Math.min(ans, dp[i]);
				}
				out.println(ans);
			}
			out.flush();
		}
	}

}

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

	public static int MAXK = 201;

	public static int[] arr = new int[MAXN];

	public static int[] distance = new int[MAXN];

	public static int[] dp = new int[MAXL];

	public static boolean[] stone = new boolean[MAXL];

	public static boolean[] reach = new boolean[201];

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
			for (int i = 1; i <= m; ++i) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			if (s == t) {
				int ans = 0;
				for (int i = 1; i <= m; ++i) {
					if (arr[i] % s == 0) {
						ans++;
					}
				}
				out.println(ans);
			} else {
				Arrays.sort(arr, 1, m + 1);
				base = reduce(s, t);
				for (int i = 1; i <= m; i++) {
					distance[i] = distance[i - 1] + Math.min(arr[i] - arr[i - 1], base);
					stone[distance[i]] = true;
				}
				l = distance[m] + base;
				Arrays.fill(dp, 1, l + 1, MAXN);
				for (int i = 1; i <= l; i++) {
					for (int j = s; j <= t; j++) {
						if (i - j >= 0) {
							if (stone[i]) {
								dp[i] = Math.min(dp[i - j] + 1, dp[i]);
							} else {
								dp[i] = Math.min(dp[i - j], dp[i]);
							}
						}
					}
				}
				int ans = MAXN;
				for (int i = distance[m]; i <= l; i++) {
					ans = Math.min(ans, dp[i]);
				}
				out.println(ans);
			}
			out.flush();
		}
	}

	public static int reduce(int s, int t) {
		Arrays.fill(reach, false);
		int cnt = 0;
		int ans = 0;
		for (int i = 0; i < MAXK; i++) {
			for (int j = i + s; j < Math.min(i + t + 1, MAXK); j++) {
				reach[j] = true;
			}
			if (!reach[i]) {
				cnt = 0;
			} else {
				cnt++;
			}
			if (cnt == t) {
				ans = i;
				break;
			}
		}
		return ans;
	}

}

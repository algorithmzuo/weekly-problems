package class_2023_04_2_week;

// 来自华为社招笔试
// 测试链接 : https://www.luogu.com.cn/problem/P1052
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
import java.util.Arrays;


public class Code04_FrogHateStoneMinTimes {

	public static int MAXN = 101;

	public static int MAXL = 100001;

	public static int MAXK = 201;

	public static int[] arr = new int[MAXN];

	public static int[] distance = new int[MAXN];

	public static int[] dp = new int[MAXL];

	public static boolean[] stone = new boolean[MAXL];

	public static boolean[] reach = new boolean[201];

	public static int l, s, t, m, cut;

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
				cut = reduce(s, t);
				for (int i = 1; i <= m; i++) {
					distance[i] = distance[i - 1] + Math.min(arr[i] - arr[i - 1], cut);
					stone[distance[i]] = true;
				}
				l = distance[m] + cut;
				Arrays.fill(dp, 1, l + 1, MAXN);
				for (int i = 1; i <= l; i++) {
					for (int j = Math.max(i - t, 0); j <= i - s; j++) {
						dp[i] = Math.min(dp[i], dp[j] + (stone[i] ? 1 : 0));
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

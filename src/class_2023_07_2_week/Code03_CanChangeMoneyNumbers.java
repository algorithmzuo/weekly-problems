package class_2023_07_2_week;

// 测试链接 : http://poj.org/problem?id=1742

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_CanChangeMoneyNumbers {

	public static int MAXN = 101;

	public static int[] val = new int[MAXN];

	public static int[] cnt = new int[MAXN];

	public static int MAXM = 100001;

	public static boolean[] dp = new boolean[MAXM];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			if (n != 0 || m != 0) {
				for (int i = 1; i <= n; i++) {
					in.nextToken();
					val[i] = (int) in.nval;
				}
				for (int i = 1; i <= n; i++) {
					in.nextToken();
					cnt[i] = (int) in.nval;
				}
				out.println(compute());
			} else {
				out.flush();
			}
		}
	}

	public static int compute() {
		Arrays.fill(dp, 1, m + 1, false);
		dp[0] = true;
		for (int i = 1; i <= n; i++) {
			if (cnt[i] == 1) {
				for (int j = m; j >= val[i]; j--) {
					if (dp[j - val[i]]) {
						dp[j] = true;
					}
				}
			} else if (val[i] * cnt[i] > m) {
				for (int j = val[i]; j <= m; j++) {
					if (dp[j - val[i]]) {
						dp[j] = true;
					}
				}
			} else {
				for (int mod = 0; mod < val[i]; mod++) {
					int trueCnt = 0;
					for (int j = m - mod, size = 0; j >= 0 && size <= cnt[i]; j -= val[i], size++) {
						trueCnt += dp[j] ? 1 : 0;
					}
					for (int j = m - mod, l = j - val[i] * (cnt[i] + 1); j >= 1; j -= val[i], l -= val[i]) {
						if (dp[j]) {
							trueCnt--;
						} else {
							if (trueCnt != 0) {
								dp[j] = true;
							}
						}
						if (l >= 0) {
							trueCnt += dp[l] ? 1 : 0;
						}
					}
				}
			}
		}
		int ans = 0;
		for (int i = 1; i <= m; i++) {
			if (dp[i]) {
				ans++;
			}
		}
		return ans;
	}

}

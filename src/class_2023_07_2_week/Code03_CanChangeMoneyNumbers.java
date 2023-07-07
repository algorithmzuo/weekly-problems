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

	public static boolean[] ok = new boolean[MAXM];

	public static boolean[] window = new boolean[MAXM];

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
				out.println(dp());
				out.flush();
			}
		}
	}

	public static int dp() {
		Arrays.fill(ok, 1, m + 1, false);
		ok[0] = true;
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			if (cnt[i] == 1) {
				for (int j = m; j >= val[i]; j--) {
					if (!ok[j] && ok[j - val[i]]) {
						ok[j] = true;
						ans++;
					}
				}
			} else if (val[i] * cnt[i] > m) {
				for (int j = val[i]; j <= m; j++) {
					if (!ok[j] && ok[j - val[i]]) {
						ok[j] = true;
						ans++;
					}
				}
			} else {
				for (int mod = 0; mod < val[i]; mod++) {
					int trueCnt = 0;
					int l = 0;
					int r = 0;
					for (int j = mod; j <= m; j += val[i]) {
						if (r - l == cnt[i] + 1) {
							trueCnt -= window[l++] ? 1 : 0;
						}
						window[r++] = ok[j];
						trueCnt += ok[j] ? 1 : 0;
						if (!ok[j] && trueCnt != 0) {
							ok[j] = true;
							ans++;
						}
					}
				}
			}
		}
		return ans;
	}

}

package class_2023_07_4_week;

// 测试链接 : www.luogu.com.cn/problem/P1757

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_TeamDP {

	public static int MAXN = 1001;

	public static int MAXM = 1001;

	public static int[][] arr = new int[MAXN][3];

	public static int[] dp = new int[MAXM];

	public static int m, n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			m = (int) in.nval;
			in.nextToken();
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i][0] = (int) in.nval;
				in.nextToken();
				arr[i][1] = (int) in.nval;
				in.nextToken();
				arr[i][2] = (int) in.nval;
			}
			Arrays.sort(arr, 0, n, (a, b) -> a[2] - b[2]);
			Arrays.fill(dp, 0, m + 1, 0);
			out.println(compute());
			out.flush();
		}
	}

	public static int compute() {
		for (int start = 0, end = 1; start < n;) {
			while (end < n && arr[end][2] == arr[start][2]) {
				end++;
			}
			// [start...end)是一个组的物品
			for (int r = m; r >= 0; r--) {
				for (int i = start; i < end; i++) {
					if (r >= arr[i][0]) {
						dp[r] = Math.max(dp[r], arr[i][1] + dp[r - arr[i][0]]);
					}
				}
			}
			start = end++;
		}
		return dp[m];
	}

}

package class_2023_06_4_week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

// 除了这个题之外，再讲一下华为OD的扩展
// 测试链接 : https://www.luogu.com.cn/problem/P1809
public class Code01_CrossRiver {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] dp = new int[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			int ans = minCost();
			out.println(ans);
			out.flush();
		}

	}

	public static int minCost() {
		Arrays.sort(arr, 0, n);
		if (n == 1) {
			return arr[0];
		} else if (n == 2) {
			return arr[1];
		} else if (n == 3) {
			return arr[0] + arr[1] + arr[2];
		} else {
			dp[0] = arr[0];
			dp[1] = arr[1];
			dp[2] = arr[0] + arr[1] + arr[2];
			for (int i = 3; i < n; i++) {
				dp[i] = Math.min(dp[i - 2] + arr[0] + 2 * arr[1] + arr[i], dp[i - 1] + arr[i] + arr[0]);
			}
			return dp[n - 1];
		}
	}

}

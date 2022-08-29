package class_2022_09_3_week;

// 本题测试链接 : https://zoj.pintia.cn/problem-sets/91827364500/problems/91827367873
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交如下方法，把主类名改成Main，可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_DreamCity {

	public static int[][] tree = new int[250][2];

	public static int[][] dp = new int[250][250];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int testCases = (int) in.nval;
		for (int i = 0; i < testCases; i++) {
			in.nextToken();
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			for (int j = 0; j < n; j++) {
				in.nextToken();
				tree[j][0] = (int) in.nval;
			}
			for (int j = 0; j < n; j++) {
				in.nextToken();
				tree[j][1] = (int) in.nval;
			}
			out.println(maxWeight(n, m));
			out.flush();
		}
	}

	public static int maxWeight(int n, int m) {
		Arrays.sort(tree, 0, n, (o1, o2) -> o1[1] - o2[1]);
		dp[0][0] = tree[0][0];
		for (int i = 1; i < n; i++) {
			dp[i][0] = Math.max(dp[i - 1][0], tree[i][0]);
		}
		for (int j = 1; j < m; j++) {
			dp[0][j] = dp[0][j - 1] + tree[0][1];
		}
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + tree[i][0] + tree[i][1] * j);
			}
		}
		return dp[n - 1][m - 1];
	}

}
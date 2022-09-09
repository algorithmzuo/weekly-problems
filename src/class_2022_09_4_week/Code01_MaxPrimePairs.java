package class_2022_09_4_week;

// 来自华为
// 若两个正整数的和为素数，则这两个正整数称之为"素数伴侣"
// 给定N(偶数)个正整数中挑选出若干对，组成"素数伴侣"
// 例如有4个正整数：2，5，6，13，
// 如果将5和6分为一组的话，只能得到一组"素数伴侣"
// 如果将2和5、6和13编组，将得到两组"素数伴侣"
// 这是得到"素数伴侣"最多的划分方案
// 输入:
// 有一个正偶数 n ，表示待挑选的自然数的个数。后面给出 n 个具体的数字。
// 输出:
// 输出一个整数 K ，表示最多能找出几对"素数伴侣"
// 数据范围： 1 <= n <= 100, 2 <= val <= 30000
// 测试链接 : https://www.nowcoder.com/practice/b9eae162e02f4f928eac37d7699b352e
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.io.IOException;

public class Code01_MaxPrimePairs {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			int[][] graph = matrix(arr, n);
			out.println(km(graph) / 2);
			out.flush();
		}

	}

	public static int[][] matrix(int[] arr, int n) {
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				ans[i][j] = isPrime(arr[i] + arr[j]) ? 1 : 0;
			}
		}
		return ans;
	}

	public static boolean isPrime(int num) {
		int sqrt = (int) Math.sqrt(num);
		for (int i = 2; i <= sqrt; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static int km(int[][] graph) {
		int N = graph.length;
		int[] match = new int[N];
		int[] lx = new int[N];
		int[] ly = new int[N];
		boolean[] x = new boolean[N];
		boolean[] y = new boolean[N];
		int[] slack = new int[N];
		int invalid = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			match[i] = -1;
			lx[i] = -invalid;
			for (int j = 0; j < N; j++) {
				lx[i] = Math.max(lx[i], graph[i][j]);
			}
			ly[i] = 0;
		}
		for (int from = 0; from < N; from++) {
			for (int i = 0; i < N; i++) {
				slack[i] = invalid;
			}
			Arrays.fill(x, false);
			Arrays.fill(y, false);
			while (!dfs(from, x, y, lx, ly, match, slack, graph)) {
				int d = invalid;
				for (int i = 0; i < N; i++) {
					if (!y[i] && slack[i] < d) {
						d = slack[i];
					}
				}
				for (int i = 0; i < N; i++) {
					if (x[i]) {
						lx[i] = lx[i] - d;
					}
					if (y[i]) {
						ly[i] = ly[i] + d;
					}
				}
				Arrays.fill(x, false);
				Arrays.fill(y, false);
			}
		}
		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans += (lx[i] + ly[i]);
		}
		return ans;
	}

	public static boolean dfs(int from, boolean[] x, boolean[] y, int[] lx, int[] ly, int[] match, int[] slack,
			int[][] map) {
		int N = map.length;
		x[from] = true;
		for (int to = 0; to < N; to++) {
			if (!y[to]) {
				int d = lx[from] + ly[to] - map[from][to];
				if (d != 0) {
					slack[to] = Math.min(slack[to], d);
				} else {
					y[to] = true;
					if (match[to] == -1 || dfs(match[to], x, y, lx, ly, match, slack, map)) {
						match[to] = from;
						return true;
					}
				}
			}
		}
		return false;
	}

}

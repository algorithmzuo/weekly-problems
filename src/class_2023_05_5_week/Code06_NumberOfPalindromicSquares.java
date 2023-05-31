package class_2023_05_5_week;

// 二维哈希的解法
// 二维哈希只适用于正方形的情况
// 如果想支持普通矩阵，需要更复杂度的过程，这里不做展开
// 来自学员问题
// 如果一个正方形矩阵上下对称并且左右对称，对称的意思是互为镜像
// 那么称这个正方形矩阵叫做神奇矩阵
// 比如 :
// 1 5 5 1
// 6 3 3 6
// 6 3 3 6
// 1 5 5 1
// 这个正方形矩阵就是神奇矩阵
// 给定一个大矩阵n*m，返回其中神奇矩阵的数目
// 1 <= n,m <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P2601
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"
// 这个题所有用户的java提交里，我是唯一通过的
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_NumberOfPalindromicSquares {

	public static int MAXN = 1001;

	public static int baser = 491;

	public static int basec = 499;

	public static long[] powr = new long[MAXN];

	public static long[] powc = new long[MAXN];

	static {
		powr[0] = 1;
		powc[0] = 1;
		for (int i = 1; i < MAXN; i++) {
			powr[i] = (powr[i - 1] * baser);
		}
		for (int i = 1; i < MAXN; i++) {
			powc[i] = (powc[i - 1] * basec);
		}
	}

	public static int[][] arr1 = new int[MAXN][MAXN];

	public static int[][] arr2 = new int[MAXN][MAXN];

	public static int[][] arr3 = new int[MAXN][MAXN];

	public static long[][] sum1 = new long[MAXN][MAXN];

	public static long[][] sum2 = new long[MAXN][MAXN];

	public static long[][] sum3 = new long[MAXN][MAXN];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= m; j++) {
					in.nextToken();
					arr1[i][j] = (int) in.nval;
					arr2[n - i + 1][j] = arr1[i][j];
					arr3[i][m - j + 1] = arr1[i][j];
				}
			}
			buildHash(arr1, sum1);
			buildHash(arr2, sum2);
			buildHash(arr3, sum3);
			out.println(number());
			out.flush();
		}
	}

	public static void buildHash(int[][] arr, long[][] sum) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				sum[i][j] = sum[i][j - 1] * basec + arr[i][j];
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				sum[i][j] += sum[i - 1][j] * baser;
			}
		}
	}

	public static long hash(long[][] sum, int a, int b, int c, int d) {
		long ans = sum[c][d] - sum[a - 1][d] * powr[c - a + 1] - sum[c][b - 1] * powc[d - b + 1]
				+ sum[a - 1][b - 1] * powr[d - b + 1] * powc[c - a + 1];
		return ans;
	}

	public static int number() {
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				int l = 1;
				int r = Math.min(Math.min(i, n - i + 1), Math.min(j, m - j + 1));
				int m, find = 1;
				while (l <= r) {
					m = (l + r) / 2;
					if (ok(i - m + 1, j - m + 1, i + m - 1, j + m - 1)) {
						find = m;
						l = m + 1;
					} else {
						r = m - 1;
					}
				}
				ans += find;
			}
		}
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				int l = 1;
				int r = Math.min(Math.min(i, j), Math.min(n - i, m - j));
				int m, find = 0;
				while (l <= r) {
					m = (l + r) / 2;
					if (ok(i - m + 1, j - m + 1, i + m, j + m)) {
						find = m;
						l = m + 1;
					} else {
						r = m - 1;
					}
				}
				ans += find;
			}
		}
		return ans;
	}

	public static boolean ok(int a, int b, int c, int d) {
		if (a == c) {
			return true;
		}
		long h1 = hash(sum1, a, b, c, d);
		long h2 = hash(sum2, n - c + 1, b, n - a + 1, d);
		long h3 = hash(sum3, a, m - d + 1, c, m - b + 1);
		return h1 == h2 && h1 == h3;
	}

}
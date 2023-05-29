package class_2023_05_5_week;

// 二维哈希的做法，二维哈希似乎只能在正方形上适用，我做了挺多实验
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

public class Code07_NumberOfPalindromicSquares {

	public static int MAXN = 1001;

	public static int[][] arr = new int[MAXN << 1][MAXN << 1];

	public static long[] powr = new long[MAXN << 1];

	public static long[] powc = new long[MAXN << 1];

	public static long[][] sum = new long[MAXN << 1][MAXN << 1];

	public static int baser = 491;

	public static int basec = 499;

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
					arr[i][j] = (int) in.nval;
				}
			}
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= m; j++) {
					arr[i + n][j] = arr[n - i + 1][j];
					arr[i][j + m] = arr[i][m - j + 1];
				}
			}
			buildHash();
			out.println(number());
			out.flush();
		}
	}

	public static void buildHash() {
		powr[0] = 1;
		powc[0] = 1;
		for (int i = 1; i <= n << 1; i++) {
			powr[i] = (powr[i - 1] * baser);
		}
		for (int i = 1; i <= m << 1; i++) {
			powc[i] = (powc[i - 1] * basec);
		}
		for (int i = 1; i <= n << 1; i++) {
			for (int j = 1; j <= m << 1; j++) {
				sum[i][j] = sum[i][j - 1] * basec + arr[i][j];
			}
		}
		for (int i = 1; i <= n << 1; i++) {
			for (int j = 1; j <= m << 1; j++) {
				sum[i][j] += sum[i - 1][j] * baser;
			}
		}
	}

	public static long hash(int a, int b, int c, int d) {
		return sum[c][d] - sum[a - 1][d] * powr[c - a + 1] - sum[c][b - 1] * powc[d - b + 1]
				+ sum[a - 1][b - 1] * powr[d - b + 1] * powc[c - a + 1];
	}

	public static boolean ok(int a, int b, int c, int d) {
		return hash(a, b, c, d) == hash((n << 1) - c + 1, b, (n << 1) - a + 1, d)
				&& hash(a, b, c, d) == hash(a, (m << 1) - d + 1, c, (m << 1) - b + 1);
	}

	public static int number() {
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				int l = 0, r = Math.max(n, m);
				while (l < r) {
					int mid = (l + r + 1) >> 1;
					if (i - mid >= 1 && j - mid >= 1 && i + mid <= n && j + mid <= m
							&& ok(i - mid, j - mid, i + mid, j + mid)) {
						l = mid;
					} else {
						r = mid - 1;
					}
				}
				ans += l + 1;
				l = 0;
				r = Math.max(n, m);
				while (l < r) {
					int mid = (l + r + 1) >> 1;
					if (i - mid + 1 >= 1 && j - mid + 1 >= 1 && i + mid <= n && j + mid <= m
							&& ok(i - mid + 1, j - mid + 1, i + mid, j + mid)) {
						l = mid;
					} else {
						r = mid - 1;
					}
				}
				ans += l;
			}
		}
		return ans;
	}

}
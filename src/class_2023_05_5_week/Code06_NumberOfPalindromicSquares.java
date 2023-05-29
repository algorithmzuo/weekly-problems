package class_2023_05_5_week;

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
// 如果提交不是都通过，就多提交两次
// 洛谷对java不友好，有可以都通过的时候
// 这个题所有用户的java提交里，我是唯一通过的
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code06_NumberOfPalindromicSquares {

	public static int MAXN = 1001;

	public static int[] log2 = new int[(MAXN << 1 | 1) + 1];

	static {
		for (int k = 0, j = 1; j <= (MAXN << 1 | 1); j++) {
			if (1 << (k + 1) <= j) {
				k++;
			}
			log2[j] = k;
		}
	}

	public static int[][] arr = new int[MAXN << 1 | 1][MAXN << 1 | 1];

	public static int[][] rp = new int[MAXN << 1 | 1][MAXN << 1 | 1];

	public static int[][] cp = new int[MAXN << 1 | 1][MAXN << 1 | 1];

	public static int[][] enlarge = new int[MAXN << 1 | 1][MAXN << 1 | 1];

	public static int[][] rmq = new int[MAXN << 1 | 1][13];

	public static int[] s = new int[MAXN << 1 | 1];

	public static int[] p = new int[MAXN << 1 | 1];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 0, r = 1; i < n; i++, r += 2) {
				for (int j = 0, c = 1; j < m; j++, c += 2) {
					in.nextToken();
					arr[r][c] = (int) in.nval;
				}
			}
			n = n * 2 + 1;
			m = m * 2 + 1;
			out.println(number());
			out.flush();
		}
	}

	public static int number() {
		for (int row = 0; row < n; row++) {
			manacher(row, 0, 0, 1);
		}
		for (int col = 0; col < m; col++) {
			manacher(0, col, 1, 0);
		}
		for (int row = 1; row < n - 1; row++) {
			rowRmq(row);
			for (int col = 1; col < m - 1; col++) {
				int l = 1;
				int r = Math.min(Math.min(row + 1, n - row), Math.min(col + 1, m - col));
				int m, find = 1;
				while (l <= r) {
					m = (l + r) / 2;
					if (query(col - m + 1, col + m - 1) >= m) {
						find = m;
						l = m + 1;
					} else {
						r = m - 1;
					}
				}
				enlarge[row][col] = find;
			}
		}
		for (int col = 1; col < m - 1; col++) {
			colRmq(col);
			for (int row = 1; row < n - 1; row++) {
				int l = 1;
				int r = Math.min(Math.min(row + 1, n - row), Math.min(col + 1, m - col));
				int m, find = 1;
				while (l <= r) {
					m = (l + r) / 2;
					if (query(row - m + 1, row + m - 1) >= m) {
						find = m;
						l = m + 1;
					} else {
						r = m - 1;
					}
				}
				enlarge[row][col] = Math.min(enlarge[row][col], find);
			}
		}
		int ans = 0;
		for (int row = 1; row < n - 1; row += 2) {
			for (int col = 1; col < m - 1; col += 2) {
				ans += enlarge[row][col] / 2;
			}
		}
		for (int row = 2; row < n - 1; row += 2) {
			for (int col = 2; col < m - 1; col += 2) {
				ans += (enlarge[row][col] - 1) / 2;
			}
		}
		return ans;
	}

	public static void manacher(int row, int col, int radd, int cadd) {
		int limit = 0;
		for (int r = row, c = col; r < n && c < m; r += radd, c += cadd) {
			s[limit++] = arr[r][c];
		}
		Arrays.fill(p, 0, limit, 0);
		int C = -1;
		int R = -1;
		for (int i = 0; i < limit; i++) {
			p[i] = R > i ? Math.min(p[2 * C - i], R - i) : 1;
			while (i + p[i] < limit && i - p[i] > -1) {
				if (s[i + p[i]] == s[i - p[i]])
					p[i]++;
				else {
					break;
				}
			}
			if (i + p[i] > R) {
				R = i + p[i];
				C = i;
			}
		}
		int[][] where = cadd == 1 ? rp : cp;
		for (int i = 0, r = row, c = col; i < limit; i++, r += radd, c += cadd) {
			where[r][c] = p[i];
		}
	}

	public static void rowRmq(int row) {
		for (int i = 0; i < m; i++) {
			rmq[i][0] = cp[row][i];
		}
		for (int j = 1; (1 << j) <= m; j++) {
			for (int i = 0; i + (1 << j) - 1 < m; i++) {
				rmq[i][j] = Math.min(rmq[i][j - 1], rmq[i + (1 << (j - 1))][j - 1]);
			}
		}
	}

	public static void colRmq(int col) {
		for (int i = 0; i < n; i++) {
			rmq[i][0] = rp[i][col];
		}
		for (int j = 1; (1 << j) <= n; j++) {
			for (int i = 0; i + (1 << j) - 1 < n; i++) {
				rmq[i][j] = Math.min(rmq[i][j - 1], rmq[i + (1 << (j - 1))][j - 1]);
			}
		}
	}

	public static int query(int l, int r) {
		int k = log2[r - l + 1];
		return Math.min(rmq[l][k], rmq[r - (1 << k) + 1][k]);
	}

}
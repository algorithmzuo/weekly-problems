package class_2023_05_5_week;

// 如果n = 1，打印
// 1***
// 如果n = 2，打印
//         1***
// 3***    2***
// 如果n = 3，打印
//                 1***
//         3***    2***
// 4***    5***    6***
// 如果n = 4，打印
//                         1***
//                 3***    2***
//         4***    5***    6***
// 10**    9***    8***    7***
// 输入一个数n，表示有多少行，从1开始输出，
// 奇数行输出奇数个数，奇数行正序，偶数行输出偶数个数，偶数行逆序
// 每个数后面加*补满四位，中间空4个，第n行顶格输出
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

// 直接在插件里运行
// 根据约定，不要让n超过140，
// 因为n=140时，最后的数字是9870
// 如果n=141，那样最后的数字是10011，这样就会超过4位
// 而超过4位之后的样子你并没有说，所以n最大是140，不要超过
public class Code03_PrintZigZagWithStar {

	public static int MAXN = 100001;

	public static char[] space = new char[MAXN];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		// 提交时，把这一句删掉
		// 就是下面这一句 : System.out.println("提醒，请输入n : ")
		// 这是为了给你测试才写的，提交时候删掉这一句提醒
		System.out.println("提醒，请输入n : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			m = n * 8;
			Arrays.fill(space, 0, m, ' ');
			boolean from = true;
			for (int i = 1, j = 1; i <= n; j += i, i++) {
				fill(from, j, i);
				for (int k = 0; k < m - 4; k++) {
					out.print(space[k]);
				}
				out.println();
				from = !from;
			}
			out.flush();
		}
	}

	public static void fill(boolean from, int start, int number) {
		if (from) {
			for (int i = m - number * 8, j = 1; j <= number; i += 8, start++, j++) {
				insert(start, i);
			}
		} else {
			for (int i = m - 8, j = 1; j <= number; i -= 8, start++, j++) {
				insert(start, i);
			}
		}
	}

	public static void insert(int cur, int i) {
		int end = i + 4;
		int bit = cur > 999 ? 4 : (cur > 99 ? 3 : (cur > 9) ? 2 : 1);
		int offset = bit == 4 ? 1000 : (bit == 3 ? 100 : (bit == 2 ? 10 : 1));
		while (offset > 0) {
			space[i++] = (char) (((cur / offset) % 10) + '0');
			offset /= 10;
		}
		while (i < end) {
			space[i++] = '*';
		}
	}

}
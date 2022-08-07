package class_2022_07_2_week;

// 本题测试链接 : https://www.luogu.com.cn/problem/P5490
// 提交以下代码，并把主类名改成"Main"
// 可以直接通过
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_LineSweepAlgorithm2 {

	public static int maxn = 300001;
	public static long[][] arr = new long[maxn][4];
	public static long[] orderedY = new long[maxn];
	public static long[] cover = new long[maxn << 2];
	public static long[] realLength = new long[maxn << 2];
	public static long[] left = new long[maxn << 2];
	public static long[] right = new long[maxn << 2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				int x1 = (int) in.nval;
				in.nextToken();
				int y1 = (int) in.nval;
				in.nextToken();
				int x2 = (int) in.nval;
				in.nextToken();
				int y2 = (int) in.nval;
				orderedY[i] = y1;
				orderedY[i + n] = y2;
				arr[i][0] = x1;
				arr[i][1] = y1;
				arr[i][2] = y2;
				arr[i][3] = 1;
				arr[i + n][0] = x2;
				arr[i + n][1] = y1;
				arr[i + n][2] = y2;
				arr[i + n][3] = -1;
			}
			out.println(coverArea(n << 1));
			out.flush();
		}
	}

	public static long coverArea(int n) {
		Arrays.sort(orderedY, 1, n + 1);
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[0] <= b[0] ? -1 : 1);
		build(1, n, 1);
		long preX = 0;
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += realLength[1] * (arr[i][0] - preX);
			preX = arr[i][0];
			add(arr[i][1], arr[i][2], (int) arr[i][3], 1);
		}
		return ans;
	}

	private static void build(int l, int r, int i) {
		if (r - l > 1) {
			int m = (l + r) >> 1;
			build(l, m, i << 1);
			build(m, r, (i << 1) | 1);
		}
		left[i] = orderedY[l];
		right[i] = orderedY[r];
	}

	private static void add(long L, long R, long C, int i) {
		long l = left[i];
		long r = right[i];
		if (L <= l && R >= r) {
			cover[i] += C;
		} else {
			if (L < right[i << 1]) {
				add(L, R, C, i << 1);
			}
			if (R > left[(i << 1) | 1]) {
				add(L, R, C, (i << 1) | 1);
			}
		}
		pushUp(i);
	}

	public static void pushUp(int i) {
		if (cover[i] > 0) {
			realLength[i] = right[i] - left[i];
		} else {
			realLength[i] = realLength[i << 1] + realLength[(i << 1) | 1];
		}
	}

}
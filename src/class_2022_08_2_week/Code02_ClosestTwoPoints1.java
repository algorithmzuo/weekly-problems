package class_2022_08_2_week;

// 测试链接 : https://www.luogu.com.cn/problem/P1429
// 提交如下代码，把主类名改成Main，可以直接通过
// T(N) = 2*T(N/2) + O(N*logN)
// 这个表达式的时间复杂度是O(N*(logN的平方))
// 复杂度证明 : https://math.stackexchange.com/questions/159720/
// 网上大部分的帖子，答案都是这个复杂度
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_ClosestTwoPoints1 {

	public static int N = 200001;

	public static Point[] points = new Point[N];

	public static Point[] deals = new Point[N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			if (n == 0) {
				break;
			}
			for (int i = 0; i < n; i++) {
				in.nextToken();
				double x = (double) in.nval;
				in.nextToken();
				double y = (double) in.nval;
				points[i] = new Point(x, y);
			}
			Arrays.sort(points, 0, n, (a, b) -> a.x <= b.x ? -1 : 1);
			double ans = nearest(0, n - 1);
			out.println(String.format("%.4f", ans));
			out.flush();
		}
	}

	public static class Point {
		public double x;
		public double y;

		public Point(double a, double b) {
			x = a;
			y = b;
		}
	}

	public static double nearest(int left, int right) {
		double ans = Double.MAX_VALUE;
		if (left == right) {
			return ans;
		}
		if (left == right - 1) {
			return distance(points[left], points[right]);
		}
		int mid = (right + left) / 2;
		ans = Math.min(nearest(left, mid), nearest(mid + 1, right));
		int l = mid;
		int r = mid + 1;
		int size = 0;
		while (l >= left && points[mid].x - points[l].x <= ans) {
			deals[size++] = points[l--];
		}
		while (r <= right && points[r].x - points[mid].x <= ans) {
			deals[size++] = points[r++];
		}
		Arrays.sort(deals, 0, size, (a, b) -> a.y <= b.y ? -1 : 1);
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (deals[j].y - deals[i].y >= ans) {
					break;
				}
				ans = Math.min(ans, distance(deals[i], deals[j]));
			}
		}
		return ans;
	}

	public static double distance(Point a, Point b) {
		double x = a.x - b.x;
		double y = a.y - b.y;
		return Math.sqrt(x * x + y * y);
	}

}
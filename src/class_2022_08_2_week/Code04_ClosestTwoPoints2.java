package class_2022_08_2_week;

// 给定平面上n个点，x和y坐标都是整数
// 找出其中的一对点的距离，使得在这n个点的所有点对中，该距离为所有点对中最小的
// 返回最短距离，精确到小数点后面4位
// 测试链接 : https://www.luogu.com.cn/problem/P1429
// 提交以下所有代码，把主类名改成Main，可以直接通过
// T(N) = 2*T(N/2) + O(N)
// 这个表达式我们很熟悉，和归并排序一样的表达式
// 时间复杂度是O(N*logN)
// 需要用到归并排序的技巧才能做到
// 我们课上的独家
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_ClosestTwoPoints2 {

	public static int N = 200001;

	public static Point[] points = new Point[N];

	public static Point[] merge = new Point[N];

	public static Point[] deals = new Point[N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
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
		int mid = (right + left) / 2;
		double midX = points[mid].x;
		ans = Math.min(nearest(left, mid), nearest(mid + 1, right));
		int p1 = left;
		int p2 = mid + 1;
		int mergeSize = left;
		int dealSize = 0;
		while (p1 <= mid && p2 <= right) {
			merge[mergeSize] = points[p1].y <= points[p2].y ? points[p1++] : points[p2++];
			if (Math.abs(merge[mergeSize].x - midX) <= ans) {
				deals[dealSize++] = merge[mergeSize];
			}
			mergeSize++;
		}
		while (p1 <= mid) {
			merge[mergeSize] = points[p1++];
			if (Math.abs(merge[mergeSize].x - midX) <= ans) {
				deals[dealSize++] = merge[mergeSize];
			}
			mergeSize++;
		}
		while (p2 <= right) {
			merge[mergeSize] = points[p2++];
			if (Math.abs(merge[mergeSize].x - midX) <= ans) {
				deals[dealSize++] = merge[mergeSize];
			}
			mergeSize++;
		}
		for (int i = left; i <= right; i++) {
			points[i] = merge[i];
		}
		for (int i = 0; i < dealSize; i++) {
			for (int j = i + 1; j < dealSize; j++) {
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
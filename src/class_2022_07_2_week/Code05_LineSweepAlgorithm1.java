package class_2022_07_2_week;

import java.util.Arrays;

// 我们给出了一个（轴对齐的）二维矩形列表 rectangles 。 
// 对于 rectangle[i] = [x1, y1, x2, y2]，其中（x1，y1）是矩形 i 左下角的坐标
// (xi1, yi1) 是该矩形 左下角 的坐标， (xi2, yi2) 是该矩形 右上角 的坐标。
// 计算平面中所有 rectangles 所覆盖的 总面积 。
// 任何被两个或多个矩形覆盖的区域应只计算 一次 。
// 返回 总面积 。因为答案可能太大，返回 10^9 + 7 的 模 。
// 本题测试链接 : https://leetcode.cn/problems/rectangle-area-ii/
public class Code05_LineSweepAlgorithm1 {

	public static int rectangleArea(int[][] rectangles) {
		int n = rectangles.length;
		long[][] arr = new long[n << 1][4];
		long max = 0;
		for (int i = 0; i < n; i++) {
			int x1 = rectangles[i][0];
			int y1 = rectangles[i][1] + 1;
			int x2 = rectangles[i][2];
			int y2 = rectangles[i][3];
			arr[i][0] = x1;
			arr[i][1] = y1;
			arr[i][2] = y2;
			arr[i][3] = 1;
			arr[i + n][0] = x2;
			arr[i + n][1] = y1;
			arr[i + n][2] = y2;
			arr[i + n][3] = -1;
			max = Math.max(max, y2);
		}
		return coverArea(arr, n << 1, max);
	}

	public static int coverArea(long[][] arr, int n, long max) {
		Arrays.sort(arr, 0, n, (a, b) -> a[0] <= b[0] ? -1 : 1);
		DynamicSegmentTree dst = new DynamicSegmentTree(max);
		long preX = 0;
		long ans = 0;
		for (int i = 0; i < n; i++) {
			ans += dst.query() * (arr[i][0] - preX);
			ans %= 1000000007;
			preX = arr[i][0];
			dst.add(arr[i][1], arr[i][2], arr[i][3]);
		}
		return (int) ans;
	}

	public static class Node {
		public long cover;
		public long len;
		public Node left;
		public Node right;
	}

	public static class DynamicSegmentTree {
		public Node root;
		public long size;

		public DynamicSegmentTree(long max) {
			root = new Node();
			size = max;
		}

		public void add(long L, long R, long cover) {
			add(root, 1, size, L, R, cover);
		}

		private void add(Node cur, long l, long r, long L, long R, long cover) {
			if (L <= l && R >= r) {
				cur.cover += cover;
			} else {
				if (cur.left == null) {
					cur.left = new Node();
				}
				if (cur.right == null) {
					cur.right = new Node();
				}
				long m = l + ((r - l) >> 1);
				if (L <= m) {
					add(cur.left, l, m, L, R, cover);
				}
				if (R > m) {
					add(cur.right, m + 1, r, L, R, cover);
				}
			}
			pushUp(cur, l, r);
		}

		private void pushUp(Node cur, long l, long r) {
			if (cur.cover > 0) {
				cur.len = r - l + 1;
			} else {
				cur.len = (cur.left != null ? cur.left.len : 0) + (cur.right != null ? cur.right.len : 0);
			}
		}

		public long query() {
			return root.len;
		}

	}

}

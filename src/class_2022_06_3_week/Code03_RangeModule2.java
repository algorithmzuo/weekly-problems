package class_2022_06_3_week;

// 测试连接 : https://leetcode.com/problems/range-module/
// 动态开点线段树的解
public class Code03_RangeModule2 {

	// 只提交这个类，可以直接通过
	class RangeModule {

		DynamicSegmentTree dst;

		public RangeModule() {
			dst = new DynamicSegmentTree(1000000001);
		}

		public void addRange(int left, int right) {
			dst.update(left, right - 1, 1);
		}

		public boolean queryRange(int left, int right) {
			return dst.query(left, right - 1) == right - left;
		}

		public void removeRange(int left, int right) {
			dst.update(left, right - 1, 0);
		}

		class Node {
			public int sum;
			public int change;
			public boolean update;
			public Node left;
			public Node right;
		}

		class DynamicSegmentTree {
			public Node root;
			public int size;

			public DynamicSegmentTree(int max) {
				root = new Node();
				size = max;
			}

			private void pushUp(Node c) {
				c.sum = c.left.sum + c.right.sum;
			}

			private void pushDown(Node p, int ln, int rn) {
				if (p.left == null) {
					p.left = new Node();
				}
				if (p.right == null) {
					p.right = new Node();
				}
				if (p.update) {
					p.left.update = true;
					p.right.update = true;
					p.left.change = p.change;
					p.right.change = p.change;
					p.left.sum = p.change * ln;
					p.right.sum = p.change * rn;
					p.update = false;
				}
			}

			public void update(int s, int e, int v) {
				update(root, 1, size, s, e, v);
			}

			private void update(Node c, int l, int r, int s, int e, int v) {
				if (s <= l && r <= e) {
					c.update = true;
					c.change = v;
					c.sum = v * (r - l + 1);
				} else {
					int mid = (l + r) >> 1;
					pushDown(c, mid - l + 1, r - mid);
					if (s <= mid) {
						update(c.left, l, mid, s, e, v);
					}
					if (e > mid) {
						update(c.right, mid + 1, r, s, e, v);
					}
					pushUp(c);
				}
			}

			public int query(int s, int e) {
				return query(root, 1, size, s, e);
			}

			private int query(Node c, int l, int r, int s, int e) {
				if (s <= l && r <= e) {
					return c.sum;
				}
				int mid = (l + r) >> 1;
				pushDown(c, mid - l + 1, r - mid);
				int ans = 0;
				if (s <= mid) {
					ans += query(c.left, l, mid, s, e);
				}
				if (e > mid) {
					ans += query(c.right, mid + 1, r, s, e);
				}
				return ans;
			}

		}
	}

}

package class_2022_01_3_week;

public class Problem_0679_24Game3 {

	public static boolean judgePoint24(int[] nums) {
		SbtList<Num> arr = new SbtList<Num>();
		for (int i = 0; i < nums.length; i++) {
			arr.add(i, new Num(nums[i], 1));
		}
		return process(arr);
	}

	public static boolean process(SbtList<Num> arr) {
		if (arr.size() == 1) {
			return arr.get(0).up == 24 && arr.get(0).down == 1;
		}
		for (int i = 0; i < arr.size(); i++) {
			for (int j = i + 1; j < arr.size(); j++) {
				Num o1 = arr.get(i);
				Num o2 = arr.get(j);
				arr.remove(j);
				arr.remove(i);
				Num[] ops = { op(o1, o2, 1), 
						      op(o1, o2, 2), 
						      op(o2, o1, 2), 
						      op(o1, o2, 3), 
						      op(o1, o2, 4),
						      op(o2, o1, 4) };
				for (Num merge : ops) {
					if (merge != null) {
						arr.add(arr.size(), merge);
						if (process(arr)) {
							return true;
						}
						arr.remove(arr.size() - 1);
					}
				}
				arr.add(i, o1);
				arr.add(j, o2);
			}
		}
		return false;
	}

	public static class SBTNode<V> {
		public V value;
		public SBTNode<V> l;
		public SBTNode<V> r;
		public int size;

		public SBTNode(V v) {
			value = v;
			size = 1;
		}
	}

	public static class SbtList<V> {
		private SBTNode<V> root;

		private SBTNode<V> rightRotate(SBTNode<V> cur) {
			SBTNode<V> leftNode = cur.l;
			cur.l = leftNode.r;
			leftNode.r = cur;
			leftNode.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			return leftNode;
		}

		private SBTNode<V> leftRotate(SBTNode<V> cur) {
			SBTNode<V> rightNode = cur.r;
			cur.r = rightNode.l;
			rightNode.l = cur;
			rightNode.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			return rightNode;
		}

		private SBTNode<V> maintain(SBTNode<V> cur) {
			if (cur == null) {
				return null;
			}
			int leftSize = cur.l != null ? cur.l.size : 0;
			int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
			int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
			int rightSize = cur.r != null ? cur.r.size : 0;
			int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
			int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
			if (leftLeftSize > rightSize) {
				cur = rightRotate(cur);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			} else if (leftRightSize > rightSize) {
				cur.l = leftRotate(cur.l);
				cur = rightRotate(cur);
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			} else if (rightRightSize > leftSize) {
				cur = leftRotate(cur);
				cur.l = maintain(cur.l);
				cur = maintain(cur);
			} else if (rightLeftSize > leftSize) {
				cur.r = rightRotate(cur.r);
				cur = leftRotate(cur);
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			return cur;
		}

		private SBTNode<V> add(SBTNode<V> root, int index, SBTNode<V> cur) {
			if (root == null) {
				return cur;
			}
			root.size++;
			int leftAndHeadSize = (root.l != null ? root.l.size : 0) + 1;
			if (index < leftAndHeadSize) {
				root.l = add(root.l, index, cur);
			} else {
				root.r = add(root.r, index - leftAndHeadSize, cur);
			}
			root = maintain(root);
			return root;
		}

		private SBTNode<V> remove(SBTNode<V> root, int index) {
			root.size--;
			int rootIndex = root.l != null ? root.l.size : 0;
			if (index != rootIndex) {
				if (index < rootIndex) {
					root.l = remove(root.l, index);
				} else {
					root.r = remove(root.r, index - rootIndex - 1);
				}
				return root;
			}
			if (root.l == null && root.r == null) {
				return null;
			}
			if (root.l == null) {
				return root.r;
			}
			if (root.r == null) {
				return root.l;
			}
			SBTNode<V> pre = null;
			SBTNode<V> suc = root.r;
			suc.size--;
			while (suc.l != null) {
				pre = suc;
				suc = suc.l;
				suc.size--;
			}
			if (pre != null) {
				pre.l = suc.r;
				suc.r = root.r;
			}
			suc.l = root.l;
			suc.size = suc.l.size + (suc.r == null ? 0 : suc.r.size) + 1;
			return suc;
		}

		private SBTNode<V> get(SBTNode<V> root, int index) {
			int leftSize = root.l != null ? root.l.size : 0;
			if (index < leftSize) {
				return get(root.l, index);
			} else if (index == leftSize) {
				return root;
			} else {
				return get(root.r, index - leftSize - 1);
			}
		}

		public void add(int index, V num) {
			SBTNode<V> cur = new SBTNode<V>(num);
			if (root == null) {
				root = cur;
			} else {
				if (index <= root.size) {
					root = add(root, index, cur);
				}
			}
		}

		public V get(int index) {
			SBTNode<V> ans = get(root, index);
			return ans.value;
		}

		public void remove(int index) {
			if (index >= 0 && size() > index) {
				root = remove(root, index);
			}
		}

		public int size() {
			return root == null ? 0 : root.size;
		}

	}

	public static class Num {
		public long up;
		public long down;

		public Num(long n, long d) {
			up = n;
			down = d;
		}

	}

	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static Num gcdNum(long up, long down) {
		if (up == 0) {
			down = 1;
		} else {
			long gcd = Math.abs(gcd(up, down));
			up /= gcd;
			down /= gcd;
		}
		return new Num(up, down);
	}

	public static Num op(Num a, Num b, int op) {
		long up = 0;
		long down = 0;
		if (op < 4) {
			if (op == 1) {
				up = a.up * b.down + b.up * a.down;
			} else if (op == 2) {
				up = a.up * b.down - b.up * a.down;
			} else {
				up = a.up * b.up;
			}
			down = a.down * b.down;
		} else {
			if (b.up == 0) {
				return null;
			}
			up = a.up * b.down;
			down = a.down * b.up;
		}
		return gcdNum(up, down);
	}

}

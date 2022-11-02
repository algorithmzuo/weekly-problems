package class_2022_11_3_week;

// 来自蓝桥杯
// 等差数列的概念人人都知道
// 给定一个初始数组arr，长度为n，规定下标从1到n
// 请实现如下两个操作 : 
// void add(int l, int r, int a, int b) : 
// 在arr[l..r]这个范围上，每个数加上初始项为a，公差为b的等差数列
// arr = { 2, 2, 2, 2, 2 }
//         1  2  3  4  5
// 执行add(2, 4, 3, 2)之后，
// arr = { 2, 5, 7, 9, 2 }
//         1  2  3  4  5
// int number(int l, int r) : 
// 查询arr[l...r]上最少能划分成几个等差数列
// 两个方法请尽量快，时间复杂度要低于O(N)
public class Code04_ArithmeticProgressionGame {

	// 没测完
	public static class SegmentTree {

		public static class Info {
			public int[] number;
			public int[] lvalue;
			public int[] rvalue;
			public boolean[] single;
			public int[] ldiff;
			public int[] rdiff;

			public Info(int n) {
				number = new int[n << 2];
				lvalue = new int[n << 2];
				rvalue = new int[n << 2];
				single = new boolean[n << 2];
				ldiff = new int[n << 2];
				rdiff = new int[n << 2];
			}
		}

		private int n;
		private Info resident;
		private Info temporary;
		private int[] lazyA;
		private int[] lazyB;

		public SegmentTree(int[] arr) {
			n = arr.length;
			resident = new Info(n + 1);
			temporary = new Info(n + 1);
			lazyA = new int[(n + 1) << 2];
			lazyB = new int[(n + 1) << 2];
			build(arr, 1, n, 1);
		}

		private void build(int[] arr, int l, int r, int rt) {
			if (l == r) {
				resident.number[rt] = 1;
				resident.lvalue[rt] = arr[l - 1];
				resident.rvalue[rt] = arr[l - 1];
				resident.single[rt] = true;
			} else {
				int m = (l + r) >> 1;
				build(arr, l, m, rt << 1);
				build(arr, m + 1, r, rt << 1 | 1);
				pushUp(resident, rt, rt << 1, rt << 1 | 1);
			}
		}

		private void pushUp(Info info, int f, int l, int r) {
			if (l == -1 || r == -1) {
				int i = l == -1 ? r : l;
				info.number[f] = info.number[i];
				info.lvalue[f] = info.lvalue[i];
				info.rvalue[f] = info.rvalue[i];
				info.single[f] = info.single[i];
				info.ldiff[f] = info.ldiff[i];
				info.rdiff[f] = info.rdiff[i];
			} else {
				info.single[f] = false;
				info.lvalue[f] = info.lvalue[l];
				info.rvalue[f] = info.rvalue[r];
				if (info.single[l] && info.single[r]) {
					info.number[f] = 1;
					info.ldiff[f] = info.rvalue[f] - info.lvalue[f];
					info.rdiff[f] = -info.ldiff[f];
				} else if (info.single[l]) {
					info.rdiff[f] = info.rdiff[r];
					int a = info.rvalue[l];
					int b = info.lvalue[r];
					int diff = info.ldiff[r];
					if (b - a == diff) {
						info.number[f] = info.number[r];
						info.ldiff[f] = diff;
					} else {
						info.number[f] = info.number[r] + 1;
						info.ldiff[f] = b - a;
					}
				} else if (info.single[r]) {
					info.ldiff[f] = info.ldiff[l];
					int a = info.lvalue[r];
					int b = info.rvalue[l];
					int diff = info.rdiff[l];
					if (b - a == diff) {
						info.number[f] = info.number[l];
						info.rdiff[f] = diff;
					} else {
						info.number[f] = info.number[l] + 1;
						info.rdiff[f] = b - a;
					}
				} else {
					info.ldiff[f] = info.ldiff[l];
					info.rdiff[f] = info.rdiff[r];
					info.number[f] = info.number[l] + info.number[r];
					int a = info.rvalue[l];
					int b = info.lvalue[r];
					if (b - a == info.ldiff[r] && info.ldiff[r] == -info.rdiff[l]) {
						info.number[f]--;
					}
				}
			}
		}

		private void pushDown(int rt, int ln, int rn) {
			if (lazyA[rt] != 0 || lazyB[rt] != 0) {
				resident.lvalue[rt << 1] += lazyA[rt];
				resident.rvalue[rt << 1] += lazyA[rt] + lazyB[rt] * (ln - 1);
				resident.ldiff[rt << 1] += lazyB[rt];
				resident.rdiff[rt << 1] -= lazyB[rt];
				lazyA[rt << 1] += lazyA[rt];
				lazyB[rt << 1] += lazyB[rt];
				resident.lvalue[rt << 1 | 1] += lazyA[rt] + lazyB[rt] * ln;
				resident.rvalue[rt << 1 | 1] += lazyA[rt] + lazyB[rt] * (ln + rn - 1);
				resident.ldiff[rt << 1 | 1] += lazyB[rt];
				resident.rdiff[rt << 1 | 1] -= lazyB[rt];
				lazyA[rt << 1 | 1] += lazyA[rt] + lazyB[rt] * ln;
				lazyB[rt << 1 | 1] += lazyB[rt];
				lazyA[rt] = 0;
				lazyB[rt] = 0;
			}
		}

		public void add(int l, int r, int a, int b) {
			add(l, r, a, b, 1, n, 1);
		}

		private void add(int L, int R, int A, int B, int l, int r, int rt) {
			if (L <= l && r <= R) {
				resident.lvalue[rt] += A + B * (l - L);
				resident.rvalue[rt] += A + B * (r - L);
				resident.ldiff[rt] += B;
				resident.rdiff[rt] -= B;
				lazyA[rt] += A + B * (l - L);
				lazyB[rt] += B;
			} else {
				int m = (l + r) >> 1;
				pushDown(rt, m - l + 1, r - m);
				if (L <= m) {
					add(L, R, A, B, l, m, rt << 1);
				}
				if (R > m) {
					add(L, R, A, B, m + 1, r, rt << 1 | 1);
				}
				pushUp(resident, rt, rt << 1, rt << 1 | 1);
			}
		}

		public int number(int l, int r) {
			collect(l, r, 1, n, 1);
			return temporary.number[1];
		}

		private void collect(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				temporary.number[rt] = resident.number[rt];
				temporary.lvalue[rt] = resident.lvalue[rt];
				temporary.rvalue[rt] = resident.rvalue[rt];
				temporary.single[rt] = resident.single[rt];
				temporary.ldiff[rt] = resident.ldiff[rt];
				temporary.rdiff[rt] = resident.rdiff[rt];
			} else {
				int m = (l + r) >> 1;
				pushDown(rt, m - l + 1, r - m);
				boolean left = false;
				boolean right = false;
				if (L <= m) {
					left = true;
					collect(L, R, l, m, rt << 1);
				}
				if (R > m) {
					right = true;
					collect(L, R, m + 1, r, rt << 1 | 1);
				}
				pushUp(temporary, rt, left ? rt << 1 : -1, right ? (rt << 1 | 1) : -1);
			}
		}
	}

	// 为了测试
	public static class Right {

		public int[] arr;

		public Right(int[] A) {
			int n = A.length;
			arr = new int[n + 1];
			for (int i = 0; i < n; i++) {
				arr[i + 1] = A[i];
			}
		}

		public void add(int l, int r, int a, int b) {
			for (int i = l, grow = a; i <= r; i++, grow += b) {
				arr[i] += grow;
			}
		}

		public int number(int l, int r) {
			if (l == r || l == r - 1) {
				return 1;
			}
			boolean setDiff = false;
			int diff = 0;
			int ans = 1;
			for (int i = l + 1; i <= r; i++) {
				if (!setDiff) {
					setDiff = true;
					diff = arr[i] - arr[i - 1];
				} else {
					if (arr[i] - arr[i - 1] != diff) {
						ans++;
						setDiff = false;
					}
				}
			}
			return ans;
		}

		public static void main(String[] args) {
			int[] arr1 = { 2, 3, 4, 2, 6, 5 };
			Right r = new Right(arr1);
			System.out.println(r.number(1, arr1.length));
			r.add(4, 5, 3, 2);
			System.out.println(r.number(1, arr1.length));

			int[] arr2 = { 2, 3, 4, 2, 6, 5 };
			SegmentTree st = new SegmentTree(arr2);
			System.out.println(st.number(1, arr2.length));
			st.add(4, 5, 3, 2);
			System.out.println(st.number(1, arr2.length));
		}

	}

}

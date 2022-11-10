package class_2022_11_5_week;

// 来自蓝桥杯练习题
// 洛谷原题
// 等差数列的概念人人都知道
// 给定一个原始数组arr，长度为N
// 并且实现如下两个操作 : 
// void add(int l, int r, int a, int b) : 
// 表示在arr[l...r]这个范围上，
// 从左往右依次加 : a、a + b * 1、a + b*2、...、a + b*(r-l)
// int number(int l, int r) :
// 表示arr[l...r]这一段，最少可以划分成几个等差数列
// 这两个方法都要求实现的特别高效，因为调用次数很多
// N <= 100000
// add调用次数 <= 100000
// number调用次数 <= 100000
// 测试链接 : https://www.luogu.com.cn/problem/P4243
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_ArithmeticProgressionGame {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			n--;
			build(1, n, 1);
			in.nextToken();
			int qT = (int) in.nval;
			for (int i = 0; i < qT; i++) {
				in.nextToken();
				String op = in.sval;
				if (op.equals("A")) {
					in.nextToken();
					int l = (int) in.nval;
					in.nextToken();
					int r = (int) in.nval;
					in.nextToken();
					int a = (int) in.nval;
					in.nextToken();
					int b = (int) in.nval;
					add(l, r, a, b);
				} else {
					in.nextToken();
					int l = (int) in.nval;
					in.nextToken();
					int r = (int) in.nval;
					out.println(number(l, r));
					out.flush();
				}
			}
		}
	}

	public static class Info {
		public long ldiff;
		public long rdiff;
		public int lsplit;
		public int rsplit;
		public int stable;
		public int size;

		public Info(long a, long b, int c, int d, int e, int f) {
			ldiff = a;
			rdiff = b;
			lsplit = c;
			rsplit = d;
			stable = e;
			size = f;
		}
	}

	public static final int MAXN = 100010;

	public static int[] arr = new int[MAXN];

	public static Info[] resident = new Info[MAXN << 2];

	public static Info[] temporary = new Info[MAXN << 2];

	static {
		for (int i = 0; i < MAXN << 2; i++) {
			resident[i] = new Info(0, 0, 0, 0, 0, 0);
			temporary[i] = new Info(0, 0, 0, 0, 0, 0);
		}
	}

	public static long[] lazy = new long[MAXN << 2];

	public static int n;

	private static void build(int l, int r, int rt) {
		if (l == r) {
			resident[rt].ldiff = arr[l] - arr[l - 1];
			resident[rt].rdiff = resident[rt].ldiff;
			resident[rt].lsplit = 1;
			resident[rt].rsplit = 1;
			resident[rt].stable = 0;
			resident[rt].size = 1;
		} else {
			int m = (l + r) >> 1;
			build(l, m, rt << 1);
			build(m + 1, r, rt << 1 | 1);
			pushUp(resident, rt, rt << 1, rt << 1 | 1);
		}
	}

	private static void pushUp(Info[] info, int f, int l, int r) {
		if (l == -1 || r == -1) {
			int i = l == -1 ? r : l;
			info[f].ldiff = info[i].ldiff;
			info[f].rdiff = info[i].rdiff;
			info[f].lsplit = info[i].lsplit;
			info[f].rsplit = info[i].rsplit;
			info[f].stable = info[i].stable;
			info[f].size = info[i].size;
		} else {
			boolean connect = info[l].rdiff == info[r].ldiff;
			info[f].ldiff = info[l].ldiff;
			info[f].rdiff = info[r].rdiff;
			info[f].size = info[l].size + info[r].size;
			info[f].stable = info[l].stable + info[r].stable;
			if (info[l].stable == 0 && info[r].stable == 0) {
				if (connect) {
					info[f].lsplit = info[l].lsplit - 1;
					info[f].rsplit = info[r].rsplit - 1;
					info[f].stable++;
				} else {
					info[f].lsplit = info[f].size;
					info[f].rsplit = info[f].size;
				}
			} else if (info[l].stable == 0) {
				info[f].rsplit = info[r].rsplit;
				if (connect) {
					info[f].lsplit = info[l].lsplit - 1;
					if (info[r].lsplit > 0) {
						info[f].stable += (info[r].lsplit - 1) / 2 + 1;
					}
				} else {
					info[f].lsplit = info[l].size + info[r].lsplit;
				}
			} else if (info[r].stable == 0) {
				info[f].lsplit = info[l].lsplit;
				if (connect) {
					info[f].rsplit = info[r].rsplit - 1;
					if (info[l].rsplit > 0) {
						info[f].stable += (info[l].rsplit - 1) / 2 + 1;
					}
				} else {
					info[f].rsplit = info[r].size + info[l].rsplit;
				}
			} else {
				info[f].lsplit = info[l].lsplit;
				info[f].rsplit = info[r].rsplit;
				if (info[l].rsplit == 0 && info[r].lsplit == 0) {
					if (connect) {
						info[f].stable--;
					}
				} else if (info[l].rsplit == 0) {
					if (connect) {
						info[f].stable += (info[r].lsplit - 1) / 2;
					} else {
						info[f].stable += info[r].lsplit / 2;
					}
				} else if (info[r].lsplit == 0) {
					if (connect) {
						info[f].stable += (info[l].rsplit - 1) / 2;
					} else {
						info[f].stable += info[l].rsplit / 2;
					}
				} else {
					int add = (info[l].rsplit + info[r].lsplit) / 2;
					if (connect) {
						add = (info[l].rsplit - 1) / 2 + (info[r].lsplit - 1) / 2 + 1;
					}
					info[f].stable += add;
				}
			}
		}
	}

	private static void pushDown(int rt) {
		if (lazy[rt] != 0) {
			lazy[rt << 1] += lazy[rt];
			resident[rt << 1].ldiff += lazy[rt];
			resident[rt << 1].rdiff += lazy[rt];
			lazy[rt << 1 | 1] += lazy[rt];
			resident[rt << 1 | 1].ldiff += lazy[rt];
			resident[rt << 1 | 1].rdiff += lazy[rt];
			lazy[rt] = 0;
		}
	}

	public static void add(int l, int r, int a, int b) {
		if (l > 1) {
			add(l - 1, l - 1, a, 1, n, 1);
		}
		if (r <= n) {
			add(r, r, -((long) a + ((long) (r - l)) * b), 1, n, 1);
		}
		if (l < r) {
			add(l, r - 1, b, 1, n, 1);
		}
	}

	public static void add(int L, int R, long V, int l, int r, int rt) {
		if (L <= l && r <= R) {
			resident[rt].ldiff += V;
			resident[rt].rdiff += V;
			lazy[rt] += V;
		} else {
			int m = (l + r) >> 1;
			pushDown(rt);
			if (L <= m) {
				add(L, R, V, l, m, rt << 1);
			}
			if (R > m) {
				add(L, R, V, m + 1, r, rt << 1 | 1);
			}
			pushUp(resident, rt, rt << 1, rt << 1 | 1);
		}
	}

	public static long number(int l, int r) {
		if (l == r) {
			return 1;
		}
		query(l, r - 1, 1, n, 1);
		long ans = (r - l + 2) / 2;
		if (temporary[1].stable != 0) {
			ans = temporary[1].stable + (temporary[1].lsplit + 1) / 2 + (temporary[1].rsplit + 1) / 2;
		}
		return ans;
	}

	public static void query(int L, int R, int l, int r, int rt) {
		if (L <= l && r <= R) {
			temporary[rt].ldiff = resident[rt].ldiff;
			temporary[rt].rdiff = resident[rt].rdiff;
			temporary[rt].lsplit = resident[rt].lsplit;
			temporary[rt].rsplit = resident[rt].rsplit;
			temporary[rt].stable = resident[rt].stable;
			temporary[rt].size = resident[rt].size;
		} else {
			int m = (l + r) / 2;
			pushDown(rt);
			int ll = -1;
			if (L <= m) {
				ll = rt << 1;
				query(L, R, l, m, rt << 1);
			}
			int rr = -1;
			if (R > m) {
				rr = rt << 1 | 1;
				query(L, R, m + 1, r, rt << 1 | 1);
			}
			pushUp(temporary, rt, ll, rr);
		}
	}

}

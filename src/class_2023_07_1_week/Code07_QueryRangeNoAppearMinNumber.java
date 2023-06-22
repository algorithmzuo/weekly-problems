package class_2023_07_1_week;

// 测试链接 : https://www.luogu.com.cn/problem/P4137
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code07_QueryRangeNoAppearMinNumber {

	public static int MAXN = 200001;

	public static int[] root = new int[MAXN];

	// 正常来讲，可持久化线段树要开MAXN * 32的空间
	// 不过洛谷对java提交的空间判断很苛刻，所以改成刚够用的即可
	// 这里通过实验，开23倍空间不会爆空间，也能通过
	// 没办法，对java不友好
	// 但如果是C++，可以直接开32倍空间
	public static int MAXM = MAXN * 23;

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	public static int[] minn = new int[MAXM];

	public static int n, m, cnt;

	public static int update(int pre, int l, int r, int pos, int val) {
		int rt = ++cnt;
		left[rt] = left[pre];
		right[rt] = right[pre];
		minn[rt] = minn[pre];
		if (l == r) {
			minn[rt] = val;
		} else {
			int mid = l + ((r - l) >> 2);
			if (pos <= mid) {
				left[rt] = update(left[pre], l, mid, pos, val);
			} else {
				right[rt] = update(right[pre], mid + 1, r, pos, val);
			}
			minn[rt] = Math.min(minn[left[rt]], minn[right[rt]]);
		}
		return rt;
	}

	public static int query(int rt, int l, int r, int val) {
		if (l == r) {
			return l;
		}
		int mid = l + ((r - l) >> 2);
		if (minn[left[rt]] < val) {
			return query(left[rt], l, mid, val);
		} else {
			return query(right[rt], mid + 1, r, val);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			cnt = 0;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				int value = (int) in.nval + 1;
				if (value > n) {
					root[i] = root[i - 1];
				} else {
					root[i] = update(root[i - 1], 1, n + 1, value, i);
				}
			}
			for (int i = 0; i < m; i++) {
				in.nextToken();
				int l = (int) in.nval;
				in.nextToken();
				int r = (int) in.nval;
				out.println(query(root[r], 1, n + 1, l) - 1);
			}
			out.flush();
		}
	}

}
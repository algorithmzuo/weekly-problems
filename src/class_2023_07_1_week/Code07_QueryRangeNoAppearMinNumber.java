package class_2023_07_1_week;

// 有一个长度为 n 的数组，{ a1, a2, ... an }
// 有m次询问，每次询问一个区间内最小没有出现过的自然数
// 测试链接 : https://www.luogu.com.cn/problem/P4137
// 注意 : 题目有问题，题目要求查询区间内最小没有出现过的自然数
// 但题目其实问的是，查询区间内没有出现过的最小非负数，也就是要把0考虑进去！
// 从题目给的示例也可以看出这一点，要注意！
// 于是我查了一下并发现，现在这个世界居然把0算做自然数了
// 在我学习的年代，0是不算自然数的！这世界变化太...二了
// 而更二的是，这道题用java通过的人，在我之前只有一个人
// 但这货居然用开多线程的方法，也就是一个查询开一个线程来执行...并行通过的...
// 这世界怎么了？
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
	// 不过洛谷对java提交的空间判断很苛刻，直接开32倍空间会爆内存
	// 所以需要改成刚够用的情况，通过实验开19倍空间不会爆空间也能通过
	// 为什么是19倍？因为支持的范围是0~200001，2的18次方刚超过200001
	// 也就是每一个版本的线段树，在一个范围不断二分，至少需要18个新空间
	// 但是有时候二分可能会再下一层，所以开19倍空间肯定够用
	// 对java不友好，如果是C++，可以直接开32倍空间，也不会超内存
	// 哈哈，洛谷对java的歧视
	public static int MAXM = MAXN * 19;

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	public static int[] last = new int[MAXM];

	public static int n, m, cnt;

	public static int update(int pre, int l, int r, int val, int pos) {
		int rt = ++cnt;
		left[rt] = left[pre];
		right[rt] = right[pre];
		last[rt] = last[pre];
		if (l == r) {
			last[rt] = pos;
		} else {
			int mid = (l + r) / 2;
			if (val <= mid) {
				left[rt] = update(left[pre], l, mid, val, pos);
			} else {
				right[rt] = update(right[pre], mid + 1, r, val, pos);
			}
			last[rt] = Math.min(last[left[rt]], last[right[rt]]);
		}
		return rt;
	}

	public static int query(int rt, int l, int r, int pos) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		if (last[left[rt]] < pos) {
			return query(left[rt], l, mid, pos);
		} else {
			return query(right[rt], mid + 1, r, pos);
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
				int value = (int) in.nval;
				if (value >= n) {
					root[i] = root[i - 1];
				} else {
					root[i] = update(root[i - 1], 0, n, value, i);
				}
			}
			for (int i = 1; i <= m; i++) {
				in.nextToken();
				int l = (int) in.nval;
				in.nextToken();
				int r = (int) in.nval;
				out.println(query(root[r], 0, n, l));
			}
			out.flush();
		}
	}

}
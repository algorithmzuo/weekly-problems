package class_2022_03_2_week;

import java.util.Arrays;

// 来自字节飞书团队
// 在字节跳动，大家都使用飞书的日历功能进行会议室的预订，遇到会议高峰时期，
// 会议室就可能不够用，现在请你实现一个算法，判断预订会议时是否有空的会议室可用。
// 为简化问题，这里忽略会议室的大小，认为所有的会议室都是等价的，
// 只要空闲就可以容纳任意的会议，并且：
// 1. 所有的会议预订都是当日预订当日的时段
// 2. 会议时段是一个左闭右开的时间区间，精确到分钟
// 3. 每个会议室刚开始都是空闲状态，同一时间一个会议室只能进行一场会议
// 4. 会议一旦预订成功就会按时进行
// 比如上午11点到中午12点的会议即[660, 720)
// 给定一个会议室总数m
// 一个预定事件由[a,b,c]代表 : 
// a代表预定动作的发生时间，早来早得; b代表会议的召开时间; c代表会议的结束时间
// 给定一个n*3的二维数组，即可表示所有预定事件
// 返回一个长度为n的boolean类型的数组，表示每一个预定时间是否成功
public class Code01_MeetingCheck {

	public static boolean[] reserveMeetings(int m, int[][] meetings) {
		// 会议的总场次
		int n = meetings.length;
		// 开头时间，结尾时间
		int[] ranks = new int[n << 1];
		for (int i = 0; i < n; i++) {
			ranks[i] = meetings[i][1];
			ranks[i + n] = meetings[i][2] - 1;
		}
		Arrays.sort(ranks);
		// 0 : [6, 100, 200]
		// 1 : [4, 30,  300]
		// 30,1  100,2  200,3  300,4
		// [0,6,2,3]
		// [1,4,1,4]
		// 
		// 0 T/F ,  1,  T/  2,  
		
		// [1,4,1,4]   [0,6,2,3]  ....
		int[][] reMeetings = new int[n][4];
		int max = 0;
		for (int i = 0; i < n; i++) {
			reMeetings[i][0] = i;
			reMeetings[i][1] = meetings[i][0];
			reMeetings[i][2] = rank(ranks, meetings[i][1]);
			reMeetings[i][3] = rank(ranks, meetings[i][2] - 1);
			max = Math.max(max, reMeetings[i][3]);
		}
		SegmentTree st = new SegmentTree(max);
		Arrays.sort(reMeetings, (a, b) -> a[1] - b[1]);
		boolean[] ans = new boolean[n];
		for (int[] meeting : reMeetings) {
			if (st.queryMax(meeting[2], meeting[3]) < m) {
				ans[meeting[0]] = true;
				st.add(meeting[2], meeting[3], 1);
			}
		}
		return ans;
	}

	// 返回>=num, 最左位置
	public static int rank(int[] sorted, int num) {
		int l = 0;
		int r = sorted.length - 1;
		int m = 0;
		int ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (sorted[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans + 1;
	}

	public static class SegmentTree {
		private int n;
		private int[] max;
		private int[] lazy;

		public SegmentTree(int maxSize) {
			n = maxSize;
			max = new int[n << 2];
			lazy = new int[n << 2];
		}

		private void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}

		private void pushDown(int rt, int ln, int rn) {
			if (lazy[rt] != 0) {
				lazy[rt << 1] += lazy[rt];
				max[rt << 1] += lazy[rt];
				lazy[rt << 1 | 1] += lazy[rt];
				max[rt << 1 | 1] += lazy[rt];
				lazy[rt] = 0;
			}
		}

		public void add(int L, int R, int C) {
			add(L, R, C, 1, n, 1);
		}

		private void add(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				max[rt] += C;
				lazy[rt] += C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				add(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				add(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public int queryMax(int L, int R) {
			return queryMax(L, R, 1, n, 1);
		}

		private int queryMax(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return max[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int ans = 0;
			if (L <= mid) {
				ans = Math.max(ans, queryMax(L, R, l, mid, rt << 1));
			}
			if (R > mid) {
				ans = Math.max(ans, queryMax(L, R, mid + 1, r, rt << 1 | 1));
			}
			return ans;
		}

	}

	// 为了测试线段树
	public static class Right {
		public int[] arr;

		public Right(int maxSize) {
			arr = new int[maxSize + 1];
		}

		public void add(int L, int R, int C) {
			for (int i = L; i <= R; i++) {
				arr[i] += C;
			}
		}

		public int queryMax(int L, int R) {
			int ans = 0;
			for (int i = L; i <= R; i++) {
				ans = Math.max(ans, arr[i]);
			}
			return ans;
		}

	}

	// 测试线段树的对数器
	public static void main(String[] args) {
		int N = 50;
		int V = 10;
		int testTimes1 = 1000;
		int testTimes2 = 1000;
		System.out.println("测试线段树开始");
		for (int i = 0; i < testTimes1; i++) {
			int n = (int) (Math.random() * N) + 1;
			SegmentTree st = new SegmentTree(n);
			Right right = new Right(n);
			for (int j = 0; j < testTimes2; j++) {
				int a = (int) (Math.random() * n) + 1;
				int b = (int) (Math.random() * n) + 1;
				int L = Math.min(a, b);
				int R = Math.max(a, b);
				if (Math.random() < 0.5) {
					int C = (int) (Math.random() * V);
					st.add(L, R, C);
					right.add(L, R, C);
				} else {
					if (st.queryMax(L, R) != right.queryMax(L, R)) {
						System.out.println("出错了!");
					}
				}
			}
		}
		System.out.println("测试线段树结束");
	}

}

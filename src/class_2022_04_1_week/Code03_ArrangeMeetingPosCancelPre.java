package class_2022_04_1_week;

import java.util.ArrayList;
import java.util.Arrays;

// 来自通维数码
// 每个会议给定开始和结束时间
// 后面的会议如果跟前面的会议有任何冲突，完全取消冲突的、之前的会议，安排当前的
// 给定一个会议数组，返回安排的会议列表
public class Code03_ArrangeMeetingPosCancelPre {

	// 比较暴力的解
	// 为了对数器来验证
	public static ArrayList<int[]> arrange1(int[][] meetings) {
		int max = 0;
		for (int[] meeting : meetings) {
			max = Math.max(max, meeting[1]);
		}
		boolean[] occupy = new boolean[max + 1];
		ArrayList<int[]> ans = new ArrayList<>();
		for (int i = meetings.length - 1; i >= 0; i--) {
			int[] cur = meetings[i];
			boolean add = true;
			for (int j = cur[0]; j < cur[1]; j++) {
				if (occupy[j]) {
					add = false;
					break;
				}
			}
			if (add) {
				ans.add(cur);
			}
			for (int j = cur[0]; j < cur[1]; j++) {
				occupy[j] = true;
			}
		}
		return ans;
	}

	// 最优解
	// 会议有N个，时间复杂度O(N*logN)
	public static ArrayList<int[]> arrange2(int[][] meetings) {
		int n = meetings.length;
		// n << 1 -> n*2
		int[] rank = new int[n << 1];
		for (int i = 0; i < meetings.length; i++) {
			rank[i] = meetings[i][0]; // 会议开头点
			rank[i + n] = meetings[i][1] - 1; // 会议的结束点
		}
		Arrays.sort(rank);
		// n*2
		SegmentTree st = new SegmentTree(n << 1);
		// 哪些会议安排了，放入到ans里去！
		ArrayList<int[]> ans = new ArrayList<>();
		// 从右往左遍历，意味着，后出现的会议，先看看能不能安排
		for (int i = meetings.length - 1; i >= 0; i--) {
			// cur 当前会议
			int[] cur = meetings[i];
			// cur[0] = 17万 -> 6
			int from = rank(rank, cur[0]);
			// cur[1] = 90 -> 89 -> 2
			int to = rank(rank, cur[1] - 1);
			if (st.sum(from, to) == 0) {
				ans.add(cur);
			}
			st.add(from, to, 1);
		}
		return ans;
	}

	public static int rank(int[] rank, int num) {
		int l = 0;
		int r = rank.length - 1;
		int m = 0;
		int ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (rank[m] >= num) {
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
		private int[] sum;
		private int[] lazy;

		public SegmentTree(int size) {
			n = size + 1;
			sum = new int[n << 2];
			lazy = new int[n << 2];
			n--;
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
			if (lazy[rt] != 0) {
				lazy[rt << 1] += lazy[rt];
				sum[rt << 1] += lazy[rt] * ln;
				lazy[rt << 1 | 1] += lazy[rt];
				sum[rt << 1 | 1] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}

		public void add(int L, int R, int C) {
			add(L, R, C, 1, n, 1);
		}

		private void add(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				sum[rt] += C * (r - l + 1);
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

		public int sum(int L, int R) {
			return query(L, R, 1, n, 1);
		}

		private int query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return sum[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}

	}

	// 为了测试
	public static int[][] randomMeeting(int len, int time) {
		int[][] meetings = new int[len][2];
		for (int i = 0; i < len; i++) {
			int a = (int) (Math.random() * (time + 1));
			int b = (int) (Math.random() * (time + 1));
			if (a == b) {
				b++;
			}
			meetings[i][0] = Math.min(a, b);
			meetings[i][1] = Math.max(a, b);
		}
		return meetings;
	}

	// 为了测试
	public static int[][] copyMeetings(int[][] meetings) {
		int len = meetings.length;
		int[][] ans = new int[len][2];
		for (int i = 0; i < len; i++) {
			ans[i][0] = meetings[i][0];
			ans[i][1] = meetings[i][1];
		}
		return ans;
	}

	// 为了测试
	public static boolean equal(ArrayList<int[]> arr1, ArrayList<int[]> arr2) {
		if (arr1.size() != arr2.size()) {
			return false;
		}
		for (int i = 0; i < arr1.size(); i++) {
			int[] a = arr1.get(i);
			int[] b = arr2.get(i);
			if (a[0] != b[0] || a[1] != b[1]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int n = 100;
		int t = 5000;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[][] meetings1 = randomMeeting(len, t);
			int[][] meetings2 = copyMeetings(meetings1);
			ArrayList<int[]> ans1 = arrange1(meetings1);
			ArrayList<int[]> ans2 = arrange2(meetings2);
			if (!equal(ans1, ans2)) {
				System.out.println("出错了!");
				System.out.println(ans1.size());
				System.out.println(ans2.size());
				System.out.println("====");
			}
		}
		System.out.println("测试结束");
	}

}

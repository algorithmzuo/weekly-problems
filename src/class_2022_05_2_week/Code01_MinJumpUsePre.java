package class_2022_05_2_week;

// 来自学员问题
// 为了给刷题的同学一些奖励，力扣团队引入了一个弹簧游戏机
// 游戏机由 N 个特殊弹簧排成一排，编号为 0 到 N-1
// 初始有一个小球在编号 0 的弹簧处。若小球在编号为 i 的弹簧处
// 通过按动弹簧，可以选择把小球向右弹射 jump[i] 的距离，或者向左弹射到任意左侧弹簧的位置
// 也就是说，在编号为 i 弹簧处按动弹簧，
// 小球可以弹向 0 到 i-1 中任意弹簧或者 i+jump[i] 的弹簧（若 i+jump[i]>=N ，则表示小球弹出了机器）
// 小球位于编号 0 处的弹簧时不能再向左弹。
// 为了获得奖励，你需要将小球弹出机器。
// 请求出最少需要按动多少次弹簧，可以将小球从编号 0 弹簧弹出整个机器，即向右越过编号 N-1 的弹簧。
// 测试链接 : https://leetcode-cn.com/problems/zui-xiao-tiao-yue-ci-shu/
public class Code01_MinJumpUsePre {

	public int minJump1(int[] jump) {
		int n = jump.length;
		int[] dp = new int[n];
		dp[n - 1] = 1;
		for (int i = n - 2; i >= 0; i--) {
			dp[i] = jump[i] + i >= n ? 1 : (dp[i + jump[i]] + 1);
			// 下面的for循环中，最核心的一个判断 : dp[j] >= dp[i] + 1 才继续
			// 这是一个特别强大的剪枝
			// 没有就超时
			// 有就打败100%的人
			for (int j = i + 1; j < n && dp[j] >= dp[i] + 1; j++) {
				dp[j] = dp[i] + 1;
			}
		}
		return dp[0];
	}

	public int minJump2(int[] jump) {
		int n = jump.length;
		SegmentTree st = new SegmentTree(n);
		st.update(n, n, 1);
		for (int i = n - 2, j = n - 1; i >= 0; i--, j--) {
			st.update(j, j, i + jump[i] >= n ? 1 : (st.getValue(i + jump[i] + 1) + 1));
			int l = j + 1;
			int r = n;
			int m = 0;
			int ans = -1;
			while (l <= r) {
				m = (l + r) / 2;
				if (st.getValue(m) > st.getValue(j) + 1) {
					ans = m;
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			if (ans != -1) {
				st.update(j + 1, ans, st.getValue(j) + 1);
			}
		}
		return st.getValue(1);
	}

	public static class SegmentTree {
		private int n;
		private int MAXN;
		private int[] sum;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int size) {
			n = size;
			MAXN = n + 1;
			sum = new int[MAXN << 2];
			change = new int[MAXN << 2];
			update = new boolean[MAXN << 2];
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				sum[rt << 1] = change[rt] * ln;
				sum[rt << 1 | 1] = change[rt] * rn;
				update[rt] = false;
			}
		}

		public void update(int l, int r, int v) {
			update(l, r, v, 1, n, 1);
		}

		private void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				sum[rt] = C * (r - l + 1);
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				update(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public int getValue(int index) {
			return query(index, index, 1, n, 1);
		}

		public int query(int L, int R, int l, int r, int rt) {
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

}

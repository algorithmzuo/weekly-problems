package class_2022_04_1_week;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

// 来自快手
// 某公司年会上，大家要玩一食发奖金游戏，一共有n个员工，
// 每个员工都有建设积分和捣乱积分
// 他们需要排成一队，在队伍最前面的一定是老板，老板也有建设积分和捣乱积分
// 排好队后，所有员工都会获得各自的奖金，
// 该员工奖金 = 排在他前面所有人的建设积分乘积 / 该员工自己的捣乱积分，向下取整
// 为了公平(放屁)，老板希望 : 让获得奖金最高的员工，所获得的奖金尽可能少
// 所以想请你帮他重新排一下队伍，返回奖金最高的员工获得的、尽可能少的奖金数额
// 1 <= n <= 1000, 1<= 积分 <= 10000;
public class Code06_MaxMoneyMostMin {

	// 暴力方法
	// 为了验证
	// a : 老板的贡献积分
	// b : 老板的捣乱积分
	// value[i] : i号员工的贡献积分
	// trouble[i] : i号员工的捣乱积分
	// 返回 : 奖金最高的员工获得的、尽可能少的奖金数额
	public static long mostMin1(int a, int b, int[] value, int[] trouble) {
		return process1(a, value, trouble, 0);
	}

	public static long process1(int boss, int[] value, int[] trouble, int index) {
		if (index == value.length) {
			long valueAll = boss;
			long ans = 0;
			for (int i = 0; i < value.length; i++) {
				ans = Math.max(ans, valueAll / trouble[i]);
				valueAll *= value[i];
			}
			return ans;
		} else {
			long ans = Long.MAX_VALUE;
			for (int i = index; i < value.length; i++) {
				swap(value, trouble, i, index);
				ans = Math.min(ans, process1(boss, value, trouble, index + 1));
				swap(value, trouble, i, index);
			}
			return ans;
		}
	}

	public static void swap(int[] value, int[] trouble, int i, int j) {
		int tmp = value[i];
		value[i] = value[j];
		value[j] = tmp;
		tmp = trouble[i];
		trouble[i] = trouble[j];
		trouble[j] = tmp;
	}

	// 正式方法
	// 所有员工数量为N
	// 假设所有员工建设积分乘起来为M
	// 时间复杂度O(N * logN * logM)
	public static long mostMin2(int a, int b, int[] value, int[] trouble) {
		int n = value.length;
		long l = 0;
		long r = 0;
		long valueAll = a;
		long[][] staff = new long[n][2];
		for (int i = 0; i < n; i++) {
			r = Math.max(r, valueAll / trouble[i]);
			valueAll *= value[i];
			staff[i][0] = (long) value[i] * (long) trouble[i];
			staff[i][1] = value[i];
		}
		Arrays.sort(staff, (x, y) -> (y[0] >= x[0] ? 1 : -1));
		long m = 0;
		long ans = 0;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (yeah(valueAll, staff, m)) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	// staff长度为N，时间复杂度O(N * logN)
	public static boolean yeah(long all, long[][] staff, long limit) {
		int n = staff.length;
		SegmentTree st = new SegmentTree(n);
		HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
		for (int i = 0, index = 1; i < n; i++, index++) {
			int value = (int) staff[i][1];
			st.update(index, value);
			if (!map.containsKey(value)) {
				map.put(value, new LinkedList<>());
			}
			map.get(value).addLast(index);
		}
		for (int k = 0; k < n; k++) {
			int right = boundary(staff, all, limit);
			if (right == 0) {
				return false;
			}
			int max = st.max(right);
			if (max == 0) {
				return false;
			}
			int index = map.get(max).pollFirst();
			st.update(index, 0);
			all /= max;
		}
		return true;
	}

	public static int boundary(long[][] staff, long all, long limit) {
		int l = 0;
		int r = staff.length - 1;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (all / staff[m][0] <= limit) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans + 1;
	}

	public static class SegmentTree {
		private int n;
		private int[] max;
		private int[] update;

		public SegmentTree(int maxSize) {
			n = maxSize + 1;
			max = new int[n << 2];
			update = new int[n << 2];
			Arrays.fill(update, -1);
		}

		public void update(int index, int c) {
			update(index, index, c, 1, n, 1);
		}

		public int max(int right) {
			return max(1, right, 1, n, 1);
		}

		private void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt] != -1) {
				update[rt << 1] = update[rt];
				max[rt << 1] = update[rt];
				update[rt << 1 | 1] = update[rt];
				max[rt << 1 | 1] = update[rt];
				update[rt] = -1;
			}
		}

		private void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				max[rt] = C;
				update[rt] = C;
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

		private int max(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return max[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int ans = 0;
			if (L <= mid) {
				ans = Math.max(ans, max(L, R, l, mid, rt << 1));
			}
			if (R > mid) {
				ans = Math.max(ans, max(L, R, mid + 1, r, rt << 1 | 1));
			}
			return ans;
		}

	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * value) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 9;
		int v = 50;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int a = (int) (Math.random() * v) + 1;
			int b = (int) (Math.random() * v) + 1;
			int len = (int) (Math.random() * n);
			int[] value = randomArray(len, v);
			int[] trouble = randomArray(len, v);
			long ans1 = mostMin1(a, b, value, trouble);
			long ans2 = mostMin2(a, b, value, trouble);
			if (ans1 != ans2) {
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

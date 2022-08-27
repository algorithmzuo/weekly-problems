package class_2022_09_2_week;

import java.util.Arrays;

// 来自美团
// 给定一个正数n, 表示从0位置到n-1位置每个位置放着1件衣服
// 从0位置到n-1位置不仅有衣服，每个位置还摆着1个机器人
// 给定两个长度为n的数组，powers和rates
// powers[i]表示i位置的机器人的启动电量
// rates[i]表示i位置的机器人收起1件衣服的时间
// 使用每个机器人只需要付出启动电量
// 当i位置的机器人收起i位置的衣服，它会继续尝试往右收起i+1位置衣服
// 如果i+1位置的衣服已经被其他机器人收了或者其他机器人正在收
// 这个机器人就会停机, 不再收衣服。
// 不过如果它不停机，它会同样以rates[i]的时间来收起这件i+1位置的衣服
// 也就是收衣服的时间为每个机器人的固定属性，当它收起i+1位置的衣服，
// 它会继续检查i+2位置...一直到它停机或者右边没有衣服可以收了
// 形象的来说，机器人会一直尝试往右边收衣服，收k件的话就耗费k * rates[i]的时间
// 但是当它遇见其他机器人工作的痕迹，就会认为后面的事情它不用管了，进入停机状态
// 你手里总共有电量b，准备在0时刻将所有想启动的机器人全部一起启动
// 过后不再启动新的机器人，并且启动机器人的电量之和不能大于b
// 返回在最佳选择下，假快多久能收完所有衣服
// 如果无论如何都收不完所有衣服，返回-1
// 给定数据: int n, int b, int[] powers, int[] rates
// 数据范围: 
// powers长度 == rates长度 == n <= 1000
// 1 <= b <= 10^5
// 1 <= powers[i]、rates[i] <= 10^5
public class Code03_RobotAndClothes {

	// 通过不了的简单动态规划方法
	// 只是为了对数器验证
	public static int fast1(int n, int b, int[] powers, int[] rates) {
		int[][] dp = new int[n][b + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= b; j++) {
				dp[i][j] = -1;
			}
		}
		int ans = process1(powers, rates, n, 0, b, dp);
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// i....这些衣服
	// 由i....这些机器人负责
	// 在剩余电量还有rest的情况下
	// 收完i....这些衣服最少时间是多少
	// 如果怎么都收不完
	// 返回Integer.MAX_VALUE
	public static int process1(int[] powers, int[] rates, int n, int i, int rest, int[][] dp) {
		if (i == n) {
			return 0;
		}
		if (powers[i] > rest) {
			return Integer.MAX_VALUE;
		}
		if (dp[i][rest] != -1) {
			return dp[i][rest];
		}
		int ans = Integer.MAX_VALUE;
		for (int j = i; j < n; j++) {
			int curCost = (j - i + 1) * rates[i];
			int nextCost = process1(powers, rates, n, j + 1, rest - powers[i], dp);
			int curAns = Math.max(curCost, nextCost);
			ans = Math.min(ans, curAns);
		}
		dp[i][rest] = ans;
		return ans;
	}

	// 正式方法
	// 时间复杂度O( N^2 * log(rates[0] * n))
	// 揭示了大的思路，可以继续用线段树优化枚举，详情看fast3
	// 解题思路:
	// 二分答案
	// 定义函数minPower：
	// 如果一定要在time时间内捡完所有衣服，请返回使用最少的电量
	// 如果minPower，这个函数能实现
	// 那么只要二分出最小的答案即可
	public static int fast2(int n, int b, int[] powers, int[] rates) {
		if (n == 0) {
			return 0;
		}
		if (b == 0 || powers[0] > b) {
			return -1;
		}
		// 最小时间只可能在[1, rates[0] * n]范围上
		int l = 1;
		int r = rates[0] * n;
		int m = 0;
		int ans = -1;
		// 二分答案
		// 规定的时间就是m
		// minPower(powers, rates, m):
		// 如果一定要在time时间内捡完所有衣服，返回最小电量
		// 如果这个最小电量 <= 总电量，说明m时间可行，左侧继续二分答案
		// 如果这个最小电量 > 总电量，说明m时间不可行，右侧继续二分答案
		while (l <= r) {
			m = (l + r) / 2;
			if (minPower2(powers, rates, m) <= b) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static int minPower2(int[] powers, int[] rates, int time) {
		int[] dp = new int[powers.length];
		Arrays.fill(dp, -1);
		return process2(powers, rates, 0, time, dp);
	}

	// i....这么多的衣服
	// 在time时间内一定要收完
	// 返回最小电量
	// 如果怎么都收不完，返回系统最大值
	public static int process2(int[] powers, int[] rates, int i, int time, int[] dp) {
		int n = powers.length;
		if (i == n) {
			return 0;
		}
		if (dp[i] != -1) {
			return dp[i];
		}
		int usedTime = rates[i];
		int nextMinPower = Integer.MAX_VALUE;
		// 这个枚举其实还可以用线段树优化一下
		// 不过对于大思路来说已经无关紧要了
		for (int j = i; j < n && usedTime <= time; j++) {
			// i....j由当前的i号机器负责
			// j+1...由后面机器负责
			nextMinPower = Math.min(nextMinPower, process2(powers, rates, j + 1, time, dp));
			usedTime += rates[i];
		}
		int ans = nextMinPower == Integer.MAX_VALUE ? nextMinPower : (powers[i] + nextMinPower);
		dp[i] = ans;
		return ans;
	}

	// fast2的思路 + 线段树优化枚举
	// 时间复杂度O(N * logN * log(rates[0] * N))
	public static int fast3(int n, int b, int[] powers, int[] rates) {
		if (n == 0) {
			return 0;
		}
		if (b == 0 || powers[0] > b) {
			return -1;
		}
		int l = 1;
		int r = rates[0] * n;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (minPower3(powers, rates, m) <= b) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static int minPower3(int[] powers, int[] rates, int time) {
		int n = powers.length;
		int[] dp = new int[n + 1];
		SegmentTree st = new SegmentTree(n + 1);
		st.update(n, 0);
		for (int i = n - 1; i >= 0; i--) {
			if (rates[i] > time) {
				dp[i] = Integer.MAX_VALUE;
			} else {
				int j = Math.min(i + (time / rates[i]) - 1, n - 1);
				int next = st.min(i + 1, j + 1);
				int ans = next == Integer.MAX_VALUE ? next : (powers[i] + next);
				dp[i] = ans;
			}
			st.update(i, dp[i]);
		}
		return dp[0];
	}

	public static class SegmentTree {
		private int n;
		private int[] min;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int size) {
			n = size + 1;
			min = new int[n << 2];
			change = new int[n << 2];
			update = new boolean[n << 2];
			update(1, size, Integer.MAX_VALUE, 1, size, 1);
		}

		private void pushUp(int rt) {
			min[rt] = Math.min(min[rt << 1], min[rt << 1 | 1]);
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				min[rt << 1] = change[rt];
				min[rt << 1 | 1] = change[rt];
				update[rt] = false;
			}
		}

		public void update(int i, int v) {
			update(i + 1, i + 1, v, 1, n, 1);
		}

		private void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				min[rt] = C;
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

		public int min(int l, int r) {
			return min(l + 1, r + 1, 1, n, 1);
		}

		private int min(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return min[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int left = Integer.MAX_VALUE;
			int right = Integer.MAX_VALUE;
			if (L <= mid) {
				left = min(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				right = min(L, R, mid + 1, r, rt << 1 | 1);
			}
			return Math.min(left, right);
		}

	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 200;
		int B = 100;
		int P = 20;
		int R = 10;
		int testTimes = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int b = (int) (Math.random() * B) + 1;
			int[] powers = randomArray(n, P);
			int[] rates = randomArray(n, R);
			int ans1 = fast1(n, b, powers, rates);
			int ans2 = fast2(n, b, powers, rates);
			int ans3 = fast3(n, b, powers, rates);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了!");
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 10000;
		int b = 100000;
		int[] powers = randomArray(n, b);
		int[] rates = randomArray(n, b);
		System.out.println("衣服规模 : " + n);
		System.out.println("电量规模 : " + b);
		System.out.println("机器人启动费用取值规模 : " + b);
		System.out.println("机器人工作速度取值规模 : " + b);
		long start = System.currentTimeMillis();
		fast3(n, b, powers, rates);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}

package class_2022_09_4_week;

// 智能机器人要坐专用电梯把货物送到指定地点
// 整栋楼只有一部电梯，并且由于容量限制智能机器人只能放下一件货物
// 给定K个货物，每个货物都有所在楼层(from)和目的楼层(to)
// 假设电梯速度恒定为1，相邻两个楼层之间的距离为1
// 例如电梯从10层去往19层的时间为9
// 机器人装卸货物的时间极快不计入
// 电梯初始地点为第1层，机器人初始地点也是第1层
// 并且在运送完所有货物之后，机器人和电梯都要回到1层
// 返回智能机器人用电梯将每个物品都送去目标楼层的最快时间
// 注意：如果智能机器人选了一件物品，则必须把这个物品送完，不能中途丢下
// 输入描述：
// 正数k，表示货物数量，1 <= k <= 16
// from、to数组，长度都是k，1 <= from[i]、to[i] <= 10000
// from[i]表示i号货物所在的楼层
// to[i]表示i号货物要去往的楼层
// 返回最快的时间
public class Code05_RobotDeliverGoods {

	// 暴力方法
	// 全排序代码
	public static int minCost1(int k, int[] from, int[] to) {
		return process(0, k, from, to);
	}

	public static int process(int i, int k, int[] from, int[] to) {
		if (i == k) {
			int ans = 0;
			int cur = 1;
			for (int j = 0; j < k; j++) {
				ans += Math.abs(from[j] - cur);
				ans += Math.abs(to[j] - from[j]);
				cur = to[j];
			}
			return ans + cur - 1;
		} else {
			int ans = Integer.MAX_VALUE;
			for (int j = i; j < k; j++) {
				swap(from, to, i, j);
				ans = Math.min(ans, process(i + 1, k, from, to));
				swap(from, to, i, j);
			}
			return ans;
		}
	}

	public static void swap(int[] from, int[] to, int i, int j) {
		int tmp = from[i];
		from[i] = from[j];
		from[j] = tmp;
		tmp = to[i];
		to[i] = to[j];
		to[j] = tmp;
	}

	// 正式方法
	public static int minCost2(int k, int[] from, int[] to) {
		int m = 1 << k;
		int[][] dp = new int[m][k];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < k; j++) {
				dp[i][j] = -1;
			}
		}
		return f(0, 0, k, from, to, dp);
	}

	public static int f(int status, int i, int k, int[] from, int[] to, int[][] dp) {
		if (dp[status][i] != -1) {
			return dp[status][i];
		}
		int ans = Integer.MAX_VALUE;
		if (status == (1 << k) - 1) {
			ans = to[i] - 1;
		} else {
			for (int j = 0; j < k; j++) {
				if ((status & (1 << j)) == 0) {
					int come = Math.abs(from[j] - (status == 0 ? 1 : to[i]));
					int deliver = Math.abs(to[j] - from[j]);
					int next = f(status | (1 << j), j, k, from, to, dp);
					ans = Math.min(ans, come + deliver + next);
				}
			}
		}
		dp[status][i] = ans;
		return ans;
	}

	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	public static void main(String[] args) {
		int k = 5;
		int[] from = { 1, 3, 6, 5, 7 };
		int[] to = { 4, 6, 3, 2, 8 };
		System.out.println(minCost1(k, from, to));
		System.out.println(minCost2(k, from, to));

		int N = 8;
		int V = 100;
		int testTimes = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			k = (int) (Math.random() * N) + 1;
			from = randomArray(k, V);
			to = randomArray(k, V);
			int ans1 = minCost1(k, from, to);
			int ans2 = minCost2(k, from, to);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		k = 16;
		V = 10000;
		from = randomArray(k, V);
		to = randomArray(k, V);
		System.out.println("货物数量 : " + k);
		System.out.println("楼层范围 : " + V);
		long start = System.currentTimeMillis();
		minCost2(k, from, to);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}

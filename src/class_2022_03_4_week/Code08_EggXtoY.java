package class_2022_03_4_week;

// 来自学员问题
// 大妈一开始手上有x个鸡蛋，她想让手上的鸡蛋数量变成y
// 操作1 : 从仓库里拿出1个鸡蛋到手上，x变成x+1个
// 操作2 : 如果手上的鸡蛋数量是3的整数倍，大妈可以直接把三分之二的鸡蛋放回仓库，手里留下三分之一
// 返回从x到y的最小操作次数
// 1 <= x,y <= 10^18
// 练一下，用平凡解做限制
public class Code08_EggXtoY {

	// 彻底贪心！
	public static int minTimes1(int x, int y) {
		if (x <= y) {
			return y - x;
		}
		// 0 0
		// 1 2
		// 2 1
		int mod = x % 3;
		// 鸡蛋拿到3的整数倍，需要耗费的行动点数
		int need = mod == 0 ? 0 : (mod == 1 ? 2 : 1);
		return need + 1 + minTimes1((x + 2) / 3, y);
	}

	public static int minTimes2(int x, int y) {
		if (x <= y) {
			return y - x;
		}
		int limit = minTimes1(x, y);
		int[][] dp = new int[x + limit + 1][limit + 1];
		return process(x, y, 0, limit, dp);
	}

	// 当前鸡蛋数量cur，目标aim
	// 之前已经用了多少行动点，pre
	// limit : 一定行动点，超过limit，不需要尝试了！
	public static int process(int cur, int aim, int pre, int limit, int[][] dp) {
		if (pre > limit) {
			return Integer.MAX_VALUE;
		}
		if (dp[cur][pre] != 0) {
			return dp[cur][pre];
		}
		int ans = 0;
		if (cur == aim) {
			ans = pre;
		} else {
			int p1 = process(cur + 1, aim, pre + 1, limit, dp);
			int p2 = Integer.MAX_VALUE;
			if (cur % 3 == 0) {
				p2 = process(cur / 3, aim, pre + 1, limit, dp);
			}
			ans = Math.min(p1, p2);
		}
		dp[cur][pre] = ans;
		return ans;
	}

	public static void main(String[] args) {
		int max = 3000;
		int testTime = 500;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int x = (int) (Math.random() * max) + 1;
			int y = (int) (Math.random() * max) + 1;
			int ans1 = minTimes1(x, y);
			int ans2 = minTimes2(x, y);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println("x = " + x);
				System.out.println("y = " + y);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");

	}

}

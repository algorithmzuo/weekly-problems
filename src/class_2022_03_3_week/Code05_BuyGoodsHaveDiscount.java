package class_2022_03_3_week;

// 来自字节内部训练营
// 某公司游戏平台的夏季特惠开始了，你决定入手一些游戏。现在你一共有X元的预算。
// 该平台上所有的 n 个游戏均有折扣，标号为 i 的游戏的原价a_i元，现价只要b_i元
// 也就是说该游戏可以优惠 a_i - b_i，并且你购买该游戏能获得快乐值为 w_i
// 由于优惠的存在，你可能做出一些冲动消费导致最终买游戏的总费用超过预算，
// 只要满足 : 获得的总优惠金额不低于超过预算的总金额
// 那在心理上就不会觉得吃亏。
// 现在你希望在心理上不觉得吃亏的前提下，获得尽可能多的快乐值。
// 测试链接 : https://leetcode-cn.com/problems/tJau2o/
// 提交以下的code，将主类名字改成"Main"
// 可以直接通过
import java.util.Scanner;

public class Code05_BuyGoodsHaveDiscount {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int money = sc.nextInt();
			int[] costs = new int[n];
			long[] values = new long[n];
			int size = 0;
			long ans = 0;
			for (int i = 0; i < n; i++) {
				// 打折前
				int pre = sc.nextInt();
				// 打折后
				int pos = sc.nextInt();
				// 满足度
				int happy = sc.nextInt();
				// 节省的钱(save) = 打折前(pre) - 打折后(pos)
				int save = pre - pos;
				// 带来的好处(well) = 节省的钱 - 打折后(pos)
				int well = save - pos;
				// 比如，一件"一定要买的商品":
				// 预算 = 100，商品原价 = 10，打折后 = 3
				// 那么好处 = (10 - 3) - 3 = 4
				// 所以，这件商品把预算增加到了104，一定要买
				// 接下来，比如一件"需要考虑的商品"，预算 = 104，商品原价 = 10，打折后 = 8
				// 那么好处 = (10 - 8) - 8 = -6
				// 这件商品，就花掉6元！
				// 也就是说，以后花的不是打折后的值，是"坏处"
				int cost = -well;
				if (well >= 0) {
					money += well;
					ans += happy;
				} else {
					costs[size] = cost;
					values[size++] = happy;
				}

			}
			long[][] dp = new long[size + 1][money + 1];
			for (int a = 0; a <= size; a++) {
				for (int b = 0; b <= money; b++) {
					dp[a][b] = -2;
				}
			}
			ans += process(costs, values, size, 0, money, dp);
			System.out.println(ans);
		}
		sc.close();
	}

	public static long process(int[] costs, long[] values, int size, int i, int money, long[][] dp) {
		if (money < 0) {
			return -1;
		}
		if (i == size) {
			return 0;
		}
		if (dp[i][money] != -2) {
			return dp[i][money];
		}
		long p1 = process(costs, values, size, i + 1, money, dp);
		long p2 = -1;
		long next = process(costs, values, size, i + 1, money - costs[i], dp);
		if (next != -1) {
			p2 = values[i] + next;
		}
		long ans = Math.max(p1, p2);
		dp[i][money] = ans;
		return ans;
	}

}
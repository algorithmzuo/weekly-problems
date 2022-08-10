package class_2022_08_2_week;

// 来自学员问题
// 给定怪兽的血量为hp
// 第i回合如果用刀砍，怪兽在这回合会直接掉血，没有后续效果
// 第i回合如果用毒，怪兽在这回合不会掉血，
// 但是之后每回合都会掉血，并且所有中毒的后续效果会叠加
// 给定的两个数组cuts、poisons，两个数组等长，长度都是n
// 表示你在n回合内的行动，
// 每一回合的刀砍的效果由cuts[i]表示
// 每一回合的中毒的效果由poisons[i]表示
// 如果你在n个回合内没有直接杀死怪兽，意味着你已经无法有新的行动了
// 但是怪兽如果有中毒效果的话，那么怪兽依然会在hp耗尽的那回合死掉
// 返回你最快能在多少回合内将怪兽杀死
// 数据范围 : 
// 1 <= n <= 10的5次方
// 1 <= hp <= 10的9次方
// 1 <= cuts[i]、poisons[i] <= 10的9次方
public class Code05_CutOrPoison {

	// 不算好的方法
	// 为了验证
	public static int fast1(int[] cuts, int[] poisons, int hp) {
		int sum = 0;
		for (int num : poisons) {
			sum += num;
		}
		int[][][] dp = new int[cuts.length][hp + 1][sum + 1];
		return process1(cuts, poisons, 0, hp, 0, dp);
	}

	public static int process1(int[] cuts, int[] poisons, int index, int restHp, int poisonEffect, int[][][] dp) {
		restHp -= poisonEffect;
		if (restHp <= 0) {
			return index + 1;
		}
		// restHp > 0
		if (index == cuts.length) {
			if (poisonEffect == 0) {
				return Integer.MAX_VALUE;
			} else {
				return cuts.length + 1 + (restHp + poisonEffect - 1) / poisonEffect;
			}
		}
		if (dp[index][restHp][poisonEffect] != 0) {
			return dp[index][restHp][poisonEffect];
		}
		int p1 = restHp <= cuts[index] ? (index + 1)
				: process1(cuts, poisons, index + 1, restHp - cuts[index], poisonEffect, dp);
		int p2 = process1(cuts, poisons, index + 1, restHp, poisonEffect + poisons[index], dp);
		int ans = Math.min(p1, p2);
		dp[index][restHp][poisonEffect] = ans;
		return ans;
	}

	// 真正想实现的方法
	// O(N * log(hp))
	public static int fast2(int[] cuts, int[] poisons, int hp) {
		int l = 1;
		int r = hp + 1;
		int m = 0;
		int ans = Integer.MAX_VALUE;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (ok(cuts, poisons, hp, m)) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static boolean ok(int[] cuts, int[] posions, long hp, int limit) {
		int n = Math.min(cuts.length, limit);
		for (int i = 0, j = 1; i < n; i++, j++) {
			hp -= Math.max((long) cuts[i], (long) (limit - j) * (long) posions[i]);
			if (hp <= 0) {
				return true;
			}
		}
		return false;
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
		int N = 30;
		int cutV = 20;
		int posionV = 10;
		int hpV = 200;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] cuts = randomArray(n, cutV);
			int[] posions = randomArray(n, posionV);
			int hp = (int) (Math.random() * hpV) + 1;
			int ans1 = fast1(cuts, posions, hp);
			int ans2 = fast2(cuts, posions, hp);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

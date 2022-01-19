package class_2022_01_4_week;

// things是一个N*3的二维数组，商品有N件，商品编号从1~N
// 比如things[3] = [300, 2, 6]
// 代表第3号商品：价格300，重要度2，它是6号商品的附属商品
// 再比如things[6] = [500, 3, 0]
// 代表第6号商品：价格500，重要度3，它不是任何附属，它是主商品
// 每件商品的收益是价格*重要度，花费就是价格
// 如果一个商品是附属品，那么只有它附属的主商品购买了，它才能被购买
// 任何一个附属商品，只会有1个主商品
// 任何一个主商品的附属商品数量，不会超过2件
// 主商品和附属商品的层级最多有2层
// 给定二维数组things、钱数money，返回整体花费不超过money的情况下，最大的收益总和
// 测试链接 : https://www.nowcoder.com/practice/f9c6f980eeec43ef85be20755ddbeaf4
// 请把如下的代码的主类名改为"Main", 可以直接通过
import java.util.ArrayList;
import java.util.Scanner;

public class Code01_BuyThingsAboutCollocation {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int money = sc.nextInt();
			int size = sc.nextInt();
			ArrayList<ArrayList<int[]>> things = new ArrayList<>();
			things.add(new ArrayList<>());
			for (int i = 0; i < size; i++) {
				ArrayList<int[]> cur = new ArrayList<>();
				cur.add(new int[] { sc.nextInt(), sc.nextInt(), sc.nextInt() });
				things.add(cur);
			}
			int n = clean(things, size);
			int ans = maxScore(things, n, money);
			System.out.println(ans);
		}
		sc.close();
	}

	public static int clean(ArrayList<ArrayList<int[]>> things, int size) {
		for (int i = 1; i <= size; i++) {
			int[] cur = things.get(i).get(0);
			if (cur[2] != 0) {
				things.get(i).clear();
				things.get(cur[2]).add(cur);
			}
		}
		int n = 0;
		for (int i = 0; i <= size; i++) {
			if (!things.get(i).isEmpty()) {
				things.set(n++, things.get(i));
			}
		}
		return n;
	}

	public static int maxScore(ArrayList<ArrayList<int[]>> things, int n, int money) {
		int[][] dp = new int[n][money + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= money; j++) {
				dp[i][j] = -2;
			}
		}
		return process(things, n, 0, money, dp);
	}

	public static int process(ArrayList<ArrayList<int[]>> things, int n, int index, int rest, int[][] dp) {
		if (rest < 0) {
			return -1;
		}
		if (index == n) {
			return 0;
		}
		if (dp[index][rest] != -2) {
			return dp[index][rest];
		}
		ArrayList<int[]> project = things.get(index);
		int[] a = project.get(0);
		int[] b = project.size() > 1 ? project.get(1) : null;
		int[] c = project.size() > 2 ? project.get(2) : null;
		int p1 = process(things, n, index + 1, rest, dp);
		int p2 = process(things, n, index + 1, rest - a[0], dp);
		if (p2 != -1) {
			p2 += a[0] * a[1];
		}
		int p3 = b != null ? process(things, n, index + 1, rest - a[0] - b[0], dp) : -1;
		if (p3 != -1) {
			p3 += a[0] * a[1] + b[0] * b[1];
		}
		int p4 = c != null ? process(things, n, index + 1, rest - a[0] - c[0], dp) : -1;
		if (p4 != -1) {
			p4 += a[0] * a[1] + c[0] * c[1];
		}
		int p5 = c != null ? process(things, n, index + 1, rest - a[0] - b[0] - c[0], dp) : -1;
		if (p5 != -1) {
			p5 += a[0] * a[1] + b[0] * b[1] + c[0] * c[1];
		}
		int ans = Math.max(Math.max(Math.max(p1, p2), Math.max(p3, p4)), p5);
		dp[index][rest] = ans;
		return ans;
	}

}

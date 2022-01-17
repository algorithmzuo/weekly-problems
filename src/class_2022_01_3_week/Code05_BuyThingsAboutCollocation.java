package class_2022_01_3_week;

// 测试链接 : https://www.nowcoder.com/practice/f9c6f980eeec43ef85be20755ddbeaf4
// 请把如下的代码的主类名改为"Main", 可以直接通过
import java.util.ArrayList;
import java.util.Scanner;

public class Code05_BuyThingsAboutCollocation {

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
		int ans = 0;
		ArrayList<int[]> project = things.get(index);
		if (project.size() == 1) {
			int[] a = project.get(0);
			int p1 = process(things, n, index + 1, rest, dp);
			int p2 = -1;
			int p2next = process(things, n, index + 1, rest - a[0], dp);
			if (p2next != -1) {
				p2 = a[0] * a[1] + p2next;
			}
			ans = Math.max(p1, p2);
		} else if (project.size() == 2) {
			int[] a = project.get(0);
			int[] b = project.get(1);
			int p1 = process(things, n, index + 1, rest, dp);
			int p2 = -1;
			int p2next = process(things, n, index + 1, rest - a[0], dp);
			if (p2next != -1) {
				p2 = a[0] * a[1] + p2next;
			}
			int p3 = -1;
			int p3next = process(things, n, index + 1, rest - a[0] - b[0], dp);
			if (p3next != -1) {
				p3 = a[0] * a[1] + b[0] * b[1] + p3next;
			}
			ans = Math.max(p1, Math.max(p2, p3));
		} else {
			int[] a = project.get(0);
			int[] b = project.get(1);
			int[] c = project.get(2);
			int p1 = process(things, n, index + 1, rest, dp);
			int p2 = -1;
			int p2next = process(things, n, index + 1, rest - a[0], dp);
			if (p2next != -1) {
				p2 = a[0] * a[1] + p2next;
			}
			int p3 = -1;
			int p3next = process(things, n, index + 1, rest - a[0] - b[0], dp);
			if (p3next != -1) {
				p3 = a[0] * a[1] + b[0] * b[1] + p3next;
			}
			int p4 = -1;
			int p4next = process(things, n, index + 1, rest - a[0] - c[0], dp);
			if (p4next != -1) {
				p4 = a[0] * a[1] + c[0] * c[1] + p4next;
			}
			int p5 = -1;
			int p5next = process(things, n, index + 1, rest - a[0] - b[0] - c[0], dp);
			if (p5next != -1) {
				p5 = a[0] * a[1] + b[0] * b[1] + c[0] * c[1] + p5next;
			}
			ans = Math.max(Math.max(Math.max(p1, p2), Math.max(p3, p4)), p5);
		}
		dp[index][rest] = ans;
		return ans;
	}

}

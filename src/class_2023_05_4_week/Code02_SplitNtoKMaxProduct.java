package class_2023_05_4_week;

// 来自华为
// 一个数字n，一定要分成k份
// 得到的乘积尽量大是多少
public class Code02_SplitNtoKMaxProduct {

	// 暴力递归
	// 一定能得到最优解
	public static int maxValue1(int n, int k) {
		if (k == 0 || n < k) {
			return -1;
		}
		return process1(n, k);
	}

	// 剩余的数字rest，一定要拆成j份，返回最大乘积
	public static int process1(int rest, int j) {
		if (j == 1) {
			return rest;
		}
		// 当前拆出来的一份是cur，
		// cur从1开始枚举，一直到rest
		// 剩下的rest - cur，要去分j-1份
		// 要保证 : 剩下的rest - cur 要 >= j-1，也就是剩下的要够分
		int ans = Integer.MIN_VALUE;
		for (int cur = 1; cur <= rest && (rest - cur) >= (j - 1); cur++) {
			int curAns = cur * process1(rest - cur, j - 1);
			ans = Math.max(ans, curAns);
		}
		return ans;
	}

	// 贪心的解
	// 这是最优解
	public static int maxValue2(int n, int k) {
		if (k == 0 || n < k) {
			return -1;
		}
		// 比如n = 10, k = 4
		// 最优解为 : 3 3 2 2
		// 贪心的思路 :
		// 10 / 4 = 2，也就是大概围绕2来划分，分成四个2
		// 10 % 4 = 2，也就是四个2里，可以有两个2升级成3
		// 得到 : 3 3 2 2
		// a : 大体分为k个a
		int a = n / k;
		// b : 可以有b个a，升级成a+1
		int b = n % k;
		int ans = 1;
		for (int i = 0; i < b; i++) {
			ans *= a + 1;
		}
		for (int i = 0; i < k - b; i++) {
			ans *= a;
		}
		return ans;
	}

	public static void main(String[] args) {
		// 可以自己来用参数实验
		int n = 13;
		int k = 4;
		System.out.println(maxValue1(n, k));
		System.out.println(maxValue2(n, k));
	}

}

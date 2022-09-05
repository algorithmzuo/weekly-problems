package class_2022_09_3_week;

import java.util.Arrays;

// 来自美团
// 有三个题库A、B、C，每个题库均有n道题目，且题目都是从1到n进行编号
// 每个题目都有一个难度值
// 题库A中第i个题目的难度为ai
// 题库B中第i个题目的难度为bi
// 题库C中第i个题目的难度为ci
// 小美准备组合出一套试题，试题共有三道题，
// 第一题来自题库A，第二题来自题库B，第三题来自题库C
// 试题要求题目难度递增，且梯度不能过大
// 具体地说，第二题的难度必须大于第一题的难度，但不能大于第一题难度的两倍
// 第三题的难度必须大于第二题的难度，但不能大于第二题难度的两倍
// 小美想知道在满足上述要求下，有多少种不同的题目组合
//（三道题目中只要存在一道题目不同，则两个题目组合就视为不同
// 输入描述 第一行一个正整数n, 表示每个题库的题目数量
// 第二行为n个正整数a1, a2,...... an，其中ai表示题库A中第i个题目的难度值
// 第三行为n个正整数b1, b2,...... bn，其中bi表示题库B中第i个题目的难度值
// 第四行为n个正整数c1, c2,...... cn，其中ci表示题库C中第i个题目的难度值
// 1 ≤ n ≤ 20000, 1 ≤ ai, bi, ci ≤ 10^9。
public class Code03_ExaminationPaperWays {

	// 暴力方法
	// 时间复杂度O(N^3)
	// 为了验证
	public static int ways1(int[] a, int[] b, int[] c) {
		int n = a.length;
		Arrays.sort(a);
		Arrays.sort(b);
		Arrays.sort(c);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n && b[j] <= a[i] * 2; j++) {
				if (b[j] > a[i]) {
					for (int k = 0; k < n && c[k] <= b[j] * 2; k++) {
						if (c[k] > b[j]) {
							ans++;
						}
					}
				}
			}
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int ways2(int[] a, int[] b, int[] c) {
		int n = a.length;
		Arrays.sort(a);
		Arrays.sort(b);
		Arrays.sort(c);
		int[] help = new int[n];
		for (int i = 0, l = -1, r = 0; i < n; i++) {
			while (l + 1 < n && c[l + 1] <= b[i]) {
				l++;
			}
			while (r < n && c[r] <= b[i] * 2) {
				r++;
			}
			help[i] = Math.max(r - l - 1, 0);
		}
		for (int i = 1; i < n; i++) {
			help[i] += help[i - 1];
		}
		int ans = 0;
		for (int i = 0, l = -1, r = 0; i < n; i++) {
			while (l + 1 < n && b[l + 1] <= a[i]) {
				l++;
			}
			while (r < n && b[r] <= a[i] * 2) {
				r++;
			}
			if (r - l - 1 > 0) {
				ans += sum(help, l + 1, r - 1);
			}
		}
		return ans;
	}

	public static int sum(int[] help, int l, int r) {
		return l == 0 ? help[r] : help[r] - help[l - 1];
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int testTimes = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] a = randomArray(n, V);
			int[] b = randomArray(n, V);
			int[] c = randomArray(n, V);
			int ans1 = ways1(a, b, c);
			int ans2 = ways2(a, b, c);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

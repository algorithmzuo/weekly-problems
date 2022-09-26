package class_2022_09_4_week;

import java.util.HashMap;
import java.util.HashSet;

// 来自字节
// 给定正数N，表示用户数量，用户编号从0~N-1
// 给定正数M，表示实验数量，实验编号从0~M-1
// 给定长度为N的二维数组A，
// A[i] = { a, b, c }表示，用户i报名参加了a号、b号、c号实验
// 给定正数Q，表示查询的条数
// 给定长度为Q的二维数组B，
// B[i] = { e, f }表示，第i条查询想知道e号、f号实验，一共有多少人(去重统计)
// 返回每一条查询的结果数组
// 数据描述 : 
// 1 <= N <= 10^5
// 1 <= M <= 10^2
// 1 <= Q <= 10^4
// 所有查询所列出的所有实验编号数量(也就是二维数组B，行*列的规模) <= 10^5
public class Code04_EveryQueryUsers {

	// 暴力方法
	// 为了验证
	public static int[] record1(int n, int m, int q, int[][] A, int[][] B) {
		HashMap<Integer, HashSet<Integer>> expUsersMap = new HashMap<>();
		for (int i = 0; i < m; i++) {
			expUsersMap.put(i, new HashSet<>());
		}
		for (int i = 0; i < n; i++) {
			for (int exp : A[i]) {
				expUsersMap.get(exp).add(i);
			}
		}
		int[] ans = new int[q];
		HashSet<Integer> help = new HashSet<>();
		for (int i = 0; i < q; i++) {
			help.clear();
			for (int exp : B[i]) {
				for (int user : expUsersMap.get(exp)) {
					help.add(user);
				}
			}
			ans[i] = help.size();
		}
		return ans;
	}

	// 正式方法
	public static int[] record2(int n, int m, int q, int[][] A, int[][] B) {
		int parts = (n + 31) / 32;
		int[][] bitMap = new int[m][parts];
		for (int i = 0; i < n; i++) {
			for (int exp : A[i]) {
				bitMap[exp][i / 32] |= 1 << (i % 32);
			}
		}
		int[] ans = new int[q];
		for (int i = 0; i < q; i++) {
			int all = 0;
			for (int j = 0; j < parts; j++) {
				int status = 0;
				for (int exp : B[i]) {
					status |= bitMap[exp][j];
				}
				all += countOnes(status);
			}
			ans[i] = all;
		}
		return ans;
	}

	// 大厂刷题班，32节，leetcode专题 : https://leetcode.com/problems/number-of-1-bits/
	public static int countOnes(int n) {
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}

	// 为了测试
	public static int[][] randomMatrix(int n, int m, int v) {
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans[i][j] = (int) (Math.random() * v);
			}
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int M = 20;
		int Q = 50;
		int testTime = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int m = (int) (Math.random() * M) + 1;
			int[][] A = randomMatrix(n, (int) (Math.random() * m) + 1, m);
			int q = (int) (Math.random() * Q) + 1;
			int[][] B = randomMatrix(q, (int) (Math.random() * m) + 1, m);
			int[] ans1 = record1(n, m, q, A, B);
			int[] ans2 = record2(n, m, q, A, B);
			boolean pass = true;
			for (int j = 0; j < q; j++) {
				if (ans1[j] != ans2[j]) {
					pass = false;
					break;
				}
			}
			if (!pass) {
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 100000;
		int m = 100;
		int[][] A = randomMatrix(n, m, m);
		int q = 10000;
		int c = 10;
		int[][] B = randomMatrix(q, c, m);
		System.out.println("用户数量 : " + n);
		System.out.println("实验数量 : " + m);
		System.out.println("用户参加的实验数量总和 : " + n * m);
		System.out.println("查询条数 : " + q);
		System.out.println("每条查询的实验数量 : " + c);
		System.out.println("所有查询所列出的所有实验编号数量 : " + q * c);
		long start = System.currentTimeMillis();
		record2(n, m, q, A, B);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}

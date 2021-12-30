package class_2021_12_5_week;

import java.util.ArrayList;

// 测试链接 : https://leetcode.com/problems/loud-and-rich/
public class Code01_LoudAndRich {

	// richer[i] = {a, b} a比b更有钱  a -> b
	// quiet[i] = k, i这个人安静值是k
	public static int[] loudAndRich(int[][] richer, int[] quiet) {
		int N = quiet.length;
		// a -> b
		// a -> c
		// b -> c
		// a : b c
		// b : c
		// nexts[0] = {5,7,3}
		// 0 : 5 7 3
		// 5最没钱的，
		// nexts[5] = { }
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			// 0 : {}
			// 1 : {}
			// n-1 : {}
			nexts.add(new ArrayList<>());
		}
		// 入度
		// 0 : 0
		// 1 : 2
		int[] degree = new int[N];
		for (int[] r : richer) {
			// [a,b]  a -> b
			nexts.get(r[0]).add(r[1]);
			degree[r[1]]++;
		}
		// 所有入度为0的点，入队列
		int[] zeroQueue = new int[N];
		int l = 0;
		int r = 0;
		for (int i = 0; i < N; i++) {
			if (degree[i] == 0) {
				zeroQueue[r++] = i;
			}
		}
		// ans[i] = j : 比i有钱的所有人里，j最安静
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = i;
		}
		while (l < r) { // 如果队列不空
			// 弹出一个入度为0的点
			int cur = zeroQueue[l++];
			// 1) 消除当前cur的影响！
			for (int next : nexts.get(cur)) {
				// cur : 比cur有钱，最安静的！ans[cur]
				if (quiet[ans[next]] > quiet[ans[cur]]) {
					ans[next] = ans[cur];
				}
				if (--degree[next] == 0) {
					zeroQueue[r++] = next;
				}
			}
		}
		return ans;
	}

}

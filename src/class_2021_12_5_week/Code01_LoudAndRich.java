package class_2021_12_5_week;

import java.util.ArrayList;

// 测试链接 : https://leetcode.com/problems/loud-and-rich/
public class Code01_LoudAndRich {

	public static int[] loudAndRich(int[][] richer, int[] quiet) {
		int N = quiet.length;
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			nexts.add(new ArrayList<>());
		}
		int[] degree = new int[N];
		for (int[] r : richer) {
			nexts.get(r[0]).add(r[1]);
			degree[r[1]]++;
		}
		int[] zeroQueue = new int[N];
		int l = 0;
		int r = 0;
		for (int i = 0; i < N; i++) {
			if (degree[i] == 0) {
				zeroQueue[r++] = i;
			}
		}
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = i;
		}
		while (l < r) {
			int cur = zeroQueue[l++];
			for (int next : nexts.get(cur)) {
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

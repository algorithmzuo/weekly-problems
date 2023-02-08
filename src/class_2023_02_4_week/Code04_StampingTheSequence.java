package class_2023_02_4_week;

import java.util.ArrayList;
import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/stamping-the-sequence/
public class Code04_StampingTheSequence {

	public static int[] movesToStamp(String stamp, String target) {
		char[] s = stamp.toCharArray();
		char[] t = target.toCharArray();
		int m = s.length;
		int n = t.length;
		int[] inDegrees = new int[n - m + 1];
		Arrays.fill(inDegrees, m);
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		int[] queue = new int[n - m + 1];
		int l = 0;
		int r = 0;
		for (int i = 0; i <= n - m; i++) {
			for (int j = 0; j < m; j++) {
				if (t[i + j] == s[j]) {
					if (--inDegrees[i] == 0) {
						queue[r++] = i;
					}
				} else {
					graph.get(i + j).add(i);
				}
			}
		}
		boolean[] visited = new boolean[n];
		int[] path = new int[n - m + 1];
		int size = 0;
		while (l < r) {
			int cur = queue[l++];
			path[size++] = cur;
			for (int i = 0; i < m; i++) {
				if (!visited[cur + i]) {
					visited[cur + i] = true;
					for (int next : graph.get(cur + i)) {
						if (--inDegrees[next] == 0) {
							queue[r++] = next;
						}
					}
				}
			}
		}
		if (size != n - m + 1) {
			return new int[0];
		}
		for (int i = 0, j = size - 1; i < j; i++, j--) {
			int tmp = path[i];
			path[i] = path[j];
			path[j] = tmp;
		}
		return path;
	}

}

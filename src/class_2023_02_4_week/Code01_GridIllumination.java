package class_2023_02_4_week;

import java.util.HashMap;
import java.util.HashSet;

// 测试链接 : https://leetcode.cn/problems/grid-illumination/
public class Code01_GridIllumination {

	public static int[][] move = { { 0, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 }, { -1, -1 }, { -1, 1 }, { 1, 0 }, { 1, -1 },
			{ 1, 1 } };

	public static int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
		long limit = n;
		HashMap<Integer, Integer> row = new HashMap<>();
		HashMap<Integer, Integer> col = new HashMap<>();
		HashMap<Integer, Integer> leftUpDiag = new HashMap<>();
		HashMap<Integer, Integer> rightUpDiag = new HashMap<>();
		HashSet<Long> points = new HashSet<>();
		for (int[] p : lamps) {
			if (points.add(limit * p[0] + p[1])) {
				row.put(p[0], row.getOrDefault(p[0], 0) + 1);
				col.put(p[1], col.getOrDefault(p[1], 0) + 1);
				leftUpDiag.put(p[0] - p[1], leftUpDiag.getOrDefault(p[0] - p[1], 0) + 1);
				rightUpDiag.put(p[0] + p[1], rightUpDiag.getOrDefault(p[0] + p[1], 0) + 1);

			}
		}
		int[] ans = new int[queries.length];
		int ansi = 0;
		for (int[] q : queries) {
			ans[ansi++] = (row.containsKey(q[0]) || col.containsKey(q[1]) || leftUpDiag.containsKey(q[0] - q[1])
					|| rightUpDiag.containsKey(q[0] + q[1])) ? 1 : 0;
			for (int[] m : move) {
				int r = q[0] + m[0];
				int c = q[1] + m[1];
				int lu = r - c;
				int ru = r + c;
				if (r < 0 || r >= n || c < 0 || c >= n) {
					continue;
				}
				if (points.contains(limit * r + c)) {
					points.remove(limit * r + c);
					minusOrRemove(row, r);
					minusOrRemove(col, c);
					minusOrRemove(leftUpDiag, lu);
					minusOrRemove(rightUpDiag, ru);
				}
			}
		}
		return ans;
	}

	public static void minusOrRemove(HashMap<Integer, Integer> map, int key) {
		if (map.get(key) == 1) {
			map.remove(key);
		} else {
			map.put(key, map.get(key) - 1);
		}
	}

}

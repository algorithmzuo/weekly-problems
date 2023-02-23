package class_2023_02_4_week;

import java.util.HashMap;
import java.util.HashSet;

// 在大小为 n x n 的网格 grid 上，每个单元格都有一盏灯，最初灯都处于 关闭 状态
// 给你一个由灯的位置组成的二维数组 lamps
// 其中 lamps[i] = [rowi, coli] 表示 打开 位于 grid[rowi][coli] 的灯
// 即便同一盏灯可能在 lamps 中多次列出，不会影响这盏灯处于 打开 状态
// 当一盏灯处于打开状态，它将会照亮 自身所在单元格
// 以及同一 行 、同一 列 和两条 对角线 上的 所有其他单元格
// 另给你一个二维数组 queries ，其中 queries[j] = [rowj, colj]
// 对于第 j 个查询，如果单元格 [rowj, colj] 是被照亮的
// 则查询结果为 1 ，否则为 0 。在第 j 次查询之后 [按照查询的顺序]
// 关闭 位于单元格 grid[rowj][colj] 上
// 及相邻 8 个方向上（与单元格 grid[rowi][coli] 共享角或边）的任何灯
// 返回一个整数数组 ans 作为答案， ans[j] 应等于第 j 次查询 queries[j] 的结果
// 1 表示照亮，0 表示未照亮
// 测试链接 : https://leetcode.cn/problems/grid-illumination/
public class Code03_GridIllumination {

	public static int[][] move = { 
			{ 0, 0 },
			{ 0, -1 },
			{ 0, 1 },
			{ -1, 0 },
			{ -1, -1 },
			{ -1, 1 },
			{ 1, 0 },
			{ 1, -1 },
			{ 1, 1 } };

	// n -> 大区域是 n * n
	// lamps
	// queries
	public static int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
		long limit = n;
		HashMap<Integer, Integer> row = new HashMap<>();
		HashMap<Integer, Integer> col = new HashMap<>();
		HashMap<Integer, Integer> leftUpDiag = new HashMap<>();
		HashMap<Integer, Integer> rightUpDiag = new HashMap<>();
		// (x,y) -> x*列的数量 + y -> 得到的数字代表一个点
		HashSet<Long> points = new HashSet<>();
		for (int[] p : lamps) {
			// 所有的灯，注册！
			// 如果之前加过，add方法返回fasle
			// 如果之前没加过，add方法返回true
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
			// q[0], q[1]
			ans[ansi++] = (row.containsKey(q[0]) 
					|| col.containsKey(q[1])
					|| leftUpDiag.containsKey(q[0] - q[1])
					|| rightUpDiag.containsKey(q[0] + q[1])) ? 1 : 0;
			for (int[] m : move) {
				int r = q[0] + m[0];
				int c = q[1] + m[1];
				// (r,c)位置，有灯就关，没灯算了！
				int lu = r - c;
				int ru = r + c;
				if (r < 0 || r >= n || c < 0 || c >= n) {
					continue;
				}
				// r,c -> 列数 * r + c
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

package class_2023_02_3_week;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

// 欧拉路径问题
// 测试链接 : https://leetcode.com/problems/valid-arrangement-of-pairs/
public class Code01_ValidArrangementOfPairs {

	public static int[][] validArrangement(int[][] pairs) {
		HashMap<Integer, LinkedList<Integer>> nexts = new HashMap<>();
		HashMap<Integer, Integer> degrees = new HashMap<>();
		for (int[] pair : pairs) {
			nexts.putIfAbsent(pair[0], new LinkedList<>());
			nexts.putIfAbsent(pair[1], new LinkedList<>());
			degrees.putIfAbsent(pair[0], 0);
			degrees.putIfAbsent(pair[1], 0);
		}
		for (int[] pair : pairs) {
			nexts.get(pair[0]).add(pair[1]);
			degrees.put(pair[0], degrees.get(pair[0]) - 1);
			degrees.put(pair[1], degrees.get(pair[1]) + 1);
		}
		int any = pairs[0][0];
		Integer from = null;
		for (Integer cur : degrees.keySet()) {
			if (degrees.get(cur) == -1) {
				from = cur;
				break;
			}
		}
		ArrayList<int[]> record = new ArrayList<>();
		dfs(from != null ? from : any, nexts, record);
		int[][] ans = new int[record.size()][2];
		for (int i = 0, j = record.size() - 1; j >= 0; i++, j--) {
			ans[j][0] = record.get(i)[0];
			ans[j][1] = record.get(i)[1];
		}
		return ans;
	}

	public static void dfs(int from, HashMap<Integer, LinkedList<Integer>> nexts, ArrayList<int[]> record) {
		LinkedList<Integer> next = nexts.get(from);
		while (!next.isEmpty()) {
			int to = next.poll();
			dfs(to, nexts, record);
			record.add(new int[] { from, to });
		}
	}

}

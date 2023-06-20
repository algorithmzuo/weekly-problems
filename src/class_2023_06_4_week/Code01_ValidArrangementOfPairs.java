package class_2023_06_4_week;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

// 欧拉路径问题
// 给你一个下标从 0 开始的二维整数数组 pairs
// 其中 pairs[i] = [starti, endi]
// 如果 pairs 的一个重新排列
// 满足对每一个下标 i （ 1 <= i < pairs.length ）
// 都有 endi-1 == starti ，
// 那么我们就认为这个重新排列是 pairs 的一个 合法重新排列
// 请你返回 任意一个 pairs 的合法重新排列
// 注意：数据保证至少存在一个 pairs 的合法重新排列
// 测试链接 : https://leetcode.cn/problems/valid-arrangement-of-pairs/
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

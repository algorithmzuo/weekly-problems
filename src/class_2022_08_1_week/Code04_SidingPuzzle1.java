package class_2022_08_1_week;

import java.util.HashSet;
import java.util.PriorityQueue;

// 在一个 2 * 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 
// 以及一块空缺用 0 来表示。一次 移动 定义为选择 0 与一个相邻的数字（上下左右）进行交换.
// 最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。
// 给出一个谜板的初始状态 board ，
// 返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。
// 测试链接 : https://leetcode.cn/problems/sliding-puzzle/
public class Code04_SidingPuzzle1 {

	public static int b6 = 100000;

	public static int b5 = 10000;

	public static int b4 = 1000;

	public static int b3 = 100;

	public static int b2 = 10;

	public static int[] nexts = new int[3];

	public static int[][] end = { { 1, 2 }, { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 }, { 1, 1 } };

	public static int slidingPuzzle(int[][] m) {
		HashSet<Integer> set = new HashSet<>();
		int from = m[0][0] * b6 + m[0][1] * b5 + m[0][2] * b4 + m[1][0] * b3 + m[1][1] * b2 + m[1][2];
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[0] + a[1]) - (b[0] + b[1]));
		heap.add(new int[] { 0, distance(from), from });
		int ans = -1;
		while (!heap.isEmpty()) {
			int[] arr = heap.poll();
			int distance = arr[0];
			int cur = arr[2];
			if (set.contains(cur)) {
				continue;
			}
			if (cur == 123450) {
				ans = distance;
				break;
			}
			set.add(cur);
			int nextSize = nexts(cur);
			for (int i = 0; i < nextSize; i++) {
				int next = nexts[i];
				if (!set.contains(next)) {
					heap.add(new int[] { distance + 1, distance(next), next });
				}
			}
		}
		return ans;
	}

	public static int nexts(int from) {
		int a = from / b6;
		int b = (from / b5) % 10;
		int c = (from / b4) % 10;
		int d = (from / b3) % 10;
		int e = (from / b2) % 10;
		int f = from % 10;
		if (a == 0) {
			nexts[0] = from + (b - a) * b6 + (a - b) * b5;
			nexts[1] = from + (d - a) * b6 + (a - d) * b3;
			return 2;
		} else if (b == 0) {
			nexts[0] = from + (a - b) * b5 + (b - a) * b6;
			nexts[1] = from + (c - b) * b5 + (b - c) * b4;
			nexts[2] = from + (e - b) * b5 + (b - e) * b2;
			return 3;
		} else if (c == 0) {
			nexts[0] = from + (b - c) * b4 + (c - b) * b5;
			nexts[1] = from + (f - c) * b4 + (c - f);
			return 2;
		} else if (d == 0) {
			nexts[0] = from + (a - d) * b3 + (d - a) * b6;
			nexts[1] = from + (e - d) * b3 + (d - e) * b2;
			return 2;
		} else if (e == 0) {
			nexts[0] = from + (b - e) * b2 + (e - b) * b5;
			nexts[1] = from + (d - e) * b2 + (e - d) * b3;
			nexts[2] = from + (f - e) * b2 + (e - f);
			return 3;
		} else {
			nexts[0] = from + (e - f) + (f - e) * b2;
			nexts[1] = from + (c - f) + (f - c) * b4;
			return 2;
		}
	}

	public static int distance(int num) {
		int ans = end[num / b6][0] + end[num / b6][1];
		ans += end[(num / b5) % 10][0] + Math.abs(end[(num / b5) % 10][1] - 1);
		ans += end[(num / b4) % 10][0] + Math.abs(end[(num / b4) % 10][1] - 2);
		ans += Math.abs(end[(num / b3) % 10][0] - 1) + end[(num / b3) % 10][1];
		ans += Math.abs(end[(num / b2) % 10][0] - 1) + Math.abs(end[(num / b2) % 10][1] - 1);
		ans += Math.abs(end[num % 10][0] - 1) + Math.abs(end[num % 10][1] - 2);
		return ans;
	}

}

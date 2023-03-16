package class_2023_04_1_week;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

//  测试链接 : https://leetcode.cn/problems/minimum-knight-moves/
public class Code01_MinimumKnightMoves {

	// A*算法的实现
	public static int minKnightMoves(int x, int y) {
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[0] + a[1]) - (b[0] + b[1]));
		HashMap<Integer, HashSet<Integer>> closed = new HashMap<>();
		heap.add(new int[] { 0, distance(0, 0, x, y), 0, 0 });
		int ans = Integer.MAX_VALUE;
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int cost = cur[0];
			int row = cur[2];
			int col = cur[3];
			if (isPoped(closed, row, col)) {
				continue;
			}
			if (row == x && col == y) {
				ans = cost;
				break;
			}
			close(closed, row, col);
			add(cost + 1, row + 2, col + 1, x, y, closed, heap);
			add(cost + 1, row + 1, col + 2, x, y, closed, heap);
			add(cost + 1, row - 1, col + 2, x, y, closed, heap);
			add(cost + 1, row - 2, col + 1, x, y, closed, heap);
			add(cost + 1, row - 2, col - 1, x, y, closed, heap);
			add(cost + 1, row - 1, col - 2, x, y, closed, heap);
			add(cost + 1, row + 1, col - 2, x, y, closed, heap);
			add(cost + 1, row + 2, col - 1, x, y, closed, heap);
		}
		return ans;
	}

	// 如果之间弹出过(r,c)点，返回true
	// 如果之间没弹出过(r,c)点，返回false
	public static boolean isPoped(HashMap<Integer, HashSet<Integer>> closed, int r, int c) {
		return closed.containsKey(r) && closed.get(r).contains(c);
	}

	// 把(r,c)点加入closed表
	public static void close(HashMap<Integer, HashSet<Integer>> closed, int r, int c) {
		if (!closed.containsKey(r)) {
			closed.put(r, new HashSet<>());
		}
		closed.get(r).add(c);
	}

	public static void add(int cost, int r, int c, int x, int y, HashMap<Integer, HashSet<Integer>> closed,
			PriorityQueue<int[]> heap) {
		if (!isPoped(closed, r, c)) {
			heap.add(new int[] { cost, distance(r, c, x, y), r, c });
		}
	}

	// 曼哈顿距离 / 3
	// 为什么要定成这个
	// 因为估计函数的估计代价 要小于等于 真实代价
	// 我们知道，走"日"字是一次蹦3个曼哈顿距离
	// 如果A点到B点的曼哈顿距离是3，不是任意的A和B都能通过走"日"的方式，一步达到的
	// 所以真实代价 >= 曼哈顿距离 / 3
	// 那就把 曼哈顿距离 / 3，定成估计函数
	public static int distance(int r, int c, int x, int y) {
		return (Math.abs(x - r) + Math.abs(y - c)) / 3;
	}

}

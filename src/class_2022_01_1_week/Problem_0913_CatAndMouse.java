package class_2022_01_1_week;

import java.util.ArrayList;

// 本题是Code02_CatAndMoused的补充code
// leetcode官网增加了测试用例
// 进一步说明，贪心的方式其实是没有道理的
// 不过原来贪心的方法，把源代码第35行
// int limit = (n << 1) + 3;
// 改成：
// int limit = (n << 1) + 12;
// 依然继续能通过
// 但是贪心依然是没道理的，人为构造的话总能找出反例来
// 所以补充了一个拓扑排序的解法
public class Problem_0913_CatAndMouse {

	public static final int MOUSE_TURN = 0, CAT_TURN = 1;
	public static final int DRAW = 0, MOUSE_WIN = 1, CAT_WIN = 2;

	public int catMouseGame(int[][] graph) {
		int n = graph.length;
		int[][][] indegree = new int[n][n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n; j++) {
				indegree[i][j][MOUSE_TURN] = graph[i].length;
				indegree[i][j][CAT_TURN] = graph[j].length;
			}
		}
		for (int i = 0; i < n; i++) {
			for (int node : graph[0]) {
				indegree[i][node][CAT_TURN]--;
			}
		}
		int[][][] ans = new int[n][n][2];
		int[][] queue = new int[n * n * 2][];
		int left = 0;
		int right = 0;
		for (int j = 1; j < n; j++) {
			ans[0][j][MOUSE_TURN] = MOUSE_WIN;
			ans[0][j][CAT_TURN] = MOUSE_WIN;
			queue[right++] = new int[] { 0, j, MOUSE_TURN };
			queue[right++] = new int[] { 0, j, CAT_TURN };
		}
		for (int i = 1; i < n; i++) {
			ans[i][i][MOUSE_TURN] = CAT_WIN;
			ans[i][i][CAT_TURN] = CAT_WIN;
			queue[right++] = new int[] { i, i, MOUSE_TURN };
			queue[right++] = new int[] { i, i, CAT_TURN };
		}
		while (left != right) {
			int[] cur = queue[left++];
			int mouse = cur[0], cat = cur[1], turn = cur[2];
			int curAns = ans[mouse][cat][turn];
			for (int[] prevState : preStatus(graph, mouse, cat, turn)) {
				int prevMouse = prevState[0], prevCat = prevState[1], prevTurn = prevState[2];
				if (ans[prevMouse][prevCat][prevTurn] == DRAW) {
					boolean canWin = (curAns == MOUSE_WIN && prevTurn == MOUSE_TURN)
							|| (curAns == CAT_WIN && prevTurn == CAT_TURN);
					if (canWin) {
						ans[prevMouse][prevCat][prevTurn] = curAns;
						queue[right++] = new int[] { prevMouse, prevCat, prevTurn };
					} else {
						if (--indegree[prevMouse][prevCat][prevTurn] == 0) {
							int lose = prevTurn == MOUSE_TURN ? CAT_WIN : MOUSE_WIN;
							ans[prevMouse][prevCat][prevTurn] = lose;
							queue[right++] = new int[] { prevMouse, prevCat, prevTurn };
						}
					}
				}
			}
		}
		return ans[1][2][MOUSE_TURN];
	}

	public ArrayList<int[]> preStatus(int[][] graph, int mouse, int cat, int turn) {
		ArrayList<int[]> prevStates = new ArrayList<int[]>();
		int prevTurn = turn == MOUSE_TURN ? CAT_TURN : MOUSE_TURN;
		if (prevTurn == MOUSE_TURN) {
			for (int prev : graph[mouse]) {
				prevStates.add(new int[] { prev, cat, prevTurn });
			}
		} else {
			for (int prev : graph[cat]) {
				if (prev != 0) {
					prevStates.add(new int[] { mouse, prev, prevTurn });
				}
			}
		}
		return prevStates;
	}

}

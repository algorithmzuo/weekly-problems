package class_2023_07_2_week;

// 测试链接 : https://leetcode.com/problems/maximum-employees-to-be-invited-to-a-meeting/
public class Code04_MaximumEmployeesToBeInvitedToAMeeting {

	public static int maximumInvitations(int[] favorite) {
		int[][] loved = beLoved(favorite);
		int[] degree = degree(loved);
		int n = favorite.length;
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		for (int i = 0; i < n; i++) {
			if (degree[i] == 0) {
				queue[r++] = i;
			}
		}
		boolean[] zeroVisited = new boolean[n];
		int visited = 0;
		while (l < r) {
			int cur = queue[l++];
			zeroVisited[cur] = true;
			visited++;
			if (--degree[favorite[cur]] == 0) {
				queue[r++] = favorite[cur];
			}
		}
		if (visited == n) {
			return 0;
		}
		boolean[] cycleVisited = new boolean[n];
		int arrangeTwoCycle = 0;
		int arrangeMoreCycle = 0;
		for (int i = 0; i < n; i++) {
			if (!zeroVisited[i] && !cycleVisited[i]) {
				cycleVisited[i] = true;
				if (favorite[favorite[i]] == i) {
					cycleVisited[favorite[i]] = true;
					arrangeTwoCycle += maxFollow(i, zeroVisited, loved) + maxFollow(favorite[i], zeroVisited, loved);
				} else {
					int cur = favorite[i];
					int curAns = 1;
					while (cur != i) {
						cycleVisited[cur] = true;
						curAns++;
						cur = favorite[cur];
					}
					arrangeMoreCycle = Math.max(arrangeMoreCycle, curAns);
				}
			}
		}
		return Math.max(arrangeTwoCycle, arrangeMoreCycle);
	}

	// 生成被喜欢表
	public static int[][] beLoved(int[] favorite) {
		int n = favorite.length;
		int[] size = new int[n];
		for (int love : favorite) {
			size[love]++;
		}
		int[][] loved = new int[n][];
		for (int i = 0; i < n; i++) {
			loved[i] = new int[size[i]];
		}
		for (int i = 0; i < n; i++) {
			loved[favorite[i]][--size[favorite[i]]] = i;
		}
		return loved;
	}

	// 求每个点的入度
	public static int[] degree(int[][] loved) {
		int n = loved.length;
		int[] degree = new int[n];
		for (int i = 0; i < n; i++) {
			degree[i] = loved[i].length;
		}
		return degree;
	}

	// cur不在环上的追随者链条最大长度
	public static int maxFollow(int cur, boolean[] zeroed, int[][] from) {
		if (from[cur].length == 0) {
			return 1;
		}
		int ans = 0;
		for (int pre : from[cur]) {
			if (zeroed[pre]) {
				ans = Math.max(ans, maxFollow(pre, zeroed, from));
			}
		}
		return ans + 1;
	}

}

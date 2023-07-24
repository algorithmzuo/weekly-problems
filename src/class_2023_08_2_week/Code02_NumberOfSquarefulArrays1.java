package class_2023_08_2_week;

import java.util.Arrays;
import java.util.ArrayList;

// 测试链接 : https://leetcode.cn/problems/number-of-squareful-arrays/
public class Code02_NumberOfSquarefulArrays1 {

	public static int MAXN = 13;

	public static int[] f = new int[MAXN];
	static {
		f[0] = 1;
		for (int i = 1; i < MAXN; ++i) {
			f[i] = i * f[i - 1];
		}
	}

	public static int numSquarefulPerms(int[] nums) {
		int n = nums.length;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		int[][] dp = new int[n][1 << n];
		for (int i = 0; i < n; ++i) {
			graph.add(new ArrayList<>());
			Arrays.fill(dp[i], -1);
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1, s; j < n; j++) {
				s = (int) (Math.sqrt(nums[i] + nums[j]));
				if (s * s == nums[i] + nums[j]) {
					graph.get(i).add(j);
					graph.get(j).add(i);
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < n; ++i) {
			ans += dfs(graph, i, 1 << i, n, dp);
		}
		// 去重的关键逻辑
		Arrays.sort(nums);
		int start = 0;
		for (int end = 1; end < n; end++) {
			if (nums[start] != nums[end]) {
				ans /= f[end - start];
				start = end;
			}
		}
		ans /= f[n - start];
		return ans;
	}

	public static int dfs(ArrayList<ArrayList<Integer>> graph, int i, int s, int n, int[][] dp) {
		if (s == (1 << n) - 1) {
			return 1;
		}
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		int ans = 0;
		for (int next : graph.get(i)) {
			if ((s & (1 << next)) == 0) {
				ans += dfs(graph, next, s | (1 << next), n, dp);
			}
		}
		dp[i][s] = ans;
		return ans;
	}

}

package class_2023_07_4_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/number-of-squareful-arrays/
public class Code03_NumberOfSquarefulArrays2 {

	public static int MAXN = 13;

	// 阶乘表
	public static int[] f = new int[MAXN];
	static {
		f[0] = 1;
		for (int i = 1; i < MAXN; ++i) {
			f[i] = i * f[i - 1];
		}
	}

	// 链式前向星建图
	public static int[] head = new int[MAXN];
	public static int[] to = new int[MAXN * MAXN];
	public static int[] next = new int[MAXN * MAXN];
	public static int cnt;
	// 动态规划表
	public static int[][] dp = new int[MAXN][1 << MAXN];

	public static void build(int n) {
		cnt = 0;
		for (int i = 0; i < n; i++) {
			head[i] = -1;
			Arrays.fill(dp[i], 0, 1 << n, -1);
		}
	}

	public static void addEdge(int i, int j) {
		int top = head[i];
		head[i] = cnt;
		to[cnt] = j;
		next[cnt] = top;
		cnt++;
	}

	public static int numSquarefulPerms(int[] nums) {
		int n = nums.length;
		build(n);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1, s; j < n; j++) {
				s = (int) (Math.sqrt(nums[i] + nums[j]));
				if (s * s == nums[i] + nums[j]) {
					addEdge(i, j);
					addEdge(j, i);
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < n; ++i) {
			ans += dfs(i, 1 << i, n);
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

	public static int dfs(int i, int s, int n) {
		if (s == (1 << n) - 1) {
			return 1;
		}
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		int ans = 0;
		for (int edge = head[i]; edge != -1; edge = next[edge]) {
			if ((s & (1 << to[edge])) == 0) {
				ans += dfs(to[edge], s | (1 << to[edge]), n);
			}
		}
		dp[i][s] = ans;
		return ans;
	}

}

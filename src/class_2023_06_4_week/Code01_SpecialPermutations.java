package class_2023_06_4_week;

// 给你一个下标从 0 开始的整数数组 nums ，它包含 n 个 互不相同 的正整数
// 如果 nums 的一个排列满足以下条件，我们称它是一个特别的排列
// 对于 0 <= i < n - 1 的下标 i
// 要么 nums[i] % nums[i+1] == 0
// 要么 nums[i+1] % nums[i] == 0
// 请你返回特别排列的总数目，由于答案可能很大，请将它对 1000000007 取余 后返回
// 测试链接 : https://leetcode.cn/problems/special-permutations/
public class Code01_SpecialPermutations {

	public static int specialPerm(int[] nums) {
		int n = nums.length;
		int[][] dp = new int[1 << n][n];
		for (int i = 0; i < (1 << n); i++) {
			for (int j = 0; j < n; j++) {
				dp[i][j] = -1;
			}
		}
		return process(nums, n, 0, 0, dp);
	}

	public static int mod = 1000000007;
	
	// a  : 4 7 9 2 1 ...  固定数组
	//      0 1 2 3 4 ...
	// 4 2 1 9 
	// s :           1 1 1 0 1
	//           6 5 4 3 2 1 0
	// p = 2
	// 潜台词 : 之前选的数字，一定相邻两个数，都合法！
	// 返回值 : 根据上面的情况，继续把所有数字选完！返回有多少合法的！数量！
	// s : 最多14位，2^14 = 16384
	// p : 数组下标，0~13, 14种
	// 16384 * 14 = 229376 * 14 -> 3 211 264
	public static int process(int[] a, int n, int s, int p, int[][] dp) {
		if (dp[s][p] != -1) {
			return dp[s][p];
		}
		// 方法数
		int ans = 0;
		// n = 7
		// s : 01111111
		if (s == (1 << n) - 1) {
			ans = 1;
		} else {
			// s : 哪些下标上的数已经选了
			// p : 选的数中，最后一个数来自哪个下标
			// 当前该怎么挑选数字？
			// arr[p] （前）   现在 x
			// 1) arr[p]  % x == 0
			// 2) x % arr[p] == 0
			for (int i = 0; i < n; i++) {
				// 0位置的数，能不能做当前
				// 1位置的数，能不能做当前
				// 2位置的数，能不能做当前
				// 3位置的数，能不能做当前
				if (s == 0 // 之前没有选择过数字，当然现在随意
						||
				((s & (1 << i)) == 0 && (a[p] % a[i] == 0 || a[i] % a[p] == 0))) {
					ans = (ans + process(a, n, s | (1 << i), i, dp)) % mod;
				}
			}
		}
		dp[s][p] = ans;
		return ans;
	}

}

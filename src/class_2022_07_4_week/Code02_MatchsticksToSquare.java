package class_2022_07_4_week;

// 你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。
// 你要用 所有的火柴棍 拼成一个正方形。
// 你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
// 如果你能拼出正方形，则返回 true ，否则返回 false 。
// 测试链接 : https://leetcode.cn/problems/matchsticks-to-square/
public class Code02_MatchsticksToSquare {

	// 利用数组里的火柴，必须都使用
	// 能不能拼出正方形
	public static boolean yesOrNo(int[] arr) {
		long sum = 0;
		for (int num : arr) {
			sum += num;
		}
		if (sum % 4 != 0) {
			return false;
		}
		long len = sum / 4;
		// 最多15根
		// 00000000000.....000000000000000
		return process(arr, 0, 0, len, 4);
	}

	// 所有火柴都在arr里
	// 哪些火柴用了、哪些火柴没用，的状态都在status里
	// sum: 当前耕耘的这条边，长度已经达成了sum
	// len: 固定参数，每条边必须都达成这个长度
	// edges: 还剩几条边，没填完
	// 返回：最终能不能达成正方形的目标
	// 看似，三个可变参数status, sum, edges
	// 其实，只有一个，status
	// 15位状态，2^15 -> 32...
	public static boolean process(int[] arr, int status, long sum, long len, int edges) {
		if (edges == 0) {
			return status == (1 << arr.length) - 1 ? true : false;
		}
		// 剩下边 edges > 0
		boolean ans = false;
		for (int i = 0; i < arr.length; i++) { // 当前可以选择的边，全试一遍！
			// 当前的i就是火柴编号！
			if ((status & (1 << i)) == 0) {
				if (sum + arr[i] <= len) {
					// 当前的边已经耕耘长度sum + 当前边的长度 不能超过len！
					if (sum + arr[i] < len) {
						ans = process(arr, status | (1 << i), sum + arr[i], len, edges);
					} else { // sum + arr[i] == len
						ans = process(arr, status | (1 << i), 0, len, edges - 1);
					}
				}
			}
			if (ans) {
				break;
			}
		}
		return ans;
	}

	// 利用数组里的火柴，必须都使用
	// 能不能拼出正方形
	public static boolean yesOrNo2(int[] arr) {
		long sum = 0;
		for (int num : arr) {
			sum += num;
		}
		if (sum % 4 != 0) {
			return false;
		}
		long len = sum / 4;
		// dp[status] =
		int[] dp = new int[1 << arr.length];
		// dp[status] == 0 没算过！
		// dp[status] == 1 算过，结果是true
		// dp[status] == -1 算过，结果是false

		return process2(arr, 0, 0, len, 4, dp);
	}

	// 所有火柴都在arr里
	// 哪些火柴用了、哪些火柴没用，的状态都在status里
	// sum: 当前耕耘的这条边，长度已经达成了sum
	// len: 固定参数，每条边必须都达成这个长度
	// edges: 还剩几条边，没填完
	// 返回：最终能不能达成正方形的目标
	// 看似，三个可变参数status, sum, edges
	// 其实，只有一个，status
	// 15位状态，2^15 -> 32...
	public static boolean process2(int[] arr, int status, long sum, long len, int edges, int[] dp) {
		if (edges == 0) {
			return status == (1 << arr.length) - 1 ? true : false;
		}
		// 缓存命中
		if (dp[status] != 0) {
			return dp[status] == 1;
		}
		// 剩下边 edges > 0
		boolean ans = false;
		for (int i = 0; i < arr.length; i++) { // 当前可以选择的边，全试一遍！
			// 当前的i就是火柴编号！
			if ((status & (1 << i)) == 0) {
				if (sum + arr[i] <= len) {
					// 当前的边已经耕耘长度sum + 当前边的长度 不能超过len！
					if (sum + arr[i] < len) {
						ans = process2(arr, status | (1 << i), sum + arr[i], len, edges, dp);
					} else { // sum + arr[i] == len
						ans = process2(arr, status | (1 << i), 0, len, edges - 1, dp);
					}
				}
			}
			if (ans) {
				break;
			}
		}
		// ans == true dp[status] = 1
		// ans == false dp[status] = -1
		dp[status] = ans ? 1 : -1;
		return ans;
	}

	public static boolean makesquare(int[] matchsticks) {
		int sum = 0;
		for (int num : matchsticks) {
			sum += num;
		}
		if ((sum & 3) != 0) {
			return false;
		}
		int[] dp = new int[1 << matchsticks.length];
		return process(matchsticks, 0, 0, sum >> 2, 4, dp);
	}

	public static boolean process(int[] arr, int status, int cur, int len, int edges, int[] dp) {
		if (dp[status] != 0) {
			return dp[status] == 1;
		}
		boolean ans = false;
		if (edges == 0) {
			ans = (status == (1 << arr.length) - 1) ? true : false;
		} else {
			for (int i = 0; i < arr.length && !ans; i++) {
				if (((1 << i) & status) == 0 && cur + arr[i] <= len) {
					if (cur + arr[i] == len) {
						ans |= process(arr, status | (1 << i), 0, len, edges - 1, dp);
					} else {
						ans |= process(arr, status | (1 << i), cur + arr[i], len, edges, dp);
					}
				}
			}
		}
		dp[status] = ans ? 1 : -1;
		return ans;
	}

}

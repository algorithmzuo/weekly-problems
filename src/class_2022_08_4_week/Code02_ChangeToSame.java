package class_2022_08_4_week;

// 来自美团
// 8.20笔试
// 小团生日收到妈妈送的两个一模一样的数列作为礼物！
// 他很开心的把玩，不过不小心没拿稳将数列摔坏了！
// 现在他手上的两个数列分别为A和B，长度分别为n和m。
// 小团很想再次让这两个数列变得一样。他现在能做两种操作：
// 操作一是将一个选定数列中的某一个数a改成数b，这会花费|b-a|的时间，
// 操作二是选择一个数列中某个数a，将它从数列中丢掉，花费|a|的时间。
// 小团想知道，他最少能以多少时间将这两个数列变得再次相同！
// 输入描述：
// 第一行两个空格隔开的正整数n和m，分别表示数列A和B的长度。
// 接下来一行n个整数，分别为A1 A2…An
// 接下来一行m个整数，分别为B1 B2…Bm
// 对于所有数据，1 ≤ n,m ≤ 2000， |Ai|,|Bi| ≤ 10000
// 输出一行一个整数，表示最少花费时间，来使得两个数列相同。
public class Code02_ChangeToSame {

	// A B
	// zuo(A,B,0,0)
	// A[ai.....] 对应 B[bi.....]
	// 请变一样
	// 返回最小代价
	public static int zuo(int[] A, int[] B, int ai, int bi) {
		if (ai == A.length && bi == B.length) {
			return 0;
		}
		if (ai != A.length && bi == B.length) {
			return A[ai] + zuo(A, B, ai + 1, bi);
		}
		if (ai == A.length && bi != B.length) {
			return B[bi] + zuo(A, B, ai, bi + 1);
		}
		// A[ai] 有数 B[bi] 有数
		// 可能性1 ： 删掉A[ai]
		int p1 = A[ai] + zuo(A, B, ai + 1, bi);
		// 可能性2 ： 删掉B[bi]
		int p2 = B[bi] + zuo(A, B, ai, bi + 1);
		// 可能性3 ： 同时删掉
		// int p3 = A[ai] + B[bi] + zuo(A, B, ai + 1, bi + 1);
		// 可能性4 ： 变！A[ai] -> B[bi] B[bi] -> A[ai]
		int p4 = Math.abs(A[ai] - B[bi]) + zuo(A, B, ai + 1, bi + 1);
		// 可能性5 ： A[ai] == B[bi]
		return Math.min(p1, Math.min(p2, p4));
	}

	public static int minCost(int[] A, int[] B) {
		int n = A.length;
		int m = B.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = -1;
			}
		}
		return change(A, B, 0, 0, dp);
	}

	// 暴力递归
	// A[indexA....]和B[indexB....]完全一样
	// 需要付出最少的代价返回
	public static int change(int[] A, int[] B, int indexA, int indexB) {
		if (indexA == A.length && indexB == B.length) {
			return 0;
		}
		if (indexA == A.length && indexB != B.length) {
			return B[indexB] + change(A, B, indexA, indexB + 1);
		}
		if (indexA != A.length && indexB == B.length) {
			return A[indexA] + change(A, B, indexA + 1, indexB);
		}
		// indexA、indexB都没到最后
		// 可能性1，丢掉A[indexA]
		int p1 = A[indexA] + change(A, B, indexA + 1, indexB);
		// 可能性2，丢掉B[indexB]
		int p2 = B[indexB] + change(A, B, indexA, indexB + 1);
		// 可能性3，同时丢掉A[indexA]、B[indexB]
		// 可能性4，把A[indexA]改成B[indexB](也是B[indexB]改成A[indexA]，因为代价一样)
		// 可能性5，A[indexA]本来就是等于B[indexB]的，改的代价为0
		// 可以分析出可能性3，肯定是不如可能性4、可能性5的
		// 所以舍弃掉可能性3
		int p45 = Math.abs(A[indexA] - B[indexB]) + change(A, B, indexA + 1, indexB + 1);
		return Math.min(Math.min(p1, p2), p45);
	}

	// 上面的暴力递归方法改动态规划
	public static int change(int[] A, int[] B, int indexA, int indexB, int[][] dp) {
		if (indexA == A.length && indexB == B.length) {
			return 0;
		}
		if (dp[indexA][indexB] != -1) {
			return dp[indexA][indexB];
		}
		int ans = 0;
		if (indexA == A.length && indexB != B.length) {
			ans = B[indexB] + change(A, B, indexA, indexB + 1, dp);
		} else if (indexA != A.length && indexB == B.length) {
			ans = A[indexA] + change(A, B, indexA + 1, indexB, dp);
		} else {
			int p1 = A[indexA] + change(A, B, indexA + 1, indexB, dp);
			int p2 = B[indexB] + change(A, B, indexA, indexB + 1, dp);
			int p45 = Math.abs(A[indexA] - B[indexB]) + change(A, B, indexA + 1, indexB + 1, dp);
			ans = Math.min(Math.min(p1, p2), p45);
		}
		dp[indexA][indexB] = ans;
		return ans;
	}

}

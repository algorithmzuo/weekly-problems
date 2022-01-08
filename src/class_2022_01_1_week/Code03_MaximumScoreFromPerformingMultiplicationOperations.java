package class_2022_01_1_week;

// 测试链接 : https://leetcode.com/problems/maximum-score-from-performing-multiplication-operations/
public class Code03_MaximumScoreFromPerformingMultiplicationOperations {

	// B数组消耗完之前，A数组不会耗尽，题目输入保证的！
	// A[left...right]
	// B[0..i-1]已经消耗完了！B[i...m-1]
	// 直到把B数组消耗完，能获得的最大分数返回
	public static int zuo(int[] A, int[] B, int left, int right) {
		int leftAlready = left;
		int rightAlready = A.length - right - 1;
		int i = leftAlready + rightAlready;
		if (i == B.length) {
			return 0;
		}
		// 没消耗完
		int p1 = A[left] * B[i] + zuo(A, B, left + 1, right);
		int p2 = A[right] * B[i] + zuo(A, B, left, right - 1);
		return Math.max(p1, p2);
	}

	public static int maximumScore1(int[] A, int[] B) {
		if (A == null || A.length == 0 || B == null || B.length == 0 || A.length < B.length) {
			return 0;
		}
		return process1(A, B, 0, A.length - 1);
	}

	public static int process1(int[] A, int[] B, int L, int R) {
		int indexB = L + A.length - R - 1;
		if (indexB == B.length) {
			return 0;
		} else {
			int p1 = A[L] * B[indexB] + process1(A, B, L + 1, R);
			int p2 = A[R] * B[indexB] + process1(A, B, L, R - 1);
			return Math.max(p1, p2);
		}
	}

	public static int maximumScore2(int[] A, int[] B) {
		if (A == null || A.length == 0 || B == null || B.length == 0 || A.length < B.length) {
			return 0;
		}
		int N = A.length;
		int M = B.length;
		int[][] dp = new int[M + 1][M + 1];
		for (int L = M - 1; L >= 0; L--) {
			for (int j = L + 1; j <= M; j++) {
				int R = N - M + j - 1;
				int indexB = L + N - R - 1;
				dp[L][j] = Math.max(A[L] * B[indexB] + dp[L + 1][j], A[R] * B[indexB] + dp[L][j - 1]);
			}
		}
		return dp[0][M];
	}

}

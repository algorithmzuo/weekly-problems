package class_2022_01_1_week;

// 给定两个相等长度的整型数组A和B
// 每一轮获得的分数 = 从数组A的最左或者最右拿出的数字 * 从数组B的最左拿出的数字
// 数字一旦拿出就认为从原数组中删除了
// 返回所有轮结束后获得的最大分数
public class Code01_ALeftRightBLeftGame {

	public static int maxScore1(int[] A, int[] B) {
		if (A == null || A.length == 0 || B == null || B.length == 0 || A.length != B.length) {
			return 0;
		}
		return process1(A, B, 0, A.length - 1);
	}

	public static int process1(int[] A, int[] B, int L, int R) {
		int indexB = L + A.length - R - 1;
		if (L == R) {
			return A[L] * B[indexB];
		} else {
			int p1 = A[L] * B[indexB] + process1(A, B, L + 1, R);
			int p2 = A[R] * B[indexB] + process1(A, B, L, R - 1);
			return Math.max(p1, p2);
		}
	}

	public static int maxScore2(int[] A, int[] B) {
		if (A == null || A.length == 0 || B == null || B.length == 0 || A.length != B.length) {
			return 0;
		}
		int N = A.length;
		int[][] dp = new int[N][N];
		for (int i = 0; i < N; i++) {
			dp[i][i] = A[i] * B[N - 1];
		}
		for (int L = N - 2; L >= 0; L--) {
			for (int R = L + 1; R < N; R++) {
				int indexB = L + N - R - 1;
				dp[L][R] = Math.max(A[L] * B[indexB] + dp[L + 1][R], A[R] * B[indexB] + dp[L][R - 1]);
			}
		}
		return dp[0][N - 1];
	}

}

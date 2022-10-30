package class_2022_10_4_week;

// 来自学员问题
// 给定一个二维数组matrix
// 每个格子都是正数，每个格子都和上、下、左、右相邻
// 你可以从任何一个格子出发，走向相邻的格子
// 把沿途的数字乘起来，希望得到的最终数字中，结尾的0最多
// 走的过程中，向左走或者向右走的拐点，最多只能有一次
// 返回结尾最多的0，能是多少
// 1 <= 行、列 <= 400
public class Code02_MostTrailingZerosTurnLeftOfRightAtMostOnce {

	public static int mostTrailingZeros(int[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		// f2[i][j] : matrix[i][j]自己有几个2的因子
		int[][] f2 = new int[n][m];
		// f5[i][j] : matrix[i][j]自己有几个5的因子
		int[][] f5 = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				f2[i][j] = factors(matrix[i][j], 2);
				f5[i][j] = factors(matrix[i][j], 5);
			}
		}
		int[][] leftF2 = new int[n][m];
		int[][] leftF5 = new int[n][m];
		int[][] upF2 = new int[n][m];
		int[][] upF5 = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				leftF2[i][j] = f2[i][j] + (j > 0 ? leftF2[i][j - 1] : 0);
				leftF5[i][j] = f5[i][j] + (j > 0 ? leftF5[i][j - 1] : 0);
				upF2[i][j] = f2[i][j] + (i > 0 ? upF2[i - 1][j] : 0);
				upF5[i][j] = f5[i][j] + (i > 0 ? upF5[i - 1][j] : 0);
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// 来到(i,j)
				int l2 = j == 0 ? 0 : leftF2[i][j - 1];
				int l5 = j == 0 ? 0 : leftF5[i][j - 1];
				int r2 = leftF2[i][m - 1] - leftF2[i][j];
				int r5 = leftF5[i][m - 1] - leftF5[i][j];
				int u2 = i == 0 ? 0 : upF2[i - 1][j];
				int u5 = i == 0 ? 0 : upF5[i - 1][j];
				int d2 = upF2[n - 1][j] - upF2[i][j];
				int d5 = upF5[n - 1][j] - upF5[i][j];
				int p1 = Math.min(l2 + u2 + f2[i][j], l5 + u5 + f5[i][j]);
				int p2 = Math.min(l2 + r2 + f2[i][j], l5 + r5 + f5[i][j]);
				int p3 = Math.min(l2 + d2 + f2[i][j], l5 + d5 + f5[i][j]);
				int p4 = Math.min(u2 + r2 + f2[i][j], u5 + r5 + f5[i][j]);
				int p5 = Math.min(u2 + d2 + f2[i][j], u5 + d5 + f5[i][j]);
				int p6 = Math.min(r2 + d2 + f2[i][j], r5 + d5 + f5[i][j]);
				ans = Math.max(ans, Math.max(Math.max(p1, p2), Math.max(Math.max(p3, p4), Math.max(p5, p6))));
			}
		}
		return ans;
	}

	public static int factors(int num, int f) {
		int ans = 0;
		while (num % f == 0) {
			ans++;
			num /= f;
		}
		return ans;
	}

	public static void main(String[] args) {
		int[][] matrix1 = { { 5, 8, 3, 1 }, { 4, 15, 12, 1 }, { 6, 7, 10, 1 }, { 9, 1, 2, 1 } };
		System.out.println(mostTrailingZeros(matrix1));

		int[][] matrix2 = { { 7500, 10, 11, 12 }, { 6250, 13, 14, 15 }, { 134, 17, 16, 1 }, { 5500, 2093, 5120, 238 } };
		System.out.println(mostTrailingZeros(matrix2));

	}

}

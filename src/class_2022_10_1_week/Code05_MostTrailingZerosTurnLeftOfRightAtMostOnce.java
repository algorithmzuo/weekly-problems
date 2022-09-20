package class_2022_10_1_week;

// 来自学员问题
// 给定一个二维数组matrix
// 每个格子都是正数，每个格子都和上、下、左、右相邻
// 你可以从任何一个格子出发，走向相邻的格子
// 把沿途的数字乘起来，希望得到的最终数字中，结尾的0最多
// 走的过程中，向左走或者向右走的拐点，最多只能有一个
// 返回结尾最多的0，能是多少
// 1 <= 行、列 <= 400
public class Code05_MostTrailingZerosTurnLeftOfRightAtMostOnce {

	public static class Factor {
		public int twos;
		public int fives;

		public Factor(int t, int f) {
			twos = t;
			fives = f;
		}
	}

	public static int mostTrailingZeros(int[][] matrix) {
		int n = matrix.length;
		int m = matrix[0].length;
		Factor[][] left = new Factor[n][m];
		for (int i = 0; i < n; i++) {
			left[i][0] = factor2And5(matrix[i][0]);
			for (int j = 1; j < m; j++) {
				left[i][j] = factor2And5(matrix[i][j]);
				left[i][j].twos += left[i][j - 1].twos;
				left[i][j].fives += left[i][j - 1].fives;
			}
		}
		Factor[][] up = new Factor[n][m];
		for (int j = 0; j < m; j++) {
			up[0][j] = factor2And5(matrix[0][j]);
			for (int i = 1; i < n; i++) {
				up[i][j] = factor2And5(matrix[i][j]);
				up[i][j].twos += up[i - 1][j].twos;
				up[i][j].fives += up[i - 1][j].fives;
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int l2 = j == 0 ? 0 : left[i][j - 1].twos;
				int l5 = j == 0 ? 0 : left[i][j - 1].fives;
				int r2 = left[i][m - 1].twos - left[i][j].twos;
				int r5 = left[i][m - 1].fives - left[i][j].fives;
				int u2 = i == 0 ? 0 : up[i - 1][j].twos;
				int u5 = i == 0 ? 0 : up[i - 1][j].fives;
				int d2 = up[n - 1][j].twos - up[i][j].twos;
				int d5 = up[n - 1][j].fives - up[i][j].fives;
				Factor cur = factor2And5(matrix[i][j]);
				int cur2 = cur.twos;
				int cur5 = cur.fives;
				int p1 = Math.min(l2 + u2 + cur2, l5 + u5 + cur5);
				int p2 = Math.min(l2 + r2 + cur2, l5 + r5 + cur5);
				int p3 = Math.min(l2 + d2 + cur2, l5 + d5 + cur5);
				int p4 = Math.min(u2 + r2 + cur2, u5 + r5 + cur5);
				int p5 = Math.min(u2 + d2 + cur2, u5 + d5 + cur5);
				int p6 = Math.min(r2 + d2 + cur2, r5 + d5 + cur5);
				ans = Math.max(ans, Math.max(Math.max(p1, p2), Math.max(Math.max(p3, p4), Math.max(p5, p6))));
			}
		}
		return ans;
	}

	public static Factor factor2And5(int num) {
		int twos = 0;
		int fives = 0;
		while (num % 2 == 0) {
			twos++;
			num /= 2;
		}
		while (num % 5 == 0) {
			fives++;
			num /= 5;
		}
		return new Factor(twos, fives);
	}

	public static void main(String[] args) {
		int[][] matrix1 = { { 5, 8, 3, 1 }, { 4, 15, 12, 1 }, { 6, 7, 10, 1 }, { 9, 1, 2, 1 } };
		System.out.println(mostTrailingZeros(matrix1));

		int[][] matrix2 = { { 7500, 10, 11, 12 }, { 6250, 13, 14, 15 }, { 134, 17, 16, 1 }, { 5500, 2093, 5120, 238 } };
		System.out.println(mostTrailingZeros(matrix2));

	}

}

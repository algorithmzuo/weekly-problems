package class_2022_11_1_week;

// 来自蚂蚁金服
// 得分的定义 :
// 含有大小2*2的矩阵，要么：
// 1 0
// 0 1 可以得1分
// 要么
// 0 1
// 1 0 可以得1分
// 那么一个任意大小的矩阵就有若干得分点，比如
// 0 1 0
// 1 0 1
// 这个矩阵就有2个得分点
// 给定正数N，正数M，求所有可能的情况里，所有的得分点总和
// 1 <= N、M <= 10^9
public class Code02_ScoreAllMatrix {

	public static int score1(int n, int m) {
		if (n < 2 || m < 2) {
			return 0;
		}
		int[][] matrix = new int[n][m];
		return process(matrix, 0, 0, n, m);
	}

	public static int process(int[][] matrix, int i, int j, int n, int m) {
		if (i == n) {
			int score = 0;
			for (int r = 1; r < n; r++) {
				for (int c = 1; c < m; c++) {
					if (check(matrix, r, c)) {
						score++;
					}
				}
			}
			return score;
		}
		if (j == m) {
			return process(matrix, i + 1, 0, n, m);
		}
		int score = 0;
		matrix[i][j] = 1;
		score += process(matrix, i, j + 1, n, m);
		matrix[i][j] = 0;
		score += process(matrix, i, j + 1, n, m);
		return score;
	}

	public static boolean check(int[][] m, int r, int c) {
		return (m[r - 1][c - 1] == 0 && m[r][c - 1] == 1 && m[r - 1][c] == 1 && m[r][c] == 0)
				|| (m[r - 1][c - 1] == 1 && m[r][c - 1] == 0 && m[r - 1][c] == 0 && m[r][c] == 1);
	}

	public static int score2(int n, int m) {
		if (n < 2 || m < 2) {
			return 0;
		}
		return (n * m - m - n + 1) * (1 << (n * m - 3));
	}

	public static void main(String[] args) {
		int n = 5;
		int m = 4;
		System.out.println(score1(n, m));
		System.out.println(score2(n, m));
	}

}

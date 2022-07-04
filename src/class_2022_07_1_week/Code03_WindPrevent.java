package class_2022_07_1_week;

// 来自真实笔试
// 给定一个二维数组matrix，数组中的每个元素代表一棵树的高度。
// 你可以选定连续的若干行组成防风带，防风带每一列的防风高度为这一列的最大值
// 防风带整体的防风高度为，所有列防风高度的最小值。
// 比如，假设选定如下三行
// 1 5 4
// 7 2 6
// 2 3 4
// 1、7、2的列，防风高度为7
// 5、2、3的列，防风高度为5
// 4、6、4的列，防风高度为6
// 防风带整体的防风高度为5，是7、5、6中的最小值
// 给定一个正数k，k <= matrix的行数，表示可以取连续的k行，这k行一起防风。
// 求防风带整体的防风高度最大值
public class Code03_WindPrevent {

	public static int bestHeight1(int[][] matrix, int k) {
		int n = matrix.length;
		int m = matrix[0].length;
		int ans = 0;
		for (int startRow = 0; startRow < n; startRow++) {
			int bottleNeck = Integer.MAX_VALUE;
			for (int col = 0; col < m; col++) {
				int height = 0;
				for (int endRow = startRow; endRow < n && (endRow - startRow + 1 <= k); endRow++) {
					height = Math.max(height, matrix[endRow][col]);
				}
				bottleNeck = Math.min(bottleNeck, height);
			}
			ans = Math.max(ans, bottleNeck);
		}
		return ans;
	}

	public static int bestHeight2(int[][] matrix, int k) {
		int n = matrix.length;
		int m = matrix[0].length;
		int[][] windowMaxs = new int[m][n];
		int[][] windowLR = new int[m][2];
		for (int i = 0; i < k; i++) {
			addRow(matrix, m, i, windowMaxs, windowLR);
		}
		int ans = bottleNeck(matrix, m, windowMaxs, windowLR);
		for (int i = k; i < n; i++) {
			addRow(matrix, m, i, windowMaxs, windowLR);
			deleteRow(m, i - k, windowMaxs, windowLR);
			ans = Math.max(ans, bottleNeck(matrix, m, windowMaxs, windowLR));
		}
		return ans;
	}

	public static void addRow(int[][] matrix, int m, int row, int[][] windowMaxs, int[][] windowLR) {
		for (int col = 0; col < m; col++) {
			while (windowLR[col][0] != windowLR[col][1]
					&& matrix[windowMaxs[col][windowLR[col][1] - 1]][col] <= matrix[row][col]) {
				windowLR[col][1]--;
			}
			windowMaxs[col][windowLR[col][1]++] = row;
		}
	}

	public static void deleteRow(int m, int row, int[][] windowMaxs, int[][] windowLR) {
		for (int col = 0; col < m; col++) {
			if (windowMaxs[col][windowLR[col][0]] == row) {
				windowLR[col][0]++;
			}
		}
	}

	public static int bottleNeck(int[][] matrix, int m, int[][] windowMaxs, int[][] windowLR) {
		int ans = Integer.MAX_VALUE;
		for (int col = 0; col < m; col++) {
			ans = Math.min(ans, matrix[windowMaxs[col][windowLR[col][0]]][col]);
		}
		return ans;
	}

	public static int[][] generateMatrix(int n, int m, int v) {
		int[][] matrix = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j] = (int) (Math.random() * v) + 1;
			}
		}
		return matrix;
	}

	public static void main(String[] args) {
		int nMax = 10;
		int mMax = 10;
		int vMax = 50;
		int testTimes = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * nMax) + 1;
			int m = (int) (Math.random() * mMax) + 1;
			int[][] matrix = generateMatrix(n, m, vMax);
			int k = (int) (Math.random() * n) + 1;
			int ans1 = bestHeight1(matrix, k);
			int ans2 = bestHeight2(matrix, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

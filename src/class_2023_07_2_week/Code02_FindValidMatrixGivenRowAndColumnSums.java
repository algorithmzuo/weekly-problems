package class_2023_07_2_week;

// 测试链接 : https://leetcode.cn/problems/find-valid-matrix-given-row-and-column-sums/
public class Code02_FindValidMatrixGivenRowAndColumnSums {

	public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
		int n = rowSum.length;
		int m = colSum.length;
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans[i][j] = Math.min(rowSum[i], colSum[j]);
				rowSum[i] -= ans[i][j];
				colSum[j] -= ans[i][j];
			}
		}
		return ans;
	}

}

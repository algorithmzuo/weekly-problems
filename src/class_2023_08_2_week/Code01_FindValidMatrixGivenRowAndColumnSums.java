package class_2023_08_2_week;

// 给你两个非负整数数组 rowSum 和 colSum
// 其中 rowSum[i] 是二维矩阵中第 i 行元素的和
// colSum[j] 是第 j 列元素的和，换言之你不知道矩阵里的每个元素
// 但是你知道每一行和每一列的和
// 请找到大小为 rowSum.length x colSum.length 的任意 非负整数 矩阵
// 且该矩阵满足 rowSum 和 colSum 的要求
// 请你返回任意一个满足题目要求的二维矩阵，题目保证存在 至少一个 可行矩阵。
// 测试链接 : https://leetcode.cn/problems/find-valid-matrix-given-row-and-column-sums/
public class Code01_FindValidMatrixGivenRowAndColumnSums {

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

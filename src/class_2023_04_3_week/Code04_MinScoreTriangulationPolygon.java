package class_2023_04_3_week;

// 测试链接 : https://leetcode.cn/problems/minimum-score-triangulation-of-polygon/
public class Code04_MinScoreTriangulationPolygon {

	public static int minScoreTriangulation(int[] values) {
		int[][] dp = new int[values.length][values.length];
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				dp[i][j] = -1;
			}
		}
		return f(values, 0, values.length - 1, dp);
	}

	// values[i...j]范围上这些点，要分解成多个三角形
	// 三角形一个端点是values[i]，另一个端点是values[j]
	// 那么第三个点在i+1....j-1之间选
	// 比如选了m点 : i......m.......j
	// 当前获得的分数为values[i] * values[m] * values[j]
	// 接下来，i.....m去分解三角形、m.....j去分解三角形
	public static int f(int[] values, int i, int j, int[][] dp) {
		if (i >= j - 1) {
			// 不够三个点，不会有得分
			return 0;
		}
		if (dp[i][j] != -1) {
			// 缓存的答案
			// 如果命中直接返回
			// 看体系学习班，动态规划的章节
			return dp[i][j];
		}
		int ans = Integer.MAX_VALUE;
		for (int m = i + 1; m < j; m++) {
			ans = Math.min(ans, f(values, i, m, dp) + f(values, m, j, dp) + values[i] * values[m] * values[j]);
		}
		dp[i][j] = ans;
		return ans;
	}

}

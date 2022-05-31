package class_2022_06_1_week;

// 本题测试链接 : https://leetcode.com/problems/where-will-the-ball-fall/
public class Code01_WhereWillTheBallFall {

	public static int[] findBall(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		int[] ans = new int[m];
		for (int col = 0; col < m; col++) {
			int i = 0;
			int j = col;
			while (i < n) {
				int jnext = j + grid[i][j];
				if (jnext < 0 || jnext == m || grid[i][j] != grid[i][jnext]) {
					ans[col] = -1;
					break;
				}
				i++;
				j = jnext;
			}
			if (i == n) {
				ans[col] = j;
			}
		}
		return ans;
	}
	
}

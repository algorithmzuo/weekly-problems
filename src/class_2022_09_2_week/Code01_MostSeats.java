package class_2022_09_2_week;

import java.util.Arrays;

// 来自华为
// 给定一个二维数组map，代表一个餐厅，其中只有0、1两种值
// map[i][j] == 0 表示(i,j)位置是空座
// map[i][j] == 1 表示(i,j)位置坐了人
// 根据防疫要求，任何人的上、下、左、右，四个相邻的方向都不能再坐人
// 输入的map已经符合了这个要求，但是为了餐厅利用的最大化
// 也许还能在不违反防疫要求的情况下，继续安排人吃饭
// 请返回还能安排的最大人数
// 比如:
// 1 0 0 0
// 0 0 0 1
// 不违反防疫要求的情况下，这个餐厅最多还能安排2人，如下所示，X是新安排的人
// 1 0 X 0
// 0 X 0 1
// 再比如:
// 1 0 0 0 0 1
// 0 0 0 0 0 0
// 0 1 0 0 0 1
// 0 0 0 0 0 0
// 不违反防疫要求的情况下，这个餐厅最多还能安排7人，如下所示，X是新安排的人
// 1 0 0 X 0 1
// 0 0 X 0 X 0
// 0 1 0 X 0 1
// X 0 X 0 X 0
public class Code01_MostSeats {

	public static int[][] dp = new int[20][1 << 20];

	public static int mostSeats(int[][] map) {
		int n = map.length;
		int m = map[0].length;
		int[] arr = new int[n];
		for (int row = 0; row < n; row++) {
			int status = 0;
			for (int col = 0, i = m - 1; col < m; col++, i--) {
				status |= map[row][col] << i;
			}
			arr[row] = status;
		}
		for (int i = 0; i < n; i++) {
			Arrays.fill(dp[i], -2);
		}
		int ans = f(arr, 0, 0, m);
		return ans == -1 ? 0 : ans;
	}

	public static int f(int[] arr, int row, int pre, int m) {
		if (row == arr.length) {
			return 0;
		}
		if (dp[row][pre] != -2) {
			return dp[row][pre];
		}
		int cur = arr[row];
		int ans = 0;
		if ((cur & pre) != 0) {
			ans = -1;
		} else {
			ans = g(arr, row, m - 1, pre, cur, m);
		}
		dp[row][pre] = ans;
		return ans;
	}

	public static int g(int[] arr, int row, int col, int pre, int seats, int m) {
		if (col == -1) {
			return f(arr, row + 1, seats, m);
		} else {
			int p1 = g(arr, row, col - 1, pre, seats, m);
			int p2 = -1;
			if ((pre & (1 << col)) == 0 && (seats & (1 << col)) == 0
					&& (col == m - 1 || (seats & (1 << (col + 1))) == 0)
					&& (col == 0 || (seats & (1 << (col - 1))) == 0)) {
				int next2 = g(arr, row, col - 1, pre, seats | (1 << col), m);
				if (next2 != -1) {
					p2 = 1 + next2;
				}
			}
			return Math.max(p1, p2);
		}
	}

	public static void main(String[] args) {
		int[][] map1 = { { 1, 0, 0, 0 }, { 0, 0, 0, 1 } };
		System.out.println(mostSeats(map1));

		int[][] map2 = { { 1, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0 } };
		System.out.println(mostSeats(map2));
	}

}

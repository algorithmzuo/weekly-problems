package class_2022_09_4_week;

import java.util.Arrays;

// 来自华为
// 给定一个二维数组map，代表一个餐厅，其中只有0、1两种值
// map[i][j] == 0 表示(i,j)位置是空座
// map[i][j] == 1 表示(i,j)位置坐了人
// 根据防疫要求，任何人的上、下、左、右，四个相邻的方向都不能再坐人
// 但是为了餐厅利用的最大化，也许还能在不违反防疫要求的情况下，继续安排人吃饭
// 请返回还能安排的最大人数
// 如果一开始的状况已经不合法，直接返回-1
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
// 数据范围 : 1 <= 矩阵的行、列 <= 20
public class Code03_MostSeats {

//	public static int maxPeople(int[][] matrix) {
//		int n = matrix.length;
//		int m = matrix[0].length;
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < m; j++) {
//				if (matrix[i][j] == 1) {
//					if (i > 0 && matrix[i - 1][j] == 1) {
//						return -1;
//					}
//					if (i < n - 1 && matrix[i + 1][j] == 1) {
//						return -1;
//					}
//					if (j > 0 && matrix[i][j - 1] == 1) {
//						return -1;
//					}
//					if (j < m - 1 && matrix[i][j + 1] == 1) {
//						return -1;
//					}
//				}
//			}
//		}
//		int[] arr = new int[n];
//		for (int i = 0; i < n; i++) {
//			int status = 0;
//			for (int j = 0; j < m; j++) {
//				if (matrix[i][j] == 1) {
//					status |= 1 << j;
//				}
//
//				arr[i] = status;
//			}
//		}
//		return zuo(arr, n, m, 0, 0, 0);
//
//	}
//
//	public static int zuo(int[] arr, int n, int m, int i, int j, int status) {
//		if (i == n) {
//			return 0;
//		}
//		if (j == m) {
//			return zuo(arr, n, m, i + 1, 0, status);
//		}
//		// 怎么判断已经无效了
//		if ((arr[i] & status) != 0) {
//			return -1;
//		}
//		// (i,j)
//		int left = status(status, j - 1, m);
//		int up = status(status, j, m);
//		int right = status(arr[i], j + 1, m);
//		int cur = status(arr[i], j, m);
//		// 当前位置不安排新人了
//		int p1 = -1;
//		if (cur == 0) { // 原始的该位置无人
//			int nextStatus = status ^ (up << j);
//			p1 = zuo(arr, n, m, i, j + 1, nextStatus);
//		} else {  // 原始的该位置有人
//			int nextStatus = (status | (1 << j));
//			p1 = zuo(arr, n, m, i, j + 1, nextStatus);
//		}
//		// 当前位置安排新人
//		int p2 = -1;
//		if(left == 0 && up == 0 && right == 0 && cur == 0) {
//			int nextStatus = (status | (1 << j));
//			int next = zuo(arr, n, m, i, j + 1, nextStatus);
//			if(next != -1) {
//				p2 = 1 + next;
//			}
//		}
//		return Math.max(p1, p2);
//	}

	// 为了测试，普通方法
	// 普通的状态压缩动态规划
	// 每一行用dfs的方法
	// 体系学习班，章节44 : 状态压缩的动态规划，贴瓷砖问题类似
	public static int mostSeats1(int[][] map) {
		int n = map.length;
		int m = map[0].length;
		int[] arr = new int[n];
		for (int row = 0; row < n; row++) {
			int status = 0;
			for (int col = 0, i = m - 1; col < m; col++, i--) {
				if (map[row][col] == 1) {
					if (row > 0 && map[row - 1][col] == 1) {
						return -1;
					}
					if (col > 0 && map[row][col - 1] == 1) {
						return -1;
					}
				}
				status |= map[row][col] << i;
			}
			arr[row] = status;
		}
		int[][] dp = new int[n][1 << m];
		for (int i = 0; i < n; i++) {
			Arrays.fill(dp[i], -2);
		}
		int ans = process1(arr, 0, 0, m, dp);
		return ans == -1 ? 0 : ans;
	}

	public static int process1(int[] arr, int row, int pre, int m, int[][] dp) {
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
			ans = dfs(arr, row, m - 1, pre, cur, m, dp);
		}
		dp[row][pre] = ans;
		return ans;
	}

	public static int dfs(int[] arr, int row, int col, int pre, int seats, int m, int[][] dp) {
		if (col == -1) {
			return process1(arr, row + 1, seats, m, dp);
		} else {
			int p1 = dfs(arr, row, col - 1, pre, seats, m, dp);
			int p2 = -1;
			if ((pre & (1 << col)) == 0 && (seats & (1 << col)) == 0
					&& (col == m - 1 || (seats & (1 << (col + 1))) == 0)
					&& (col == 0 || (seats & (1 << (col - 1))) == 0)) {
				int next2 = dfs(arr, row, col - 1, pre, seats | (1 << col), m, dp);
				if (next2 != -1) {
					p2 = 1 + next2;
				}
			}
			return Math.max(p1, p2);
		}
	}

	// 正式方法
	// 轮廓线dp
	public static int mostSeats2(int[][] map) {
		int n = map.length;
		int m = map[0].length;
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			int status = 0;
			for (int j = 0; j < m; j++) {
				if (map[i][j] == 1) {
					if (i > 0 && map[i - 1][j] == 1) {
						return -1;
					}
					if (j > 0 && map[i][j - 1] == 1) {
						return -1;
					}
				}
				status |= map[i][j] << j;
			}
			arr[i] = status;
		}
		int s = 1 << m;
		int[][][] dp = new int[n][m][s];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < s; k++) {
					dp[i][j][k] = -2;
				}
			}
		}
		int ans = process2(arr, n, m, 0, 0, 0, dp);
		return ans == -1 ? 0 : ans;
	}

	// 20 * 20 * 2^20 -> 4 * 10^8
	public static int process2(int[] arr, int n, int m, int i, int j, int status, int[][][] dp) {
		if (j == m) {
			return process2(arr, n, m, i + 1, 0, status, dp);
		}
		if (i == n) {
			return 0;
		}
		if (dp[i][j][status] != -2) {
			return dp[i][j][status];
		}
		int left = status(status, j - 1, m);
		int up = status(status, j, m);
		int cur = status(arr[i], j, m);
		int right = status(arr[i], j + 1, m);
		if (up == 1 && cur == 1) {
			return -1;
		}
		int p1 = -1;
		if (cur == 1) {
			p1 = process2(arr, n, m, i, j + 1, status | (1 << j), dp);
		} else {
			p1 = process2(arr, n, m, i, j + 1, (status | (1 << j)) ^ (1 << j), dp);
		}
		int p2 = -1;
		if (left == 0 && up == 0 && cur == 0 && right == 0) {
			int next2 = process2(arr, n, m, i, j + 1, status | (1 << j), dp);
			if (next2 != -1) {
				p2 = 1 + next2;
			}
		}
		int ans = Math.max(p1, p2);
		dp[i][j][status] = ans;
		return ans;
	}

	public static int status(int status, int i, int m) {
		return (i < 0 || i == m || (status & (1 << i)) == 0) ? 0 : 1;
	}

	public static int[][] randomMatrix(int n, int m, double oneP) {
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans[i][j] = Math.random() < oneP ? 1 : 0;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 10;
		int M = 10;
		// 产生1的概率
		double oneP = 0.15;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int m = (int) (Math.random() * M) + 1;
			int[][] map = randomMatrix(n, m, oneP);
			int ans1 = mostSeats1(map);
			int ans2 = mostSeats2(map);
			if (ans1 != ans2) {
				for (int[] arr : map) {
					for (int num : arr) {
						System.out.print(num + " ");
					}
					System.out.println();
				}
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");

		int n = 20;
		int[][] map = new int[n][n];
		System.out.println("最大规模 : " + n + " * " + n);
		long start = System.currentTimeMillis();
		mostSeats2(map);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
	}

}

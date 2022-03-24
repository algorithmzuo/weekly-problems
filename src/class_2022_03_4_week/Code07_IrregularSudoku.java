package class_2022_03_4_week;

// 来自网易
// 不规则数独问题
// 3*3填数独
// 每一行要填1~3
// 每一列要填1~3
// 3*3的区域会拆分成不规则的三个集团区域
// 每个集团区域3个格子
// 每个集团的区域都一定是一个连在一起的整体，可能不规则
// 每个集团内要填1~3
// 如果只有一个解返回"Unique"，如果有多个解返回"Multiple"，如果没有解返回"No"
// 解析请看，大厂刷题班，28节，leetcode原题，数独那两个题
// 本题就是改变一下桶的归属而已
public class Code07_IrregularSudoku {

	// sudoku[i][j] == 0，代表这个位置没有数字，需要填
	// sudoku[i][j] != 0，代表这个位置有数字，不需要填
	// map[0] = {0,0}、{0,1}、{1,0} 代表0集团拥有的三个点
	// map[i] = {a,b}、{c,d}、{e,f} 代表i集团拥有的三个点
	public static String solution(int[][] sudoku, int[][][] map) {
		boolean[][] row = new boolean[3][4];
		boolean[][] col = new boolean[3][4];
		boolean[][] bucket = new boolean[3][4];
		int[][] own = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int[] arr : map[i]) {
				own[arr[0]][arr[1]] = i;
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (sudoku[i][j] != 0) {
					row[i][sudoku[i][j]] = true;
					col[j][sudoku[i][j]] = true;
					bucket[own[i][j]][sudoku[i][j]] = true;
				}
			}
		}
		int ans = process(sudoku, 0, 0, row, col, bucket, own);
		return ans == 0 ? "No" : (ans == 1 ? "Unique" : "Multiple");
	}

	public static int process(int[][] sudoku, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket,
			int[][] own) {
		if (i == 3) {
			return 1;
		}
		int nexti = j != 2 ? i : i + 1;
		int nextj = j != 2 ? j + 1 : 0;
		if (sudoku[i][j] != 0) {
			return process(sudoku, nexti, nextj, row, col, bucket, own);
		} else {
			int ans = 0;
			int bid = own[i][j];
			for (int num = 1; num <= 3; num++) {
				if ((!row[i][num]) && (!col[j][num]) && (!bucket[bid][num])) {
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
					sudoku[i][j] = num;
					ans += process(sudoku, nexti, nextj, row, col, bucket, own);
					row[i][num] = false;
					col[j][num] = false;
					bucket[bid][num] = false;
					sudoku[i][j] = 0;
					if (ans > 1) {
						return ans;
					}
				}
			}
			return ans;
		}
	}

	public static void main(String[] args) {
		int[][] sudoku1 = { 
				{ 0, 2, 0 }, 
				{ 1, 0, 2 }, 
				{ 0, 0, 0 } 
				};
		int[][][] map1 = { 
				{ { 0, 0 }, { 0, 1 }, { 1, 0 } },
				{ { 0, 2 }, { 1, 1 }, { 1, 2 } },
				{ { 2, 0 }, { 2, 1 }, { 2, 2 } } 
				};
		System.out.println(solution(sudoku1, map1));
		
		int[][] sudoku2 = { 
				{ 0, 0, 3 }, 
				{ 0, 0, 0 }, 
				{ 0, 0, 0 } 
				};
		int[][][] map2 = { 
				{ { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, 1 }, { 0, 2 }, { 1, 2 } },
				{ { 2, 0 }, { 2, 1 }, { 2, 2 } } 
				};
		System.out.println(solution(sudoku2, map2));
		
		int[][] sudoku3 = { 
				{ 0, 0, 3 }, 
				{ 1, 0, 0 }, 
				{ 0, 0, 2 } 
				};
		int[][][] map3 = { 
				{ { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, 1 }, { 0, 2 }, { 1, 2 } },
				{ { 2, 0 }, { 2, 1 }, { 2, 2 } } 
				};
		System.out.println(solution(sudoku3, map3));
		
		int[][] sudoku4 = { 
				{ 3, 0, 3 }, 
				{ 1, 0, 0 }, 
				{ 0, 0, 2 } 
				};
		int[][][] map4 = { 
				{ { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, 1 }, { 0, 2 }, { 1, 2 } },
				{ { 2, 0 }, { 2, 1 }, { 2, 2 } } 
				};
		System.out.println(solution(sudoku4, map4));

	}

}

package class_2022_01_4_week;

// 来自米哈游
// 测试链接 : https://leetcode.com/problems/battleships-in-a-board/
public class Code04_BattleshipsInABoard {

	public static int countBattleships(char[][] m) {
		int ans = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				if ((m[i][j] == 'X') && (i == 0 || m[i - 1][j] != 'X') && (j == 0 || m[i][j - 1] != 'X')) {
					ans++;
				}
			}
		}
		return ans;
	}

}

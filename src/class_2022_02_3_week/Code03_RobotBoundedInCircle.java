package class_2022_02_3_week;

// 测试链接 : https://leetcode.com/problems/robot-bounded-in-circle/
public class Code03_RobotBoundedInCircle {

	public static boolean isRobotBounded(String ins) {
		int row = 0;
		int col = 0;
		int direction = 0;
		char[] str = ins.toCharArray();
		for (char cur : str) {
			if (cur == 'R') {
				direction = right(direction);
			} else if (cur == 'L') {
				direction = left(direction);
			} else {
				row = row(direction, row);
				col = col(direction, col);
			}
		}
		return row == 0 && col == 0 || direction != 0;
	}

	public static int left(int direction) {
		return direction == 0 ? 3 : (direction - 1);
	}

	public static int right(int direction) {
		return direction == 3 ? 0 : (direction + 1);
	}

	public static int row(int direction, int r) {
		return (direction == 1 || direction == 3) ? r : (r + (direction == 0 ? 1 : -1));
	}

	public static int col(int direction, int c) {
		return (direction == 0 || direction == 2) ? c : (c + (direction == 1 ? 1 : -1));
	}

}

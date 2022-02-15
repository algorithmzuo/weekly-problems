package class_2022_03_2_week;

// 来自微软
// 给定一个正数num，要返回一个大于num的数，并且每一位和相邻位的数字不能相等
// 返回达标的数字中，最小的那个
public class Code03_NearBiggerNoSameNeighbour {

	public static int near(int num) {
		char[] raw = ("0" + String.valueOf(num + 1)).toCharArray();
		process(raw);
		return Integer.valueOf(String.valueOf(raw));
	}

	public static void process(char[] raw) {
		for (int i = 1; i < raw.length; i++) {
			if (raw[i - 1] == raw[i]) {
				addOne(raw, i);
				for (int j = i + 1; j < raw.length; j++) {
					raw[j] = '0';
				}
				process(raw);
				return;
			}
		}
	}

	public static void addOne(char[] r, int i) {
		boolean up = true;
		while (up && r[i] == '9') {
			r[i--] = '0';
		}
		r[i]++;
	}

	public static void main(String[] args) {
		int num1 = 55;
		System.out.println(near(num1));

		int num2 = 1765;
		System.out.println(near(num2));

		int num3 = 98;
		System.out.println(near(num3));

		int num4 = 44432;
		System.out.println(near(num4));

		int num5 = 3298;
		System.out.println(near(num5));

		int num6 = 9999998;
		System.out.println(near(num6));
	}

}

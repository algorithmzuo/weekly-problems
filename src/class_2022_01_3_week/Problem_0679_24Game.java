package class_2022_01_3_week;

public class Problem_0679_24Game {

	public static boolean judgePoint24(int[] cards) {
		if (cards == null || cards.length == 0) {
			return false;
		}
		int n = cards.length;
		Number[] arr = new Number[n];
		for (int i = 0; i < n; i++) {
			arr[i] = new Number(cards[i], 1);
		}
		return judge(arr, cards.length);
	}

	public static boolean judge(Number[] arr, int size) {
		if (size == 1) {
			return arr[0].numerator == 24 && arr[0].denominator == 1;
		}
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				Number inum = arr[i];
				Number jnum = arr[j];
				arr[j] = arr[size - 1];
				arr[i] = add(inum, jnum);
				if (judge(arr, size - 1)) {
					return true;
				}
				arr[i] = minus(inum, jnum);
				if (judge(arr, size - 1)) {
					return true;
				}
				arr[i] = minus(jnum, inum);
				if (judge(arr, size - 1)) {
					return true;
				}
				arr[i] = multiply(inum, jnum);
				if (judge(arr, size - 1)) {
					return true;
				}
				arr[i] = divide(inum, jnum);
				if (arr[i] != null && judge(arr, size - 1)) {
					return true;
				}
				arr[i] = divide(jnum, inum);
				if (arr[i] != null && judge(arr, size - 1)) {
					return true;
				}
				arr[i] = inum;
				arr[j] = jnum;
			}
		}
		return false;
	}

	public static class Number {
		public int numerator;
		public int denominator;

		public Number(int n, int d) {
			numerator = n;
			denominator = d;
		}
	}

	public static Number add(Number a, Number b) {
		return simple(a.numerator * b.denominator + b.numerator * a.denominator, a.denominator * b.denominator);
	}

	public static Number minus(Number a, Number b) {
		return simple(a.numerator * b.denominator - b.numerator * a.denominator, a.denominator * b.denominator);
	}

	public static Number multiply(Number a, Number b) {
		return simple(a.numerator * b.numerator, a.denominator * b.denominator);
	}

	public static Number divide(Number a, Number b) {
		return b.numerator == 0 ? null : simple(a.numerator * b.denominator, a.denominator * b.numerator);
	}

	public static Number simple(int up, int down) {
		if (up == 0) {
			return new Number(0, 1);
		}
		int gcd = Math.abs(gcd(up, down));
		return new Number(up / gcd, down / gcd);
	}

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

}

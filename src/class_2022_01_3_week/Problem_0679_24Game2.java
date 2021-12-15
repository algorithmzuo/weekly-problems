package class_2022_01_3_week;

import java.util.ArrayList;

public class Problem_0679_24Game2 {

	public static boolean judgePoint24(int[] nums) {
		ArrayList<Num> arr = new ArrayList<>();
		for (int num : nums) {
			arr.add(new Num(num, 1));
		}
		return process(arr);
	}

	public static boolean process(ArrayList<Num> arr) {
		if (arr.size() == 1) {
			return arr.get(0).up == 24 && arr.get(0).down == 1;
		}
		for (int i = 0; i < arr.size(); i++) {
			for (int j = i + 1; j < arr.size(); j++) {
				Num o1 = arr.get(i);
				Num o2 = arr.get(j);
				arr.remove(j);
				arr.remove(i);
				Num[] ops = { op(o1, o2, 1), 
						      op(o1, o2, 2), 
						      op(o2, o1, 2), 
						      op(o1, o2, 3), 
						      op(o1, o2, 4),
						      op(o2, o1, 4) };
				for (Num merge : ops) {
					if (merge != null) {
						arr.add(arr.size(), merge);
						if (process(arr)) {
							return true;
						}
						arr.remove(arr.size() - 1);
					}
				}
				arr.add(i, o1);
				arr.add(j, o2);
			}
		}
		return false;
	}

	public static class Num {
		public long up;
		public long down;

		public Num(long n, long d) {
			up = n;
			down = d;
		}
	}

	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static Num gcdNum(long up, long down) {
		if (up == 0) {
			down = 1;
		} else {
			long gcd = Math.abs(gcd(up, down));
			up /= gcd;
			down /= gcd;
		}
		return new Num(up, down);
	}

	public static Num op(Num a, Num b, int op) {
		long up = 0;
		long down = 0;
		if (op < 4) {
			if (op == 1) {
				up = a.up * b.down + b.up * a.down;
			} else if (op == 2) {
				up = a.up * b.down - b.up * a.down;
			} else {
				up = a.up * b.up;
			}
			down = a.down * b.down;
		} else {
			if (b.up == 0) {
				return null;
			}
			up = a.up * b.down;
			down = a.down * b.up;
		}
		return gcdNum(up, down);
	}

}

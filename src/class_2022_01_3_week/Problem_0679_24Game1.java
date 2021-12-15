package class_2022_01_3_week;

import java.util.HashMap;

public class Problem_0679_24Game1 {

	public static boolean judgePoint24(int[] nums) {
		return process(nums, 0);
	}

	public static boolean process(int[] nums, int index) {
		if (index == nums.length) {
			return judge24(nums);
		} else {
			for (int swap = index; swap < nums.length; swap++) {
				swap(nums, index, swap);
				if (process(nums, index + 1)) {
					return true;
				}
				swap(nums, index, swap);
			}
			return false;
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	public static class Num {
		public long up;
		public long down;

		public Num(long n, long d) {
			up = n;
			down = d;
		}

		public String getKey() {
			return String.valueOf(up) + "_" + String.valueOf(down);
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
		if (op == 1) {
			up = a.up * b.down + b.up * a.down;
			down = a.down * b.down;
		} else if (op == 2) {
			up = a.up * b.down - b.up * a.down;
			down = a.down * b.down;
		} else if (op == 3) {
			up = a.up * b.up;
			down = a.down * b.down;
		} else {
			up = a.up * b.down;
			down = a.down * b.up;
		}
		return gcdNum(up, down);
	}

	public static class NumMap {
		public HashMap<String, Num> map;

		public NumMap() {
			map = new HashMap<>();
		}

		public void add(Num num) {
			map.put(num.getKey(), num);
		}
	}

	public static boolean judge24(int[] nums) {
		int N = nums.length;
		NumMap[][] dp = new NumMap[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = i; j < N; j++) {
				dp[i][j] = new NumMap();
			}
		}
		for (int i = 0; i < N; i++) {
			dp[i][i].add(new Num(nums[i], 1));
		}
		for (int i = N - 2; i >= 0; i--) {
			for (int j = i + 1; j < N; j++) {
				for (int k = i; k < j; k++) {
					for (Num left : dp[i][k].map.values()) {
						for (Num right : dp[k + 1][j].map.values()) {
							for (int op = 1; op < 4; op++) {
								dp[i][j].add(op(left, right, op));
							}
							if (right.up != 0) {
								dp[i][j].add(op(left, right, 4));
							}
						}
					}
				}
			}
		}
		for (Num num : dp[0][N - 1].map.values()) {
			if (num.up == 24 && num.down == 1) {
				return true;
			}
		}
		return false;
	}

}

package class_2022_03_4_week;

// 测试链接 : https://leetcode.com/problems/number-of-different-subsequences-gcds/
public class Code05_NumberOfDifferentSubsequencesGCDs {

	// 1 + n/2 + n/3 + n/4 + ... + n/n -> O(N * logN)
	public static int countDifferentSubsequenceGCDs(int[] nums) {
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			max = Math.max(max, num);
		}
		boolean[] set = new boolean[max + 1];
		for (int num : nums) {
			set[num] = true;
		}
		int ans = 0;
		for (int a = 1; a <= max; a++) {
			int g = a;
			for (; g <= max; g += a) {
				if (set[g]) {
					break;
				}
			}
			for (int b = g; b <= max; b += a) {
				if (set[b]) {
					g = gcd(g, b);
					if (g == a) {
						ans++;
						break;
					}
				}
			}
		}
		return ans;
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

}

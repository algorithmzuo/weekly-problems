package class_2022_11_1_week;

// 来自国外题目论坛
// 给定一个数组arr，任何两个数差值的绝对值都要加入到arr里
// 然后新的arr，继续任何两个数差值的绝对值都要加入到arr里
// 一直到arr大小固定
// 请问最终arr长度是多少
// 1 <= arr的长度 <= 10^5
// 1 <= arr的数值 <= 10^5
public class Code02_AbsToArrayFinalLength {

	public static int finalLen(int[] arr) {
		int max = 0;
		for (int num : arr) {
			max = Math.max(max, num);
		}
		int gcd = arr[0];
		for (int num : arr) {
			gcd = gcd(gcd, num);
		}
		return max / gcd;
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

}

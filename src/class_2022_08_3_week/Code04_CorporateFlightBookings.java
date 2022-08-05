package class_2022_08_3_week;

// 测试链接 : https://leetcode.cn/problems/corporate-flight-bookings/
public class Code04_CorporateFlightBookings {

	public static int[] corpFlightBookings(int[][] bookings, int n) {
		int[] cnt = new int[n + 2];
		for (int[] book : bookings) {
			cnt[book[0]] += book[2];
			cnt[book[1] + 1] -= book[2];
		}
		for (int i = 1; i < cnt.length; i++) {
			cnt[i] += cnt[i - 1];
		}
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = cnt[i + 1];
		}
		return ans;
	}

}

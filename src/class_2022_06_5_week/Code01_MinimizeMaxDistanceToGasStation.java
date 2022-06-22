package class_2022_06_5_week;

// 测试链接 : https://leetcode.cn/problems/minimize-max-distance-to-gas-station/
public class Code01_MinimizeMaxDistanceToGasStation {

	public static double minmaxGasDist(int[] stations, int K) {
		double accuracy = 0.0000001D;
		double l = 0;
		double r = 100000000D;
		double m = 0;
		double ans = 0;
		while (r - l > accuracy) {
			m = (l + r) / 2;
			if (ok(m, stations, K)) {
				r = m;
				ans = m;
			} else {
				l = m;
			}
		}
		return ans;
	}

	public static boolean ok(double limit, int[] stations, int K) {
		int used = 0;
		for (int i = 1; i < stations.length; i++) {
			used += (int) ((stations[i] - stations[i - 1]) / limit);
			if (used > K) {
				return false;
			}
		}
		return true;
	}

}

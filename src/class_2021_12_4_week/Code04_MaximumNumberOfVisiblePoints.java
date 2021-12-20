package class_2021_12_4_week;

import java.util.Arrays;
import java.util.List;

// 整个二维平面算是一张地图，给定[x,y]，表示你站在x行y列
// 你可以选择面朝的任何方向
// 给定一个正数值angle，表示你视野的角度为
// 这个角度内你可以看无穷远，这个角度外你看不到任何东西
// 给定一批点的二维坐标，返回你在朝向最好的情况下，最多能看到几个点
// 测试链接 : https://leetcode.com/problems/maximum-number-of-visible-points/
public class Code04_MaximumNumberOfVisiblePoints {

	public static int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
		int n = points.size();
		int a = location.get(0);
		int b = location.get(1);
		int zero = 0;
		double[] arr = new double[n << 1];
		int m = 0;
		for (int i = 0; i < n; i++) {
			int x = points.get(i).get(0) - a;
			int y = points.get(i).get(1) - b;
			if (x == 0 && y == 0) {
				zero++;
			} else {
				arr[m] = Math.toDegrees(Math.atan2(y, x));
				arr[m + 1] = arr[m] + 360;
				m += 2;
			}
		}
		Arrays.sort(arr, 0, m);
		int max = 0;
		for (int i = 0, j = 0; i < n; i++) {
			while (j < m && arr[j] - arr[i] <= angle) {
				j++;
			}
			max = Math.max(max, j - i);
		}
		return max + zero;
	}

}

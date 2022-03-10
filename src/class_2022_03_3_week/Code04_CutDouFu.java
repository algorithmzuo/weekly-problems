package class_2022_03_3_week;

import java.util.Arrays;

// 来自美团
// 有一块10000 * 10000 * 10000的立方体豆腐
// 豆腐的前左下角放在(0,0,0)点，豆腐的后右上角放在(10000,10000,10000)点
// 下面给出切法的数据结构
// [a,b]
// a = 1，表示x = b处，一把无穷大的刀平行于yz面贯穿豆腐切过去
// a = 2，表示y = b处，一把无穷大的刀平行于xz面贯穿豆腐切过去
// a = 3，表示z = b处，一把无穷大的刀平行于xy面贯穿豆腐切过去
// a = 1 or 2 or 3，0<=b<=10000
// 给定一个n*2的二维数组，表示切了n刀
// 返回豆腐中最大的一块体积是多少
public class Code04_CutDouFu {

	public static long maxCut(int[][] cuts) {
		if (cuts == null || cuts.length == 0) {
			return 10000L * 10000L * 10000L;
		}
		Arrays.sort(cuts, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		int n = cuts.length;
		int i = 0;
		int xMaxDiff = 0;
		int pre = 0;
		while (i < n && cuts[i][0] == 1) {
			xMaxDiff = Math.max(xMaxDiff, cuts[i][1] - pre);
			pre = cuts[i][1];
			i++;
		}
		xMaxDiff = Math.max(xMaxDiff, 10000 - pre);
		int yMaxDiff = 0;
		pre = 0;
		while (i < n && cuts[i][0] == 2) {
			yMaxDiff = Math.max(yMaxDiff, cuts[i][1] - pre);
			pre = cuts[i][1];
			i++;
		}
		yMaxDiff = Math.max(yMaxDiff, 10000 - pre);
		int zMaxDiff = 0;
		pre = 0;
		while (i < n && cuts[i][0] == 3) {
			zMaxDiff = Math.max(zMaxDiff, cuts[i][1] - pre);
			pre = cuts[i][1];
			i++;
		}
		zMaxDiff = Math.max(zMaxDiff, 10000 - pre);
		return (long) xMaxDiff * (long) yMaxDiff * (long) zMaxDiff;
	}

}

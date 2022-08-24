package class_2022_08_4_week;

// 来自神策
// 给定一个数组arr，表示连续n天的股价，数组下标表示第几天
// 指标X：任意两天的股价之和 - 此两天间隔的天数
// 比如
// 第3天，价格是10
// 第9天，价格是30
// 那么第3天和第9天的指标X = 10 + 30 - (9 - 3) = 34
// 返回arr中最大的指标X

public class Code01_MaxXFromStock {

	public static int maxX(int[] arr) {
		if (arr == null || arr.length < 2) {
			return -1;
		}
		int preBest = arr[0];
		int ans = 0;
		for (int i = 1; i < arr.length; i++) {
			ans = Math.max(ans, arr[i] - i + preBest);
			preBest = Math.max(preBest, arr[i] + i);
		}
		return ans;
	}

}

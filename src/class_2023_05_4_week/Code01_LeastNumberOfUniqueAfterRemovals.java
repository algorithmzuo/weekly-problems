package class_2023_05_4_week;

import java.util.Arrays;
import java.util.HashMap;

// 先来一个智力题
// 给定n个二维坐标，表示在二维平面的n个点，
// 坐标为double类型，精度最多小数点后两位
// 希望在二维平面上画一个圆，圈住其中的k个点，其他的n-k个点都要在圆外
// 返回一个圆心和半径，表示哪个圆可以圈住其中的k个点
// 坐标和半径都是double类型，最多保留小数点后两位
// 下面是正式题目
// 给你一个整数数组 arr 和一个整数 k
// 现需要从数组中恰好移除 k 个元素
// 请找出移除后数组中不同整数的最少数目
// 测试链接 : https://leetcode.cn/problems/least-number-of-unique-integers-after-k-removals/
public class Code01_LeastNumberOfUniqueAfterRemovals {

	public static int findLeastNumOfUniqueInts(int[] arr, int k) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int num : arr) {
			map.put(num, map.getOrDefault(num, 0) + 1);
		}
		int n = map.size();
		int[] cnts = new int[n];
		int i = 0;
		for (int cnt : map.values()) {
			cnts[i++] = cnt;
		}
		Arrays.sort(cnts);
		for (i = 0; i < n; i++) {
			k -= cnts[i];
			if (k <= 0) {
				if (k == 0) {
					i++;
				}
				break;
			}
		}
		return n - i;
	}

}

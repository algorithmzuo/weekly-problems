package class_2023_02_1_week;

import java.util.TreeMap;

// 给定一个整数数组 A，你可以从某一起始索引出发，跳跃一定次数
// 在你跳跃的过程中，第 1、3、5... 次跳跃称为奇数跳跃
// 而第 2、4、6... 次跳跃称为偶数跳跃
// 你可以按以下方式从索引 i 向后跳转到索引 j（其中 i < j）：
// 在进行奇数跳跃时（如，第 1，3，5... 次跳跃），你将会跳到索引 j
// 使得 A[i] <= A[j]，A[j] 是可能的最小值。如果存在多个这样的索引 j
// 你只能跳到满足要求的最小索引 j 上。
// 在进行偶数跳跃时（如，第 2，4，6... 次跳跃）
// 你将会跳到索引 j，使得 A[i] >= A[j]，A[j] 是可能的最大值
// 如果存在多个这样的索引 j，你只能跳到满足要求的最小索引 j 上。
//（对于某些索引 i，可能无法进行合乎要求的跳跃。）
// 如果从某一索引开始跳跃一定次数（可能是 0 次或多次）
// 就可以到达数组的末尾（索引 A.length - 1）
// 那么该索引就会被认为是好的起始索引
// 返回好的起始索引的数量
// 测试链接 : https://leetcode.cn/problems/odd-even-jump/
public class Code02_OddEvenJump {

	public static int oddEvenJumps(int[] arr) {
		int n = arr.length;
		int[] odd = new int[n];
		int[] even = new int[n];
		TreeMap<Integer, Integer> orderMap = new TreeMap<>();
		for (int i = n - 1; i >= 0; i--) {
			Integer to = orderMap.ceilingKey(arr[i]);
			odd[i] = to == null ? -1 : orderMap.get(to);
			to = orderMap.floorKey(arr[i]);
			even[i] = to == null ? -1 : orderMap.get(to);
			orderMap.put(arr[i], i);
		}
		boolean[][] dp = new boolean[n][2];
		dp[n - 1][0] = true;
		dp[n - 1][1] = true;
		int ans = 1;
		for (int i = n - 2; i >= 0; i--) {
			dp[i][0] = odd[i] != -1 && dp[odd[i]][1];
			dp[i][1] = even[i] != -1 && dp[even[i]][0];
			ans += dp[i][0] ? 1 : 0;
		}
		return ans;
	}

}

package class_2022_11_4_week;

// 来自字节
// 11.02笔试
// leetcode原题
// 有一堆石头，用整数数组 stones 表示
// 其中 stones[i] 表示第 i 块石头的重量。
// 每一回合，从中选出任意两块石头，然后将它们一起粉碎
// 假设石头的重量分别为 x 和 y，且 x <= y
// 那么粉碎的可能结果如下：
// 如果 x == y，那么两块石头都会被完全粉碎；
// 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
// 最后，最多只会剩下一块 石头
// 返回此石头 最小的可能重量
// 如果没有石头剩下，就返回 0
// 测试链接 : https://leetcode.cn/problems/last-stone-weight-ii/
public class Code04_LastStoneWeightII {

	public int lastStoneWeightII(int[] arr) {
		int n = arr.length;
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		int half = sum / 2;
		int[][] dp = new int[n + 1][half + 1];
		for (int i = n - 1; i >= 0; i--) {
			for (int rest = 0; rest <= half; rest++) {
				int p1 = dp[i + 1][rest];
				int p2 = 0;
				if (arr[i] <= rest) {
					p2 = arr[i] + dp[i + 1][rest - arr[i]];
				}
				dp[i][rest] = Math.max(p1, p2);
			}
		}
		return Math.abs(sum - dp[0][half] - dp[0][half]);
	}

}

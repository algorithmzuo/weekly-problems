package class_2022_01_3_week;

import java.util.ArrayList;
import java.util.LinkedList;

// 来自美团
// 小团去参加军训，军训快要结束了，长官想要把大家一排n个人分成m组，然后让每组分别去参加阅兵仪式
// 只能选择相邻的人一组，不能随意改变队伍中人的位置
// 阅兵仪式上会进行打分，其中有一个奇怪的扣分点是每组的最大差值，即每组最大值减去最小值
// 长官想要让这分成的m组总扣分量最小，即这m组分别的极差之和最小
// 长官正在思索如何安排中，就让小团来帮帮他吧
public class Code03_SplitToMArraysMinScore {

	// 暴力方法
	// 为了验证
	public static int minScore1(int[] arr, int m) {
		if (m == 0) {
			return 0;
		}
		return process(arr, 1, m, arr[0], arr[0]);
	}

	public static int process(int[] arr, int index, int rest, int preMin, int preMax) {
		if (index == arr.length) {
			return rest == 1 ? (preMax - preMin) : -1;
		}
		int p1 = process(arr, index + 1, rest, Math.min(preMin, arr[index]), Math.max(preMax, arr[index]));
		int p2next = process(arr, index + 1, rest - 1, arr[index], arr[index]);
		int p2 = p2next == -1 ? -1 : (preMax - preMin + p2next);
		if (p1 == -1) {
			return p2;
		}
		if (p2 == -1) {
			return p1;
		}
		return Math.min(p1, p2);
	}

	public static int score(ArrayList<LinkedList<Integer>> sets) {
		int ans = 0;
		for (LinkedList<Integer> set : sets) {
			if (set.isEmpty()) {
				return Integer.MAX_VALUE;
			}
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;
			for (int num : set) {
				max = Math.max(max, num);
				min = Math.min(min, num);
			}
			ans += max - min;
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(M * N * N)
	// 特别难的一个结论 : 这道题不能利用四边形不等式，我试了
	// 这个特别难的结论不要求必须掌握，因为四边形不等式的技巧非常冷门
	public static int minScore2(int[] arr, int m) {
		if (m == 0) {
			return 0;
		}
		int n = arr.length;
		int[][] score = new int[n][n];
		for (int i = 0; i < n; i++) {
			int max = arr[i];
			int min = arr[i];
			score[i][i] = max - min;
			for (int j = i + 1; j < n; j++) {
				max = Math.max(max, arr[j]);
				min = Math.min(min, arr[j]);
				score[i][j] = max - min;
			}
		}
		int[][] dp = new int[m + 1][n];
		for (int i = 0; i < n; i++) {
			dp[1][i] = score[0][i];
		}
		for (int split = 2; split <= m; split++) {
			for (int i = split; i < n; i++) {
				dp[split][i] = dp[split - 1][i];
				for (int j = 1; j <= i; j++) {
					dp[split][i] = Math.min(dp[split][i], dp[split - 1][j - 1] + score[j][i]);
				}
			}
		}
		return dp[m][n - 1];
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v);
		}
		return arr;
	}

	public static void main(String[] args) {
		int len = 15;
		int value = 50;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, value);
			int m = (int) (Math.random() * n) + 1;
			int ans1 = minScore1(arr, m);
			int ans2 = minScore2(arr, m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("m : " + m);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

package class_2022_05_4_week;

// 来自弗吉尼亚理工大学(VT)，算法考试卷
// 精选了还可以的几道题
// 这些都是简单难度的动态规划，是面试中最常见的难度
// 这几个题都有一些非常小的常见技巧可说
public class Code02_SomeDPFromVT {

	// 题目1
	// 找到一个最少张数的方案，让FunnyGoal、OffenseGoal，都超过一个值
	// 定义如下尝试过程
	// 贴纸数组stickers
	// stickers[i][0] : i号贴纸的Funny值
	// stickers[i][1] : i号贴纸的Offense值
	// index....所有的贴纸，随便选择。index之前的贴纸不能选择，
	// 在让restFunny和restOffense都小于等于0的要求下，返回最少的贴纸数量
	public static int minStickers1(int[][] stickers, int funnyGoal, int offenseGoal) {
		return process1(stickers, 0, funnyGoal, offenseGoal);
	}

	public static int process1(int[][] stickers, int index, int restFunny, int restOffense) {
		if (restFunny <= 0 && restOffense <= 0) {
			return 0;
		}
		if (index == stickers.length) {
			return Integer.MAX_VALUE;
		}
		// 不选当前的贴纸
		int p1 = process1(stickers, index + 1, restFunny, restOffense);
		// 选当前贴纸
		int p2 = Integer.MAX_VALUE;
		int next2 = process1(stickers, index + 1, Math.max(0, restFunny - stickers[index][0]),
				Math.max(0, restOffense - stickers[index][1]));
		if (next2 != Integer.MAX_VALUE) {
			p2 = next2 + 1;
		}
		return Math.min(p1, p2);
	}

	// 改动态规划
	public static int minStickers2(int[][] stickers, int funnyGoal, int offenseGoal) {
		int[][][] dp = new int[stickers.length][funnyGoal + 1][offenseGoal + 1];
		for (int i = 0; i < stickers.length; i++) {
			for (int j = 0; j <= funnyGoal; j++) {
				for (int k = 0; k <= offenseGoal; k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		return process2(stickers, 0, funnyGoal, offenseGoal, dp);
	}

	public static int process2(int[][] stickers, int index, int restFunny, int restOffense, int[][][] dp) {
		if (restFunny <= 0 && restOffense <= 0) {
			return 0;
		}
		if (index == stickers.length) {
			return Integer.MAX_VALUE;
		}
		if (dp[index][restFunny][restOffense] != -1) {
			return dp[index][restFunny][restOffense];
		}
		// 不选当前的贴纸
		int p1 = process2(stickers, index + 1, restFunny, restOffense, dp);
		// 选当前贴纸
		int p2 = Integer.MAX_VALUE;
		int next2 = process2(stickers, index + 1, Math.max(0, restFunny - stickers[index][0]),
				Math.max(0, restOffense - stickers[index][1]), dp);
		if (next2 != Integer.MAX_VALUE) {
			p2 = next2 + 1;
		}
		int ans = Math.min(p1, p2);
		dp[index][restFunny][restOffense] = ans;
		return ans;
	}
	
	// 严格位置依赖的动态规划
	public static int minStickers3(int[][] stickers, int funnyGoal, int offenseGoal) {
		int n = stickers.length;
		int[][][] dp = new int[n + 1][funnyGoal + 1][offenseGoal + 1];
		for (int f = 0; f <= funnyGoal; f++) {
			for (int o = 0; o <= offenseGoal; o++) {
				if (f != 0 || o != 0) {
					dp[n][f][o] = Integer.MAX_VALUE;
				}
			}
		}
		for (int i = n - 1; i >= 0; i--) {
			for (int f = 0; f <= funnyGoal; f++) {
				for (int o = 0; o <= offenseGoal; o++) {
					if (f != 0 || o != 0) {
						int p1 = dp[i + 1][f][o];
						int p2 = Integer.MAX_VALUE;
						int next2 = dp[i + 1][Math.max(0, f - stickers[i][0])][Math.max(0, o - stickers[i][1])];
						if (next2 != Integer.MAX_VALUE) {
							p2 = next2 + 1;
						}
						dp[i][f][o] = Math.min(p1, p2);
					}
				}
			}
		}
		return dp[0][funnyGoal][offenseGoal];
	}

	// 题目2
	// 绳子总长度为M
	// 每一个长度的绳子对应一个价格，比如(6, 10)表示剪成长度为6的绳子，对应价格10
	// 可以重复切出某个长度的绳子
	// 定义递归如下：
	// 所有可以切出来的长度 对应 价值都在数组ropes里
	// ropes[i] = {6, 10} 代表i方案为：切出长度为6的绳子，可以卖10元
	// index....所有的方案，随便选择。index之前的方案，不能选择
	// 返回最大的价值
	// 自己去改动态规划
	public static int maxValue(int[][] ropes, int index, int restLen) {
		if (restLen <= 0) {
			return 0;
		}
		// 当前index方案，就是不考虑
		int p1 = maxValue(ropes, index + 1, restLen);
		// 当前index方案，考虑，然后因为可以重复选，所以注意下面的子过程调用
		int p2 = -1;
		if (ropes[index][0] <= restLen) {
			// 当前index方案，选了一份
			// 但是下面依然可以重复的选index方案
			// 所以子过程里的index不增加，只是剩余长度减少
			p2 = ropes[index][1] + maxValue(ropes, index, restLen - ropes[index][0]);
		}
		return Math.max(p1, p2);
	}

	// 题目3
	// 定义尝试过程如下
	// arr[i] = {4, 9}表示，第i个序列4开始，9结束
	// pre : 代表选择的上一个序列，的，index是多少
	// 比如选择的上一个序列如果是(4,9)，是第5个序列，那么pre==5
	// 特别注意：如果从来没有选过序列，那么pre == -1
	// 这个函数含义 :
	// index....所有的序列，随便选择。index之前的序列，不能选择
	// 上一个选择的序列，是pre号，如果pre==-1,说明之前没有选择过序列
	// 返回题目要求的那种连接方式下，最大的序列数量
	public static int maxNumberSubsequence(int[][] arr, int index, int pre) {
		if (index == arr.length) {
			return 0;
		}
		// 就是不要当前序列
		int p1 = maxNumberSubsequence(arr, index + 1, pre);
		// 要当前序列
		int p2 = -1;
		if (pre == -1 || arr[pre][1] == arr[index][0]) {
			p2 = 1 + maxNumberSubsequence(arr, index + 1, index);
		}
		return Math.max(p1, p2);
	}

}

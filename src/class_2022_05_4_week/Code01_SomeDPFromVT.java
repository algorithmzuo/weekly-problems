package class_2022_05_4_week;

// 来自弗吉尼亚理工大学(VT)，算法考试卷
// 精选了还可以的几道题
// 这些都是简单难度的动态规划，是面试中最常见的难度
// 这几个题都有一些非常小的常见技巧可说
public class Code01_SomeDPFromVT {

//	// arr[i][0] : 有趣值
//	// arr[i][1] : 进攻值
//	// arr[index...]所有的方案自由选择
//	// 必须让restFunny、restOffense值 <= 0
//	// 返回最小的方案数量(index...)
//	public static int process(int[][] arr, int index, int restFunny, int restOffense) {
//		if (restFunny <= 0 && restOffense <= 0) {
//			return 0;
//		}
//		// 有的值，还没扣完
//		if (index == arr.length) {
//			return Integer.MAX_VALUE; // 无效值
//		}
//		// 有的值还没扣完 但是还有方案可选
//		// index号方案 不要 要
//		int p1 = process(arr, index + 1, restFunny, restOffense);
//		// 要用index号方案
//		int p2 = Integer.MAX_VALUE;
//		int next = process(arr, index + 1, restFunny - arr[index][0], restOffense - arr[index][1]);
//		if (next != Integer.MAX_VALUE) {
//			p2 = 1 + next;
//		}
//		return Math.min(p1, p2);
//	}

	// 题目1
	// 方案1 : {7, 10}
	// xxxx : {a , b}
	// 1 2 3 4
	// FunnyGoal = 100
	// OffenseGoal = 130
	// 找到一个最少方案数，让FunnyGoal、OffenseGoal，都大于等于
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
	// 100 -> M
	// (6, 100) (7,23) (10,34) -> arr
	// 每一个长度的绳子对应一个价格，比如(6, 10)表示剪成长度为6的绳子，对应价格10
	// 可以重复切出某个长度的绳子
	// 定义递归如下：
	// 所有可以切出来的长度 对应 价值都在数组ropes里
	// ropes[i] = {6, 10} 代表i方案为：切出长度为6的绳子，可以卖10元
	// index....所有的方案，随便选择。index之前的方案，不能选择
	// 返回最大的价值
	// 自己去改动态规划
	// arr[i][0] -> i号方案能切多少长度
	// arr[i][1] -> 切出来这个长度，就能获得的价值
	// arr[index....]自由选择，绳子还剩restLen长度
	// 返回，最大价值
	public static int maxValue(int[][] arr, int index, int restLen) {
		if (restLen <= 0 || index == arr.length) {
			return 0;
		}
		// 绳子还有剩余、且还有方案
		// index号方案
		// 不选
		int p1 = maxValue(arr, index + 1, restLen);
		// 选
		int p2 = 0;
		if (arr[index][0] <= restLen) { // 剩余绳子够长，才能选当前方案
			p2 = arr[index][1] + maxValue(arr, index, restLen - arr[index][0]);
		}
		return Math.max(p1, p2);
	}

//	public static int maxValue(int[][] ropes, int index, int restLen) {
//		if (restLen <= 0) {
//			return 0;
//		}
//		// 当前index方案，就是不考虑
//		int p1 = maxValue(ropes, index + 1, restLen);
//		// 当前index方案，考虑，然后因为可以重复选，所以注意下面的子过程调用
//		int p2 = -1;
//		if (ropes[index][0] <= restLen) {
//			// 当前index方案，选了一份
//			// 但是下面依然可以重复的选index方案
//			// 所以子过程里的index不增加，只是剩余长度减少
//			p2 = ropes[index][1] + maxValue(ropes, index, restLen - ropes[index][0]);
//		}
//		return Math.max(p1, p2);
//	}

	// 题目3
	// 每一个序列都是[a,b]的形式，a < b
	// 序列连接的方式为，前一个序列的b，要等于后一个序列的a
	// 比如 : [3, 7]、[7, 13]、[13, 26]这三个序列就可以依次连接
	// 给定若干个序列，求最大连接的数量
	// 定义尝试过程如下
	// arr[i] = {4, 9}表示，第i个序列4开始，9结束
	// pre : 代表选择的上一个序列，的，index是多少
	// 比如选择的上一个序列如果是(4,9)，是第5个序列，那么pre==5
	// 特别注意：如果从来没有选过序列，那么pre == -1
	// 这个函数含义 :
	// index....所有的序列，随便选择。index之前的序列，不能选择
	// 上一个选择的序列，是pre号，如果pre==-1,说明之前没有选择过序列
	// 返回题目要求的那种连接方式下，最大的序列数量
	// [5,13] [1,19] [2, 3] [79, 81] ...
	// [1,19] [2, 3] [5, 13] [79, 81]
	// arr[i][0] : 开头
	// arr[i][1] : 结尾
	// arr已经根据开头排过序了！
	// preEnd index
	// [1, 3] [2, 4] [4, 7]
	// 0 1 2
	// maxLen(0, -1)
	// 0（选） -> maxLen(1, 0)
	// 在arr[index...]选择序列，之前选的，离index最近的序列，位置在preIndex
	// 请返回，index...能链接起来的，序列数量的最大值
	public static int maxLen(int[][] arr, int index, int preIndex) {
		if (index == arr.length) {
			return 0;
		}
		// 还有序列可以选
		// index号序列
		// 不选
		int p1 = maxLen(arr, index + 1, preIndex);
		// 选
		int p2 = 0;
		// [3,17] index(9,24)
		if (arr[preIndex][1] == arr[index][0]) { // 才能选
			p2 = 1 + maxLen(arr, index + 1, index);
		}
		return Math.max(p1, p2);
	}

	// O(N^2)
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
	
	// O(N)
	

}

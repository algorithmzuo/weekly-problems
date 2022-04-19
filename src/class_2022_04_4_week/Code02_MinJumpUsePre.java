package class_2022_04_4_week;

// 来自学员问题
// 为了给刷题的同学一些奖励，力扣团队引入了一个弹簧游戏机
// 游戏机由 N 个特殊弹簧排成一排，编号为 0 到 N-1
// 初始有一个小球在编号 0 的弹簧处。若小球在编号为 i 的弹簧处
// 通过按动弹簧，可以选择把小球向右弹射 jump[i] 的距离，或者向左弹射到任意左侧弹簧的位置
// 也就是说，在编号为 i 弹簧处按动弹簧，
// 小球可以弹向 0 到 i-1 中任意弹簧或者 i+jump[i] 的弹簧（若 i+jump[i]>=N ，则表示小球弹出了机器）
// 小球位于编号 0 处的弹簧时不能再向左弹。
// 为了获得奖励，你需要将小球弹出机器。
// 请求出最少需要按动多少次弹簧，可以将小球从编号 0 弹簧弹出整个机器，即向右越过编号 N-1 的弹簧。
// 测试链接 : https://leetcode-cn.com/problems/zui-xiao-tiao-yue-ci-shu/
public class Code02_MinJumpUsePre {

	public int minJump(int[] jump) {
		int n = jump.length;
		int[] dp = new int[n];
		dp[n - 1] = 1;
		for (int i = n - 2; i >= 0; i--) {
			dp[i] = jump[i] + i >= n ? 1 : (dp[i + jump[i]] + 1);
			// 下面的for循环中，最核心的一个判断 : dp[j] >= dp[i] + 1 才继续
			// 这是一个特别强大的剪枝
			// 没有就超时
			// 有就打败100%的人
			for (int j = i + 1; j < n && dp[j] >= dp[i] + 1; j++) {
				dp[j] = dp[i] + 1;
			}
		}
		return dp[0];
	}

}

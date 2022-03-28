package class_2022_04_1_week;

// 测试链接 : https://leetcode-cn.com/problems/zui-xiao-tiao-yue-ci-shu/
public class Code08_MinJump {

	public int minJump(int[] jump) {
		int n = jump.length;
		int[] dp = new int[n];
		dp[n - 1] = 1;
		for (int i = n - 2; i >= 0; i--) {
			dp[i] = jump[i] + i >= n ? 1 : (dp[i + jump[i]] + 1);
			// 最核心 : dp[j] >= dp[i] + 1 才继续
			for (int j = i + 1; j < n && dp[j] >= dp[i] + 1; j++) {
				dp[j] = dp[i] + 1;
			}
		}
		return dp[0];
	}

}

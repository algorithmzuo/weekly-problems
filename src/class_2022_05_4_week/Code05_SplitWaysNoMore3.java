package class_2022_05_4_week;

// 来自华为
// m个人分n个月饼，
// 每个人至少分1个，分第一多的月饼数a1，分第二多的月饼数a2，分第三多的月饼数a3... 
// 要求a1-a2<=3，a2-a3<=3...有多少种分法?
public class Code05_SplitWaysNoMore3 {

	public static int ways(int m, int n) {
		if (m > n) {
			return 0;
		}
		if (m == 1) {
			return 1;
		}
		int[][][] dp = new int[n][m][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < n; k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		int ans = 0;
		for (int first = 1; first <= n / m; first++) {
			ans += process(first, m - 1, n - first, dp);
		}
		return ans;
	}

	// 前一个人拿了pre块月饼
	// 剩下people个人
	// 剩下cake块月饼
	// 当前的人不能拿少于pre块，也不能比pre+3块多
	// 返回方法数
	public static int process(int pre, int people, int cake, int[][][] dp) {
		if (pre * people > cake) {
			return 0;
		}
		if (people == 1) {
			return 1;
		}
		if (dp[pre][people][cake] != -1) {
			return dp[pre][people][cake];
		}
		int ans = 0;
		for (int pick = pre; pick <= Math.min(cake, pre + 3); pick++) {
			ans += process(pick, people - 1, cake - pick, dp);
		}
		dp[pre][people][cake] = ans;
		return ans;
	}

	public static void main(String[] args) {
		int m = 30;
		int n = 50;
		System.out.println(ways(m, n));
	}

}

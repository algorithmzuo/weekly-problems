package class_2023_05_3_week;

// 来自学员问题
// 假设每一次获得随机数的时候，这个数字大于100的概率是P
// 尝试N次，其中大于100的次数在A次~B次之间的概率是多少?
// 0 < P < 1, P是double类型
// 1 <= A <= B <= N <= 100
public class Code02_More100TimesBetweenAB {

	public static double probability(double P, int N, int A, int B) {
		double[][] dp = new double[N + 1][N + 1];
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= N; j++) {
				dp[i][j] = -1;
			}
		}
		double ans = 0;
		for (int j = A; j <= B; j++) {
			ans += process(P, 1D - P, N, j, dp);
		}
		return ans;
	}

	// 获得随机数大于100的概率是more
	// 获得随机数小于等于100的概率是less
	// 还有i次需要去扔
	// 扔出大于100的次数必须是j次
	// 返回概率
	public static double process(double more, double less, int i, int j, double[][] dp) {
		if (i < 0 || j < 0 || i < j) {
			return 0D;
		}
		// i >= 0 & j >= 0 & i >= j
		if (i == 0 && j == 0) {
			return 1D;
		}
		// i < 0
		// i == 0 j > 0
		// i == 0 j < 0
		// i == 0 j == 0
		// 如果是上面四种情况，都提前返回了
		// i > 0 & i >= j
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		// 1) > 100 情况，more * (i - 1, j - 1)
		// 2) <= 100 情况 , less * (i - 1, j)
		// 总体达成的概率 = 1) + 2)
		double ans = more * process(more, less, i - 1, j - 1, dp) + less * process(more, less, i - 1, j, dp);
		dp[i][j] = ans;
		return ans;
	}

	public static void main(String[] args) {
		double P = 0.6;
		int N = 100;
		int A = 30;
		int B = 50;
		System.out.println(probability(P, N, A, B));
	}

}

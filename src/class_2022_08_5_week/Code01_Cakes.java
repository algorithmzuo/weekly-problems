package class_2022_08_5_week;

// 来自学员面试
// nim博弈
// 有a块草莓蛋糕，有b块芝士蛋糕，两人轮流拿蛋糕
// 每次不管是谁只能选择在草莓蛋糕和芝士蛋糕中拿一种
// 拿的数量在1~m之间随意
// 谁先拿完最后的蛋糕谁赢
// 返回先手赢还是后手赢
public class Code01_Cakes {

	// 草莓蛋糕a块
	// 巧克力蛋糕b块
	// 每次可以在任意一种上拿1~m块
	// 返回谁会赢，"先手" or "后手"
	public static String[][][] dp = new String[101][101][101];

	// 暴力方法
	// 为了验证
	public static String whoWin1(int a, int b, int m) {
		if (m >= Math.max(a, b)) { // nim博弈
			return a != b ? "先手" : "后手";
		}
		if (a == b) {
			// 蛋糕一样多
			// 先手必输，因为先手不管拿什么，拿多少
			// 后手都在另一堆上，拿同样多的蛋糕
			// 继续让两堆蛋糕一样多
			// 最终先手必输，后手必赢
			return "后手";
		}
		if (dp[a][b][m] != null) {
			return dp[a][b][m];
		}
		String ans = "后手";
		for (int pick = 1; pick <= Math.min(a, m); pick++) {
			if (whoWin1(a - pick, b, m).equals("后手")) {
				ans = "先手";
			}
			if (ans.equals("先手")) {
				break;
			}
		}
		for (int pick = 1; pick <= Math.min(b, m); pick++) {
			if (whoWin1(a, b - pick, m).equals("后手")) {
				return "先手";
			}
			if (ans.equals("先手")) {
				break;
			}
		}
		dp[a][b][m] = ans;
		return ans;
	}

	// 正式解法
	// 时间复杂度O(1)
	// 先看nim博弈
	public static String whoWin2(int a, int b, int m) {
		if (m >= Math.max(a, b)) { // nim博弈
			return a != b ? "先手" : "后手";
		}
		// m < max(a,b)
		if (a == b) {
			// 蛋糕一样多
			// 先手必输，因为先手不管拿什么，拿多少
			// 后手都在另一堆上，拿同样多的蛋糕
			// 继续让两堆蛋糕一样多
			// 最终先手必输，后手必赢
			return "后手";
		}
		// 如果 a != b
		// 关注a和b的差值，
		// 谁最先遇到差值为0，谁输
		// 那么这就是巴什博奕
		// 差值蛋糕数量共rest个。
		// 每次从最少取1个，最多取m个，最后取光的人取胜。
		// 如果rest=(m+1)*k + s (s!=0) 那么先手一定必胜
		// 因为第一次取走s个，
		// 接下来无论对手怎么取，
		// 先手都能保证取到所有(m+1)倍数的点，
		// 那么循环下去一定能取到差值最后一个。
		int rest = Math.max(a, b) - Math.min(a, b);
		return rest % (m + 1) != 0 ? "先手" : "后手";
	}

	public static void main(String[] args) {
		int V = 23;
		System.out.println("测试开始");
		int cnt = 0;
		int all = (V + 1) * (V + 1) * (V + 1);
		for (int a = 0; a <= V; a++) {
			for (int b = 0; b <= V; b++) {
				for (int m = 0; m <= V; m++) {
					String ans1 = whoWin1(a, b, m);
					String ans2 = whoWin2(a, b, m);
					if (!ans1.equals(ans2)) {
						System.out.println("出错了！");
						System.out.println("a : " + a);
						System.out.println("b : " + b);
						System.out.println("m : " + m);
						System.out.println("ans1 : " + ans1);
						System.out.println("ans2 : " + ans2);
						break;
					}
					cnt++;
					if (cnt % 1000 == 0) {
						System.out.println("已经测了" + cnt + "组(共" + all + "组)");
					}
				}
			}
		}
		System.out.println("测试结束");
	}

}

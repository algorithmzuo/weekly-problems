package class_2023_03_1_week;

// 来自学员问题
// 给定一个1~N的排列，每次将相邻两数相加，可以得到新的序列，长度是N-1
// 再对新的序列，每次将相邻两数相加，可以得到新的序列，长度是N-2
// 这样下去可以最终只剩一个数字
// 比如 : 
// 3 1 2 4
//  4 3 6
//   7 9
//    16
// 现在如果知道N，和最后的数字sum，反推最原始的序列是什么
// 如果有多个答案，返回字典序最小的那个
// 字典序看做所有数字拼起来的字符串字典序
// 比如
// 1, 10, 2... 拼起来是 1102...
// 1, 2, 3, 4... 拼起来是 1234...
// 认为 1, 10, 2...的字典序更小
// 如果给定的n和sum，有答案，返回一个N长度的答案数组
// 如果给定的n和sum，无答案，返回一个1长度的数组{ -1 }
// 输入 : N = 4, sum = 16
// 输出 : 3 1 2 4
// 输入 : N = 10, sum = 4116
// 输出 : 1 3 5 7 10 9 8 6 4 2 
// 0 < n <= 10, sum随意
public class Code01_LexicographicalSmallestPermutation {

	// 准备好杨辉三角形，作为每一项系数
	public static int[][] moduluses = { 
			{},
			{ 1 },
			{ 1, 1 },
			{ 1, 2, 1 },
			{ 1, 3, 3, 1 },
			{ 1, 4, 6, 4, 1 },
			{ 1, 5, 10, 10, 5, 1 },
			{ 1, 6, 15, 20, 15, 6, 1 },
			{ 1, 7, 21, 35, 35, 21, 7, 1 },
			{ 1, 8, 28, 56, 70, 56, 28, 8, 1 },
			{ 1, 9, 36, 84, 126, 126, 84, 36, 9, 1 } };

	// sums[1] = 只有1这个数字，整出来的最大和不会超过sums[1]
	// sum[i] =  只有1~i这些数字，整出来的最大和不会超过sums[i]
	public static int[] sums = { 0, 1, 3, 9, 24, 61, 148, 350, 808, 1837, 4116 };

	public static int[] lsp(int n, int sum) {
		if (n < 1 || n > 10 || sum > sums[n]) {
			return new int[] { -1 };
		}
		int[][] dp = new int[1 << (n + 1)][sums[n] + 1];
		if (!process(((1 << (n + 1)) - 1) ^ 1, sum, 0, n, moduluses[n], dp)) {
			return new int[] { -1 };
		}
		int[] ans = new int[n];
		int index = 0;
		// n = 7
		// 000..000 100000000
		// 000..000 011111111
		// 000..000 011111110
		int status = ((1 << (n + 1)) - 1) ^ 1;
		int rest = sum;
		while (status != 0) {
			ans[index] = dp[status][rest];
			status ^= 1 << ans[index];
			rest -= ans[index] * moduluses[n][index];
			index++;
		}
		return ans;
	}

	// 一开始给你1 ~ 7
	// 一开始的status :  000000..000011111110
	// 1、2、3、4、5、6
	// 当前还有2、4、5可用      
	//                            6 5 4 3 2 1 0
	// status :                   0 1 1 0 1 0 0
	// status : 还能用的数字，都在status里！
	// rest : 还剩多少和，需要去搞定!
	// index : 当前可能把status里剩下的某个数字，拿出来用，需要 * 系数的！
	//         modulus[index]就是当前的系数！
	// 剩下的过程，能不能搞定rest这个和！
	// 能搞定返回true，不能搞定返回false
	// 递归最终要的目的，不仅仅是获得返回值！dp表填好!
	public static boolean process(int status, int rest, int index, int n, int[] modulus, int[][] dp) {
		if (rest < 0) {
			return false;
		}
		if (status == 0) { // 0000000000..000000000
			return rest == 0 ? true : false;
		}
		// dp[status][rest] == 0 (status,rest)之前没算过！
		// dp[status][rest] == -1 (status,rest)之前算过！返回了false！
		// dp[status][rest] != -1  (status,rest)之前算过！返回了true！
		// dp[status][rest] : 
		// 从字典序小的数字开始试，一旦试出来，记录当前是哪个数字试出来的！
		if (dp[status][rest] != 0) {
			return dp[status][rest] != -1;
		}
		// n < 10  1 2 3 4 5 6... status里得有！
		// n == 10 10 1 2 3 4 ... status里得有！
		// ans : 哪个数字试出来的！
		int ans = -1;
		if (n == 10 && (status & (1 << 10)) != 0) {
			// 真的可以先试10！
			if (process(status ^ (1 << 10), rest - modulus[index] * 10, index + 1, n, modulus, dp)) {
				ans = 10;
			}
		}
		// ans == 10
		if (ans == -1) {
			for (int i = 1; i <= n; i++) {
				// i : 1 2 3 ... n status得有才可以!
				if ((status & (1 << i)) != 0) {
					if (process(status ^ (1 << i), rest - modulus[index] * i, index + 1, n, modulus, dp)) {
						ans = i;
						break;
					}
				}
			}
		}
		dp[status][rest] = ans;
		return ans != -1;
	}

	public static void main(String[] args) {
		int N1 = 4;
		int sum1 = 16;
		int[] ans1 = lsp(N1, sum1);
		for (int num : ans1) {
			System.out.print(num + " ");
		}
		System.out.println();

		int N2 = 10;
		int sum2 = 4116;
		int[] ans2 = lsp(N2, sum2);
		for (int num : ans2) {
			System.out.print(num + " ");
		}
		System.out.println();
		
		int N3 = 10;
		int sum3 = 3688;
		int[] ans3 = lsp(N3, sum3);
		for (int num : ans3) {
			System.out.print(num + " ");
		}
		System.out.println();
		
		int N4 = 10;
		int sum4 = 4013;
		int[] ans4 = lsp(N4, sum4);
		for (int num : ans4) {
			System.out.print(num + " ");
		}
		System.out.println();
	}

}
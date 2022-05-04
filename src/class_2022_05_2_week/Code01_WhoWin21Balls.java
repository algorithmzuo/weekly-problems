package class_2022_05_2_week;

// 来自微众
// 人工智能岗
// 一开始有21个球，甲和乙轮流拿球，甲先乙后
// 每个人在自己的回合，一定要拿不超过3个球，不能不拿
// 最终谁的总球数为偶数，谁赢
// 请问谁有必胜策略
public class Code01_WhoWin21Balls {

	// 我们补充一下设定，假设一开始的球数量不是21，是任意的正数
	// 如果最终两个人拿的都是偶数，认为无人获胜，平局
	// 如果最终两个人拿的都是奇数，认为无人获胜，平局
	// rest代表目前剩下多少球
	// cur == 0 代表目前是甲行动
	// cur == 1 代表目前是乙行动
	// first == 0 代表目前甲所选的球数，加起来是偶数
	// first == 1 代表目前甲所选的球数，加起来是奇数
	// second == 0 代表目前乙所选的球数，加起来是偶数
	// second == 1 代表目前乙所选的球数，加起来是奇数
	// 返回选完了rest个球，谁会赢，只会返回"甲"、"乙"、"平"
	// win1方法，就是彻底暴力的做所有尝试，并且返回最终的胜利者
	// 在甲的回合，甲会尝试所有的可能，以保证自己会赢，如果自己怎么都不会赢，那也要尽量平局，如果这个也不行，只能对方赢
	// 在乙的回合，乙会尝试所有的可能，以保证自己会赢，如果自己怎么都不会赢，那也要尽量平局，如果这个也不行，只能对方赢
	// 算法和数据结构体系学习班，视频39章节，牛羊吃草问题，就是类似这种递归
	public static String win1(int rest, int cur, int first, int second) {
		if (rest == 0) {
			if (first == 0 && second == 1) {
				return "甲";
			}
			if (first == 1 && second == 0) {
				return "乙";
			}
			return "平";
		}
		if (cur == 0) { // 甲行动
			String bestAns = "乙";
			for (int pick = 1; pick <= Math.min(3, rest); pick++) {
				String curAns = win1(rest - pick, 1, first ^ (pick & 1), second);
				if (curAns.equals("甲")) {
					bestAns = "甲";
					break;
				}
				if (curAns.equals("平")) {
					bestAns = "平";
				}
			}
			return bestAns;
		} else { // 乙行动
			String bestAns = "甲";
			for (int pick = 1; pick <= Math.min(3, rest); pick++) {
				String curAns = win1(rest - pick, 0, first, second ^ (pick & 1));
				if (curAns.equals("乙")) {
					bestAns = "乙";
					break;
				}
				if (curAns.equals("平")) {
					bestAns = "平";
				}
			}
			return bestAns;
		}
	}

	// 下面的win2方法，仅仅是把win1方法，做了记忆化搜索
	// 变成了动态规划
	public static String[][][][] dp = new String[5000][2][2][2];

	public static String win2(int rest, int cur, int first, int second) {
		if (rest == 0) {
			if (first == 0 && second == 1) {
				return "甲";
			}
			if (first == 1 && second == 0) {
				return "乙";
			}
			return "平";
		}
		if (dp[rest][cur][first][second] != null) {
			return dp[rest][cur][first][second];
		}
		if (cur == 0) { // 甲行动
			String bestAns = "乙";
			for (int pick = 1; pick <= Math.min(3, rest); pick++) {
				String curAns = win2(rest - pick, 1, first ^ (pick & 1), second);
				if (curAns.equals("甲")) {
					bestAns = "甲";
					break;
				}
				if (curAns.equals("平")) {
					bestAns = "平";
				}
			}
			dp[rest][cur][first][second] = bestAns;
			return bestAns;
		} else { // 乙行动
			String bestAns = "甲";
			for (int pick = 1; pick <= Math.min(3, rest); pick++) {
				String curAns = win2(rest - pick, 0, first, second ^ (pick & 1));
				if (curAns.equals("乙")) {
					bestAns = "乙";
					break;
				}
				if (curAns.equals("平")) {
					bestAns = "平";
				}
			}
			dp[rest][cur][first][second] = bestAns;
			return bestAns;
		}
	}

	// 为了测试
	public static void main(String[] args) {
		for (int rest = 1; rest <= 500; rest++) {
			System.out.println("球数为 " + rest + " 时 , 赢的是 " + win2(rest, 0, 0, 0));
		}
	}

}
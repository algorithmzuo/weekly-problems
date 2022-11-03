package class_2022_11_1_week;

// 给你下标从 0 开始、长度为 n 的字符串 pattern ，
// 它包含两种字符，'I' 表示 上升 ，'D' 表示 下降 。
// 你需要构造一个下标从 0 开始长度为 n + 1 的字符串，且它要满足以下条件：
// num 包含数字 '1' 到 '9' ，其中每个数字 至多 使用一次。
// 如果 pattern[i] == 'I' ，那么 num[i] < num[i + 1] 。
// 如果 pattern[i] == 'D' ，那么 num[i] > num[i + 1] 。
// 请你返回满足上述条件字典序 最小 的字符串 num。
// 测试链接 : https://leetcode.cn/problems/construct-smallest-number-from-di-string/
public class Code05_CreateMinNumberFromPattern {

	public static String smallestNumber(String pattern) {
		return String.valueOf(create(pattern.toCharArray(), 0, 0, 0));
	}

	
	// pattern I I I D
	//         0 1 2 i
	//       1 3 4 5 2
	// -1
	
	//       1589
	//        9 8    5 4 3 2 1 0
	//        1 1    1 0 0 0 1 0
	//       number = 1589
	// 返回 i... 所有数字都决定了，并且不破坏pattern，并且1~9每个数字最多用一次
	// 能出来的最小值是啥，返回
	public static int create(char[] pattern, int index, int status, int number) {
		if (index == pattern.length + 1) {
			return number;
		}
		int cur = 0;
		while ((cur = next(status, cur)) != -1) {
			// cur == 0 , 当前位，1 X
			// cur == 1 , 当前位，2 X
			// cur == 2,  当前位，4 
			// partern I D >
			//         0 1 2 3
			//         ? ? ? ?
			//         D
			//         0 1
			//         5 ?
			if (index == 0 
					||
					(pattern[index - 1] == 'I' && number % 10 < cur) || (pattern[index - 1] == 'D' && number % 10 > cur)) {
				int ans = create(pattern, index + 1, status | (1 << cur), number * 10 + cur);
				if (ans != -1) {
					return ans;
				}
			}
		}
		return -1;
	}

	// status :
	//               9 8 7 6 5 4 3 2 1 0
	//               1 1 1 1 1 1 0 0 1 0
	// 返回没有使用，且 > num, 最小的数字
	// num = 3
	public static int next(int status, int num) {
		for (int i = num + 1; i <= 9; i++) {
			if ((status & (1 << i)) == 0) {
				return i;
			}
		}
		return -1;
	}

}

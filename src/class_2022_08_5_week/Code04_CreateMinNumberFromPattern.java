package class_2022_08_5_week;

// 给你下标从 0 开始、长度为 n 的字符串 pattern ，
// 它包含两种字符，'I' 表示 上升 ，'D' 表示 下降 。
// 你需要构造一个下标从 0 开始长度为 n + 1 的字符串，且它要满足以下条件：
// num 包含数字 '1' 到 '9' ，其中每个数字 至多 使用一次。
// 如果 pattern[i] == 'I' ，那么 num[i] < num[i + 1] 。
// 如果 pattern[i] == 'D' ，那么 num[i] > num[i + 1] 。
// 请你返回满足上述条件字典序 最小 的字符串 num。
// 测试链接 : https://leetcode.cn/problems/construct-smallest-number-from-di-string/
public class Code04_CreateMinNumberFromPattern {

	public static String smallestNumber(String pattern) {
		return String.valueOf(create(pattern.toCharArray(), 0, 0, 0));
	}

	public static int create(char[] p, int i, int s, int n) {
		if (i == p.length + 1) {
			return n;
		}
		int cur = 0;
		while ((cur = next(s, cur)) != -1) {
			if (i == 0 || (p[i - 1] == 'I' && n % 10 < cur) || (p[i - 1] == 'D' && n % 10 > cur)) {
				int ans = create(p, i + 1, s | (1 << cur), n * 10 + cur);
				if (ans != -1) {
					return ans;
				}
			}
		}
		return -1;
	}

	public static int next(int s, int num) {
		for (int i = num + 1; i <= 9; i++) {
			if ((s & (1 << i)) == 0) {
				return i;
			}
		}
		return -1;
	}

}

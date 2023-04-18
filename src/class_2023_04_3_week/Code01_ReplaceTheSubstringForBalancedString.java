package class_2023_04_3_week;

// 来自华为OD
// 完美走位问题
// 给定一个由'W'、'A'、'S'、'D'四种字符组成的字符串，长度一定是4的倍数
// 你可以把任意连续的一段子串，变成'W'、'A'、'S'、'D'组成的随意状态
// 目的是让4种字符词频一样
// 返回需要修改的最短子串长度
// 找到了出处，是leetcode原题
// 测试链接 : https://leetcode.cn/problems/replace-the-substring-for-balanced-string/
public class Code01_ReplaceTheSubstringForBalancedString {

	public static int balancedString(String str) {
		int n = str.length();
		int[] arr = new int[n];
		int[] cnts = new int[4];
		for (int i = 0; i < n; i++) {
			char c = str.charAt(i);
			arr[i] = c == 'W' ? 1 : (c == 'E' ? 2 : (c == 'R' ? 3 : 0));
			cnts[arr[i]]++;
		}
		int ans = n;
		for (int l = 0, r = 0; l < n; l++) {
			while (!ok(cnts, l, r) && r < n) {
				cnts[arr[r++]]--;
			}
			if (ok(cnts, l, r)) {
				ans = Math.min(ans, r - l);
			} else {
				break;
			}
			cnts[arr[l]]++;
		}
		return ans;
	}

	public static boolean ok(int[] cnts, int l, int r) {
		int maxCnt = Math.max(Math.max(cnts[0], cnts[1]), Math.max(cnts[2], cnts[3]));
		int changes = maxCnt * 4 - cnts[0] - cnts[1] - cnts[2] - cnts[3];
		int rest = r - l - changes;
		return rest >= 0 && rest % 4 == 0;
	}

}

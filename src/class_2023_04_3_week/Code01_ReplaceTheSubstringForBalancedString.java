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

	// Q W E R
	// 0 1 2 3
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
		// L = 0......
		//      1.....
		//       2....r
		//        3......
		for (int l = 0, r = 0; l < n; l++) {
			// !ok(cnts, l, r) , 当前窗口[l....r)，如果不能让四种字符一样多！
			// && r < n，虽然没达标，但是还有努力空间
			while (!ok(cnts, l, r) && r < n) {
				cnts[arr[r++]]--;
			}
			// 1) ok了
			// 2) 依然不ok，而且也没努力空间了
			if (ok(cnts, l, r)) {
				ans = Math.min(ans, r - l);
			} else {
				break;
			}
			cnts[arr[l]]++;
		}
		return ans;
	}

	// 窗口，str[l.....r)，你可以自由变化，但是窗口外的不能变化
	// l = 3, r = 10
	// [3....9]
	// 窗口长度 = r - l
	// cnts，窗口之外每一种字符的词频统计，不能算窗口内的统计的！
	// w : cnts[0]
	// a : cnts[1]
	// s : cnts[2]
	// d : cnts[3]
	// cnts只有4长度
	public static boolean ok(int[] cnts, int l, int r) {
		// 窗口外最大词频是多少
		int maxCnt = Math.max(Math.max(cnts[0], cnts[1]), Math.max(cnts[2], cnts[3]));
		// 需要多少空间，拉平？changes
		// maxCnt - cnts[0] + maxCnt - cnts[1] maxCnt - cnts[2] maxCnt - cnts[3]   
		int changes = maxCnt * 4 - cnts[0] - cnts[1] - cnts[2] - cnts[3];
		int rest = r - l - changes;
		return rest >= 0 && rest % 4 == 0;
	}

}

package class_2022_06_1_week;

// 测试链接 : https://leetcode.com/problems/unique-substrings-in-wraparound-string/
public class Code04_UniqueSubstringsInWraparoundString {

	public int findSubstringInWraproundString(String s) {
		char[] str = s.toCharArray();
		int n = str.length;
		int ans = 0;
		int len = 1;
		int[] max = new int[256];
		max[str[0]]++;
		for (int i = 1; i < n; i++) {
			char cur = str[i];
			char pre = str[i - 1];
			if ((pre == 'z' && cur == 'a') || pre + 1 == cur) {
				len++;
			} else {
				len = 1;
			}
			max[cur] = Math.max(max[cur], len);
		}
		for (int i = 0; i < 256; i++) {
			ans += max[i];
		}
		return ans;
	}

}

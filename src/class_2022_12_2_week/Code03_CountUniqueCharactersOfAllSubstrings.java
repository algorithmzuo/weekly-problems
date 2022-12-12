package class_2022_12_2_week;

import java.util.ArrayList;
import java.util.HashMap;

// 我们定义了一个函数 countUniqueChars(s) 来统计字符串 s 中的唯一字符，
// 并返回唯一字符的个数。
// 例如：s = "LEETCODE" ，则其中 "L", "T","C","O","D" 都是唯一字符，
// 因为它们只出现一次，所以 countUniqueChars(s) = 5 。
// 本题将会给你一个字符串 s ，我们需要返回 countUniqueChars(t) 的总和，
// 其中 t 是 s 的子字符串。输入用例保证返回值为 32 位整数。
// 注意，某些子字符串可能是重复的，但你统计时也必须算上这些重复的子字符串
//（也就是说，你必须统计 s 的所有子字符串中的唯一字符）。
// 测试链接 : https://leetcode.cn/problems/count-unique-characters-of-all-substrings-of-a-given-string/
public class Code03_CountUniqueCharactersOfAllSubstrings {

	public static int uniqueLetterString(String s) {
		HashMap<Character, ArrayList<Integer>> indies = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!indies.containsKey(c)) {
				indies.put(c, new ArrayList<>());
				indies.get(c).add(-1);
			}
			indies.get(c).add(i);
		}
		int res = 0;
		for (HashMap.Entry<Character, ArrayList<Integer>> entry : indies.entrySet()) {
			ArrayList<Integer> arr = entry.getValue();
			arr.add(s.length());
			for (int i = 1; i < arr.size() - 1; i++) {
				res += (arr.get(i) - arr.get(i - 1)) * (arr.get(i + 1) - arr.get(i));
			}
		}
		return res;
	}

}

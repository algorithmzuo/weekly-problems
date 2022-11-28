package class_2022_12_2_week;

import java.util.ArrayList;
import java.util.HashMap;

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

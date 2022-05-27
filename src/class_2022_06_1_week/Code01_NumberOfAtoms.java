package class_2022_06_1_week;

import java.util.TreeMap;

// 给你一个字符串化学式 formula ，返回 每种原子的数量 。
// 原子总是以一个大写字母开始，接着跟随 0 个或任意个小写字母，表示原子的名字。
// 如果数量大于 1，原子后会跟着数字表示原子的数量。如果数量等于 1 则不会跟数字。
// 例如，"H2O" 和 "H2O2" 是可行的，但 "H1O2" 这个表达是不可行的。
// 两个化学式连在一起可以构成新的化学式。
// 例如 "H2O2He3Mg4" 也是化学式。
// 由括号括起的化学式并佐以数字（可选择性添加）也是化学式。
// 例如 "(H2O2)" 和 "(H2O2)3" 是化学式。
// 返回所有原子的数量，格式为：第一个（按字典序）原子的名字，跟着它的数量（如果数量大于 1），
// 然后是第二个原子的名字（按字典序），跟着它的数量（如果数量大于 1），以此类推。
// 示例 1：
// 输入：formula = "H2O"
// 输出："H2O"
// 解释：原子的数量是 {'H': 2, 'O': 1}。
// 示例 2：
// 输入：formula = "Mg(OH)2"
// 输出："H2MgO2"
// 解释：原子的数量是 {'H': 2, 'Mg': 1, 'O': 2}。
// 示例 3：
// 输入：formula = "K4(ON(SO3)2)2"
// 输出："K4N2O14S4"
// 解释：原子的数量是 {'K': 4, 'N': 2, 'O': 14, 'S': 4}。
// 测试链接 : https://leetcode.com/problems/number-of-atoms/
public class Code01_NumberOfAtoms {

	public static String countOfAtoms(String str) {
		char[] s = str.toCharArray();
		Info info = process(s, 0);
		StringBuilder builder = new StringBuilder();
		for (String key : info.cntMap.keySet()) {
			builder.append(key);
			int cnt = info.cntMap.get(key);
			if (cnt > 1) {
				builder.append(cnt);
			}
		}
		return builder.toString();
	}

	public static class Info {
		public TreeMap<String, Integer> cntMap;
		public int end;

		public Info(TreeMap<String, Integer> c, int e) {
			cntMap = c;
			end = e;
		}
	}

	public static Info process(char[] s, int i) {
		TreeMap<String, Integer> cntMap = new TreeMap<>();
		int cnt = 0;
		StringBuilder builder = new StringBuilder();
		Info info = null;
		while (i < s.length && s[i] != ')') {
			if (s[i] >= 'A' && s[i] <= 'Z' || s[i] == '(') {
				if (builder.length() != 0 || info != null) {
					cnt = cnt == 0 ? 1 : cnt;
					if (builder.length() != 0) {
						String key = builder.toString();
						cntMap.put(key, cntMap.getOrDefault(key, 0) + cnt);
						builder.delete(0, builder.length());
					} else {
						for (String key : info.cntMap.keySet()) {
							cntMap.put(key, cntMap.getOrDefault(key, 0) + info.cntMap.get(key) * cnt);
						}
						info = null;
					}
					cnt = 0;
				}
				if (s[i] == '(') {
					info = process(s, i + 1);
					i = info.end + 1;
				} else {
					builder.append(s[i++]);
				}
			} else if (s[i] >= 'a' && s[i] <= 'z') {
				builder.append(s[i++]);
			} else {
				cnt = cnt * 10 + s[i++] - '0';
			}
		}
		if (builder.length() != 0 || info != null) {
			cnt = cnt == 0 ? 1 : cnt;
			if (builder.length() != 0) {
				String key = builder.toString();
				cntMap.put(key, cntMap.getOrDefault(key, 0) + cnt);
				builder.delete(0, builder.length());
			} else {
				for (String key : info.cntMap.keySet()) {
					cntMap.put(key, cntMap.getOrDefault(key, 0) + info.cntMap.get(key) * cnt);
				}
				info = null;
			}
			cnt = 0;
		}
		return new Info(cntMap, i);
	}

}

package class_2023_03_2_week;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

// 测试链接 : https://leetcode.cn/problems/brace-expansion-ii/
public class Code05_BraceExpansionII {

	public static List<String> braceExpansionII(String expression) {
		return new ArrayList<>(process(expression.toCharArray(), 0).ans);
	}

	public static class Info {
		public TreeSet<String> ans;
		public int end;

		public Info(TreeSet<String> a, int e) {
			ans = a;
			end = e;
		}
	}

	public static Info process(char[] exp, int start) {
		TreeSet<String> ans = new TreeSet<>();
		List<TreeSet<String>> parts = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		while (start != exp.length && exp[start] != '}') {
			if (exp[start] == '{') {
				addStringToParts(builder, parts);
				Info next = process(exp, start + 1);
				parts.add(next.ans);
				start = next.end + 1;
			} else if (exp[start] == ',') {
				addStringToParts(builder, parts);
				addPartsToSet(ans, parts);
				start++;
				parts.clear();
			} else {
				builder.append(exp[start]);
				start++;
			}
		}
		addStringToParts(builder, parts);
		addPartsToSet(ans, parts);
		return new Info(ans, start);
	}

	public static void addStringToParts(StringBuilder builder, List<TreeSet<String>> parts) {
		if (builder.length() != 0) {
			parts.add(new TreeSet<>());
			parts.get(parts.size() - 1).add(builder.toString());
			builder.delete(0, builder.length());
		}
	}

	public static void addPartsToSet(TreeSet<String> ans, List<TreeSet<String>> parts) {
		process(parts, 0, "", ans);
	}

	public static void process(List<TreeSet<String>> list, int i, String path, TreeSet<String> ans) {
		if (i == list.size()) {
			if (!path.equals("")) {
				ans.add(path);
			}
		} else {
			for (String cur : list.get(i)) {
				process(list, i + 1, path + cur, ans);
			}
		}
	}

}

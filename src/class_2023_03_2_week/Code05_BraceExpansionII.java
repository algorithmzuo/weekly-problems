package class_2023_03_2_week;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

// 如果你熟悉 Shell 编程，那么一定了解过花括号展开，它可以用来生成任意字符串。
// 花括号展开的表达式可以看作一个由 花括号、逗号 和 小写英文字母 组成的字符串
// 定义下面几条语法规则：
// 如果只给出单一的元素 x，那么表达式表示的字符串就只有 "x"。R(x) = {x}
// 例如，表达式 "a" 表示字符串 "a"。
// 而表达式 "w" 就表示字符串 "w"。
// 当两个或多个表达式并列，以逗号分隔，我们取这些表达式中元素的并集
// R({e_1,e_2,...}) = R(e_1) ∪ R(e_2) ∪ ...
// 例如，表达式 "{a,b,c}" 表示字符串 "a","b","c"。
// 而表达式 "{{a,b},{b,c}}" 也可以表示字符串 "a","b","c"。
// 要是两个或多个表达式相接，中间没有隔开时，
// 我们从这些表达式中各取一个元素依次连接形成字符串
// R(e_1 + e_2) = {a + b for (a, b) in R(e_1) × R(e_2)}
// 例如，表达式 "{a,b}{c,d}" 表示字符串 "ac","ad","bc","bd"。
// 表达式之间允许嵌套，单一元素与表达式的连接也是允许的。
// 例如，表达式 "a{b,c,d}" 表示字符串 "ab","ac","ad"​​​​​​。
// 例如，表达式 "a{b,c}{d,e}f{g,h}" 
// 可以表示字符串 : 
// "abdfg", "abdfh", "abefg", "abefh",
// "acdfg", "acdfh", "acefg", "acefh"。
// 给出表示基于给定语法规则的表达式 expression
// 返回它所表示的所有字符串组成的有序列表。
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

	// exp[start..........]  遇到我的} 或者exp终止位置，停！
	public static Info process(char[] exp, int start) {
		// 最终的结果，返回
		TreeSet<String> ans = new TreeSet<>();
		// 集合A X 集合B X 集合C  ,  遇到, parts清空
		// ................        遇到, parts清空
		// 每一回的结果，加入到ans
		List<TreeSet<String>> parts = new ArrayList<>();
		// 收集字符
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
			} else { // 遇到英文字母
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

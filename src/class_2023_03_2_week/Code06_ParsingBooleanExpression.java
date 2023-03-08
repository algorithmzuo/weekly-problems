package class_2023_03_2_week;

// 测试链接 : https://leetcode.cn/problems/parsing-a-boolean-expression/
public class Code06_ParsingBooleanExpression {

	public static boolean parseBoolExpr(String expression) {
		return process(expression.toCharArray(), 0).ans;
	}

	public static class Info {
		public boolean ans;
		public int end;

		public Info(boolean a, int e) {
			ans = a;
			end = e;
		}
	}

	public static Info process(char[] exp, int index) {
		char judge = exp[index];
		if (judge == 'f') {
			return new Info(false, index);
		} else if (judge == 't') {
			return new Info(true, index);
		} else {
			boolean ans;
			index += 2;
			if (judge == '!') {
				Info next = process(exp, index);
				ans = !next.ans;
				index = next.end + 1;
			} else if (judge == '&') {
				ans = true;
				while (index < exp.length && exp[index] != ')') {
					if (exp[index] == ',') {
						index++;
					} else {
						Info next = process(exp, index);
						if (!next.ans) {
							ans = false;
						}
						index = next.end + 1;
					}
				}
			} else {
				ans = false;
				while (index < exp.length && exp[index] != ')') {
					if (exp[index] == ',') {
						index++;
					} else {
						Info next = process(exp, index);
						if (next.ans) {
							ans = true;
						}
						index = next.end + 1;
					}
				}
			}
			return new Info(ans, index);
		}
	}

}

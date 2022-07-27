package class_2022_08_2_week;

import java.util.Stack;

// 给定一个逆波兰式
// 转化成正确的中序表达式
// 要求只有必要加括号的地方才加括号
public class Code02_ReversePolishNotation {

	// 请保证给定的逆波兰式是正确的！
	public static int getAns(String rpn) {
		if (rpn == null || rpn.equals("")) {
			return 0;
		}
		String[] parts = rpn.split(" ");
		Stack<Integer> stack = new Stack<>();
		for (String part : parts) {
			if (part.equals("+") || part.equals("-") || part.equals("*") || part.equals("/")) {
				int right = stack.pop();
				int left = stack.pop();
				int ans = 0;
				if (part.equals("+")) {
					ans = left + right;
				} else if (part.equals("-")) {
					ans = left - right;
				} else if (part.equals("*")) {
					ans = left * right;
				} else {
					ans = left / right;
				}
				stack.push(ans);
			} else {
				stack.push(Integer.valueOf(part));
			}
		}
		// stack 只有一个数，最终的结果
		return stack.pop();
	}

	enum Operation {
		SingleNumber, AddOrMinus, MultiplyOrDivide;
	}

	// 请保证输入的逆波兰式是正确的
	// 否则该函数不保证正确性
	// 逆波兰式仅支持+、-、*、/
	// 想支持更多算术运算符自己改
	public static String convert(String rpn) {
		if (rpn == null || rpn.equals("")) {
			return rpn;
		}
		String[] parts = rpn.split(" ");
		Stack<String> stack1 = new Stack<>();
		Stack<Operation> stack2 = new Stack<>();
		for (String cur : parts) {
			if (cur.equals("+") || cur.equals("-")) {
				String b = stack1.pop();
				String a = stack1.pop();
				stack2.pop();
				stack2.pop();
				stack1.push(a + cur + b);
				stack2.push(Operation.AddOrMinus);
			} else if (cur.equals("*") || cur.equals("/")) {
				String b = stack1.pop();
				String a = stack1.pop();
				Operation bOp = stack2.pop();
				Operation aOp = stack2.pop();
				String left = aOp == Operation.AddOrMinus ? ("(" + a + ")") : (a);
				String right = bOp == Operation.AddOrMinus ? ("(" + b + ")") : (b);
				stack1.push(left + cur + right);
				stack2.push(Operation.MultiplyOrDivide);
			} else {
				stack1.push(cur);
				stack2.push(Operation.SingleNumber);
			}
		}
		return stack1.pop();
	}

	public static void main(String[] args) {
		// 3*(-5+13)+6/(2-3+2)-4*5*3
		String rpn = "3 -5 13 + * 6 2 3 - 2 + / + 4 5 3 * * -";
		System.out.println(getAns(rpn));
		System.out.println(convert(rpn));
	}

}

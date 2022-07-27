package class_2022_08_2_week;

import java.util.Stack;

// 给定一个逆波兰式
// 转化成正确的中序表达式
// 要求只有必要加括号的地方才加括号
public class Code02_ReversePolishNotation {

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
		String ans = convert(rpn);
		System.out.println(ans);
	}

}

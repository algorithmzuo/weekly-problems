package class_2022_08_2_week;

import java.util.Stack;

// 栈只提供push、pop、isEmpty三个方法
// 请完成无序栈的排序，要求排完序之后，从栈顶到栈底从小到大
// 只能使用栈提供的push、pop、isEmpty三个方法、以及递归函数
// 除此之外不能使用任何的容器，任何容器都不许，连数组也不行
// 也不能自己定义任何结构体
// 就是只用：
// 1) 栈提供的push、pop、isEmpty三个方法
// 2) 简单返回值的递归函数
public class Code01_SortStackUsingRecursive {

	public static void sort(Stack<Integer> stack) {
		int deep = size(stack);
		while (deep > 0) {
			int max = findMax(stack, deep);
			int k = findMaxTimes(stack, deep, max);
			maxDown(stack, deep, max, k);
			deep -= k;
		}
	}

	// 求栈的大小
	// 但是不改变栈的任何数据状况
	public static int size(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return 0;
		}
		int hold = stack.pop();
		int size = size(stack) + 1;
		stack.push(hold);
		return size;
	}

	// 从stack顶部出发，只往下找deep层
	// 返回最大值
	// 完全不改变stack的任何数据状况
	public static int findMax(Stack<Integer> stack, int deep) {
		if (deep == 0) {
			return Integer.MIN_VALUE;
		}
		int num = stack.pop();
		int restMax = findMax(stack, deep - 1);
		int ans = Math.max(num, restMax);
		stack.push(num);
		return ans;
	}

	// 已知从stack顶部出发，只往下找deep层，最大值是max
	// 返回这个最大值出现了几次，只找到deep层！再往下不找了！
	// 完全不改变stack的任何数据状况
	public static int findMaxTimes(Stack<Integer> stack, int deep, int max) {
		if (deep == 0) {
			return 0;
		}
		int num = stack.pop();
		int times = findMaxTimes(stack, deep - 1, max);
		times += num == max ? 1 : 0;
		stack.push(num);
		return times;
	}

	// 已知从stack顶部出发，只往下找deep层，最大值是max
	// 并且这个max出现了k次
	// 请把这k个max沉底，不是沉到stack整体的底部，而是到deep层
	// stack改变数据状况，但是只在从顶部到deep层的范围上改变
	public static void maxDown(Stack<Integer> stack, int deep, int max, int k) {
		if (deep == 0) {
			for (int i = 0; i < k; i++) {
				stack.push(max);
			}
		} else {
			int cur = stack.pop();
			maxDown(stack, deep - 1, max, k);
			if (cur < max) {
				stack.push(cur);
			}
		}
	}

	// 为了测试
	// 生成随机栈
	public static Stack<Integer> generateRandomStack(int n, int v) {
		Stack<Integer> ans = new Stack<Integer>();
		for (int i = 0; i < n; i++) {
			ans.add((int) (Math.random() * v));
		}
		return ans;
	}

	// 为了测试
	// 检测栈是不是有序的
	public static boolean isSorted(Stack<Integer> stack) {
		int step = Integer.MIN_VALUE;
		while (!stack.isEmpty()) {
			if (step > stack.peek()) {
				return false;
			}
			step = stack.pop();
		}
		return true;
	}

	// 为了测试
	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.add(1);
		test.add(5);
		test.add(4);
		test.add(5);
		test.add(3);
		test.add(2);
		test.add(3);
		test.add(1);
		test.add(4);
		test.add(2);
		// 1 5 4 5 3 2 3 1 4 2
		sort(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

		int N = 20;
		int V = 20;
		int testTimes = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N);
			Stack<Integer> stack = generateRandomStack(n, V);
			sort(stack);
			if (!isSorted(stack)) {
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

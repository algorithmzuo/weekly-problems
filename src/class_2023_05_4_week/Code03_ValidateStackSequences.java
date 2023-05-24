package class_2023_05_4_week;

// 来自美团
// 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，
// 只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，
// 返回 true；否则，返回 false 。
// 测试链接 : https://leetcode.cn/problems/validate-stack-sequences/
public class Code03_ValidateStackSequences {

	public boolean validateStackSequences(int[] pushed, int[] popped) {
		int n = pushed.length;
		int size = 0;
		for (int i = 0, j = 0; i < n; i++) {
			// i : 入栈数组，哪个位置的数要进栈
			// j : 出栈数组，对比的位置
			pushed[size++] = pushed[i];
			while (size > 0 && j < n && pushed[size - 1] == popped[j]) {
				size--;
				j++;
			}
		}
		return size == 0;
	}

}

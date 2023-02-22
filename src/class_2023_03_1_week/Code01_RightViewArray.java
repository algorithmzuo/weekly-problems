package class_2023_03_1_week;

// 来自学员最近的笔试
// 请根据二叉树的前序遍历，中序遍历恢复二叉树，并打印出二叉树的右视图
// 树的节点个数在10000以内
// 节点值在10000以内且不重复
// 测试链接 : https://www.nowcoder.com/practice/c9480213597e45f4807880c763ddd5f0
// 把如下的类改名为"Solution"，整个提交可以直接通过
public class Code01_RightViewArray {

	public static int MAXN = 10001;

	// 每一个数字在中序遍历中的位置
	public static int[] find = new int[MAXN];

	// 每一层最右的节点值
	public static int[] right = new int[MAXN];

	public static int[] solve(int[] pre, int[] in) {
		int n = pre.length;
		// 每一个数字在中序遍历中的位置，记录好
		for (int i = 0; i < n; i++) {
			find[in[i]] = i;
			right[i] = 0;
		}
		// 返回树的最大深度
		int deep = f(pre, in, 0, n - 1, 0, n - 1, 0);
		int[] ans = new int[deep];
		for (int i = 0; i < deep; i++) {
			// 依次拷贝每一层最右的节点值
			ans[i] = right[i];
		}
		return ans;
	}

	// 当前想象中的树，
	// 先序是pre[prel...prer]
	// 中序是in[inl...inr]
	// 头节点在第level层
	// 按照先中、再右、再左的顺序，遍历想象中的树(不真的建出来，只是遍历它)
	// 沿途收集每一层最右节点的值
	// 并返回树的最大深度
	public static int f(int[] pre, int[] in, int prel, int prer, int inl, int inr, int level) {
		if (prel > prer) { // 已经遇到null节点了，返回level
			return level;
		}
		// 当前头的值
		int headValue = pre[prel];
		// 头在中序数组中的位置
		int headIn = find[headValue];
		// 根据头在中序数组中的位置，分出左侧大小
		int leftSize = headIn - inl;
		// 根据头在中序数组中的位置，分出右侧大小
		int rightSize = inr - headIn;
		// 当前的头如果是leve层上第一个遇到的，收集
		if (right[level] == 0) {
			right[level] = headValue;
		}
		// 先遍历右树，一定要先遍历右树！
		int rightDeep = f(pre, in, prer - rightSize + 1, prer, headIn + 1, inr, level + 1);
		// 再遍历左树
		int leftDeep = f(pre, in, prel + 1, prel + leftSize, inl, headIn - 1, level + 1);
		// 返回最大深度
		return Math.max(rightDeep, leftDeep);
	}

}

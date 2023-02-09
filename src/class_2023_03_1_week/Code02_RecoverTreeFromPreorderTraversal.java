package class_2023_03_1_week;

// 测试链接 : https://leetcode.cn/problems/recover-a-tree-from-preorder-traversal/
public class Code02_RecoverTreeFromPreorderTraversal {

	// 不提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	// 提交如下的代码
	public static int MAXN = 2001;

	public static int[] queue = new int[MAXN];

	public static int l, r;

	public static TreeNode recoverFromPreorder(String traversal) {
		l = 0;
		r = 0;
		int number = 0;
		int level = 0;
		boolean pickLevel = true;
		for (int i = 0; i < traversal.length(); i++) {
			if (traversal.charAt(i) != '-') {
				if (pickLevel) {
					queue[r++] = level;
					level = 0;
					pickLevel = false;
				}
				number = number * 10 + traversal.charAt(i) - '0';
			} else {
				if (!pickLevel) {
					queue[r++] = number;
					number = 0;
					pickLevel = true;
				}
				level++;
			}
		}
		queue[r++] = number;
		return f();
	}

	public static TreeNode f() {
		int level = queue[l++];
		TreeNode head = new TreeNode(queue[l++]);
		if (l < r && queue[l] > level) {
			head.left = f();
		}
		if (l < r && queue[l] > level) {
			head.right = f();
		}
		return head;
	}

}

package class_2021_12_2_week;

import java.util.HashSet;

// 测试链接 : https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iv/
public class Code04_LowestCommonAncestorOfABinaryTreeIV {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
		HashSet<Integer> set = new HashSet<>();
		for (TreeNode node : nodes) {
			set.add(node.val);
		}
		return process(root, set, set.size()).find;
	}

	public static class Info {
		// 找没找到最低公共祖先
		// 没找到，find = null
		// 找到了最低公共祖先，find是最低公共祖先
		public TreeNode find;
		// 我这颗子树上，删掉了几个节点！
		public int removes;

		public Info(TreeNode f, int r) {
			find = f;
			removes = r;
		}
	}

	public static Info process(TreeNode x, HashSet<Integer> set, int all) {
		if (x == null) {
			return new Info(null, 0);
		}
		Info left = process(x.left, set, all);
		if (left.find != null) {
			return left;
		}
		Info right = process(x.right, set, all);
		if (right.find != null) {
			return right;
		}
		int cur = set.contains(x.val) ? 1 : 0;
		set.remove(x.val);
		if (left.removes + right.removes + cur == all) {
			return new Info(x, all);
		} else {
			return new Info(null, left.removes + right.removes + cur);
		}
	}

}

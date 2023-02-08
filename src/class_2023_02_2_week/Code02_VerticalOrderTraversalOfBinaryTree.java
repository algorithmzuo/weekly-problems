package class_2023_02_2_week;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// 给你二叉树的根结点 root ，请你设计算法计算二叉树的 垂序遍历 序列。
// 对位于 (row, col) 的每个结点而言，
// 其左右子结点分别位于 (row + 1, col - 1) 和 (row + 1, col + 1)
// 树的根结点位于 (0, 0) 。
// 二叉树的 垂序遍历 从最左边的列开始直到最右边的列结束，按列索引每一列上的所有结点，
// 形成一个按出现位置从上到下排序的有序列表。如果同行同列上有多个结点，
// 则按结点的值从小到大进行排序。
// 返回二叉树的 垂序遍历 序列。
// 测试链接 : https://leetcode.cn/problems/vertical-order-traversal-of-a-binary-tree/
public class Code02_VerticalOrderTraversalOfBinaryTree {

	// 不提交这个类
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	// 提交以下所有的code
	public static class Info {
		public int row;
		public int col;
		public int val;

		public Info(int r, int c, int v) {
			row = r;
			col = c;
			val = v;
		}
	}

	public static class InfoComparator implements Comparator<Info> {

		@Override
		public int compare(Info o1, Info o2) {
			if (o1.col != o2.col) {
				return o1.col - o2.col;
			}
			if (o1.row != o2.row) {
				return o1.row - o2.row;
			}
			return o1.val - o2.val;
		}

	}

	public static List<List<Integer>> verticalTraversal(TreeNode root) {
		ArrayList<Info> collects = new ArrayList<>();
		Info rootInfo = new Info(0, 0, root.val);
		collects.add(rootInfo);
		dfs(root, rootInfo, collects);
		List<List<Integer>> ans = new ArrayList<>();
		collects.sort(new InfoComparator());
		for (int i = 0; i < collects.size(); i++) {
			if (i == 0 || collects.get(i - 1).col != collects.get(i).col) {
				ans.add(new ArrayList<>());
			}
			ans.get(ans.size() - 1).add(collects.get(i).val);
		}
		return ans;
	}

	public static void dfs(TreeNode root, Info rootInfo, ArrayList<Info> collects) {
		if (root.left != null) {
			Info leftInfo = new Info(rootInfo.row + 1, rootInfo.col - 1, root.left.val);
			collects.add(leftInfo);
			dfs(root.left, leftInfo, collects);
		}
		if (root.right != null) {
			Info rightInfo = new Info(rootInfo.row + 1, rootInfo.col + 1, root.right.val);
			collects.add(rightInfo);
			dfs(root.right, rightInfo, collects);
		}
	}

}

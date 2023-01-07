package class_2023_02_2_week;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// 测试链接 : https://leetcode.cn/problems/vertical-order-traversal-of-a-binary-tree/
public class Code01_VerticalOrderTraversalOfBinaryTree {

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

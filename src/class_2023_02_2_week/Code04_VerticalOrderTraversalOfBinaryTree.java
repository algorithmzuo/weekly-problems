package class_2023_02_2_week;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// 测试链接 : https://leetcode.cn/problems/vertical-order-traversal-of-a-binary-tree/
public class Code04_VerticalOrderTraversalOfBinaryTree {

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
			if (o1.row != o2.row) {
				return o1.row - o2.row;
			}
			if (o1.col != o2.col) {
				return o1.col - o2.col;
			}
			return o1.val - o2.val;
		}

	}

	public static List<List<Integer>> verticalTraversal(TreeNode root) {
		PriorityQueue<Info> heap = new PriorityQueue<>(new InfoComparator());
		Info rootInfo = new Info(0, 0, root.val);
		heap.add(rootInfo);
		dfs(root, rootInfo, heap);
		List<List<Integer>> ans = new ArrayList<>();
		while (!heap.isEmpty()) {
			List<Integer> curLevel = new ArrayList<>();
			Info curFirst = heap.peek();
			while (!heap.isEmpty() && heap.peek().row == curFirst.row) {
				curLevel.add(heap.poll().val);
			}
			ans.add(curLevel);
		}
		return ans;
	}

	public static void dfs(TreeNode root, Info rootInfo, PriorityQueue<Info> heap) {
		if (root.left != null) {
			Info leftInfo = new Info(rootInfo.row - 1, rootInfo.col + 1, root.left.val);
			heap.add(leftInfo);
			dfs(root.left, leftInfo, heap);
		}
		if (root.right != null) {
			Info rightInfo = new Info(rootInfo.row + 1, rootInfo.col + 1, root.right.val);
			heap.add(rightInfo);
			dfs(root.right, rightInfo, heap);
		}
	}

}

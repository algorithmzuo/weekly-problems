package class_2022_01_1_week;

import java.util.LinkedList;

// 测试链接 : https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/
public class Code03_StepByStepDirectionsFromABinaryTreeNodeToAnother {

	// 提交代码时不要提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	// 提交下面的方法
	public static String getDirections(TreeNode root, int startValue, int destValue) {
		Info all = process(root, startValue, destValue);
		StringBuilder builder = new StringBuilder();
		for (char c : all.path) {
			builder.append(c);
		}
		return builder.toString();
	}

	public static class Info {
		public boolean findStart;
		public boolean findDest;
		public LinkedList<Character> path;

		public Info(boolean s, boolean d, LinkedList<Character> p) {
			findStart = s;
			findDest = d;
			path = p;
		}
	}

	public static Info process(TreeNode h, int start, int dest) {
		if (h == null) {
			return new Info(false, false, new LinkedList<>());
		}
		Info infoL = process(h.left, start, dest);
		if (infoL.findStart && infoL.findDest) {
			return infoL;
		}
		Info infoR = process(h.right, start, dest);
		if (infoR.findStart && infoR.findDest) {
			return infoR;
		}
		boolean findStart = h.val == start || infoL.findStart || infoR.findStart;
		boolean findDest = h.val == dest || infoL.findDest || infoR.findDest;
		LinkedList<Character> path = new LinkedList<>();
		if (infoL.findStart || infoR.findStart) {
			path = infoL.findStart ? infoL.path : infoR.path;
			path.addLast('U');
		}
		if (infoL.findDest || infoR.findDest) {
			LinkedList<Character> toDest = infoL.findDest ? infoL.path : infoR.path;
			toDest.addFirst(infoL.findDest ? 'L' : 'R');
			if (!findStart) {
				path = toDest;
			} else {
				path.addAll(toDest);
			}
		}
		return new Info(findStart, findDest, path);
	}

}

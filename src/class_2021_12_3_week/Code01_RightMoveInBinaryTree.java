package class_2021_12_3_week;

// 测试链接 : https://www.nowcoder.com/test/33701596/summary
// 本题目为第1题
// 牛客网判题过程不好，卡常数了
// 课上会说什么是"卡常数"
// 卡常数怎么反馈？
public class Code01_RightMoveInBinaryTree {

	// 这个类不需要提交
	public static class TreeNode {
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	// 提交下面的代码

	public static TreeNode[] queue = new TreeNode[500000];

	public static int[] ends = new int[50];

	public static TreeNode cyclicShiftTree(TreeNode root, int k) {
		int l = 0;
		int r = 0;
		queue[r++] = root;
		int level = 0;
		while (l != r) {
			ends[level] = r;
			while (l < ends[level]) {
				TreeNode cur = queue[l++];
				if (cur != null) {
					queue[r++] = cur.left;
					queue[r++] = cur.right;
				}
			}
			level++;
		}
		for (int i = level - 1; i > 0; i--) {
			int downLeft = ends[i - 1];
			int downRight = ends[i] - 1;
			rightMove(queue, downLeft, downRight, k);
			int curLeft = i - 2 >= 0 ? ends[i - 2] : 0;
			int curRight = ends[i - 1] - 1;
			for (int j = curLeft; j <= curRight; j++) {
				if (queue[j] != null) {
					queue[j].left = queue[downLeft++];
					queue[j].right = queue[downLeft++];
				}
			}
		}
		return root;
	}

	public static void rightMove(TreeNode[] arr, int l, int r, int k) {
		int e = r + 1 - (k % (r - l + 1));
		if (e <= r) {
			for (int i = l, j = e - 1; i < j; i++, j--) {
				swap(arr, i, j);
			}
			for (int i = e, j = r; i < j; i++, j--) {
				swap(arr, i, j);
			}
			for (int i = l, j = r; i < j; i++, j--) {
				swap(arr, i, j);
			}
		}
	}

	public static void swap(TreeNode[] arr, int i, int j) {
		TreeNode tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}

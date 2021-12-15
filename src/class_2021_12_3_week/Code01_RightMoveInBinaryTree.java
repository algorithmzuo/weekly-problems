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

	public static TreeNode[] queue = new TreeNode[300000];

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
			
			// 当前层 : curLeft....curRight
			//            3(null) 4(a) 5(null) 6(b)
			// 下一层 ：downLeft....downRight
			//              7 8 9 10
			// downIndex : 下一层需要根据，k和下一层的长度，来右移。右移之后，从哪个位置开始，分配节点给当前层第一个不空的节点
			int downLeft = ends[i - 1];
			int downRight = ends[i] - 1;
			int downRightSize = k % (downRight - downLeft + 1);
			int downIndex = downRightSize == 0 ? downLeft : (downRight - downRightSize + 1);
			int curLeft = i - 2 >= 0 ? ends[i - 2] : 0;
			int curRight = ends[i - 1] - 1;
			for (int j = curLeft; j <= curRight; j++) {
				if (queue[j] != null) {
					queue[j].left = queue[downIndex];
					downIndex = nextIndex(downIndex, downLeft, downRight);
					queue[j].right = queue[downIndex];
					downIndex = nextIndex(downIndex, downLeft, downRight);
				}
			}
		}
		return root;
	}

	// l......r    i -> next index
	// 4.....9   i = 7 8 9 4
	public static int nextIndex(int i, int l, int r) {
		return i == r ? l : i + 1;
	}

}

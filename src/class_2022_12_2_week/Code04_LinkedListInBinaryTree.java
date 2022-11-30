package class_2022_12_2_week;

// 最优解是KMP算法来解
// 官方题解都没有写的最优解
// 如果二叉树节点数是N，链表节点数M，时间复杂度为O(M+N)
// 测试链接 : https://leetcode.cn/problems/linked-list-in-binary-tree/
public class Code04_LinkedListInBinaryTree {

	// 不提交这个类
	public class ListNode {
		int val;
		ListNode next;
	}

	// 不提交这个类
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	// 最优解
	// 官方题解都没有写的最优解
	// KMP算法来解
	// 如果二叉树节点数是N，链表节点数M，时间复杂度为O(M+N)
	// 提交如下的所有方法，可以直接通过
	public static boolean isSubPath(ListNode head, TreeNode root) {
		int n = 0;
		ListNode tmp = head;
		while (tmp != null) {
			n++;
			tmp = tmp.next;
		}
		int[] match = new int[n];
		n = 0;
		while (head != null) {
			match[n++] = head.val;
			head = head.next;
		}
		int[] next = getNextArray(match);
		return find(root, 0, match, next);
	}

	public static int[] getNextArray(int[] match) {
		if (match.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[match.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int cn = 0;
		while (i < next.length) {
			if (match[i - 1] == match[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

	public static boolean find(TreeNode cur, int mi, int[] match, int[] next) {
		if (mi == match.length) {
			return true;
		}
		if (cur == null) {
			return false;
		}
		while (mi >= 0 && cur.val != match[mi]) {
			mi = next[mi];
		}
		return find(cur.left, mi + 1, match, next) || find(cur.right, mi + 1, match, next);
	}

}

package class_2022_12_2_week;

// 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表
// 如果在二叉树中，存在一条一直向下的路径
// 且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True
// 否则返回 False 。
// 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
// 测试链接 : https://leetcode.cn/problems/linked-list-in-binary-tree/
// 最优解是KMP算法来解
// 官方题解都没有写的最优解
// 如果二叉树节点数是N，链表节点数M，时间复杂度为O(M+N)
public class Code05_LinkedListInBinaryTree {

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

	// 当前目标串 -> TreeNode cur 这个字符
	// 一个叫match串，match，mi位置的字符
	// 返回cur后续的路能不能把match串配完！
	public static boolean find(TreeNode cur, int mi, int[] match, int[] next) {
		if (mi == match.length) {
			return true;
		}
		if (cur == null) {
			return false;
		}
		// 当前目标串的字符 :  cur.val
		// 当前match串的字符 : match[mi]
		while (mi >= 0 && cur.val != match[mi]) {
			mi = next[mi];
		}
		// 后续的字符，先走左，配去！
		// 后续的字符，分裂可能性了！走右，配去！
		return find(cur.left, mi + 1, match, next) || find(cur.right, mi + 1, match, next);
	}

}

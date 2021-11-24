package class_2021_11_4_week;

import java.util.ArrayList;
import java.util.List;

public class Code01_RetainTree {

	public static class Node {
		// 值
		public int value;
		// 是否保留
		public boolean retain;
		// 下级节点
		public List<Node> nexts;

		public Node(int v, boolean r) {
			value = v;
			retain = r;
			nexts = new ArrayList<>();
		}
	}

	// 给定一棵树的头节点head
	// 请按照题意，保留节点，没有保留的节点删掉
	// 树调整完之后，返回头节点
	public static Node retain(Node x) {
		if (x.nexts.isEmpty()) {
			return x.retain ? x : null;
		}
		// x下层有节点
		List<Node> newNexts = new ArrayList<>();
		for (Node next : x.nexts) {
			Node newNext = retain(next);
			if (newNext != null) {
				newNexts.add(newNext);
			}
		}
		// x.nexts  老的链表，下级节点
		// newNexts 新的链表，只有保留的在里面
		// 
		if (!newNexts.isEmpty() || x.retain) {
			x.nexts = newNexts;
			return x;
		}
		return null;
	}

	// 先序打印
	public static void preOrderPrint(Node head) {
		System.out.println(head.value);
		for (Node next : head.nexts) {
			preOrderPrint(next);
		}
	}

	public static void main(String[] args) {
		Node n1 = new Node(1, false);
		Node n2 = new Node(2, true);
		Node n3 = new Node(3, false);
		Node n4 = new Node(4, false);
		Node n5 = new Node(5, false);
		Node n6 = new Node(6, true);
		Node n7 = new Node(7, true);
		Node n8 = new Node(8, false);
		Node n9 = new Node(9, false);
		Node n10 = new Node(10, false);
		Node n11 = new Node(11, false);
		Node n12 = new Node(12, false);
		Node n13 = new Node(13, true);

		n1.nexts.add(n2);
		n1.nexts.add(n3);
		n2.nexts.add(n4);
		n2.nexts.add(n5);
		n3.nexts.add(n6);
		n3.nexts.add(n7);
		n6.nexts.add(n8);
		n6.nexts.add(n9);
		n6.nexts.add(n10);
		n7.nexts.add(n11);
		n7.nexts.add(n12);
		n9.nexts.add(n13);

		Node head = retain(n1);
		preOrderPrint(head);

	}

}

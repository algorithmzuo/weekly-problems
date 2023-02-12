package class_2023_03_1_week;

import java.util.Map.Entry;
import java.util.TreeMap;

// 来自TikTok美国笔试
// 给定一个长度为N的一维数组scores, 代表0~N-1号员工的初始得分
// scores[i] = a, 表示i号员工一开始得分是a
// 给定一个长度为M的二维数组operations,
// operations[i] = {a, b, c}
// 表示第i号操作为 : 
// 如果a==1, 表示将目前分数<b的所有员工，分数改成b，c这个值无用
// 如果a==2, 表示将编号为b的员工，分数改成c
// 所有操作从0~M-1, 依次发生
// 返回一个长度为N的一维数组ans，表示所有操作做完之后，每个员工的得分是多少
// 1 <= N <= 10的6次方
// 1 <= M <= 10的6次方
// 0 <= 分数 <= 10的9次方
public class Code04_OperateScores {

	// 暴力方法
	// 为了验证
	public static int[] operateScores1(int[] scores, int[][] operations) {
		int n = scores.length;
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = scores[i];
		}
		for (int[] op : operations) {
			if (op[0] == 1) {
				for (int i = 0; i < n; i++) {
					ans[i] = Math.max(ans[i], op[1]);
				}
			} else {
				ans[op[1]] = op[2];
			}
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O((N + M) * logN)
	public static int[] operateScores2(int[] scores, int[][] operations) {
		int n = scores.length;
		Node[] nodes = new Node[n];
		TreeMap<Integer, Bucket> scoreBucketMap = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i);
			if (!scoreBucketMap.containsKey(scores[i])) {
				scoreBucketMap.put(scores[i], new Bucket());
			}
			scoreBucketMap.get(scores[i]).add(nodes[i]);
		}
		for (int[] op : operations) {
			if (op[0] == 1) {
				Integer floorKey = scoreBucketMap.floorKey(op[1] - 1);
				if (floorKey != null && !scoreBucketMap.containsKey(op[1])) {
					scoreBucketMap.put(op[1], new Bucket());
				}
				while (floorKey != null) {
					scoreBucketMap.get(op[1]).merge(scoreBucketMap.get(floorKey));
					scoreBucketMap.remove(floorKey);
					floorKey = scoreBucketMap.floorKey(op[1] - 1);
				}
			} else {
				Node cur = nodes[op[1]];
				cur.conectLastNext();
				if (!scoreBucketMap.containsKey(op[2])) {
					scoreBucketMap.put(op[2], new Bucket());
				}
				scoreBucketMap.get(op[2]).add(cur);
			}
		}
		int[] ans = new int[n];
		for (Entry<Integer, Bucket> entry : scoreBucketMap.entrySet()) {
			int score = entry.getKey();
			Bucket bucket = entry.getValue();
			Node cur = bucket.head.next;
			while (cur != bucket.tail) {
				ans[cur.index] = score;
				cur = cur.next;
			}
		}
		return ans;
	}

	public static class Bucket {

		// 注意！
		// 头和尾都是假点！
		// 头和尾中间的节点才是真实节点
		// 头为假 ...中间节点才是有用的数据... 尾为假
		// 为什么这么实现，很关键！
		// 课上讲！
		public Node head = null;
		public Node tail = null;

		public Bucket() {
			head = new Node(-1);
			tail = new Node(-1);
			head.next = tail;
			tail.last = head;
		}

		public void add(Node node) {
			node.last = tail.last;
			node.next = tail;
			tail.last.next = node;
			tail.last = node;
		}

		public void merge(Bucket join) {
			if (join.head.next != join.tail) {
				tail.last.next = join.head.next;
				join.head.next.last = tail.last;
				join.tail.last.next = tail;
				tail.last = join.tail.last;
				join.head.next = join.tail;
				join.tail.last = join.head;
			}
		}

	}

	public static class Node {
		public int index;
		public Node last = null;
		public Node next = null;

		public Node(int i) {
			index = i;
		}

		public void conectLastNext() {
			last.next = next;
			next.last = last;
		}

	}

	// 为了测试
	public static int[] randomSocres(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static int[][] randomOperations(int n, int m, int v) {
		int[][] ans = new int[m][3];
		for (int i = 0; i < m; i++) {
			if (Math.random() < 0.5) {
				ans[i][0] = 1;
				ans[i][1] = (int) (Math.random() * v);
			} else {
				ans[i][0] = 2;
				ans[i][1] = (int) (Math.random() * n);
				ans[i][2] = (int) (Math.random() * v);
			}
		}
		return ans;
	}

	// 为了测试
	public static boolean isEqual(int[] arr1, int[] arr2) {
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 1000;
		int M = 1000;
		int V = 100000;
		int testTimes = 100;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int m = (int) (Math.random() * M) + 1;
			int[] scores = randomSocres(n, V);
			int[][] operations = randomOperations(n, m, V);
			int[] ans1 = operateScores1(scores, operations);
			int[] ans2 = operateScores2(scores, operations);
			if (!isEqual(ans1, ans2)) {
				System.out.println("出错了！");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 1000000;
		int m = 1000000;
		int v = 1000000000;
		int[] scores = randomSocres(n, v);
		int[][] operations = randomOperations(n, m, v);
		System.out.println("总人数 : " + n);
		System.out.println("操作数 : " + n);
		System.out.println("值范围 : " + v);
		long start = System.currentTimeMillis();
		operateScores2(scores, operations);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}

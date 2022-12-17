package class_2023_01_1_week;

import java.util.ArrayList;
import java.util.HashMap;

// 测试链接 : https://leetcode.cn/problems/maximum-frequency-stack/
public class Code04_MaximumFrequencyStack {

	class FreqStack {

		public static class Node {
			public int val;
			public ArrayList<Integer> whenList;
			public int size;

			public Node(int v, int w) {
				val = v;
				whenList = new ArrayList<>();
				whenList.add(w);
				size = 1;
			}

			public void addOnce(int w) {
				if (whenList.size() == size) {
					whenList.add(w);
				} else {
					whenList.set(size, w);
				}
				size++;
			}

			public void minusOnce() {
				size--;
			}

			public int times() {
				return size;
			}

			public int last() {
				return whenList.get(size - 1);
			}

		}

		public int timestamp;

		public ArrayList<Node> heap;

		public HashMap<Integer, Integer> indies;

		public int heapSize;

		public FreqStack() {
			heap = new ArrayList<>();
			indies = new HashMap<>();
			timestamp = 0;
			heapSize = 0;
		}

		public void push(int val) {
			if (!indies.containsKey(val)) {
				Node newData = new Node(val, timestamp++);
				if (heap.size() == heapSize) {
					heap.add(newData);
				} else {
					heap.set(heapSize, newData);
				}
				indies.put(val, heapSize);
				heapInsert(heapSize++);
			} else {
				int index = indies.get(val);
				Node data = heap.get(index);
				data.addOnce(timestamp++);
				resign(index);
			}
		}

		public int pop() {
			Node head = heap.get(0);
			if (head.times() == 1) {
				swap(0, --heapSize);
				indies.remove(head.val);
			} else {
				head.minusOnce();
			}
			heapify(0);
			return head.val;
		}

		private void resign(int i) {
			heapInsert(i);
			heapify(i);
		}

		private void heapInsert(int i) {
			while (compare(heap.get(i), heap.get((i - 1) / 2)) < 0) {
				swap(i, (i - 1) / 2);
				i = (i - 1) / 2;
			}
		}

		private void heapify(int i) {
			int l = i * 2 + 1;
			while (l < heapSize) {
				int best = l + 1 < heapSize && compare(heap.get(l + 1), heap.get(l)) < 0 ? (l + 1) : l;
				best = compare(heap.get(best), heap.get(i)) < 0 ? best : i;
				if (best == i) {
					break;
				}
				swap(best, i);
				i = best;
				l = i * 2 + 1;
			}
		}

		private int compare(Node a, Node b) {
			return a.times() != b.times() ? (b.times() - a.times()) : (b.last() - a.last());
		}

		private void swap(int i, int j) {
			Node o1 = heap.get(i);
			Node o2 = heap.get(j);
			heap.set(i, o2);
			heap.set(j, o1);
			indies.put(o2.val, i);
			indies.put(o1.val, j);
		}

	}

}

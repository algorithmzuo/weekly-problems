package class_2023_04_2_week;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

// 测试链接 : https://leetcode.cn/problems/dinner-plate-stacks/
public class Code01_DinnerPlateStacks {

	class DinnerPlates {
		// 所有未满栈的下标放入堆
		private PriorityQueue<Integer> heap;
		// 按顺序存放所有栈
		private List<LinkedList<Integer>> list;
		// 栈的容量
		private int capacity;

		public DinnerPlates(int cap) {
			heap = new PriorityQueue<Integer>();
			list = new ArrayList<>();
			capacity = cap;
		}

		public void push(int val) {
			if (!heap.isEmpty()) {
				Deque<Integer> stack = list.get(heap.peek());
				stack.offerFirst(val);
				if (stack.size() == capacity) {
					heap.poll();
				}
			} else {
				LinkedList<Integer> stack = new LinkedList<>();
				stack.offerFirst(val);
				list.add(stack);
				if (stack.size() < capacity) {
					heap.offer(list.size() - 1);
				}
			}

		}

		public int pop() {
			if (list.size() == 0) {
				return -1;
			}
			int val = -1;
			int index = list.size() - 1;
			while (index >= 0 && list.get(index).size() == 0) {
				list.remove(index);
				if (heap.contains(index)) {
					heap.remove(index);
				}
				index--;
			}
			if (index >= 0 && list.get(index).size() > 0) {
				val = list.get(index).pollFirst();
				if (!heap.contains(index)) {
					heap.offer(index);
				}
			}
			return val;
		}

		public int popAtStack(int index) {
			if (index > list.size() - 1) {
				return -1;
			}
			if (list.get(index).size() == 0) {
				return -1;
			}
			int val = list.get(index).pollFirst();
			if (!heap.contains(index)) {
				heap.offer(index);
			}
			return val;
		}
	}

}

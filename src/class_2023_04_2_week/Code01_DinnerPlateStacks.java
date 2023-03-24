package class_2023_04_2_week;

import java.util.ArrayList;
import java.util.PriorityQueue;

// 测试链接 : https://leetcode.cn/problems/dinner-plate-stacks/
public class Code01_DinnerPlateStacks {

	class DinnerPlates {
		private final int N = 100001;
		private int[] cnt = new int[N + 1];
		private ArrayList<ArrayList<Integer>> stacks = new ArrayList<>();
		private PriorityQueue<Integer> heap = new PriorityQueue<>();
		private int capacity;
		private int size;

		public DinnerPlates(int cap) {
			capacity = cap;
			size = 0;
		}

		public void push(int val) {
			if (!heap.isEmpty()) {
				while (cnt[size] == 0 && size > 0) {
					size--;
				}
				if (heap.peek() >= size) {
					while (!heap.isEmpty() && heap.peek() > size) {
						heap.poll();
					}
					stacks.get(size).add(val);
					cnt[size]++;
				} else {
					int cur = heap.peek();
					cnt[cur]++;
					stacks.get(cur).add(val);
					if (cnt[cur] == capacity) {
						heap.poll();
					}
				}
			} else {
				if (cnt[size] == capacity && size < N) {
					size++;
				}
				if (cnt[size] == 0 && stacks.size() <= size) {
					stacks.add(new ArrayList<>());
				}
				stacks.get(size).add(val);
				cnt[size]++;
			}
		}

		public int pop() {
			if (size == 0 && cnt[size] == 0) {
				return -1;
			}
			while (cnt[size] == 0 && size > 0) {
				size--;
			}
			return stacks.get(size).remove(--cnt[size]);
		}

		public int popAtStack(int index) {
			if (cnt[index] == 0) {
				return -1;
			}
			int ans = stacks.get(index).remove(cnt[index] - 1);
			if (cnt[index] == capacity) {
				heap.offer(index);
			}
			cnt[index]--;
			return ans;
		}
	}

}

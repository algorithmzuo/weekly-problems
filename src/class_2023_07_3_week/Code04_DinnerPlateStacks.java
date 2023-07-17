package class_2023_07_3_week;

import java.util.ArrayList;
import java.util.PriorityQueue;

// 我们把无限数量的栈排成一行，按从左到右的次序从 0 开始编号
// 每个栈的的最大容量 capacity 都相同。实现一个叫「餐盘」的类 DinnerPlates
// DinnerPlates(int capacity) - 给出栈的最大容量 capacity
// void push(int val) 将给出的正整数 val 推入 从左往右第一个 没有满的栈
// int pop() 返回 从右往左第一个 非空栈顶部的值，并将其从栈中删除
//           如果所有的栈都是空的，请返回 -1。
// int popAtStack(int index) - 返回编号 index 的栈顶部的值，并将其从栈中删除
//           如果编号 index 的栈是空的，请返回 -1。
// 测试链接 : https://leetcode.cn/problems/dinner-plate-stacks/
public class Code04_DinnerPlateStacks {

	class DinnerPlates {
		private final int N = 100001;
		private int[] cnt = new int[N + 1];
		private ArrayList<ArrayList<Integer>> stacks = new ArrayList<>();
		private PriorityQueue<Integer> heap = new PriorityQueue<>();
		private int capacity;
		private int maxIndex;

		public DinnerPlates(int cap) {
			capacity = cap;
			maxIndex = 0;
		}

		public void push(int val) {
			if (!heap.isEmpty()) {
				while (cnt[maxIndex] == 0 && maxIndex > 0) {
					maxIndex--;
				}
				if (heap.peek() >= maxIndex) {
					while (!heap.isEmpty() && heap.peek() > maxIndex) {
						heap.poll();
					}
					stacks.get(maxIndex).add(val);
					cnt[maxIndex]++;
				} else {
					int cur = heap.peek();
					cnt[cur]++;
					stacks.get(cur).add(val);
					if (cnt[cur] == capacity) {
						heap.poll();
					}
				}
			} else {
				if (cnt[maxIndex] == capacity && maxIndex < N) {
					maxIndex++;
				}
				if (cnt[maxIndex] == 0 && stacks.size() == maxIndex) {
					stacks.add(new ArrayList<>());
				}
				stacks.get(maxIndex).add(val);
				cnt[maxIndex]++;
			}
		}

		public int pop() {
			if (maxIndex == 0 && cnt[maxIndex] == 0) {
				return -1;
			}
			while (cnt[maxIndex] == 0 && maxIndex > 0) {
				maxIndex--;
			}
			return stacks.get(maxIndex).remove(--cnt[maxIndex]);
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

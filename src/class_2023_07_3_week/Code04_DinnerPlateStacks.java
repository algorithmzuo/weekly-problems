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
		// 根据题目数据量，leetcode升级数据，改大
		private final int N = 100001;
		private int capacity;
		// 所有栈的结构
		private ArrayList<ArrayList<Integer>> stacks = new ArrayList<>();
		// 每个栈里有多少数字
		private int[] cnt = new int[N + 1];
		// 曾经满了，然后又因为popAtStack方法变得不满的栈
		// 编号放在这个堆里，小根堆
		// 可以理解为空洞栈组成的堆
		private PriorityQueue<Integer> heap = new PriorityQueue<>();
		// 拥有数字的栈中，最右栈的编号
		private int rightStack;

		public DinnerPlates(int cap) {
			capacity = cap;
			rightStack = 0;
		}

		// 从右往左拿数，
		public int pop() {
			while (cnt[rightStack] == 0 && rightStack > 0) {
				rightStack--;
			}
			if (cnt[rightStack] == 0) {
				// 来到0号站，还没遇到数字
				return -1;
			}
			return stacks.get(rightStack).remove(--cnt[rightStack]);
		}

		public int popAtStack(int index) {
			if (cnt[index] == 0) {
				return -1;
			}
			int ans = stacks.get(index).remove(cnt[index] - 1);
			if (cnt[index] == capacity) {
				heap.add(index);
			}
			cnt[index]--;
			return ans;
		}

		// push、pop、popAt
		
		
		public void push(int val) {
			if (heap.isEmpty()) {
				// 没有空洞
				// 0.....rightstack
				if (cnt[rightStack] == capacity && rightStack < N) {
					rightStack++;
				}
				if (stacks.size() == rightStack) {
					stacks.add(new ArrayList<>());
				}
				stacks.get(rightStack).add(val);
				cnt[rightStack]++;
			} else {
				// 有空洞
				// rightStack往左来到该去的位置，往左缩
				while (cnt[rightStack] == 0 && rightStack > 0) {
					rightStack--;
				}
				if (heap.peek() >= rightStack) {
					// 所有空洞，没用了
					while (!heap.isEmpty()) {
						heap.poll();
					}
					// 0.....rightStack 
					if (cnt[rightStack] == capacity) {
						rightStack++;
					}
					if (stacks.size() == rightStack) {
						stacks.add(new ArrayList<>());
					}
					stacks.get(rightStack).add(val);
					cnt[rightStack]++;
				} else {
					// 不是所有空洞都失效
					int cur = heap.peek();
					cnt[cur]++;
					stacks.get(cur).add(val);
					if (cnt[cur] == capacity) {
						heap.poll();
					}
				}
			}
		}

	}

}

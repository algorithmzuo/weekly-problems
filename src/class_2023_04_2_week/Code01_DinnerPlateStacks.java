package class_2023_04_2_week;

import java.util.ArrayList;

// 测试链接 : https://leetcode.cn/problems/dinner-plate-stacks/
public class Code01_DinnerPlateStacks {

	class DinnerPlates {

		public static class IndexTree {
			private int[] tree;
			private int N;

			public IndexTree(int size) {
				N = size;
				tree = new int[N + 1];
			}

			public int sum(int index) {
				int ret = 0;
				while (index > 0) {
					ret += tree[index];
					index -= index & -index;
				}
				return ret;
			}

			public void add(int index, int d) {
				while (index <= N) {
					tree[index] += d;
					index += index & -index;
				}
			}
		}

		public static class Stacks {
			public ArrayList<Integer> heads;
			public ArrayList<Integer> values;
			public ArrayList<Integer> lasts;
			public ArrayList<Integer> frees;
			public int occupySize;
			public int freeSize;

			public Stacks(int stackSize) {
				heads = new ArrayList<>();
				for (int i = 0; i < stackSize; i++) {
					heads.add(-1);
				}
				values = new ArrayList<>();
				lasts = new ArrayList<>();
				frees = new ArrayList<>();
				occupySize = 0;
				freeSize = 0;
			}

			public void push(int num, int stackIndex) {
				int headIndex = heads.get(stackIndex);
				if (freeSize == 0) {
					heads.set(stackIndex, occupySize++);
					values.add(num);
					lasts.add(headIndex);
				} else {
					int freeIndex = frees.get(--freeSize);
					heads.set(stackIndex, freeIndex);
					values.set(freeIndex, num);
					lasts.set(freeIndex, headIndex);
				}
			}

			public int pop(int stackIndex) {
				int headIndex = heads.get(stackIndex);
				int ans = values.get(headIndex);
				int newHeadIndex = lasts.get(headIndex);
				heads.set(stackIndex, newHeadIndex);
				if (freeSize >= frees.size()) {
					frees.add(headIndex);
					freeSize++;
				} else {
					frees.set(freeSize++, headIndex);
				}
				return ans;
			}

		}

		private static final int MAXN = 100000;

		Stacks ss;

		IndexTree it;

		private int capacity;

		public DinnerPlates(int cap) {
			ss = new Stacks(MAXN + 1);
			it = new IndexTree(MAXN + 1);
			capacity = cap;
		}

		public int popAtStack(int index) {
			if (index < 0 || it.sum(index + 1) - it.sum(index) == 0) {
				return -1;
			}
			it.add(index + 1, -1);
			return ss.pop(index);
		}

		public void push(int val) {
			int index = leftHasSpace();
			it.add(index + 1, 1);
			ss.push(val, index);
		}

		public int pop() {
			return popAtStack(rightHasData());
		}

		private int leftHasSpace() {
			int l = 0;
			int r = MAXN;
			int m, ans = r;
			while (l <= r) {
				m = (l + r) / 2;
				if (it.sum(m + 1) < (m + 1) * capacity) {
					ans = m;
					r = m - 1;
				} else {
					l = m + 1;
				}
			}
			return ans;
		}

		private int rightHasData() {
			int l = 0;
			int r = MAXN;
			int m, ans = r;
			while (l <= r) {
				m = (l + r) / 2;
				if (it.sum(r + 1) - it.sum(m) > 0) {
					ans = m;
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			return ans;
		}

	}

}

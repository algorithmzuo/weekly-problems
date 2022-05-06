package class_2022_05_2_week;

// 来自学员问题
// 为了给刷题的同学一些奖励，力扣团队引入了一个弹簧游戏机
// 游戏机由 N 个特殊弹簧排成一排，编号为 0 到 N-1
// 初始有一个小球在编号 0 的弹簧处。若小球在编号为 i 的弹簧处
// 通过按动弹簧，可以选择把小球向右弹射 jump[i] 的距离，或者向左弹射到任意左侧弹簧的位置
// 也就是说，在编号为 i 弹簧处按动弹簧，
// 小球可以弹向 0 到 i-1 中任意弹簧或者 i+jump[i] 的弹簧（若 i+jump[i]>=N ，则表示小球弹出了机器）
// 小球位于编号 0 处的弹簧时不能再向左弹。
// 为了获得奖励，你需要将小球弹出机器。
// 请求出最少需要按动多少次弹簧，可以将小球从编号 0 弹簧弹出整个机器，即向右越过编号 N-1 的弹簧。
// 测试链接 : https://leetcode-cn.com/problems/zui-xiao-tiao-yue-ci-shu/
public class Code01_MinJumpUsePre {

	public int minJump(int[] jump) {
		int n = jump.length;
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		queue[r++] = 0;
		IndexTree it = new IndexTree(n);
		for (int i = 1; i < n; i++) {
			it.add(i, 1);
		}
		int step = 0;
		while (l != r) {
			int tmp = r;
			for (; l < tmp; l++) {
				int cur = queue[l];
				int forward = cur + jump[cur];
				if (forward >= n) {
					return step + 1;
				}
				if (it.value(forward) != 0) {
					queue[r++] = forward;
					it.add(forward, -1);
				}
				while (it.sum(cur - 1) != 0) {
					int find = find(it, cur - 1);
					it.add(find, -1);
					queue[r++] = find;
				}
			}
			step++;
		}
		return -1;
	}

	public static int find(IndexTree it, int right) {
		int left = 0;
		int mid = 0;
		int find = 0;
		while (left <= right) {
			mid = (left + right) / 2;
			if (it.sum(mid) > 0) {
				find = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return find;
	}

	public static class IndexTree {

		private int[] tree;
		private int N;

		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		public int value(int index) {
			if (index == 0) {
				return sum(0);
			} else {
				return sum(index) - sum(index - 1);
			}
		}

		public int sum(int i) {
			int index = i + 1;
			int ret = 0;
			while (index > 0) {
				ret += tree[index];
				index -= index & -index;
			}
			return ret;
		}

		public void add(int i, int d) {
			int index = i + 1;
			while (index <= N) {
				tree[index] += d;
				index += index & -index;
			}
		}

	}

}

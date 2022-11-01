package class_2022_11_1_week;

import java.util.Arrays;
import java.util.TreeSet;

// 来自华为
// 做甜点需要购买配料，目前共有n种基料和m种配料可供选购
// 制作甜点需要遵循以下几条规则：
// 必须选择1种基料；可以添加0种、1种或多种配料，每种类型的配料最多添加2份
// 给定长度为n的数组base, base[i]表示第i种基料的价格
// 给定长度为m的数组topping, topping[j]表示第j种配料的价格
// 给定一个正数target，表示你做的甜点最终的价格要尽量接近这个数值
// 返回最接近这个数值的价格是多少，如果有多个方案，返回最接近、且<=target的价格
// 1 <= n,m <= 10
// 1 <= base[i], topping[j] <= 10 ^ 4
// 1 <= target <= 10 ^ 4
public class Code01_DessertPriceClosedTarget {

	// 方法1，用有序表的方法
	public static int closedTarget1(int[] base, int[] topping, int target) {
		TreeSet<Integer> set = new TreeSet<>();
		process1(topping, 0, 0, set);
		int ans = Integer.MAX_VALUE;
		for (int num : base) {
			int cur = num;
			if (num < target) {
				int rest = target - num;
				Integer floor = set.floor(rest);
				Integer ceiling = set.ceiling(rest);
				if (floor == null || ceiling == null) {
					cur += floor == null ? ceiling : floor;
				} else {
					cur += rest - floor <= ceiling - rest ? floor : ceiling;
				}
			}
			if (Math.abs(cur - target) < Math.abs(ans - target)
					|| (Math.abs(cur - target) == Math.abs(ans - target) && cur < ans)) {
				ans = cur;
			}
		}
		return ans;
	}

	public static void process1(int[] topping, int index, int sum, TreeSet<Integer> set) {
		if (index == topping.length) {
			set.add(sum);
		} else {
			process1(topping, index + 1, sum, set);
			process1(topping, index + 1, sum + topping[index], set);
			process1(topping, index + 1, sum + (topping[index] << 1), set);
		}
	}

	// 方法2，用数组排序+二分的方法

	public static int[] collect = new int[60000];

	public static int size = 0;

	public static int closedTarget2(int[] base, int[] topping, int target) {
		size = 0;
		process2(topping, 0, 0);
		Arrays.sort(collect, 0, size);
		int ans = Integer.MAX_VALUE;
		for (int num : base) {
			int cur = num;
			if (num < target) {
				int rest = target - num;
				int floor = floor(rest);
				int ceiling = ceiling(rest);
				if (floor == -1 || ceiling == -1) {
					cur += floor == -1 ? ceiling : floor;
				} else {
					cur += rest - floor <= ceiling - rest ? floor : ceiling;
				}
			}
			if (Math.abs(cur - target) < Math.abs(ans - target)
					|| (Math.abs(cur - target) == Math.abs(ans - target) && cur < ans)) {
				ans = cur;
			}
		}
		return ans;
	}

	public static void process2(int[] topping, int index, int sum) {
		if (index == topping.length) {
			collect[size++] = sum;
		} else {
			process2(topping, index + 1, sum);
			process2(topping, index + 1, sum + topping[index]);
			process2(topping, index + 1, sum + (topping[index] << 1));
		}
	}

	public static int floor(int num) {
		int l = 0;
		int r = size - 1;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (collect[m] <= num) {
				ans = collect[m];
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static int ceiling(int num) {
		int l = 0;
		int r = size - 1;
		int m = 0;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (collect[m] >= num) {
				ans = collect[m];
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] base1 = { 1, 7 };
		int[] topping1 = { 3, 4 };
		int target1 = 10;
		System.out.println(closedTarget1(base1, topping1, target1));
		System.out.println(closedTarget2(base1, topping1, target1));

		int[] base2 = { 2, 3 };
		int[] topping2 = { 4, 5, 100 };
		int target2 = 18;
		System.out.println(closedTarget1(base2, topping2, target2));
		System.out.println(closedTarget2(base2, topping2, target2));

		int[] base3 = { 3, 10 };
		int[] topping3 = { 2, 5 };
		int target3 = 9;
		System.out.println(closedTarget1(base3, topping3, target3));
		System.out.println(closedTarget2(base3, topping3, target3));

		int[] base4 = { 10 };
		int[] topping4 = { 1 };
		int target4 = 1;
		System.out.println(closedTarget1(base4, topping4, target4));
		System.out.println(closedTarget2(base4, topping4, target4));
	}

}

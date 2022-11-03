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
// 返回最接近这个数值的价格是多少
// 如果有多个方案，都最接近target，返回价格最小的那个答案
// 1 <= n,m <= 10
// 1 <= base[i], topping[j] <= 10 ^ 4
// 1 <= target <= 10 ^ 4
public class Code01_DessertPriceClosedTarget {

	// 方法1，用有序表的方法
	public static int closedTarget1(int[] base, int[] topping, int target) {
		// 辅料所能产生的所有价格！
		// 0 5 15 23
		TreeSet<Integer> set = new TreeSet<>();
		// 暴力展开！收集所有能产生的价格！放入辅料表里去！
		process1(topping, 0, 0, set);
		int ans = Integer.MAX_VALUE;
		for (int num : base) {
			// 枚举每一种主料的价格！
			// 最终能搭配出来的最接近的价格
			int cur = num;
			// 20   100
			// 110  100
			if (num < target) { // cur < 要求
				// 60  100
				// 40
				int rest = target - num;
				// <= rest 最接近的！
				Integer floor = set.floor(rest);
				// >= rest 最接近的！
				Integer ceiling = set.ceiling(rest);
				if (floor == null || ceiling == null) {
					cur += floor == null ? ceiling : floor;
				} else {
					cur += rest - floor <= ceiling - rest ? floor : ceiling;
				}
				// cur会选择floor,或ceiling，谁加上最接近target选谁！
			}
			if (Math.abs(cur - target) < Math.abs(ans - target)
					|| (Math.abs(cur - target) == Math.abs(ans - target) && cur < ans)) {
				ans = cur;
			}
		}
		return ans;
	}

	// 暴力展开！收集所有能产生的价格！放入辅料表里去！
	// topping[index....] 
	// topping[0...index-1]  sum
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

	public static int[] collect = new int[14348907];

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

	// 为了验证
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了验证
	public static void main(String[] args) {
		int N = 8;
		int V = 10000;
		int testTime = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int m = (int) (Math.random() * N) + 1;
			int[] base = randomArray(n, V);
			int[] topping = randomArray(m, V);
			int target = (int) (Math.random() * V) + 1;
			int ans1 = closedTarget1(base, topping, target);
			int ans2 = closedTarget2(base, topping, target);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 15;
		int m = 15;
		int[] base = randomArray(n, V);
		int[] topping = randomArray(m, V);
		int target = (int) (Math.random() * V) + 1;
		System.out.println("base数组长度 : " + n);
		System.out.println("topping数组长度 : " + m);
		System.out.println("数值范围 : " + V);
		long start = System.currentTimeMillis();
		closedTarget2(base, topping, target);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}

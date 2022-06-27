package class_2022_07_1_week;

import java.util.HashSet;

// 来自SnowFlake
// 给定一个正数n，比如6
// 表示数轴上有0,1,2,3,4,5,6
// <0 或者 >6 的位置认为无法到达
// 给定两个数字x和y，0<= x，y <= n
// 表示小人一开始在x的位置，它的目的地是y的位置，比如x = 1, y = 3
// 给定一个字符串s，比如 : rrlrlr
// 任何一个s的子序列，对应着一种运动轨迹，r表示向右，l表示向左
// 比如一开始小人在1位置，"rlr"是s的一个子序列
// 那么运动轨迹是：1 -> 2 -> 1 -> 2
// 求，s中有多少个字面值不同的子序列，能让小人从x走到y，
// 走的过程中完全不走出0到n的区域。
// 比如，s = "rrlrlr", n = 6, x = 1, y = 3
// 有如下5个字面值不同的子序列
// rr : 1 -> 2 -> 3
// rrlr : 1 -> 2 -> 3 -> 2 -> 3
// rrrl : 1 -> 2 -> 3 -> 4 -> 3
// rlrr : 1 -> 2 -> 1 -> 2 -> 3
// rrlrlr : 1 -> 2 -> 3 -> 2 -> 3 -> 2 -> 3
// 注意：一定要是字面值不同的子序列！相同字面值的子序列算一种
// 比如s中，有很多个rr的子序列，但是算一个
// 数据规模 : s串长度 <= 1000, x,y,n <= 2500
public class Code03_WaysSubsqenceXToY {

	// 暴力方法
	// 为了测试
	// 生成所有去重的子序列，一个一个验证
	public static int ways1(String s, int n, int x, int y) {
		HashSet<String> set = new HashSet<>();
		process1(s, 0, new StringBuilder(), set);
		int ans = 0;
		int cur = 0;
		for (String path : set) {
			cur = x;
			for (char cha : path.toCharArray()) {
				cur += cha == 'r' ? 1 : -1;
				if (cur < 0 || cur > n) {
					cur = -1;
					break;
				}
			}
			if (cur == y) {
				ans++;
			}
		}
		return ans;
	}

	public static void process1(String s, int index, StringBuilder builder, HashSet<String> set) {
		if (index == s.length()) {
			set.add(builder.toString());
		} else {
			process1(s, index + 1, builder, set);
			builder.append(s.charAt(index));
			process1(s, index + 1, builder, set);
			builder.deleteCharAt(builder.length() - 1);
		}
	}

	// 最优解
	// 思路来自：大厂刷题班，17节，Code05，DistinctSubseqValue问题
	// 如果字符串长度为m，位置数量n
	// 时间复杂度O(m * n)
	public static int ways2(String s, int n, int x, int y) {
		int[] all = new int[n + 1];
		int[] r = new int[n + 1];
		int[] l = new int[n + 1];
		int[] add = new int[n + 1];
		all[x] = 1;
		for (char cha : s.toCharArray()) {
			if (cha == 'r') {
				for (int i = 0; i < n; i++) {
					add[i + 1] += all[i];
				}
				for (int i = 0; i <= n; i++) {
					add[i] -= r[i];
					all[i] += add[i];
					r[i] += add[i];
					add[i] = 0;
				}
			} else {
				for (int i = 1; i <= n; i++) {
					add[i - 1] += all[i];
				}
				for (int i = 0; i <= n; i++) {
					add[i] -= l[i];
					all[i] += add[i];
					l[i] += add[i];
					add[i] = 0;
				}
			}
		}
		return all[y];
	}

	// 为了测试
	public static String randomLRString(int n) {
		char[] str = new char[n];
		for (int i = 0; i < n; i++) {
			str[i] = Math.random() < 0.5 ? 'r' : 'l';
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static void main(String[] args) {
		int max = 16;
		int testTime = 2000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * max) + 1;
			int m = (int) (Math.random() * max) + 1;
			String str = randomLRString(m);
			int x = (int) (Math.random() * (n + 1));
			int y = (int) (Math.random() * (n + 1));
			int ans1 = ways1(str, n, x, y);
			int ans2 = ways2(str, n, x, y);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println(str);
				System.out.println(n);
				System.out.println(x);
				System.out.println(y);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 25000;
		int m = 10000;
		System.out.println("位置规模 : " + n);
		System.out.println("字符串规模 : " + m);
		String str = randomLRString(m);
		int x = (int) (Math.random() * (n + 1));
		int y = (int) (Math.random() * (n + 1));
		long start = System.currentTimeMillis();
		ways2(str, n, x, y);
		long end = System.currentTimeMillis();
		System.out.println("运行时间: " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}

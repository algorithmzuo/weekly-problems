package class_2022_07_2_week;

import java.util.HashSet;

// 来自SnowFlake
// 给定一个正数n，比如6
// 表示数轴上有 0,1,2,3,4,5,6
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
public class Code02_WaysSubsqenceXToY {

	// 不要求去重？
	// 指令来到index位置的字符！
	// 当前来到的位置，cur
	// 最终要去的位置，aim
	// 返回方法数(不去重的！)
	// index 字符(要！不要！)
	public static int f(char[] str, int index, int cur, int n, int aim) {
		if (index == str.length) {
			return cur == aim ? 1 : 0;
		}
		// 可能性1 : 当前的字符，不要！
		int ways1 = f(str, index + 1, cur, n, aim);
		// 可能性2 : 当前的字符，要！
		int ways2 = 0;
		if (str[index] == 'L') {
			if (cur - 1 >= 0) {
				ways2 = f(str, index + 1, cur - 1, n, aim);
			}
		} else { // R
			if (cur + 1 <= n) {
				ways2 = f(str, index + 1, cur + 1, n, aim);
			}
		}
		return ways1 + ways2;
	}

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
		// all[i] : 让小人来到i位置的不同字面值的子序列数量
		int[] all = new int[n + 1];
		// r[i] :   让小人来到i位置的不同字面值，且以r字符结尾，的子序列数量
		int[] r = new int[n + 1];
		// l[i] :   让小人来到i位置的不同字面值，且以l字符结尾，的子序列数量
		int[] l = new int[n + 1];
		int[] add = new int[n + 1];
		// 一开始小人在x，all[x] = 1, {}
		all[x] = 1;
		// M
		for (char cha : s.toCharArray()) {
			// 当前的指令字符串，cha
			if (cha == 'r') {
				
				// 当前小人往右走
				// 0 -> 1
				// 1 -> 2
				// 5 -> 6
				// n-1 -> n
				// n -> 死
				// 4  1000
				// 5  +1000
				// 
				// 8  200
				// 9 +200
				for (int i = 0; i < n; i++) {
					// 9 方法数 新增  all[8]
					// 每一个新增方法，都还没有减去修正值呢！
					add[i + 1] += all[i];
				}
				for (int i = 0; i <= n; i++) {
					// 变了！成了纯新增！
					add[i] -= r[i];
					all[i] += add[i];
					r[i] += add[i];
					add[i] = 0;
				}
			} else { 
				// 遇到的是l
				// 当前小人往左走
				// 0 左 死
				// 1    0
				// 2    1
				// 3    2
				for (int i = 1; i <= n; i++) {
					//  7 新增  之前8位置方法数
					add[i - 1] += all[i];
				}
				for (int i = 0; i <= n; i++) {
					// 修正，变成纯新增!
					add[i] -= l[i];
					all[i] += add[i];
					l[i] += add[i];
					add[i] = 0;
				}
			}
		}
		// 去重的！
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
			String s = randomLRString(m);
			int x = (int) (Math.random() * (n + 1));
			int y = (int) (Math.random() * (n + 1));
			int ans1 = ways1(s, n, x, y);
			int ans2 = ways2(s, n, x, y);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println(s);
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
		String s = randomLRString(m);
		int x = (int) (Math.random() * (n + 1));
		int y = (int) (Math.random() * (n + 1));
		long start = System.currentTimeMillis();
		ways2(s, n, x, y);
		long end = System.currentTimeMillis();
		System.out.println("运行时间: " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}

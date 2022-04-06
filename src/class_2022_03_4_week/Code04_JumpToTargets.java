package class_2022_03_4_week;

// 来自字节
// 一开始在0位置，每一次都可以向左或者向右跳
// 第i次能向左或者向右跳严格的i步
// 请问从0到x位置，至少跳几次可以到达
// 字节考的问题其实就是这个问题
// leetcode测试链接 : https://leetcode.com/problems/reach-a-number/
public class Code04_JumpToTargets {

	public static int reachNumber(long target) {
		if (target == 0) {
			return 0;
		}
        target = Math.abs(target);
		long l = 0;
		long r = target;
		long m = 0;
		long near = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (sum(m) >= target) {
				near = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		if (sum(near) == target) {
			return (int)near;
		}
		if (((sum(near) - target) & 1) == 1) {
			near++;
		}
		if (((sum(near) - target) & 1) == 1) {
			near++;
		}
		return (int)near;
	}

	public static long sum(long n) {
		return (n * (n + 1)) / 2;
	}

}

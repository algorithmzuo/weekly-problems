package class_2022_03_3_week;

//来自字节
//一开始在0位置，每一次都可以向左或者向右跳
//第i次能向左或者向右跳严格的i步
//请问从0到x位置，至少跳几次可以到达
//字节考的问题其实就是这个问题
//找到了测试链接 : https://www.luogu.com.cn/problem/CF11B
//提交以下所有代码，把主类名改成"Main"，可以直接通过
import java.util.Scanner;

public class Code10_JumpToTargets {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int x = Math.abs(sc.nextInt());
			System.out.println(minTimes(x));
		}
		sc.close();
	}

	public static long minTimes(long x) {
		if (x == 0) {
			return 0;
		}
		long l = 0;
		long r = x;
		long m = 0;
		long near = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (sum(m) >= x) {
				near = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		if (sum(near) == x) {
			return near;
		}
		if (((sum(near) - x) & 1) == 1) {
			near++;
		}
		if (((sum(near) - x) & 1) == 1) {
			near++;
		}
		return near;
	}

	public static long sum(long n) {
		return (n * (n + 1)) / 2;
	}

}

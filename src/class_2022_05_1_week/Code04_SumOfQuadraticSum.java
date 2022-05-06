package class_2022_05_1_week;

// 来自学员问题，蓝桥杯练习题
// f(i) : i的所有因子，每个因子都平方之后，累加起来
// 比如f(10) = 1平方 + 2平方 + 5平方 + 10平方 = 1 + 4 + 25 + 100 = 130
// 给定一个数n，求f(1) + f(2) + .. + f(n)
// n <= 10的9次方
public class Code04_SumOfQuadraticSum {

	// 暴力方法
	public static long sum1(long n) {
		int[] cnt = new int[(int) n + 1];
		for (int num = 1; num <= n; num++) {
			for (int j = 1; j <= num; j++) {
				if (num % j == 0) {
					cnt[j]++;
				}
			}
		}
		long ans = 0;
		for (long i = 1; i <= n; i++) {
			ans += i * i * (long) (cnt[(int) i]);
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(开平方根N * logN)
	public static long sum2(long n) {
		long sqrt = (long) Math.pow((double) n, 0.5);
		long ans = 0;
		for (long i = 1; i <= sqrt; i++) {
			ans += i * i * (n / i);
		}
		for (long k = n / (sqrt + 1); k >= 1; k--) {
			ans += sumOfLimitNumber(n, k);
		}
		return ans;
	}

	// 平方和公式n(n+1)(2n+1)/6
	public static long sumOfLimitNumber(long v, long n) {
		long r = cover(v, n);
		long l = cover(v, n + 1);
		return ((r * (r + 1) * ((r << 1) + 1) - l * (l + 1) * ((l << 1) + 1)) * n) / 6;
	}

	public static long cover(long v, long n) {
		long l = 1;
		long r = v;
		long m = 0;
		long ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (m * n <= v) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	// 实验
	// 解法来自观察
	public static void test(int n) {
		int[] cnt = new int[n + 1];
		for (int num = 1; num <= n; num++) {
			for (int j = 1; j <= num; j++) {
				if (num % j == 0) {
					cnt[j]++;
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			System.out.println("因子 : " + i + ", 个数 : " + cnt[i]);
		}
	}

	public static void main(String[] args) {

		test(200);

		System.out.println("测试开始");
		for (long i = 1; i < 1000; i++) {
			if (sum1(i) != sum2(i)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");

		long n = 50000000000L; // 5 * 10的10次方
		long start = System.currentTimeMillis();
		sum2(n);
		long end = System.currentTimeMillis();
		System.out.println("大样本测试，n = " + n);
		System.out.println("运行时间 : " + (end - start) + " ms");
	}

}

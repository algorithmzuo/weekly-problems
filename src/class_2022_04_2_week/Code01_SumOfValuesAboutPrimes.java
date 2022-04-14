package class_2022_04_2_week;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

// 来自携程
// 给出n个数字，你可以任选其中一些数字相乘，相乘之后得到的新数字x
// x的价值是x的不同质因子的数量
// 返回所有选择数字的方案中，得到的x的价值之和
public class Code01_SumOfValuesAboutPrimes {

	// 工具！
	// 返回num质数因子列表(去重)
	// 时间复杂度，根号(num)
	public static ArrayList<Long> primes(long num) {
		ArrayList<Long> ans = new ArrayList<>();
		for (long i = 2; i * i <= num && num > 1; i++) {
			if (num % i == 0) {
				ans.add(i);
				while (num % i == 0) {
					num /= i;
				}
			}
		}
		if (num != 1) {
			ans.add(num);
		}
		return ans;
	}

	public static long sumOfValues1(int[] arr) {
		return process1(arr, 0, 1);
	}

	public static long process1(int[] arr, int index, long pre) {
		if (index == arr.length) {
			return (long) primes(pre).size();
		}
		long p1 = process1(arr, index + 1, pre);
		long p2 = process1(arr, index + 1, pre * (long) arr[index]);
		return p1 + p2;
	}

	public static long sumOfValues2(int[] arr) {
		// key : 某个质数因子
		// value : 有多少个数含有这个因子
		HashMap<Long, Long> cntMap = new HashMap<>();
		for (int num : arr) {
			for (long factor : primes(num)) {
				cntMap.put(factor, cntMap.getOrDefault(factor, 0L) + 1L);
			}
		}
		int n = arr.length;
		long ans = 0;
		// count ：含有这个因子的数，有多少个
		// others : 不含有这个因子的数，有多少个
		for (long count : cntMap.values()) {
			long others = n - count;
			ans += (power(2, count) - 1) * power(2, others);
		}
		return ans;
	}

	public static long power(long num, long n) {
		if (n == 0L) {
			return 1L;
		}
		long ans = 1L;
		while (n > 0) {
			if ((n & 1) != 0) {
				ans *= num;
			}
			num *= num;
			n >>= 1;
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * value) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 10;
		int v = 20;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(n, v);
			long ans1 = sumOfValues1(arr);
			long ans2 = sumOfValues2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了！");
				BigInteger all = new BigInteger("1");
				for (int num : arr) {
					all = all.multiply(new BigInteger(String.valueOf(num)));
				}
				System.out.println("所有数都乘起来 : " + all.toString());
				System.out.println("长整型最大范围 : " + Long.MAX_VALUE);
				System.out.println("可以看看上面的打印，看看是不是因为溢出了");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

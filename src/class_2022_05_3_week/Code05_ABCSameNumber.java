package class_2022_05_3_week;

// 来自京东
// 4.2笔试
// 给定一个长度为3N的数组，其中最多含有0、1、2三种值
// 你可以把任何一个连续区间上的数组，全变成0、1、2中的一种
// 目的是让0、1、2三种数字的个数都是N
// 返回最小的变化次数
public class Code05_ABCSameNumber {

	// 暴力方法
	// 为了验证不会超过2次
	public static int minTimes1(int[] arr) {
		int[] set = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			set[i] = arr[i];
		}
		return process1(set, 0, arr);
	}

	public static int process1(int[] set, int time, int[] origin) {
		int[] cnt = new int[3];
		for (int num : set) {
			cnt[num]++;
		}
		if (cnt[0] == cnt[1] && cnt[0] == cnt[2]) {
			return time;
		} else {
			if (time == 2) {
				return 3;
			}
			int ans = Integer.MAX_VALUE;
			for (int L = 0; L < set.length; L++) {
				for (int R = L; R < set.length; R++) {
					set(set, L, R, 0);
					ans = Math.min(ans, process1(set, time + 1, origin));
					set(set, L, R, 1);
					ans = Math.min(ans, process1(set, time + 1, origin));
					set(set, L, R, 2);
					ans = Math.min(ans, process1(set, time + 1, origin));
					rollback(set, L, R, origin);
				}
			}
			return ans;
		}
	}

	public static void set(int[] set, int L, int R, int v) {
		for (int i = L; i <= R; i++) {
			set[i] = v;
		}
	}

	public static void rollback(int[] set, int L, int R, int[] origin) {
		for (int i = L; i <= R; i++) {
			set[i] = origin[i];
		}
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int minTimes2(int[] arr) {
		int[] cnt = new int[3];
		for (int num : arr) {
			cnt[num]++;
		}
		if (cnt[0] == cnt[1] && cnt[0] == cnt[2]) {
			return 0;
		}
		int n = arr.length;
		int m = n / 3;
		if ((cnt[0] < m && cnt[1] < m) || (cnt[0] < m && cnt[2] < m) || (cnt[1] < m && cnt[2] < m)) {
			return 2;
		} else { // 只有一种数的个数是小于m的
			return once(arr, cnt, m) ? 1 : 2;
		}
	}

	public static boolean once(int[] arr, int[] cnt, int m) {
		int lessV = cnt[0] < m ? 0 : (cnt[1] < m ? 1 : 2);
		int lessT = lessV == 0 ? cnt[0] : (lessV == 1 ? cnt[1] : cnt[2]);
		if (cnt[0] > m && modify(arr, 0, cnt[0], lessV, lessT)) {
			return true;
		}
		if (cnt[1] > m && modify(arr, 1, cnt[1], lessV, lessT)) {
			return true;
		}
		if (cnt[2] > m && modify(arr, 2, cnt[2], lessV, lessT)) {
			return true;
		}
		return false;
	}

	public static boolean modify(int[] arr, int more, int moreT, int less, int lessT) {
		int[] cnt = new int[3];
		cnt[less] = lessT;
		cnt[more] = moreT;
		int aim = arr.length / 3;
		int L = 0;
		int R = 0;
		while (R < arr.length || cnt[more] <= aim) {
			if (cnt[more] > aim) {
				cnt[arr[R++]]--;
			} else if (cnt[more] < aim) {
				cnt[arr[L++]]++;
			} else {
				if (cnt[less] + R - L < aim) {
					cnt[arr[R++]]--;
				} else if (cnt[less] + R - L > aim) {
					cnt[arr[L++]]++;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	// 为了验证
	public static int[] randomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 3);
		}
		return arr;
	}

	// 为了验证
	public static void main(String[] args) {
		// 数组长度一定是3的整数倍，且 <= 3*n
		// 如下代码是验证操作次数一定不大于2次
		int n = 8;
		int testTime = 2000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int m = ((int) (Math.random() * n) + 1) * 3;
			int[] arr = randomArray(m);
			int ans1 = minTimes1(arr);
			int ans2 = minTimes2(arr);
			if (ans1 != ans2) {
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

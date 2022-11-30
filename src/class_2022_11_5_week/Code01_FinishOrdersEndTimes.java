package class_2022_11_5_week;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// 来自学员问题
// 给定一个数组componets，长度为A，
// componets[i] = j，代表i类型的任务需要耗时j
// 给定一个二维数组orders，长度为M，
// orders[i][0]代表i号订单下单时间
// orders[i][1]代表i号订单是哪种类型的任务，毫无疑问orders[i][1] < A
// 一开始所有流水线都在0时刻待命，
// 给定一个正数nums，表示流水线的数量，流水线编号为0 ~ nums-1
// 每一个流水线可以承接任何类型的任务，耗时就是componets数组给定的
// 所有订单的下单时间一定是有序的，也就是orders数组，是根据下单时间排序的
// 每一个订单开始执行的时间不能早于下单时间，
// 如果有多个流水线都可以执行当前订单，选择编号最小的流水线
// 根据上面说的任务执行细节，去依次完成所有订单
// 返回长度为M的数组ans，也就是和orders等长
// ans[i][0]代表i号订单是由哪条流水线执行的
// ans[i][1]代表i号订单的完成时间
// 1 <= A <= 10^5
// 1 <= M <= 10^5
// 1 <= nums <= 10^5
// 1 <= 时间数值 <= 10^5
public class Code01_FinishOrdersEndTimes {
	// jobTimes : [ 5 2 7 1 ]
	//              0 1 2 3
	// orders : [100, 0]    [200, 1]
	//              0          1
	// nums = 7
	//     0  1  2 ... 6
	// 暴力方法
	// 为了测试
	public static int[][] times1(int nums, int[] jobTimes, int[][] orders) {
		int[] lines = new int[nums];
		int n = orders.length;
		int[][] ans = new int[n][2];
		for (int i = 0; i < n; i++) {
			int start = orders[i][0];
			int type = orders[i][1];
			int usei = -1;
			for (int j = 0; j < nums; j++) {
				if (lines[j] <= start) {
					usei = j;
					break;
				}
			}
			if (usei != -1) {
				ans[i][0] = usei;
				ans[i][1] = start + jobTimes[type];
			} else {
				int early = Integer.MAX_VALUE;
				for (int j = 0; j < nums; j++) {
					if (lines[j] < early) {
						early = lines[j];
						usei = j;
					}
				}
				ans[i][0] = usei;
				ans[i][1] = early + jobTimes[type];
			}
			lines[usei] = ans[i][1];
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(M*logN)
	// M是订单数量，N是流水线数量
	public static int[][] times2(int nums, int[] componets, int[][] orders) {
		int n = orders.length;
		// 睡眠堆
		PriorityQueue<Line> sleepLines = new PriorityQueue<>(new WakeUpComparator());
		// 可用堆
		PriorityQueue<Line> canUseLines = new PriorityQueue<>(new IndexComparator());
		for (int i = 0; i < nums; i++) {
			canUseLines.add(new Line(0, i));
		}
		int[][] ans = new int[n][2];
		for (int i = 0; i < orders.length; i++) {
			int startTime = orders[i][0];
			int jobType = orders[i][1];
			// 当前订单在start时刻下单，所有唤醒时间比time早的流水线全可以考虑
			while (!sleepLines.isEmpty() && sleepLines.peek().time <= startTime) {
				canUseLines.add(sleepLines.poll());
			}
			// 如果可以使用的流水线不存在
			// 比如，2条流水线
			// 第0个订单，1时刻开始，用时100万，流水线A在100万+1时刻醒来
			// 第1个订单，2时刻开始，用时100万，流水线B在100万+2时刻醒来
			// 轮到第3个订单，3时刻开始，用时100万
			// 会发现可用流水线已经没有了，此时需要等到流水线A在100万+1时刻醒来，做当前订单
			Line use = null;
			if (canUseLines.isEmpty()) {
				// 如果当前时刻，可以使用的流水线不存在，需要等到可以唤醒的最早那个
				// 如果可以唤醒的最早流水线，不只一个
				// 选编号小的，看比较器的注释
				use = sleepLines.poll();
				ans[i][1] = use.time + componets[jobType];
			} else {
				// 如果当前时刻，可以使用的流水线存在，需要使用编号最小的
				use = canUseLines.poll();
				ans[i][1] = startTime + componets[jobType];
			}
			ans[i][0] = use.index;
			use.time = ans[i][1];
			sleepLines.add(use);
		}
		return ans;
	}

	// 流水线
	public static class Line {
		public int time;
		public int index;

		public Line(int t, int i) {
			time = t;
			index = i;
		}
	}

	public static class WakeUpComparator implements Comparator<Line> {

		// 谁能早醒谁在前
		// 醒来时间一样，编号小的在前
		// 为什么要考虑编号，看上面的注释
		// 如果可以使用的流水线不存在
		// 那要使用最早唤醒的那个流水线来执行当前订单
		// 但是如果最早唤醒的那个流水线不只一个，还是要选编号小的
		@Override
		public int compare(Line o1, Line o2) {
			if (o1.time != o2.time) {
				return o1.time < o2.time ? -1 : 1;
			}
			return o1.index - o2.index;
		}

	}

	public static class IndexComparator implements Comparator<Line> {

		@Override
		public int compare(Line o1, Line o2) {
			return o1.index - o2.index;
		}

	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	// 为了测试
	public static int[][] randomMatrix(int n, int a, int b) {
		int[][] ans = new int[n][2];
		for (int i = 0; i < n; i++) {
			ans[i][0] = (int) (Math.random() * a) + 1;
			ans[i][1] = (int) (Math.random() * b);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 100;
		int M = 300;
		int V = 10000;
		int testTimes = 5000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int nums = (int) (Math.random() * N) + 1;
			int orderNumber = (int) (Math.random() * M) + 1;
			int types = (int) (Math.random() * N) + 1;
			int[] componets = randomArray(types, V);
			int[][] orders = randomMatrix(orderNumber, V, types);
			Arrays.sort(orders, (a, b) -> a[0] - b[0]);
			int[][] ans1 = times1(nums, componets, orders);
			int[][] ans2 = times2(nums, componets, orders);
			for (int j = 0; j < ans1.length; j++) {
				if (ans1[j][0] != ans2[j][0] || ans1[j][1] != ans2[j][1]) {
					System.out.println("出错了！");
					System.out.println(nums);
					for (int num : componets) {
						System.out.print(num + " ");
					}
					System.out.println();
					for (int[] order : orders) {
						System.out.print("(" + order[0] + "," + order[1] + ") ");
					}
					System.out.println();
					System.out.print("ans1 : ");
					for (int[] cur : ans1) {
						System.out.print("(" + cur[0] + "," + cur[1] + ") ");
					}
					System.out.println();
					System.out.print("ans2 : ");
					for (int[] cur : ans2) {
						System.out.print("(" + cur[0] + "," + cur[1] + ") ");
					}
					System.out.println();
					return;
				}
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		N = 100000;
		M = 500000;
		V = 100000;
		int nums = N;
		int[] componets = randomArray(N, V);
		int[][] orders = randomMatrix(M, V, N);
		Arrays.sort(orders, (a, b) -> a[0] - b[0]);
		System.out.println("流水线数量 : " + N);
		System.out.println("订单数量 : " + M);
		System.out.println("时间数值范围 : " + V);
		long start = System.currentTimeMillis();
		times2(nums, componets, orders);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}

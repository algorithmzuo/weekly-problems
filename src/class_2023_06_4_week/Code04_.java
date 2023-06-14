package class_2023_06_4_week;

import java.util.Arrays;
import java.util.ArrayList;

// 1号店铺贿赂问题
// 店铺数量n，编号1~n
// 人的数量m，编号1~m
// 每个人有自己投票的店铺p，和改投1号店的报价x
// 返回想让1号店铺成为人气最高的店，至少花多少钱
// 1 <= p,n,m <= 3000
// 1 <= x <= 10^9
public class Code04_ {

	// 暴力方法
	// 为了测试
	// n : 店铺数量
	// m : 人的数量
	// arr : 长度m，每个人有支持的店铺以及改投1号店的钱数
	// 如果有某个人已经支持了1号店铺，那么钱数认为无意义
	// 返回至少要花多少钱，才能让1号店铺成为人气最高的点
	public static long minCost1(int n, int m, int[][] arr) {
		// 统计每个店铺的支持人数
		int[] cnts = new int[n + 1];
		for (int[] p : arr) {
			cnts[p[0]]++;
		}
		boolean needChange = false;
		for (int i = 2; i <= n; i++) {
			if (cnts[i] >= cnts[1]) {
				needChange = true;
				break;
			}
		}
		// 如果1号店铺已经是最大人气，直接返回0
		if (!needChange) {
			return 0;
		}
		return process(arr, 0, n, new boolean[m]);
	}

	// 暴力方法
	// 为了测试
	// 暴力走两个分支：每个人要么改，要么不改
	// 如果i号人改投，那么change[i] = true;
	// 如果i号人不改投，那么change[i] = false;
	// n : 店铺数量，固定参数
	public static long process(int[][] arr, int i, int n, boolean[] change) {
		if (i == arr.length) {
			// 统计投票结果
			int[] cnts = new int[n + 1];
			long sum = 0;
			for (int j = 0; j < arr.length; j++) {
				if (change[j]) {
					// 改投的店，人气都给1号店
					cnts[1]++;
					// 并且统计改投的钱数
					sum += arr[j][1];
				} else {
					// 没有改投的店，统计词频
					cnts[arr[j][0]]++;
				}
			}
			// 看看此时1号店是不是人气最高的
			boolean ok = true;
			for (int j = 2; j <= n; j++) {
				if (cnts[j] >= cnts[1]) {
					ok = false;
					break;
				}
			}
			if (!ok) {
				// 如果1号店不是人气最高的
				// 说明当前的方案无效
				return Long.MAX_VALUE;
			} else {
				// 否则说明当前的方案有效
				// 返回这种方案的改投钱数
				return sum;
			}
		} else {
			// 可能性1，改投
			long p1 = Long.MAX_VALUE;
			if (arr[i][0] != 1) {
				// 如果当前用户不支持1号店，才存在改投的可能性
				change[i] = true;
				p1 = process(arr, i + 1, n, change);
				change[i] = false;
			}
			// 可能性1，不改投
			long p2 = process(arr, i + 1, n, change);
			return Math.min(p1, p2);
		}
	}

	public static long minCost2(int n, int m, int[][] arr) {
		// 统计每个店铺的支持人数
		int[] cnts = new int[n + 1];
		for (int[] p : arr) {
			cnts[p[0]]++;
		}
		boolean needChange = false;
		for (int i = 2; i <= n; i++) {
			if (cnts[i] >= cnts[1]) {
				needChange = true;
				break;
			}
		}
		// 如果1号店铺已经是最大人气，直接返回0
		if (!needChange) {
			return 0;
		}
		// 把所有的人，根据改投的钱数排序
		// 钱少的在前面
		// 排序之后，人也重新编号了，因为每个人的下标变了
		Arrays.sort(arr, (a, b) -> a[1] - b[1]);
		// 每个店拥有哪些人做统计
		// 比如，5号店有：13号人、16号人、23号人
		// shops.get(5) = {13, 16, 23}
		// 注意，这个编号是排序之后的编号
		// 也就是说，如果经历了排序，然后此时分组
		// 5号店有：13号人、16号人、23号人
		// 那么13号人的转投钱数 <= 16号人的 <= 23号人的
		// shops.get(5) = {13, 16, 23}
		// 每一个下标，都是排序之后的数组中，人的下标
		ArrayList<ArrayList<Integer>> shops = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			shops.add(new ArrayList<>());
		}
		for (int i = 0; i < m; i++) {
			shops.get(arr[i][0]).add(i);
		}
		// 某个用户是否已经改投1号店了
		boolean[] used = new boolean[m];
		long ans = Long.MAX_VALUE;
		for (int i = cnts[1] + 1; i <= m; i++) {
			long money = f(arr, n, cnts[1], i, shops, used);
			if (money != -1) {
				ans = Math.min(ans, money);
			}
		}
		return ans;
	}

	// arr : 所有人都在arr里，并且根据钱数排好序了
	// n : 一共有多少店
	// already : 一号店已经有的人数
	// must : 一号店最后一定要达到的人数，并且一定要以这个人数成为人气最高的店
	// shops : 每个店铺里都有哪些支持者，固定的
	// used : 某个人是否已经改投1号店了，辅助数组
	// 返回值 :
	// 如果一号店人数最后一定要达到must，并且一定可以成为人气最高的店，那么返回至少花的钱数
	// 如果上面说的做不到，返回-1
	public static long f(int[][] arr, int n, int already, int must, ArrayList<ArrayList<Integer>> shops,
			boolean[] used) {
		// 最开始时，任何人都没有转投
		Arrays.fill(used, false);
		// 总钱数
		long sum = 0;
		for (int i = 2; i <= n; i++) {
			// 从2号店开始，考察每个店铺
			// 如果当前店铺人数>=must，那么一定要减到must以下
			// needChange : 当前的店有多少人需要转投
			int needChange = Math.max(0, shops.get(i).size() - must + 1);
			for (int j = 0; j < needChange; j++) {
				// 因为arr中已经根据钱数排序
				// 所以当前店铺拥有的支持者里
				// 也一定是根据钱数从小到大排列的
				// 这些人都给1号店铺
				int people = shops.get(i).get(j);
				sum += arr[people][1];
				// 当前的人已经算用过了，改投过了
				used[people] = true;
			}
			// 改投的人，要归属给1号店铺
			already += needChange;
			if (already > must) {
				// 如果超过了must
				// 说明1号店铺就严格达到must的人数，是做不到成为人气最高的店铺的
				// 因为别的店的人数要降到must以下，转移的人都会超过must
				// 所以返回-1
				return -1;
			}
		}
		// 经过上面的过程
		// 1号店已经接受一些人了
		// 接受的人是：
		// 别的店的人数要降到must以下，所以转移过来的人
		// 但是有可能1号店此时的人数already，还是不够must个
		// 那么要凑齐must个，必须再选花钱少的人，补齐
		for (int i = 0, j = 0; already + j < must; i++) {
			// 补齐的人员，不能原本就属于1号店铺
			// 也不能是上面的过程中，转移过来的，因为已经用掉了
			// 所以两个条件都具备，才能给1号店去补
			if (arr[i][0] != 1 && !used[i]) {
				sum += arr[i][1];
				j++;
			}
		}
		// 返回最终花的钱数
		return sum;
	}

	// 为了测试
	// 生成人数为len的数据
	// 每个人支持的店铺在1~n之间随机
	// 每个人改投的钱数在1~v之间随机
	public static int[][] randomArray(int len, int n, int v) {
		int[][] arr = new int[len][2];
		for (int i = 0; i < len; i++) {
			arr[i][0] = (int) (Math.random() * n) + 1;
			arr[i][1] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int M = 16;
		int V = 100;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int m = (int) (Math.random() * M) + 1;
			int[][] arr = randomArray(m, n, V);
			long ans1 = minCost1(n, m, arr);
			long ans2 = minCost2(n, m, arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println("n : " + n);
				System.out.println("m : " + m);
				for (int[] p : arr) {
					System.out.println(p[0] + " , " + p[1]);
				}
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");

		// 这已经是题目给的最大数据量了
		// 完全可以运行通过
		int n = 3000;
		int m = 3000;
		int v = 1000000000;
		int[][] arr = randomArray(n, m, v);
		long start = System.currentTimeMillis();
		minCost2(n, m, arr);
		long end = System.currentTimeMillis();
		System.out.println("最大数据量时的运行时间 : " + (end - start) + " 毫秒");

	}

}

package class_2022_09_3_week;

// 来自美团
// 某天，小美在玩一款游戏，游戏开始时，有n台机器，
// 每台机器都有一个能量水平，分别为a1、a2、…、an，
// 小美每次操作可以选其中的一台机器，假设选的是第i台，
// 那小美可以将其变成 ai+10^k（k为正整数且0<=k<=9），
// 由于能量过高会有安全隐患，所以机器会在小美每次操作后会自动释放过高的能量
// 即变成 (ai+10^k)%m
// 其中%m表示对m取模，由于小美还有工作没有完成，所以她想请你帮她计算一下，
// 对于每台机器，将其调节至能量水平为0至少需要多少次操作
//（机器自动释放能量不计入小美的操作次数）。
// 第一行两个正整数n和m，表示数字个数和取模数值。
// 第二行为n个正整数a1, a2,...... an，其中ai表示第i台机器初始的能量水平。
// 1 <= n <= 30000，2 <= m <= 30000, 0 <= ai <= 10^12。
public class Code03_AllNumbersModToZeroMinTimes {

	public static int[] times(int n, int m, int[] arr) {
		// map[i] : i这个余数变成余数0，需要至少操作几次？
		int[] map = new int[m];
		bfs(m, map);
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			int num = arr[i];
			int minTimes = Integer.MAX_VALUE;
			if (num < m) {
				minTimes = map[num];
			} else {
				for (int j = 0; j <= 9; j++) {
					int mod = (int) (((long) num + (long) Math.pow(10, j)) % m);
					minTimes = Math.min(minTimes, map[mod] + 1);
				}
			}
			ans[i] = minTimes;
		}
		return ans;
	}

	public static void bfs(int m, int[] map) {
		boolean[] visited = new boolean[m];
		visited[0] = true;
		int[] queue = new int[m];
		int l = 0;
		int r = 1;
		// map[0] == 0
		// 表示余数0变成余数0，需要至少0次
		// 0进队列了, queue[0] = 0
		// 0算访问过了，visited[0] = true
		while (l < r) {
			// 当前弹出的余数是cur
			int cur = queue[l++];
			// 能加的数字，从1枚举到10^9
			for (int add = 1; add <= 1000000000; add *= 10) {
				// 比如，m == 7
				// 当前余数是cur，cur变成余数0，至少要a次
				// 我们想知道 : (哪个余数b + add) % m == cur
				// 比如,add=10的时候，cur==5的时候
				// 我们想知道 : (哪个余数b + 10) % 7 == 5
				// 因为10 % 7 = 3
				// 所以其实我们在求 : 哪个余数b + 3 == 5
				// 显然b = 5 - 3 = cur - (add % m) = 2
				// 再比如，add=10的时候，cur==2的时候
				// 我们想知道 : (哪个余数b + 10) % 7 == 2
				// 因为10 % 7 = 3
				// 所以其实我们在求 : 哪个余数b + 3 == 2
				// 这明显是不对的，
				// 所以其实我们在求 : 哪个余数b + 3 == 2 + m == 9
				// 也就是b，通过加了add % m，来到了m + cur，多转了一圈
				// b = 9 - 3 = cur - (add % m) + m = 6
				// 也就是说，b = cur - (add % m)，
				// 如果不小于0，那就是这个b，是我们要找的余数
				// 如果小于0，那就是b+m，是我们要找的余数
				int from = cur - (add % m);
				if (from < 0) {
					from += m;
				}
				// 这个余数我们终于找到了，因为cur变成余数0，需要a次
				// 所以这个余数变成余数0，需要a+1次
				// 当然前提是这个余数，之前宽度优先遍历的时候，没遇到过
				if (!visited[from]) {
					visited[from] = true;
					map[from] = map[cur] + 1;
					queue[r++] = from;
				}
			}
		}
	}

	public static void main(String[] args) {
		int m = 100;
		int[] map = new int[m];
		bfs(m, map);
		for (int i = 0; i < m; i++) {
			System.out.println(i + " , " + map[i]);
		}
	}

}

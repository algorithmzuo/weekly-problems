package class_2022_10_1_week;

// 来自美团
// 某天，小美在玩一款游戏，游戏开始时，有n台机器，
// 每台机器都有一个能量水平，分别为a1、a2、…、an，
// 小美每次操作可以选其中的一台机器，假设选的是第i台，
// 那小美可以将其变成 ai+10^k（k为正整数且0≤k≤9），
// 由于能量过高会有安全隐患，所以机器会在小美每次操作后会自动释放过高的能量
// 即变成 (ai+10^k)%m
// 其中%m表示对m取模，由于小美还有工作没有完成，所以她想请你帮她计算一下，
// 对于每台机器，将其调节至能量水平为0至少需要多少次操作
//（机器自动释放能量不计入小美的操作次数）。
// 第一行两个正整数n和m，表示数字个数和取模数值。
// 第二行为n个正整数a1, a2,...... an，其中ai表示第i台机器初始的能量水平。
// 1 ≤ n ≤ 30000，2 ≤ m ≤ 30000, 0 ≤ ai < m。
public class Code03_ModToZero {

	public static int times(int n, int m, int[] arr) {
		int[] map = new int[m];
		bfs(m, map);
		int ans = 0;
		for (int num : arr) {
			ans += map[num];
		}
		return ans;
	}

	public static void bfs(int m, int[] map) {
		boolean[] visited = new boolean[m];
		visited[0] = true;
		int[] queue = new int[m];
		int l = 0;
		int r = 1;
		while (l < r) {
			int cur = queue[l++];
			for (int add = 1; add <= 1000000000; add *= 10) {
				int to = (cur + add) % m;
				if (!visited[to]) {
					visited[to] = true;
					map[to] = map[cur] + 1;
					queue[r++] = to;
				}
			}
		}
	}

}

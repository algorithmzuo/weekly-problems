package class_2022_10_2_week;

import java.util.PriorityQueue;

// 来自Airbnb、Uber
// 给定一个二维网格 grid ，其中：
// '.' 代表一个空房间
// '#' 代表一堵
// '@' 是起点
// 小写字母代表钥匙
// 大写字母代表锁
// 我们从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间
// 我们不能在网格外面行走，也无法穿过一堵墙
// 如果途经一个钥匙，我们就把它捡起来。除非我们手里有对应的钥匙，否则无法通过锁。
// 假设 k 为 钥匙/锁 的个数，且满足 1 <= k <= 6，
// 字母表中的前 k 个字母在网格中都有自己对应的一个小写和一个大写字母
// 换言之，每个锁有唯一对应的钥匙，每个钥匙也有唯一对应的锁
// 另外，代表钥匙和锁的字母互为大小写并按字母顺序排列
// 返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
// 测试链接：https://leetcode.cn/problems/shortest-path-to-get-all-keys
public class Code04_ShortestPathToGetAllKeys {

	// "@....#"
	// "..b..B"
	// 
	// @ . . . . #
	// . . B . . B
	public int shortestPathAllKeys(String[] grid) {
		int n = grid.length;
		char[][] map = new char[n][];
		for (int i = 0; i < grid.length; i++) {
			map[i] = grid[i].toCharArray();
		}
		int m = map[0].length;
		return dijkstra(map, n, m);
	}

	public static int dijkstra(char[][] map, int n, int m) {
		int startX = 0;
		int startY = 0;
		int keys = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] == '@') {
					startX = i;
					startY = j;
				}
				if (map[i][j] >= 'a' && map[i][j] <= 'z') {
					keys++;
				}
			}
		}
		// 如果有4把钥匙
		// limit = 0000..00001111
		// 如果有5把钥匙
		// limit = 0000..00011111
		// 也就是说，所有钥匙都凑齐的状态，就是limit
		int limit = (1 << keys) - 1;
		// 用堆来维持走过的点(dijkstra标准操作)
		// 维持的信息是一个个小的4维数组，arr
		// arr[0] : 当前来到的x坐标
		// arr[1] : 当前来到的y坐标
		// arr[2] : 当前收集到的钥匙状态
		// arr[3] : 从出发点到当前的距离
		// 堆根据距离的从小到大组织，距离小根堆
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[3] - b[3]);
		boolean[][][] visited = new boolean[n][m][1 << keys];
		// startX, startY, 000000
		heap.add(new int[] { startX, startY, 0, 0 });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int x = cur[0];
			int y = cur[1];
			int s = cur[2];
			int w = cur[3];
			if (s == limit) {
				return w;
			}
			if (visited[x][y][s]) {
				continue;
			}
			visited[x][y][s] = true;
			add(x - 1, y, s, w, n, m, map, visited, heap);
			add(x + 1, y, s, w, n, m, map, visited, heap);
			add(x, y - 1, s, w, n, m, map, visited, heap);
			add(x, y + 1, s, w, n, m, map, visited, heap);
		}
		return -1;
	}

	// 当前是由(a,b,s) -> (x,y,状态？)
	// w ，从最开始到达(a,b,s)这个点的距离 -> w+1
	// n,m 固定参数，防止越界
	// map 地图
	// visited 访问过的点，不要再加入到堆里去！
	// heap, 堆！
	public static void add(
			int x, int y, int s,
			int w, int n, int m,
			char[][] map, boolean[][][] visited,
			PriorityQueue<int[]> heap) {
		if (x < 0 || x == n || y < 0 || y == m || map[x][y] == '#') {
			return;
		}
		if (map[x][y] >= 'A' && map[x][y] <= 'Z') { // 锁！
			// B  ->  00000010
			//            dcba
			// x,y,状 = x,y,s
			// s == 00001000
			//          dcba
			// A    s & (1 << 0) != 0
			// B    s & (1 << 1) != 0
			// D    s & (1 << 3) != 0
			// 
			if (!visited[x][y][s] && (s & (1 << (map[x][y] - 'A'))) != 0) {
				heap.add(new int[] { x, y, s, w + 1 });
			}
		} else { // 不是锁！
			// 要么是钥匙 a b c 
			// 要么是空房间 .
			// 要么是初始位置 @
			if (map[x][y] >= 'a' && map[x][y] <= 'z') {
				s |= 1 << (map[x][y] - 'a');
			}
			if (!visited[x][y][s]) {
				heap.add(new int[] { x, y, s, w + 1 });
			}
		}
	}

}

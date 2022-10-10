package class_2022_10_2_week;

// 定义一个二维数组N*M，比如5*5数组下所示：
// 0, 1, 0, 0, 0,
// 0, 1, 1, 1, 0,
// 0, 0, 0, 0, 0,
// 0, 1, 1, 1, 0,
// 0, 0, 0, 1, 0,
// 它表示一个迷宫，其中的1表示墙壁，0表示可以走的路
// 只能横着走或竖着走，不能斜着走
// 要求编程序找出从左上角到右下角距离最短的路线
// 测试链接 : https://www.nowcoder.com/practice/cf24906056f4488c9ddb132f317e03bc
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把主类名改成"Main"
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Code03_FindMinPath {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			int[][] map = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					in.nextToken();
					map[i][j] = (int) in.nval;
				}
			}
			ArrayList<int[]> ans = dijkstra(n, m, map);
			for (int i = ans.size() - 1; i >= 0; i--) {
				out.println("(" + ans.get(i)[0] + "," + ans.get(i)[1] + ")");
			}
			out.flush();
		}
	}

	public static ArrayList<int[]> dijkstra(int n, int m, int[][] map) {
		int[][][] last = new int[n][m][2];
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		boolean[][] visited = new boolean[n][m];
		heap.add(new int[] { 0, 0, 0 });
		ArrayList<int[]> ans = new ArrayList<>();
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int x = cur[0];
			int y = cur[1];
			int w = cur[2];
			if (x == n - 1 && y == m - 1) {
				break;
			}
			if (visited[x][y]) {
				continue;
			}
			visited[x][y] = true;
			add(x, y, x - 1, y, w, n, m, map, visited, heap, last);
			add(x, y, x + 1, y, w, n, m, map, visited, heap, last);
			add(x, y, x, y - 1, w, n, m, map, visited, heap, last);
			add(x, y, x, y + 1, w, n, m, map, visited, heap, last);
		}
		int x = n - 1;
		int y = m - 1;
		while (x != 0 || y != 0) {
			ans.add(new int[] { x, y });
			int lastX = last[x][y][0];
			int lastY = last[x][y][1];
			x = lastX;
			y = lastY;
		}
		ans.add(new int[] { 0, 0 });
		return ans;
	}

	public static void add(int x, int y, int i, int j, int w, int n, int m, int[][] map, boolean[][] visited,
			PriorityQueue<int[]> heap, int[][][] last) {
		if (i >= 0 && i < n && j >= 0 && j < m && map[i][j] == 0 && !visited[i][j]) {
			heap.add(new int[] { i, j, w + 1 });
			last[i][j][0] = x;
			last[i][j][1] = y;
		}
	}

}
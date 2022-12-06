package class_2022_12_3_week;

// Floyd算法解析
// 本题测试链接 : https://www.luogu.com.cn/problem/P2910
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交如下方法，把主类名改成Main，可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_ClearAndPresentDanger {

	public static int N = 100;
	public static int M = 10000;
	public static int[] path = new int[M];
	public static int[][] distance = new int[N][N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			for (int i = 0; i < m; i++) {
				in.nextToken();
				path[i] = (int) in.nval - 1;
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					in.nextToken();
					distance[i][j] = (int) in.nval;
				}
			}
			floyd(n);
			int ans = 0;
			for (int i = 1; i < m; i++) {
				ans += distance[path[i - 1]][path[i]];
			}
			out.println(ans);
			out.flush();
		}

	}

	public static void floyd(int n) {
		// O(N^3)的过程
		// 枚举每个跳板
		// 注意! 跳板要最先枚举，然后是from和to
		for (int jump = 0; jump < n; jump++) {
			for (int from = 0; from < n; from++) {
				for (int to = 0; to < n; to++) {
					if (distance[from][jump] != Integer.MAX_VALUE && distance[jump][to] != Integer.MAX_VALUE
							&& distance[from][to] > distance[from][jump] + distance[jump][to]) {
						distance[from][to] = distance[from][jump] + distance[jump][to];
					}
				}
			}
		}
		// 如下这么写是错的
// 		for (int from = 0; from < n; from++) {
// 			for (int to = 0; to < n; to++) {
// 				for (int jump = 0; jump < n; jump++) {
// 					if (distance[from][jump] != Integer.MAX_VALUE && distance[jump][to] != Integer.MAX_VALUE
// 							&& distance[from][to] > distance[from][jump] + distance[jump][to]) {
// 						distance[from][to] = distance[from][jump] + distance[jump][to];
// 					}
// 				}
// 			}
// 		}
	}

}

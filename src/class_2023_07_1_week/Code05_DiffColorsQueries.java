package class_2023_07_1_week;

// 测试链接 : https://www.luogu.com.cn/problem/U41492

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;

// 解法思路
// 用cnt[i]表示颜色i的出现次数，ans[cur]表示结点cur的答案
// 遍历一个节点cur，我们按以下的步骤进行遍历 :
// 1. 先遍历cur的轻儿子，并计算树上每个节点的答案，但不保留遍历后对cnt数组的影响
// 2. 再遍历cur的重儿子，保留对cnt数组的影响
// 3. 再遍历cur的轻儿子，加入这些结点的贡献，以得到cur的答案
public class Code05_DiffColorsQueries {

	// 准备空间的参数
	public static int MAXN = 200005;
	// 建图相关
	public static ArrayList<ArrayList<Integer>> graph;
	static {
		graph = new ArrayList<>();
		for (int i = 0; i <= MAXN; i++) {
			graph.add(new ArrayList<>());
		}
	}
	public static int[] color = new int[MAXN];

	// 树链剖分相关
	public static int[] size = new int[MAXN];
	public static int[] heavy = new int[MAXN];
	public static int[] cnt = new int[MAXN];
	public static int[] ans = new int[MAXN];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				graph.get(i).clear();
			}
			for (int i = 1; i < n; i++) {
				in.nextToken();
				int a = (int) in.nval;
				in.nextToken();
				int b = (int) in.nval;
				graph.get(a).add(b);
				graph.get(b).add(a);
			}
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				color[i] = (int) in.nval;
			}
			dfs1(1, 0);
			dfs2(1, 0, false);
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1; i <= m; i++) {
				in.nextToken();
				int q = (int) in.nval;
				out.println(ans[q]);
			}
			out.flush();
		}
	}

	public static void dfs1(int cur, int father) {
		size[cur] = 1;
		for (int next : graph.get(cur)) {
			if (next != father) {
				dfs1(next, cur);
				size[cur] += size[next];
				if (size[heavy[cur]] < size[next]) {
					heavy[cur] = next;
				}
			}
		}
	}

	public static int total = 0;

	public static void dfs2(int cur, int father, boolean isHeavy) {
		for (int next : graph.get(cur)) {
			if (next != father && next != heavy[cur]) {
				dfs2(next, cur, false);
			}
		}
		if (heavy[cur] != 0) {
			dfs2(heavy[cur], cur, true);
		}
		add(cur, father, heavy[cur]);
		ans[cur] = total;
		if (!isHeavy) {
			delete(cur, father);
			total = 0;
		}
	}

	public static void add(int cur, int father, int except) {
		if (++cnt[color[cur]] == 1) {
			total++;
		}
		for (int next : graph.get(cur)) {
			if (next != father && next != except) {
				add(next, cur, except);
			}
		}
	}

	public static void delete(int cur, int father) {
		cnt[color[cur]]--;
		for (int next : graph.get(cur)) {
			if (next != father) {
				delete(next, cur);
			}
		}
	}

}
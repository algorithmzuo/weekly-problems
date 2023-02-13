package class_2023_01_2_week;

// 一条单向的铁路线上，火车站编号为1~n
// 每个火车站都有一个级别，最低为 1 级。
// 现有若干趟车次在这条线路上行驶，
// 每一趟都满足如下要求：
// 如果这趟车次停靠了火车站 x，则始发站、终点站之间所有级别大于等于火车站x的都必须停靠。
//（注意：起始站和终点站自然也算作事先已知需要停靠的站点）
// 现有 m 趟车次的运行情况（全部满足要求），
// 试推算这n个火车站至少分为几个不同的级别。
// 测试链接 : https://www.luogu.com.cn/problem/P1983
// 线段树建边
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_BusStationsMinLevelNumbers {

	public static final int maxn = 100001;
	// 1 500 600 1000
	// stops[1,500,600,1000]
	// 停靠车站
	public static int[] stops = new int[maxn];
	// 一段线段树范围的id编号
	// id[rt] = x，rt背后的范围这一段，给它的点编号是x
	// rt -> 线段树的某个范围的固有属性，l~r,rt
	public static int[] id = new int[maxn << 2];
	
	
	
	// id点是否为单点
	// a 单点 范围 虚拟点？
	// 70~90 rt = 60 -> 17 single[17] ? 
	public static boolean[] single = new boolean[maxn << 3];
	
	
	// id点的入度
	public static int[] inDegree = new int[maxn << 3];
	// id点拓扑排序统计的最大深度(只算路径上的单点数量)
	public static int[] singleDeep = new int[maxn << 3];
	// 链式前向星建图用
	public static int[] head = new int[maxn << 3];
	public static int[] to = new int[maxn << 3];
	public static int[] next = new int[maxn << 3];
	// 拓扑排序用
	public static int[] queue = new int[maxn << 3];
	// n为车站个数、nth为线段树上范围的编号计数、eth为边的计数
	public static int n, nth, eth;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			nth = 0;
			eth = 0;
			Arrays.fill(single, 0, (n << 2) + m + 1, false);
			Arrays.fill(inDegree, 0, (n << 2) + m + 1, 0);
			Arrays.fill(singleDeep, 0, (n << 2) + m + 1, 0);
			build(1, n, 1);
			for (int i = 0; i < m; i++) {
				in.nextToken();
				int k = (int) in.nval;
				for (int j = 0; j < k; j++) {
					in.nextToken();
					stops[j] = (int) in.nval;
				}
				int curVirtual = ++nth;
				// 虚点向停靠车站连边
				for (int j = 0; j < k; j++) {
					vLinkStop(curVirtual, stops[j], 1, n, 1);
				}
				// 不停靠的连续车站向虚点连边
				for (int j = 1; j < k; j++) {
					if (stops[j] > stops[j - 1] + 1) {
						rangeLinkV(stops[j - 1] + 1, stops[j] - 1, curVirtual, 1, n, 1);
					}
				}
			}
			out.println(topoSort());
			out.flush();
		}

	}

	public static void build(int l, int r, int rt) {
		id[rt] = ++nth;
		if (l == r) {
			single[id[rt]] = true;
		} else {
			int m = (l + r) / 2;
			build(l, m, rt << 1);
			build(m + 1, r, rt << 1 | 1);
			addEdge(id[rt << 1], id[rt]);
			addEdge(id[rt << 1 | 1], id[rt]);
		}
	}

	public static void rangeLinkV(int L, int R, int vid, int l, int r, int rt) {
		if (L <= l && r <= R) {
			addEdge(id[rt], vid);
		} else {
			int m = (l + r) / 2;
			if (L <= m) {
				rangeLinkV(L, R, vid, l, m, rt << 1);
			}
			if (R > m) {
				rangeLinkV(L, R, vid, m + 1, r, rt << 1 | 1);
			}
		}
	}

	// 17 17~17
	public static void vLinkStop(int vid, int stop, int l, int r, int rt) {
		if (l == r) {
			addEdge(vid, id[rt]);
		} else {
			int m = (l + r) / 2;
			// 1~100
			// 想去的车站是70 70~70
			// 1~50 51~100
			if (stop <= m) {
				vLinkStop(vid, stop, l, m, rt << 1);
			} else {
				vLinkStop(vid, stop, m + 1, r, rt << 1 | 1);
			}
		}
	}

	public static void addEdge(int fid, int tid) {
		inDegree[tid]++;
		to[++eth] = tid;
		next[eth] = head[fid];
		head[fid] = eth;
	}

	public static int topoSort() {
		int l = 0;
		int r = 0;
		for (int i = 1; i <= nth; i++) {
			if (inDegree[i] == 0) {
				queue[r++] = i;
				if (single[i]) {
					singleDeep[i] = 1;
				}
			}
		}
		int ans = 0;
		while (l < r) {
			int curNode = queue[l++];
			ans = Math.max(ans, singleDeep[curNode]);
			for (int edgeIndex = head[curNode]; edgeIndex != 0; edgeIndex = next[edgeIndex]) {
				int child = to[edgeIndex];
				singleDeep[child] = Math.max(singleDeep[child], singleDeep[curNode] + (single[child] ? 1 : 0));
				if (--inDegree[child] == 0) {
					queue[r++] = child;
				}
			}
		}
		return ans;
	}

}
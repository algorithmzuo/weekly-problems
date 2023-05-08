package class_2023_05_4_week;

import java.util.ArrayList;

// 来自招商银行
// 原始题目描述
// 假如某公司目前推出了N个在售的金融产品(1<=N<=100)
// 对于张三，用ai表示他购买了ai(0<=ai<=10^4)份额的第i个产品(1<=i<=N)
// 现给出K(1<=K<=N)个方案，通过这些方案，能够支持将多个不同的产品进行整合
// (也可以对单个产品进行优化)形成新的产品。
// 新的产品形成后，若用户持有了组成新产品所需的全部的原产品份额，
// 则能够将用户持有的原产品份额转换为新产品的份额，各原产品份额与新产品份额比例均为1:1
// 我们保证对于每个产品最多存在一个方案使用旧产品整合成该产品
// 并且根据方案产出的新产品的产品编号均大于各旧产品的产品编号
// 现计划根据这些方案，帮助部分愿意升级到最新产品的用户对产品进行升级
// 请协助工作人员计算当前用户能够转换出的最新产品份额的最大值
// 输入描述
// 第一行包含整数N
// 第二行包含N个整数ai
// 第三行包含整数K
// 接下来的K行，每一行代表一个方案，每一行包含整数1和M(M>=1)
// L为该方案产生的新产品的编号，M代表方案所需原产品个数
// 接下来的M个整数代表了该方案所需的每个原产品的个数
// 输出描述
// 根据日前的份额和给出的方案，经过若干次转换，输出当前用户能够得到产品N的份额最大值
// 举例
// 输入:
// 5
// 2 0 0 1 0
// 3
// 5 2 3 4
// 2 1 1
// 3 1 2
// 输出:
// 1
// 解释:
// 第一步将1份1产品转化为1份2产品
// 第二步将1份2产品转化为1份3产品
// 第三步将1份3产品和1份4产品，转成为1份5产品
// 然后不能得到更多的5产品了，所以返回1
// 实在是太困惑了，上文说的意思可谓不做人，那么我们改写一下意思，变得好理解
// 如下是改写后的题目描述
// 给定一个数组arr，长度为n，产品编号从0~n-1
// arr[i]代表初始时i号产品有多少份
// 存在一系列的产品转化方案的数组convert，长度为k
// 比如，convert[j] = {a, b, c, d, ....}
// 表示j号方案转化出来的产品是a，转化1份a需要：1份b、1份c、1份d....
// 其中a、b、c、d一定都在0~n-1范围内
// 并且a > Math.max(b, c, d, ....)
// 而且可以保证所有方案转化出来的产品一定不一样
// 请返回最终能得到的第n-1号商品的最大值
// 1 <= n <= 100
// 0 <= arr[i] <= 10^4
// k <= n
public class Code03_ConversionOfFinancialProducts {

	public static int maxValue(int[] arr, int[][] convert) {
		int n = arr.length;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] relation : convert) {
			for (int i = 1; i < relation.length; i++) {
				graph.get(relation[0]).add(relation[i]);
			}
		}
		int l = arr[n - 1] + 1;
		int r = 0;
		for (int num : arr) {
			r += num;
		}
		int m = 0, ans = arr[n - 1];
		while (l <= r) {
			m = (l + r) / 2;
			if (ok(arr, graph, m)) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static boolean ok(int[] arr, ArrayList<ArrayList<Integer>> graph, int m) {
		int n = arr.length;
		int[] indegree = new int[n];
		for (ArrayList<Integer> nexts : graph) {
			for (int next : nexts) {
				indegree[next]++;
			}
		}
		int[] zeroQueue = new int[n];
		int l = 0;
		int r = 0;
		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				zeroQueue[r++] = i;
			}
		}
		int[] need = new int[n];
		need[n - 1] = m;
		while (l < r) {
			int cur = zeroQueue[l++];
			int supplement = Math.max(need[cur] - arr[cur], 0);
			if (graph.get(cur).isEmpty() && supplement > 0) {
				return false;
			}
			for (int next : graph.get(cur)) {
				need[next] += supplement;
				if (--indegree[next] == 0) {
					zeroQueue[r++] = next;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] arr1 = { 2, 0, 0, 1, 0 };
		int[][] convert1 = { { 4, 2, 3 }, { 1, 0 }, { 2, 1 } };
		System.out.println(maxValue(arr1, convert1));

		// 我构造了一个非常好的例子
		// 课上说明一下
		int[] arr2 = { 100, 5, 5, 10 };
		int[][] convert2 = { { 1, 0 }, { 2, 0, 1 }, { 3, 0, 1, 2 } };
		System.out.println(maxValue(arr2, convert2));
	}

}

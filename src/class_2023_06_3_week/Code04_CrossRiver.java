package class_2023_06_3_week;

// 来自华为OD
// 一支n个士兵的军队正在趁夜色逃亡，途中遇到一条湍急的大河
// 敌军在T的时长后到达河面，没到过对岸的士兵都会被消灭
// 现在军队只找到了1只小船，这船最多能同时坐上2个士兵。
// 1) 当1个士兵划船过河，用时为a[i]
// 2) 当2个士兵坐船同时划船过河时, 用时为max(a[j],a[i])两士兵中用时最长的
// 3) 当2个士兵坐船只有1个士兵划船时, 用时为a[i] * 10, a[i]为划船士兵用时
// 请帮忙给出一种解决方案，保证存活的士兵最多，且过河用时最短
// 我们先看一下如下的题，再讲一下华为OD的扩展
// 测试链接 : https://www.luogu.com.cn/problem/P1809
// 有一个大晴天, Oliver与同学们一共N人出游, 他们走到一条河的东岸边，想要过河到西岸
// 而东岸边有一条小船。船太小了，一次只能乘坐两人，每个人都有一个渡河时间T
// 船划到对岸的时间等于船上渡河时间较长的人所用时间
// 现在已知N个人的渡河时间Ti
// Oliver 想要你告诉他，他们最少要花费多少时间，才能使所有人都过河
// 注意，只有船在东岸（西岸）的人才能坐上船划到对岸。

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_CrossRiver {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] dp = new int[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			int ans = minCost();
			out.println(ans);
			out.flush();
		}

	}

	public static int minCost() {
		Arrays.sort(arr, 0, n);
		if (n >= 1) {
			dp[0] = arr[0];
		}
		if (n >= 2) {
			dp[1] = arr[1];
		}
		if (n >= 3) {
			dp[2] = arr[0] + arr[1] + arr[2];
		}
		for (int i = 3; i < n; i++) {
			dp[i] = Math.min(
					dp[i - 2] + arr[1] + arr[0] + arr[i] + arr[1],
					dp[i - 1] + arr[i] + arr[0]);
		}
		return dp[n - 1];
	}

}

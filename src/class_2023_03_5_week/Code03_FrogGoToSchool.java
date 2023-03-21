package class_2023_03_5_week;

// 来自学员问题，蓝桥杯练习题
// 小青蛙住在一条河边, 它想到河对岸的学校去学习
// 小青蛙打算经过河里 的石头跳到对岸
// 河里的石头排成了一条直线, 小青蛙每次跳跃必须落在一块石头或者岸上
// 给定一个长度为n的数组arr，表示每块儿石头的高度数值
// 每块石头有一个高度, 每次小青蛙从一块石头起跳
// 这块石头的高度就会下降1, 当石头的高度下降到0时
// 小青蛙不能再跳到这块石头上(跳跃后使石头高度下降到0是允许的)
// 小青蛙一共需要去学校上x天课, 所以它需要往返x次(去x次，回x次)
// 当小青蛙具有 一个跳跃能力y时, 它能跳不超过y的距离
// 请问小青蛙的跳跃能力至少是多少才能用这些石头上完x次课
// 1 <= n <= 10^5
// 1 <= arr[i] <= 10^4
// 1 <= x <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P8775
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的所有代码，并把主类名改成"Main"
// 可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_FrogGoToSchool {

	public static int MAXN = 100001;

	public static long[] help = new long[MAXN];

	public static int n, x;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			x = (int) (in.nval);
			for (int i = 1; i < n; i++) {
				in.nextToken();
				help[i] = help[i - 1] + (int) in.nval;
			}
			out.println(minAbility());
			out.flush();
		}
	}

	// O(N)的最优解
	public static int minAbility() {
		int ans = 0;
		for (int l = 1, r = 1; l < n; l++) {
			while (r < n && help[r] - help[l - 1] < 2L * x) {
				r++;
			}
			ans = Math.max(ans, r - l + 1);
		}
		return ans;
	}

}
package class_2023_04_2_week;

// 来自学员问题，蓝桥杯练习题
// 给定一个长度为n的数组arr
// 现在你有一次机会, 将其中连续的K个数全修改成任意一个值
// 请你计算如何修改可以使修改后的数 列的最长不下降子序列最长
// 请输出这个最长的长度。
// 最长不下降子序列:子序列中的每个数不小于在它之前的数
// 1 <= k, n <= 10^5
// 1 <= arr[i] <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P8776
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

public class Code04_LongestNoDecreaseModifyKSubarray {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] ends = new int[MAXN];

	public static int n, k;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			k = (int) (in.nval);
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			if (k >= n) {
				out.println(n);
			} else {
				right();
				out.println(getAns());
			}
			out.flush();
		}
	}

	public static void right() {
		right[n] = 1;
		ends[1] = arr[n];
		int len = 1;
		for (int i = n - 1; i > 0; i--) {
			int l = 1;
			int r = len;
			int m, find = len + 1;
			while (l <= r) {
				m = (l + r) / 2;
				if (ends[m] < arr[i]) {
					find = m;
					r = m - 1;
				} else {
					l = m + 1;
				}
			}
			ends[find] = arr[i];
			len = Math.max(len, find);
			right[i] = find;
		}
	}

	public static int getAns() {
		int ans = 0;
		int len = 0;
		for (int i = k + 1, j = 1; i <= n; i++, j++) {
			int l = 1;
			int r = len;
			int m, find = len + 1;
			while (l <= r) {
				m = (l + r) / 2;
				if (ends[m] > arr[i]) {
					find = m;
					r = m - 1;
				} else {
					l = m + 1;
				}
			}
			ans = Math.max(ans, find + right[i] - 1 + k);
			l = 1;
			r = len;
			find = len + 1;
			while (l <= r) {
				m = (l + r) / 2;
				if (ends[m] > arr[j]) {
					find = m;
					r = m - 1;
				} else {
					l = m + 1;
				}
			}
			len = Math.max(len, find);
			ends[find] = arr[j];
		}
		ans = Math.max(ans, len + k);
		return ans;
	}

}
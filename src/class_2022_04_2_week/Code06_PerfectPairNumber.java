package class_2022_04_2_week;

// 来自阿里
// x = { a, b, c, d }
// y = { e, f, g, h }
// x、y两个小数组长度都是4
// 如果有: a + e = b + f = c + g = d + h
// 那么说x和y是一个完美对
// 题目给定N个小数组，每个小数组长度都是K
// 返回这N个小数组中，有多少完美对
// 本题测试链接 : https://www.nowcoder.com/practice/f5a3b5ab02ed4202a8b54dfb76ad035e
// 提交以下所有代码，把主类名改成Main
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.HashMap;

public class Code06_PerfectPairNumber {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			int[][] matrix = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					in.nextToken();
					matrix[i][j] = (int)in.nval;
				}
			}
			long ans = perfectPairs(matrix);
			out.println(ans);
			out.flush();
		}
	}

	public static long perfectPairs(int[][] matrix) {
		long ans = 0;
		// key : 字符串 特征，差值特征 : "_5_-2_6_9"
		HashMap<String, Integer> counts = new HashMap<>();
		for (int[] arr : matrix) {
			StringBuilder self = new StringBuilder();
			StringBuilder minus = new StringBuilder();
			for (int i = 1; i < arr.length; i++) {
				self.append("_" + (arr[i] - arr[i - 1]));
				minus.append("_" + (arr[i - 1] - arr[i]));
			}
			ans += counts.getOrDefault(minus.toString(), 0);
			counts.put(self.toString(), counts.getOrDefault(self.toString(), 0) + 1);
		}
		return ans;
	}

}
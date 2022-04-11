package class_2022_04_3_week;

// 来自阿里
// 本题测试链接 : https://www.nowcoder.com/practice/f5a3b5ab02ed4202a8b54dfb76ad035e
// 提交如下代码，把主类名改成Main
// 可以直接通过
import java.util.HashMap;
import java.util.Scanner;

public class Code04_PerfectPairNumber {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			int[][] matrix = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					matrix[i][j] = sc.nextInt();
				}
			}
			long ans = perfectPairs(matrix);
			System.out.println(ans);
		}
		sc.close();
	}

	public static long perfectPairs(int[][] matrix) {
		long ans = 0;
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
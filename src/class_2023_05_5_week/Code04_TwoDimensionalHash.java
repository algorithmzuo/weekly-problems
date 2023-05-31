package class_2023_05_5_week;

// 正方形矩阵哈希
// 二维哈希只适用于正方形的情况
// 如果想支持普通矩阵，需要更复杂度的过程，这里不做展开
public class Code04_TwoDimensionalHash {

	public static int MAXN = 1001;

	public static long[] powr = new long[MAXN];

	public static long[] powc = new long[MAXN];

	public static long[][] sum = new long[MAXN][MAXN];

	public static int baser = 491;

	public static int basec = 499;

	public static void buildHash(int[][] arr) {
		int n = arr.length - 1;
		int m = arr[0].length - 1;
		powr[0] = 1;
		powc[0] = 1;
		for (int i = 1; i <= n; i++) {
			powr[i] = (powr[i - 1] * baser);
		}
		for (int i = 1; i <= m; i++) {
			powc[i] = (powc[i - 1] * basec);
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				sum[i][j] = sum[i][j - 1] * basec + arr[i][j];
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				sum[i][j] += sum[i - 1][j] * baser;
			}
		}
	}

	public static int[][] randomArray(int n, int m) {
		int[][] arr = new int[n + 1][m + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				arr[i][j] = Math.random() < 0.5 ? 5 : 6;
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		int n = 100;
		int m = 100;
		int[][] arr = randomArray(n, m);
		buildHash(arr);
		int testTimes = 50000;
		int len = 5;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int a = (int) (Math.random() * 90) + 1;
			int b = (int) (Math.random() * 90) + 1;
			int c = (int) (Math.random() * 90) + 1;
			int d = (int) (Math.random() * 90) + 1;
			int sizer = (int) (Math.random() * len) + 1;
			int sizec = sizer;
//			sizec = (int) (Math.random() * len) + 1;
			boolean ans1 = rightCheck(arr, a, b, c, d, sizer, sizec);
			boolean ans2 = hashCheck(a, b, c, d, sizer, sizec);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

	public static long hash(int a, int b, int c, int d) {
		return sum[c][d] - sum[a - 1][d] * powr[c - a + 1] - sum[c][b - 1] * powc[d - b + 1]
				+ sum[a - 1][b - 1] * powr[d - b + 1] * powc[c - a + 1];
	}

	public static boolean hashCheck(int a, int b, int c, int d, int lenr, int lenc) {
		return hash(a, b, a + lenr - 1, b + lenc - 1) == hash(c, d, c + lenr - 1, d + lenc - 1);
	}

	public static boolean rightCheck(int[][] arr, int a, int b, int c, int d, int lenr, int lenc) {
		for (int i = a, j = c; i < a + lenr; i++, j++) {
			for (int p = b, q = d; p < b + lenc; p++, q++) {
				if (arr[i][p] != arr[j][q]) {
					return false;
				}
			}
		}
		return true;
	}

}
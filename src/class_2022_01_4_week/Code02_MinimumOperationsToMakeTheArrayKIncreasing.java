package class_2022_01_4_week;

// 来自Leetcode周赛第5题
// 测试链接 : https://leetcode.com/problems/minimum-operations-to-make-the-array-k-increasing/
public class Code02_MinimumOperationsToMakeTheArrayKIncreasing {

	public static int kIncreasing(int[] arr, int k) {
		int n = arr.length;
		int[] help = new int[(n + k - 1) / k];
		int ans = 0;
		for (int i = 0; i < k; i++) {
			ans += need(arr, help, n, i, k);
		}
		return ans;
	}

	public static int need(int[] arr, int[] help, int n, int start, int k) {
		int j = 0;
		int size = 0;
		for (; start < n; start += k, j++) {
			size = insert(help, size, arr[start]);
		}
		return j - size;
	}

	public static int insert(int[] help, int size, int num) {
		int l = 0;
		int r = size - 1;
		int m = 0;
		int ans = size;
		while (l <= r) {
			m = (l + r) / 2;
			if (help[m] > num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		help[ans] = num;
		return ans == size ? size + 1 : size;
	}

}

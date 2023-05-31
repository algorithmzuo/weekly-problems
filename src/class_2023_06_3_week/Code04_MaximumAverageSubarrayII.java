package class_2023_06_3_week;

// 测试链接 : https://leetcode.com/problems/maximum-average-subarray-ii/
public class Code04_MaximumAverageSubarrayII {

	// 时间复杂度O(N * log(MaxValue))
	public double findMaxAverage1(int[] nums, int k) {
		int n = nums.length;
		if (n == 1) {
			return (double) nums[0];
		}
		double L = 0;
		double R = 0;
		double[] arr = new double[n];
		for (int i = 0; i < n; i++) {
			arr[i] = nums[i];
			L = Math.min(L, arr[i]);
			R = Math.max(R, arr[i]);
		}
		double E = 0.00001;
		double M = 0;
		double ans = 0;
		while (L + E <= R) {
			M = (L + R) / 2;
			if (f(arr, M) >= k) {
				ans = M;
				L = M;
			} else {
				R = M;
			}
		}
		return ans;
	}

	// 返回arr中平均值大于等于v的最长子数组长度
	// 体系学习班, 40节，第4题
	public static int f(double[] arr, double v) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] -= v;
		}
		int n = arr.length;
		double[] sums = new double[n];
		int[] ends = new int[n];
		sums[n - 1] = arr[n - 1];
		ends[n - 1] = n - 1;
		for (int i = n - 2; i >= 0; i--) {
			if (sums[i + 1] >= 0) {
				sums[i] = arr[i] + sums[i + 1];
				ends[i] = ends[i + 1];
			} else {
				sums[i] = arr[i];
				ends[i] = i;
			}
		}
		int end = 0;
		double sum = 0;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			while (end < n && sum + sums[end] >= 0) {
				sum += sums[end];
				end = ends[end] + 1;
			}
			ans = Math.max(ans, end - i);
			if (end > i) {
				sum -= arr[i];
			} else {
				end = i + 1;
			}
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] += v;
		}
		return ans;
	}

	// 时间复杂度O(N)
	// 论文级别的，比较难，不做要求了
	public static double findMaxAverage(int[] nums, int k) {
		int n = nums.length;
		if (n == 1 && k == 1) {
			return (double) nums[0];
		}
		int[] sum = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			sum[i] = sum[i - 1] + nums[i - 1];
		}
		int[] queue = new int[n + 1];
		int head = 0;
		int tail = 0;
		double ans = 0;
		for (int i = k; i <= n; ++i) {
			while (tail - head >= 2 && density(sum, i - k, queue[tail - 1]) <= density(sum, i - k, queue[tail - 2])) {
				tail--;
			}
			queue[tail++] = i - k;
			while (tail - head >= 2 && density(sum, i, queue[head]) <= density(sum, i, queue[head + 1])) {
				head++;
			}
			ans = Math.max(ans, density(sum, i, queue[head]));
		}
		return ans;
	}

	public static double density(int[] sum, int right, int left) {
		return (double) (sum[right] - sum[left]) / (right - left);
	}

}

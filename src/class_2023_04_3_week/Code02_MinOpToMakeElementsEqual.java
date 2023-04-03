package class_2023_04_3_week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 测试链接 : https://leetcode.cn/problems/minimum-operations-to-make-all-array-elements-equal/
public class Code02_MinOpToMakeElementsEqual {

	public static List<Long> minOperations(int[] nums, int[] queries) {
		int n = nums.length;
		Arrays.sort(nums);
		long[] sum = new long[n + 1];
		for (int i = 0; i < n; i++) {
			sum[i + 1] = sum[i] + nums[i];
		}
		ArrayList<Long> ans = new ArrayList<>();
		int less, more;
		long curAns;
		for (int v : queries) {
			less = bs(nums, v);
			curAns = (long) (less + 1) * v - sum(sum, 0, less);
			more = bs(nums, v + 1);
			curAns += sum(sum, more + 1, n - 1) - (long) (n - more - 1) * v;
			ans.add(curAns);
		}
		return ans;
	}

	// 查找 <v 最右的位置
	// 没有返回-1
	public static int bs(int[] nums, int v) {
		int l = 0;
		int r = nums.length - 1;
		int m, ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (nums[m] < v) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static long sum(long[] sum, int l, int r) {
		return l > r ? 0 : (sum[r + 1] - sum[l]);
	}

}

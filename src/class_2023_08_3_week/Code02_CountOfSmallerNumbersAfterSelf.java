package class_2023_08_3_week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 可以用归并分治，但这里用树状数组实现
// 测试链接 : https://leetcode.cn/problems/count-of-smaller-numbers-after-self/
public class Code02_CountOfSmallerNumbersAfterSelf {

	public static int MAXV = 10001;
	public static int MAXN = 100001;
	public static int[] tree = new int[MAXV << 1];
	public static int[] cnts = new int[MAXN];
	public static int n, m;

	public static void build() {
		Arrays.fill(tree, 1, m + 1, 0);
		Arrays.fill(cnts, 0, n, 0);
	}

	public static void add(int x) {
		while (x <= m) {
			tree[x]++;
			x += x & (-x);
		}
	}

	public static void set(int i, int x) {
		int ans = 0;
		while (x > 0) {
			ans += tree[x];
			x -= x & (-x);
		}
		cnts[i] = ans;
	}

	public List<Integer> countSmaller(int[] nums) {
		n = nums.length;
		int min = nums[0];
		int max = nums[0];
		for (int i = 1; i < n; i++) {
			min = Math.min(min, nums[i]);
			max = Math.max(max, nums[i]);
		}
		m = max - min + 1;
		build();
		for (int i = n - 1, num; i >= 0; i--) {
			num = nums[i] - min + 1;
			add(num);
			set(i, num - 1);
		}
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ans.add(cnts[i]);
		}
		return ans;
	}

}

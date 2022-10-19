package class_2022_10_3_week;

// 给定一个整数数组 A，坡是元组 (i, j)，其中  i < j 且 A[i] <= A[j]
// 这样的坡的宽度为 j - i
// 找出 A 中的坡的最大宽度，如果不存在，返回 0
// 示例 1：
// 输入：[6,0,8,2,1,5]
// 输出：4
// 解释：
// 最大宽度的坡为 (i, j) = (1, 5): A[1] = 0 且 A[5] = 5
// 示例 2：
// 输入：[9,8,1,0,1,9,4,0,4,1]
// 输出：7
// 解释：
// 最大宽度的坡为 (i, j) = (2, 9): A[2] = 1 且 A[9] = 1
// 测试链接 : https://leetcode.cn/problems/maximum-width-ramp/
public class Code01_MaximumWidthRamp {

	public static int maxWidthRamp(int[] arr) {
		int n = arr.length;
		int[] stack = new int[n];
		int r = 0;
		for (int i = 0; i < n; i++) {
			if (r == 0 || arr[stack[r - 1]] > arr[i]) {
				stack[r++] = i;
			}
		}
		int ans = 0;
		for (int j = n - 1; j >= 0; j--) {
			while (r != 0 && arr[stack[r - 1]] <= arr[j]) {
				int i = stack[--r];
				ans = Math.max(ans, j - i);
			}
		}
		return ans;
	}

}

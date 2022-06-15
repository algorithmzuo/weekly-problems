package class_2022_06_4_week;

// 测试链接 : https://leetcode.com/problems/max-chunks-to-make-sorted-ii/
public class Code01_MaxChunksToMakeSortedII {

	public int maxChunksToSorted(int[] arr) {
		int n = arr.length;
		int[] mins = new int[n];
		mins[n - 1] = arr[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			mins[i] = Math.min(arr[i], mins[i + 1]);
		}
		int ans = 1;
		int max = arr[0];
		for (int i = 1; i < n; i++) {
			if (max <= mins[i]) {
				ans++;
			}
			max = Math.max(max, arr[i]);
		}
		return ans;
	}

}

package class_2023_03_3_week;

// 测试链接 : https://leetcode.cn/problems/divide-array-into-increasing-sequences/
public class Code01_DivideArrayIntoIncreasingSequences {

	public static boolean canDivideIntoSubsequences(int[] nums, int k) {
		int cnt = 1;
		int maxCnt = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i - 1] != nums[i]) {
				maxCnt = Math.max(maxCnt, cnt);
				cnt = 1;
			} else {
				cnt++;
			}
		}
		maxCnt = Math.max(maxCnt, cnt);
		return nums.length / maxCnt >= k;
	}

}

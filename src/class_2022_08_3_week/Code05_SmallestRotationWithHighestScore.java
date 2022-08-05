package class_2022_08_3_week;

// 测试链接 : https://leetcode.cn/problems/smallest-rotation-with-highest-score/
public class Code05_SmallestRotationWithHighestScore {

	public static int bestRotation(int[] nums) {
		int n = nums.length;
		int[] cnt = new int[n + 1];
		for (int i = 0; i < n; i++) {
			if (nums[i] < n) {
				if (i <= nums[i]) {
					add(cnt, nums[i] - i, n - i - 1);
				} else {
					add(cnt, 0, n - i - 1);
					add(cnt, n - i + nums[i], n - 1);
				}

			}
		}
		for (int i = 1; i <= n; i++) {
			cnt[i] += cnt[i - 1];
		}
		int max = cnt[0];
		int ans = 0;
		for (int i = n - 1; i >= 1; i--) {
			if (cnt[i] > max) {
				max = cnt[i];
				ans = i;
			}
		}
		return ans == 0 ? 0 : (n - ans);
	}

	public static void add(int[] cnt, int l, int r) {
		cnt[l]++;
		cnt[r + 1]--;
	}

}

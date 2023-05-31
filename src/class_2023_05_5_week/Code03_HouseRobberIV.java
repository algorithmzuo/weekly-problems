package class_2023_05_5_week;

// 测试链接 : https://leetcode.cn/problems/house-robber-iv/
public class Code03_HouseRobberIV {

	public static int minCapability(int[] nums, int k) {
		int l = 1;
		int r = 0;
		for (int num : nums) {
			r = Math.max(num, r);
		}
		int m, ans = 0;
		// 二分答案法
		while (l <= r) {
			// m是当前盗贼的能力
			// 很明显盗贼能力越大，能盗窃房屋数量的最大值只可能不变、或者变多，不可能变少
			m = (l + r) / 2;
			if (robber(nums, m) >= k) {
				// 如果盗贼当前的能力下，盗窃房屋数量的最大值超过了k
				// 说明这个能力达标，但是希望看看左侧范围上，还有没有依然能达标的能力
				// 所以，记录答案，去左侧二分
				ans = m;
				r = m - 1;
			} else {
				// 如果盗贼当前的能力下，盗窃房屋数量的最大值小于k
				// 说明这个能力不达标，只能去右侧范围上看看有没有达标的能力
				// 所以，不记录答案，去右侧二分
				l = m + 1;
			}
		}
		return ans;
	}

	// 盗贼能力为ability时，返回盗贼最多能窃取多少间房屋
	// 注意不能窃取相邻房屋
	public static int robber(int[] nums, int ability) {
		// lastLast表示0...0范围上，盗贼最多能窃取多少间房屋
		int lastLast = nums[0] <= ability ? 1 : 0;
		int n = nums.length;
		if (n == 1) {
			return lastLast;
		}
		// last表示0...1范围上，盗贼最多能窃取多少间房屋
		int last = (nums[0] <= ability || nums[1] <= ability) ? 1 : 0;
		int ans = Math.max(lastLast, last);
		for (int i = 2; i < n; i++) {
			// 可能性1 : 就是不盗窃i号房屋
			// 那么0...i-1范围上怎么盗窃得到的最优解(last)，就是此时0....i范围上的最优解
			int p1 = last;
			// 可能性2 : 就是盗窃i号房屋
			// 先决条件 : i号房屋的财产 <= 当前盗贼的能力，这个前提必须具备，才能盗窃i号房屋
			// 如果先决条件具备，那么盗窃完i号房屋，之前就只能在0...i-2范围上去选房屋了(lastLast)
			int p2 = 0;
			if (nums[i] <= ability) {
				p2 = lastLast + 1;
			}
			// 两种可能性取最优
			int cur = Math.max(p1, p2);
			// 记录答案
			ans = Math.max(ans, cur);
			// 不要忘了更新lastLast、last
			// lastLast始终表示: 在0...i-2范围上去选房屋了，怎么盗窃数量最多
			// last始终表示: 在0...i-1范围上去选房屋了，怎么盗窃数量最多
			lastLast = last;
			last = cur;
		}
		return ans;
	}

}

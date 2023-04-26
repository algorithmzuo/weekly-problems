package class_2023_05_3_week;

// 来自小红书
// 测试链接 : https://leetcode.cn/problems/minimum-adjacent-swaps-for-k-consecutive-ones/
public class Code03_MinimumAdjacentSwapsForConsecutiveOnes {

	// . . . 1 0 1 0 0 | 0 0 X X |
	//       3 4 5 6 7   8 9
	// 假设3位置的1，和5位置的1，移动到8、9位置
	// 最优移动是 :
	// 5位置的1，移动到9位置
	// 3位置的1，移动到8位置
	// 距离和 = 9 - 5 + 8 - 3
	// 距离和 = 9 + 8 - (5 + 3)
	// 推广 :
	// 假设最优移动是 :
	// a位置的1，向右移动到c位置
	// b位置的1，向右移动到d位置
	// 距离和 = c + d - (a + b)
	// 这是对所有1向右移动来说的
	// 那么所有1向左移动是同理的
	// . . . | X X 0 0 | 0 1 0 0 1
	//             c d   5 a 7 8 b
	// 假设最优移动是 :
	// a位置的1，向左移动到c位置
	// b位置的1，向左移动到d位置
	// 距离和 = a - c + b - d = c + d - (a + b) 整体的绝对值
	// 综上，不管向左还是向右
	// 假设最优移动是 :
	// a位置的1，移动到c位置
	// b位置的1，移动到d位置
	// 距离和 = | c + d - (a + b) |
	// 枚举所有K长度的窗口 :
	// 对窗口的左半边来说，就是再左边的1向右移动
	// 对窗口的右半边来说，就是再右边的1向左移动
	// 举个例子
	// 0 1 1 0 1 1 1 | 0 0 0  0 | 1  0  1  1
	// 0 1 2 3 4 5 6   7 8 9 10  11 12 13 14
	// 假设K = 4
	// 当前窗口来到7~10位置的区域
	// 那么，这个窗口的7位置、8位置是左半边
	// 这个窗口的9位置、10位置是右半边
	// 此时的最优策略为 :
	// 对左半边来说，
	// 6位置的1，应该去8位置
	// 5位置的1，应该去7位置
	// 对右半边来说，
	// 11位置的1，应该去9位置
	// 13位置的1，应该去10位置
	// 上面就是某一个长度为K的窗口，最优的移动策略
	// 那么枚举每一个长度为K的窗口，选出代价最好的时候，就是答案
	// 当然求每一个窗口的代价，就是上文给你说的公式
	public static int minMoves(int[] nums, int k) {
		if (k == 1) {
			return 0;
		}
		int n = nums.length;
		int x = (k - 1) / 2;
		int leftAimIndiesSum = x * (x + 1) / 2;
		int rightAimIndiesSum = (int) ((long) (k - 1) * k / 2 - leftAimIndiesSum);
		int ans = Integer.MAX_VALUE;
		int l = 0;
		int m = (k - 1) / 2;
		int r = k - 1;
		int leftNeedOnes = m + 1;
		int leftWindowL = 0;
		int leftWindowOnes = 0;
		int leftWindowOnesIndiesSum = 0;
		for (int i = 0; i < m; i++) {
			if (nums[i] == 1) {
				leftWindowOnes++;
				leftWindowOnesIndiesSum += i;
			}
		}
		int rightNeedOnes = k - leftNeedOnes;
		int rightWindowR = m;
		int rightWindowOnes = nums[m];
		int rightWindowOnesIndiesSum = nums[m] == 1 ? m : 0;
		for (; r < n; l++, m++, r++) {
			if (nums[m] == 1) {
				leftWindowOnes++;
				leftWindowOnesIndiesSum += m;
				rightWindowOnes--;
				rightWindowOnesIndiesSum -= m;
			}
			while (leftWindowOnes > leftNeedOnes) {
				if (nums[leftWindowL] == 1) {
					leftWindowOnes--;
					leftWindowOnesIndiesSum -= leftWindowL;
				}
				leftWindowL++;
			}
			while (rightWindowOnes < rightNeedOnes && rightWindowR + 1 < n) {
				if (nums[rightWindowR + 1] == 1) {
					rightWindowOnes++;
					rightWindowOnesIndiesSum += rightWindowR + 1;
				}
				rightWindowR++;
			}
			if (leftWindowOnes == leftNeedOnes && rightWindowOnes == rightNeedOnes) {
				ans = Math.min(ans,
						leftAimIndiesSum - leftWindowOnesIndiesSum + rightWindowOnesIndiesSum - rightAimIndiesSum);
			}
			leftAimIndiesSum += m + 1 - l;
			rightAimIndiesSum += r - m;
		}
		return ans;
	}

}

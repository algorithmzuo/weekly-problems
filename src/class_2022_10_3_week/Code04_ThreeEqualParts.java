package class_2022_10_3_week;

// 测试链接 : https://leetcode.cn/problems/three-equal-parts/
public class Code04_ThreeEqualParts {

	public static int[] threeEqualParts(int[] arr) {
		// 计算arr中1的数量
		int ones = 0;
		for (int num : arr) {
			ones += num == 1 ? 1 : 0;
		}
		// 如果1的数量不能被3整除，肯定不存在方案
		if (ones % 3 != 0) {
			return new int[] { -1, -1 };
		}
		int n = arr.length;
		// 如果1的数量是0，怎么划分都可以了，因为全是0
		if (ones == 0) {
			return new int[] { 0, n - 1 };
		}
		// 接下来的过程
		// 因为1的数量能被3整除，比如一共有12个1
		// 那么第一段肯定含有第1个1~第4个1
		// 那么第二段肯定含有第5个1~第8个1
		// 那么第三段肯定含有第9个1~第12个1
		// 所以把第1个1，当做第一段的开头，start1
		// 所以把第5个1，当做第二段的开头，start2
		// 所以把第9个1，当做第三段的开头，start3
		int part = ones / 3;
		int start1 = -1;
		int start2 = -1;
		int start3 = -1;
		int cnt = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] == 1) {
				cnt++;
				if (start1 == -1 && cnt == 1) {
					start1 = i;
				}
				if (start2 == -1 && cnt == part + 1) {
					start2 = i;
				}
				if (start3 == -1 && cnt == 2 * part + 1) {
					start3 = i;
				}
			}
		}
		// 第一段的开头往下的部分
		// 第二段的开头往下的部分
		// 第三段的开头往下的部分
		// 要都一样，这三段的状态才是一样的
		// 所以接下来就验证这一点，是不是每一步都一样
		while (start3 < n) {
			if (arr[start1] != arr[start2] || arr[start1] != arr[start3]) {
				// 一旦不一样，肯定没方案了
				return new int[] { -1, -1 };
			}
			start1++;
			start2++;
			start3++;
		}
		// 如果验证通过，返回断点即可
		return new int[] { start1 - 1, start2 };
	}

}

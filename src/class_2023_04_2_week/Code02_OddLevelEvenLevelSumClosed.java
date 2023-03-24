package class_2023_04_2_week;

// 来自腾讯音乐
// 给定一棵树，一共有n个点
// 每个点上没有值，请把1~n这些数字，不重复的分配到二叉树上
// 做到 : 奇数层节点的值总和 与 偶数层节点的值总和 相差不超过1
// 返回奇数层节点分配值的一个方案
// 2 <= n <= 10^5 
public class Code02_OddLevelEvenLevelSumClosed {

	public static int[] team(int n, int k) {
		int sum = (n + 1) * n / 2;
		int p1 = sum / 2;
		int p2 = (sum + 1) / 2;
		int[] ans = generate(p1, n, k);
		if (ans == null && (sum & 1) == 1) {
			ans = generate(p2, n, k);
		}
		return ans != null ? ans : new int[] { -1 };
	}

	public static int[] generate(int wantSum, int n, int k) {
		int sumMinK = (k + 1) * k / 2;
		int range = n - k;
		if (wantSum < sumMinK || wantSum > sumMinK + k * range) {
			return null;
		}
		int add = wantSum - sumMinK;
		int rightSize = add / range;
		int midIndex = (k - rightSize) + (add % range);
		int leftSize = k - rightSize - (add % range == 0 ? 0 : 1);
		int[] ans = new int[k];
		for (int i = 0; i < leftSize; i++) {
			ans[i] = i + 1;
		}
		if (add % range != 0) {
			ans[leftSize] = midIndex;
		}
		for (int i = k - 1, j = 0; j < rightSize; i--, j++) {
			ans[i] = n - j;
		}
		return ans;
	}

	public static void main(String[] args) {
		// n是最大值，1~n这些数字都有
		int n = 100;
		// k是个数
		int k = 33;
		// 1~n这些数字，选k个，能不能求和逼近一半
		// 返回方案
		int[] ans = team(n, k);
		System.out.println("总和 : " + (n + 1) * n / 2);
		System.out.println("长度 : " + ans.length);
		int sum = 0;
		System.out.print("数字 : ");
		for (int num : ans) {
			System.out.print(num + " ");
			sum += num;
		}
		System.out.println();
		System.out.println("求和 : " + sum);
		System.out.println("剩余 : " + ((n + 1) * n / 2 - sum));
	}

}

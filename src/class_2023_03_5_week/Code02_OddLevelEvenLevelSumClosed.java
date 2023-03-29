package class_2023_03_5_week;

// 来自腾讯音乐
// 给定一棵树，一共有n个点
// 每个点上没有值，请把1~n这些数字，不重复的分配到二叉树上
// 做到 : 奇数层节点的值总和 与 偶数层节点的值总和 相差不超过1
// 返回奇数层节点分配值的一个方案
// 2 <= n <= 10^5 
// 所以课上会讲怎么转化，然后如下的代码实现
public class Code02_OddLevelEvenLevelSumClosed {

	// 1 ~ n 奇数节点的个数是k个
	// 返回奇数节点的值有哪些
	public static int[] team(int n, int k) {
		// 1 ~ n ,  sum = 10   k个奇数  5
		// 1 ~ n ,  sum = 15   k个奇数  7  8
		int sum = (n + 1) * n / 2;
		int p1 = sum / 2;
		int p2 = (sum + 1) / 2;
		int[] ans = generate(p1, n, k);
		if (ans == null && (sum & 1) == 1) {
			ans = generate(p2, n, k);
		}
		return ans != null ? ans : new int[] { -1 };
	}

	// 一共 1 ~ n 这些数字
	// 其中选k个数字
	// 一定要让k个数字的累加和是wantSum
	// 返回，哪k个数字，只要返回一种方法就可以
	public static int[] generate(int wantSum, int n, int k) {
		// k个数字，和最小的情况，1 2 3 ... k
		int sumMinK = (k + 1) * k / 2;
		// 每个数提升的幅度
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

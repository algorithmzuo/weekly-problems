package class_2022_03_4_week;

// 来自学员问题
// 给定一个数字n，表示一开始有编号1~n的树木，列成一条直线
// 给定一个有序数组arr，表示现在哪些树已经没了，arr[i]一定在[1,n]范围
// 给定一个数字m，表示你可以补种多少棵树
// 返回补种之后，最长的连续树木，有多少棵
public class Code06_LongestContinuousTrees {

	public static int longestTrees(int n, int m, int[] arr) {
		int ans = 0;
		int start = 1;
		for (int i = 0, j = m; j < arr.length; i++, j++) {
			ans = Math.max(ans, arr[j] - start);
			start = arr[i] + 1;
		}
		ans = Math.max(ans, n - start + 1);
		return ans;
	}

}

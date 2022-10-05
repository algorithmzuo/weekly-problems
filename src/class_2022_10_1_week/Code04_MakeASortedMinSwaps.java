package class_2022_10_1_week;

import java.util.Arrays;

// 来自学员问题
// 商场中有一展柜A，其大小固定，现已被不同的商品摆满
// 商家提供了一些新商品B，需要对A中的部分商品进行更新替换
// B中的商品可以自由使用，也就是可以用B中的任何商品替换A中的任何商品
// A中的商品一旦被替换，就认为消失了！而不是回到了B中！
// 要求更新过后的展柜中，商品严格按照价格由低到高进行排列
// 不能有相邻商品价格相等的情况
// A[i]为展柜中第i个位置商品的价格，B[i]为各个新商品的价格
// 求能够满足A中商品价格严格递增的最小操作次数，若无法满足则返回-1
public class Code04_MakeASortedMinSwaps {

	// 可以用B里的数字，替换A里的数字，想让A严格递增
	// 返回至少换几个数字
	public static int minSwaps(int[] A, int[] B) {
		// 根据题意，B里的数字随意拿
		// 所以B里的数字排序，不会影响拿
		// 同时，A如果从左往右考虑，依次被B替换上去的数字，肯定是从小到大的
		// 这是一定的！比如B = {5,3,2,9}
		// 可能先用5替换A的某个左边的数，再用2替换A的某个右边的数吗？不可能
		// 所以将B排序
		Arrays.sort(B);
		int ans = process(A, B, 0, 0, 0);
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// 参数解释：
	// A[0...ai-1]范围上已经做到升序了
	// 接下来请让A[ai....]范围上的数字做到升序
	// 之前的过程中，B里可能已经拿过一些数字了
	// 拿过的数字都在B[0...bi-1]范围上，不一定都拿了
	// 但是最后拿的数字一定是B[bi-1]
	// 如果想用B里的数字替换当前的A[ai]，请在B[bi....]范围上考虑拿数字
	// pre : 表示之前的A[ai-1]被替换了没有，
	// 如果pre==0，表示A[ai-1]没被替换
	// 如果pre==1，表示A[ai-1]被替换了，被谁替换的？被B[bi-1]替换的！
	// 返回值：让A[ai....]范围上的数字做到升序，最少还要在B[bi...]上拿几个数字
	// 如果返回值是Integer.MAX_VALUE，表示怎么都做不到
	// 这就是一个三维动态规划，自己改！
	// ai 是N范围
	// bi 是M范围
	// pre 只有0、1两种值
	// 所以时间复杂度O(N*M*logM)
	// 这个logM怎么来的，二分来的，看代码！
	public static int process(int[] A, int[] B, int ai, int bi, int pre) {
		if (ai == A.length) {
			return 0;
		}
		// 之前的数是什么
		int preNum = 0;
		if (ai == 0) {
			preNum = Integer.MIN_VALUE;
		} else {
			if (pre == 0) {
				preNum = A[ai - 1];
			} else {
				preNum = B[bi - 1];
			}
		}
		// 可能性1，搞定当前的A[ai]，不依靠交换
		int p1 = preNum < A[ai] ? process(A, B, ai + 1, bi, 0) : Integer.MAX_VALUE;
		// 可能性2，搞定当前的A[ai]，依靠交换
		int p2 = Integer.MAX_VALUE;
		// 在B[bi....]这个范围上，找到>preNum，最左的位置
		// 这一步是可以二分的！因为B整体有序
		int nearMoreIndex = bs(B, bi, preNum);
		if (nearMoreIndex != -1) {
			int next2 = process(A, B, ai + 1, nearMoreIndex + 1, 1);
			if (next2 != Integer.MAX_VALUE) {
				p2 = 1 + next2;
			}
		}
		return Math.min(p1, p2);
	}

	// 在B[start....]这个范围上，找到>num，最左的位置
	public static int bs(int[] B, int start, int num) {
		int ans = -1;
		int l = start;
		int r = B.length - 1;
		int m = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (B[m] > num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] A1 = { 1, 8, 3, 6, 9 };
		int[] B1 = { 1, 3, 2, 4 };
		System.out.println(minSwaps(A1, B1));
		int[] A2 = { 4, 8, 3, 10, 5 };
		int[] B2 = { 1, 3, 2, 4 };
		System.out.println(minSwaps(A2, B2));
		int[] A3 = { 1, 8, 3, 6, 9 };
		int[] B3 = { 4, 3, 1 };
		System.out.println(minSwaps(A3, B3));
	}

}

package class_2023_05_2_week;

// 来自微众银行
// 给出两个长度均为n的数组
// A = { a1, a2, ... ,an }
// B = { b1, b2, ... ,bn }。
// 你需要求出其有多少个区间[L,R]满足:
// 数组A中下标在[L,R]中的元素之和在[La,Ra]之中
// 数组B中下标在[L,R]中的元素之和在[Lb,Rb]之中
// 输入
// 第一行有一个正整数N(1<=N<=100000)，代表两个数组的长度。
// 第二行有N个非负整数，范围在0到1000000000之间，代表数组中的元素。
// 第三行有N个非负整数，范围在0到1000000000之间，代表数组中的元素。
// 第四行有4个整数La,Ra,Lb,Rb，范围在0到10^18之间，代表题目描述中的参数。
// 输出
// 输出一个整数，代表所求的答案。
public class Code02_ValidRangeBetweenTwoArrays {

	// 暴力方法
	// 为了测试
	public static int nums1(int[] A, int[] B, int la, int ra, int lb, int rb) {
		int n = A.length;
		int ans = 0;
		for (int l = 0; l < n; l++) {
			for (int r = l; r < n; r++) {
				int sumA = 0;
				int sumB = 0;
				for (int i = l; i <= r; i++) {
					sumA += A[i];
					sumB += B[i];
				}
				if (sumA >= la && sumA <= ra && sumB >= lb && sumB <= rb) {
					ans++;
				}
			}
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int nums2(int[] A, int[] B, int la, int ra, int lb, int rb) {
		int n = A.length;
		int ans = 0;
		int rightA1 = 0, sumA1 = 0, rightA2 = 0, sumA2 = 0, rightB1 = 0, sumB1 = 0, rightB2 = 0, sumB2 = 0;
		for (int l = 0; l < n; l++) {
			while (rightA1 < n && sumA1 + A[rightA1] < la) {
				sumA1 += A[rightA1++];
			}
			while (rightA2 < n && sumA2 + A[rightA2] <= ra) {
				sumA2 += A[rightA2++];
			}
			while (rightB1 < n && sumB1 + B[rightB1] < lb) {
				sumB1 += B[rightB1++];
			}
			while (rightB2 < n && sumB2 + B[rightB2] <= rb) {
				sumB2 += B[rightB2++];
			}
			int left = Math.max(rightA1, rightB1);
			int right = Math.min(rightA2, rightB2);
			if (left < right) {
				ans += right - left;
			}
			if (rightA1 == l) {
				rightA1++;
			} else {
				sumA1 -= A[l];
			}
			sumA2 -= A[l];
			if (rightB1 == l) {
				rightB1++;
			} else {
				sumB1 -= B[l];
			}
			sumB2 -= B[l];
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 50;
		int V = 100;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N);
			int[] A = randomArray(n, V);
			int[] B = randomArray(n, V);
			int a = (int) (Math.random() * V);
			int b = (int) (Math.random() * V);
			int c = (int) (Math.random() * V);
			int d = (int) (Math.random() * V);
			int la = Math.min(a, b);
			int ra = Math.max(a, b);
			int lb = Math.min(c, d);
			int rb = Math.max(c, d);
			int ans1 = nums1(A, B, la, ra, lb, rb);
			int ans2 = nums2(A, B, la, ra, lb, rb);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}

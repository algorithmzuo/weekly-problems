package class_2022_03_4_week;

// 来自学员问题
// 找到非负数组中拥有"最大或的结果"的最短子数组
public class Code08_MaxOrSmallestSubarray {

	public static int longest1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = 0;
		for (int num : arr) {
			max |= num;
		}
		if (max == 0) {
			return 1;
		}
		int n = arr.length;
		int ans = n;
		for (int i = 0; i < n; i++) {
			int cur = 0;
			for (int j = i; j < n; j++) {
				cur |= arr[j];
				if (cur == max) {
					ans = Math.min(ans, j - i + 1);
					break;
				}
			}
		}
		return ans;
	}

	public static int longest2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = 0;
		for (int num : arr) {
			max |= num;
		}
		if (max == 0) {
			return 1;
		}
		int n = arr.length;
		int ans = n;
		int[] counts = new int[32];
		int l = 0;
		int cur = 0;
		for (int r = 0; r < n; r++) {
			cur = add(counts, arr[r]);
			while (cur == max) {
				cur = delete(counts, arr[l++]);
			}
			if (l > 0) {
				cur = add(counts, arr[--l]);
			}
			if (cur == max) {
				ans = Math.min(ans, r - l + 1);
			}
		}
		return ans;
	}

	public static int add(int[] counts, int num) {
		int ans = 0;
		for (int i = 0; i < 32; i++) {
			counts[i] += (num & (1 << i)) != 0 ? 1 : 0;
			ans |= (counts[i] > 0 ? 1 : 0) << i;
		}
		return ans;
	}

	public static int delete(int[] counts, int num) {
		int ans = 0;
		for (int i = 0; i < 32; i++) {
			counts[i] -= (num & (1 << i)) != 0 ? 1 : 0;
			ans |= (counts[i] > 0 ? 1 : 0) << i;
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v);
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 50;
		int value = 50000;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, value);
			int ans1 = longest1(arr);
			int ans2 = longest2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

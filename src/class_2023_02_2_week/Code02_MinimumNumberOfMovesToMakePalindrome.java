package class_2023_02_2_week;

import java.util.ArrayList;
import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/minimum-number-of-moves-to-make-palindrome/
public class Code02_MinimumNumberOfMovesToMakePalindrome {

	public static int MAXN = 2010;

	public static int[] arr = new int[MAXN];

	public static int[] help = new int[MAXN];

	public static int[] tree = new int[MAXN];

	public static int n;

	public static ArrayList<ArrayList<Integer>> indies;

	static {
		indies = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			indies.add(new ArrayList<>());
		}
	}

	public static int minMovesToMakePalindrome(String s) {
		n = s.length();
		Arrays.fill(arr, 1, n + 1, 0);
		for (int c = 0; c < 26; c++) {
			indies.get(c).clear();
		}

		for (int i = 0, j = 1; i < n; i++, j++) {
			int c = s.charAt(i) - 'a';
			indies.get(c).add(j);
		}
		boolean findOdd = false;
		for (int c = 0; c < 26; c++) {
			if ((indies.get(c).size() & 1) == 1) {
				if (findOdd) {
					return -1;
				} else {
					findOdd = true;
				}
			}
		}
		createIndexTree();
		for (int i = 0, l = 1; i < n; i++, l++) {
			if (arr[l] == 0) {
				int c = s.charAt(i) - 'a';
				int r = indies.get(c).remove(indies.get(c).size() - 1);
				if (l == r) {
					arr[l] = (1 + n) / 2;
					add(l, -1);
				} else {
					int kth = sum(l);
					arr[l] = kth;
					arr[r] = n - kth + 1;
					add(r, -1);
				}
			}
		}
		return reversePairs(1, n);
	}

	public static void createIndexTree() {
		Arrays.fill(tree, 1, n + 1, 0);
		for (int i = 1; i <= n; i++) {
			add(i, 1);
		}
	}

	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= i & -i;
		}
		return ans;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += i & -i;
		}
	}

	public static int reversePairs(int l, int r) {
		if (l >= r) {
			return 0;
		}
		int mid = l + ((r - l) >> 1);
		return reversePairs(l, mid) + reversePairs(mid + 1, r) + merge(arr, l, mid, r);
	}

	public static int merge(int[] arr, int l, int m, int r) {
		int i = r;
		int p1 = m;
		int p2 = r;
		int ans = 0;
		while (p1 >= l && p2 > m) {
			ans += arr[p1] > arr[p2] ? (p2 - m) : 0;
			help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
		}
		while (p1 >= l) {
			help[i--] = arr[p1--];
		}
		while (p2 > m) {
			help[i--] = arr[p2--];
		}
		for (i = l; i <= r; i++) {
			arr[i] = help[i];
		}
		return ans;
	}

}

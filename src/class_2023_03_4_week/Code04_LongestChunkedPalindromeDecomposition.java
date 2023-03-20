package class_2023_03_4_week;

// 你会得到一个字符串 text
// 你应该把它分成 k 个子字符串 (subtext1, subtext2，…， subtextk)
// 要求满足:
// subtexti 是 非空 字符串
// 所有子字符串的连接等于 text 
// ( 即subtext1 + subtext2 + ... + subtextk == text )
// subtexti == subtextk - i + 1 表示所有 i 的有效值( 即 1 <= i <= k )
// 返回k可能最大值。
// 测试链接 : https://leetcode.cn/problems/longest-chunked-palindrome-decomposition/
public class Code04_LongestChunkedPalindromeDecomposition {

	// 时间复杂度O(N^2)
	// 题解上的做法
	// 但这不是最优解
	// 而且没有帖子写出最优解
	public static int longestDecomposition1(String str) {
		if (str.length() == 1) {
			return 1;
		}
		char[] s = str.toCharArray();
		int n = s.length;
		int l = 0;
		int r = n - 1;
		int ans = 0;
		while (l <= r) {
			int size = 1;
			for (; 2 * size <= r - l + 1; size++) {
				if (same1(s, l, r, size)) {
					break;
				}
			}
			if (2 * size <= r - l + 1) {
				ans += 2;
			}
			l += size;
			r -= size;
		}
		return r == l - 1 ? ans : (ans + 1);
	}

	public static boolean same1(char[] s, int l, int r, int size) {
		for (int i = l, j = r - size + 1; j <= r; i++, j++) {
			if (s[i] != s[j]) {
				return false;
			}
		}
		return true;
	}

	// 时间复杂度O(N * logN)
	// 最优解
	// 利用DC3算法生成后缀数组、进而生成高度数组来做的
	// 需要体系学习班，DC3生成后缀数组详解
	// 不过Leetcode的数据量太小，根本显不出最优解的优异
	// 所以我自己写了测试来展示
	public static int longestDecomposition2(String str) {
		if (str.length() == 1) {
			return 1;
		}
		char[] s = str.toCharArray();
		int n = s.length;
		DC3 dc3 = generateDC3(s, n);
		int[] rank = dc3.rank;
		RMQ rmq = new RMQ(dc3.height);
		int l = 0;
		int r = n - 1;
		int ans = 0;
		while (l <= r) {
			int size = 1;
			for (; 2 * size <= r - l + 1; size++) {
				if (same2(rank, rmq, l, r, size)) {
					break;
				}
			}
			if (2 * size <= r - l + 1) {
				ans += 2;
			}
			l += size;
			r -= size;
		}
		return r == l - 1 ? ans : (ans + 1);
	}

	public static boolean same2(int[] rank, RMQ rmq, int l, int r, int size) {
		int start1 = l;
		int start2 = r - size + 1;
		int minStart = Math.min(rank[start1], rank[start2]);
		int maxStart = Math.max(rank[start1], rank[start2]);
		return rmq.min(minStart + 1, maxStart) >= size;
	}

	public static DC3 generateDC3(char[] s, int n) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (char cha : s) {
			min = Math.min(min, cha);
			max = Math.max(max, cha);
		}
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = s[i] - min + 1;
		}
		return new DC3(arr, max - min + 1);
	}

	public static class DC3 {
		public int[] sa;
		public int[] rank;
		public int[] height;

		public DC3(int[] nums, int max) {
			sa = sa(nums, max);
			rank = rank();
			height = height(nums);
		}

		private int[] sa(int[] nums, int max) {
			int n = nums.length;
			int[] arr = new int[n + 3];
			for (int i = 0; i < n; i++) {
				arr[i] = nums[i];
			}
			return skew(arr, n, max);
		}

		private int[] skew(int[] nums, int n, int K) {
			int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
			int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
			for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
				if (0 != i % 3) {
					s12[j++] = i;
				}
			}
			radixPass(nums, s12, sa12, 2, n02, K);
			radixPass(nums, sa12, s12, 1, n02, K);
			radixPass(nums, s12, sa12, 0, n02, K);
			int name = 0, c0 = -1, c1 = -1, c2 = -1;
			for (int i = 0; i < n02; ++i) {
				if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
					name++;
					c0 = nums[sa12[i]];
					c1 = nums[sa12[i] + 1];
					c2 = nums[sa12[i] + 2];
				}
				if (1 == sa12[i] % 3) {
					s12[sa12[i] / 3] = name;
				} else {
					s12[sa12[i] / 3 + n0] = name;
				}
			}
			if (name < n02) {
				sa12 = skew(s12, n02, name);
				for (int i = 0; i < n02; i++) {
					s12[sa12[i]] = i + 1;
				}
			} else {
				for (int i = 0; i < n02; i++) {
					sa12[s12[i] - 1] = i;
				}
			}
			int[] s0 = new int[n0], sa0 = new int[n0];
			for (int i = 0, j = 0; i < n02; i++) {
				if (sa12[i] < n0) {
					s0[j++] = 3 * sa12[i];
				}
			}
			radixPass(nums, s0, sa0, 0, n0, K);
			int[] sa = new int[n];
			for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
				int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
				int j = sa0[p];
				if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
						: leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
					sa[k] = i;
					t++;
					if (t == n02) {
						for (k++; p < n0; p++, k++) {
							sa[k] = sa0[p];
						}
					}
				} else {
					sa[k] = j;
					p++;
					if (p == n0) {
						for (k++; t < n02; t++, k++) {
							sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
						}
					}
				}
			}
			return sa;
		}

		private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
			int[] cnt = new int[k + 1];
			for (int i = 0; i < n; ++i) {
				cnt[nums[input[i] + offset]]++;
			}
			for (int i = 0, sum = 0; i < cnt.length; ++i) {
				int t = cnt[i];
				cnt[i] = sum;
				sum += t;
			}
			for (int i = 0; i < n; ++i) {
				output[cnt[nums[input[i] + offset]]++] = input[i];
			}
		}

		private boolean leq(int a1, int a2, int b1, int b2) {
			return a1 < b1 || (a1 == b1 && a2 <= b2);
		}

		private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
			return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
		}

		private int[] rank() {
			int n = sa.length;
			int[] ans = new int[n];
			for (int i = 0; i < n; i++) {
				ans[sa[i]] = i;
			}
			return ans;
		}

		private int[] height(int[] s) {
			int n = s.length;
			int[] ans = new int[n];
			for (int i = 0, k = 0; i < n; ++i) {
				if (rank[i] != 0) {
					if (k > 0) {
						--k;
					}
					int j = sa[rank[i] - 1];
					while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
						++k;
					}
					ans[rank[i]] = k;
				}
			}
			return ans;
		}

	}

	public static class RMQ {

		public int[][] min;

		public RMQ(int[] arr) {
			int n = arr.length;
			int k = power2(n);
			min = new int[n + 1][k + 1];
			for (int i = 1; i <= n; i++) {
				min[i][0] = arr[i - 1];
			}
			for (int j = 1; (1 << j) <= n; j++) {
				for (int i = 1; i + (1 << j) - 1 <= n; i++) {
					min[i][j] = Math.min(min[i][j - 1], min[i + (1 << (j - 1))][j - 1]);
				}
			}
		}

		public int min(int l, int r) {
			l++;
			r++;
			int k = power2(r - l + 1);
			return Math.min(min[l][k], min[r - (1 << k) + 1][k]);
		}

		private int power2(int m) {
			int ans = 0;
			while ((1 << ans) <= (m >> 1)) {
				ans++;
			}
			return ans;
		}

	}

	// 为了测试
	public static String generateS(int a, int b) {
		char[] ans = new char[a + b];
		for (int i = 0; i < a; i++) {
			ans[i] = 'a';
		}
		for (int i = a, j = 0; j < b; i++, j++) {
			ans[i] = 'b';
		}
		return String.valueOf(ans);
	}

	// 为了测试
	public static String generateT(String part, int n) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < n; i++) {
			builder.append(part);
		}
		return builder.toString();
	}

	// 为了测试
	public static void main(String[] args) {
		System.out.println("先展示一下DC3的用法");
		String test = "aaabaaa";
		DC3 dc3 = generateDC3(test.toCharArray(), test.length());
		System.out.println("sa[i]表示字典序排名第i的是什么位置开头的后缀串");
		int[] sa = dc3.sa;
		for (int i = 0; i < test.length(); i++) {
			System.out.println(i + " : " + sa[i]);
		}

		System.out.println("rank[i]表示i位置开头的后缀串的字典序排多少名");
		int[] rank = dc3.rank;
		for (int i = 0; i < test.length(); i++) {
			System.out.println(i + " : " + rank[i]);
		}
		System.out.println("height[i]表示字典序排名i的后缀串和前一个排名的后缀串，最长公共前缀是多长");
		int[] height = dc3.height;
		for (int i = 0; i < test.length(); i++) {
			System.out.println(i + " : " + height[i]);
		}

		System.out.println("性能测试开始");
		long start, end;
		// 构造一个字符串s
		// s = a....一共30万个a....ab
		String s = generateS(300000, 1);
		// 构造总的字符串t
		// t = s + s
		// t的总长度是60万长度 -> 6 * 10^5
		String t = generateT(s, 2);

		// longestDecomposition1跑完是很慢的
		// 耐心等吧，运行时间在15秒左右
		start = System.currentTimeMillis();
		System.out.println("方法1的结果 : " + longestDecomposition1(t));
		end = System.currentTimeMillis();
		System.out.println("方法1的运行时间 : " + (end - start) + " 毫秒");

		// longestDecomposition2跑完是很快的
		// 而且你构造不出让longestDecomposition2方法慢的例子
		// 字符串长度在10^6以内，可以随意构造字符串
		// longestDecomposition2方法都会很快
		start = System.currentTimeMillis();
		System.out.println("方法2的结果 : " + longestDecomposition2(t));
		end = System.currentTimeMillis();
		System.out.println("方法2的运行时间 : " + (end - start) + " 毫秒");

		System.out.println("性能测试结束");
	}

}

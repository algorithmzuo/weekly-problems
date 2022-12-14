package class_2023_01_1_week;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/orderly-queue/
public class Code03_OrderlyQueue {

	public static String orderlyQueue(String s, int k) {
		if (k > 1) {
			// 时间复杂度O(N*logN)
			// 证明 :
			// 如果k == 2
			// 总可以做到 : 1小 2小 ...
			// 总可以做到 : 3小 .... 1小 2小 ...
			// 总可以做到 : 3小 1小 2小 ...
			// 总可以做到 : 4小 .... 1小 2小 3小 ...
			// 总可以做到 : 4小 1小 2小 3小 .....
			// 总可以做到 : 5小 ..... 1小 2小 3小 4小 ...
			// ...
			// 所以总可以做到有序
			// k > 2就更能做到了，所以k > 1直接排序返回
			char[] str = s.toCharArray();
			Arrays.sort(str);
			return String.valueOf(str);
		} else {
			// 时间复杂度O(N)
			// k == 1时
			// 把字符串看做一个环，就是看看从哪切开字典序最小
			// 通过s = s + s的方式，长度2n，可以得到所有环
			// 然后用DC3算法看看前n个位置，谁的字典序最小即可
			// 虽然从通过百分比来看并不优异
			// 但那是因为leetcode准备的数据量太小了，字符串才1000长度所以显不出优势
			// 如果字符串很长优势就明显了
			// 因为时间复杂度O(N)一定是最优解
			String s2 = s + s;
			int n = s2.length();
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = s2.charAt(i) - 'a' + 1;
			}
			DC3 dc3 = new DC3(arr, 26);
			n >>= 1;
			int minRankIndex = 0;
			for (int i = 1; i < n; i++) {
				if (dc3.rank[i] < dc3.rank[minRankIndex]) {
					minRankIndex = i;
				}
			}
			return s.substring(minRankIndex) + s.substring(0, minRankIndex);
		}
	}

	// 如果字符串长度N，
	// DC3算法搞定字符串所有后缀串字典序排名的时间复杂度O(N)
	// 体系学习班有讲，有兴趣的同学可以看看
	public static class DC3 {

		public int[] sa;

		public int[] rank;

		public DC3(int[] nums, int max) {
			sa = sa(nums, max);
			rank = rank();
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

	}

}

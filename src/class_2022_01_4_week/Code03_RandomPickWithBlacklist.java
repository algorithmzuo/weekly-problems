package class_2022_01_4_week;

import java.util.Arrays;
import java.util.HashMap;

// 给定一个包含 [0，n) 中不重复整数的黑名单 blacklist
// 写一个函数从 [0, n) 中返回一个不在 blacklist 中的随机整数
// 对它进行优化使其尽量少调用系统方法 Math.random()
// 1 <= n <= 1000000000
// 0 <= blacklist.length < min(100000, N)
// 测试链接: https://leetcode.com/problems/random-pick-with-blacklist/
public class Code03_RandomPickWithBlacklist {

	class Solution {

		// 0~99 所有的数字都可以随机，可能有若干黑名单！
		// 填到洞里去，size -> 0~size-1  是最后调整的下标范围，紧实的！
		private int size;

		// 13 -> 99
		// 27 -> 98
		private HashMap<Integer, Integer> convert = new HashMap<>();

		public Solution(int n, int[] blackArray) {
			Arrays.sort(blackArray);
			int m = blackArray.length;
			// [34, 56, 78, 103, ..., 980]
			//  0   1    2   3        100
			for (int i = 0; i < m && blackArray[i] < n; i++) {
				for (n--; n > blackArray[i]; n--) {
					if (n == blackArray[m - 1]) {
						m--;
					} else {
						convert.put(blackArray[i], n);
						break;
					}
				}
			}
			size = n;
		}

		public int pick() {
			int ans = (int) (Math.random() * size);
			// 13 -> 99
			// 17 -> 98
			// 35 -> 35
			return convert.containsKey(ans) ? convert.get(ans) : ans;
		}

	}

}
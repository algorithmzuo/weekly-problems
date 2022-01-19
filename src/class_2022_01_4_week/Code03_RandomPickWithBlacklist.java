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

		private int size;

		private HashMap<Integer, Integer> convert = new HashMap<>();

		public Solution(int n, int[] blackArray) {
			Arrays.sort(blackArray);
			int m = blackArray.length;
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
			return convert.containsKey(ans) ? convert.get(ans) : ans;
		}

	}

}
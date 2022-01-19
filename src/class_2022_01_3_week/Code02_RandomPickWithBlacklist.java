package class_2022_01_3_week;

import java.util.Arrays;
import java.util.HashMap;

// 测试链接: https://leetcode.com/problems/random-pick-with-blacklist/
public class Code02_RandomPickWithBlacklist {

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
package class_2021_12_5_week;

import java.util.Arrays;
import java.util.HashMap;

// 测试链接: https://leetcode.com/problems/random-pick-with-blacklist/
public class Code02_RandomPickWithBlacklist {

	class Solution {

		private int size;

		private HashMap<Integer, Integer> convert = new HashMap<>();

		public Solution(int n, int[] blacklist) {
			Arrays.sort(blacklist);
			int last = blacklist.length - 1;
			for (int black : blacklist) {
				if (black >= n) {
					break;
				}
				for (n--; n > black; n--) {
					if (n == blacklist[last]) {
						last--;
					} else {
						convert.put(black, n);
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
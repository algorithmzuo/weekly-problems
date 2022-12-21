package class_2023_01_2_week;

import java.util.ArrayList;
import java.util.HashMap;

// 测试链接 : https://leetcode.cn/problems/maximum-frequency-stack/
public class Code03_MaximumFrequencyStack {

	class FreqStack {

		private int topTimes;
		private HashMap<Integer, ArrayList<Integer>> cntValues = new HashMap<>();
		private HashMap<Integer, Integer> valueTopTime = new HashMap<>();

		public void push(int val) {
			valueTopTime.put(val, valueTopTime.getOrDefault(val, 0) + 1);
			int curTopTimes = valueTopTime.get(val);
			if (!cntValues.containsKey(curTopTimes)) {
				cntValues.put(curTopTimes, new ArrayList<>());
			}
			ArrayList<Integer> curTimeValues = cntValues.get(curTopTimes);
			curTimeValues.add(val);
			topTimes = Math.max(topTimes, curTopTimes);
		}

		public int pop() {
			ArrayList<Integer> topTimeValues = cntValues.get(topTimes);
			int last = topTimeValues.size() - 1;
			int ans = topTimeValues.remove(last);
			if (topTimeValues.size() == 0) {
				cntValues.remove(topTimes--);
			}
			int times = valueTopTime.get(ans);
			if (times == 1) {
				valueTopTime.remove(ans);
			} else {
				valueTopTime.put(ans, times - 1);
			}
			return ans;
		}
	}

}

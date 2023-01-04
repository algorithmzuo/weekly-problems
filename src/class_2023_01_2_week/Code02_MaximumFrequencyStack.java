package class_2023_01_2_week;

import java.util.ArrayList;
import java.util.HashMap;

// 设计一个类似堆栈的数据结构，将元素推入堆栈，并从堆栈中弹出出现频率最高的元素。
// 实现 FreqStack 类:
// FreqStack() 构造一个空的堆栈。
// void push(int val) 将一个整数 val 压入栈顶。
// int pop() 删除并返回堆栈中出现频率最高的元素。
// 如果出现频率最高的元素不只一个，则移除并返回最接近栈顶的元素。
// 测试链接 : https://leetcode.cn/problems/maximum-frequency-stack/
public class Code02_MaximumFrequencyStack {

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

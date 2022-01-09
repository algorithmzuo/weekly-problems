package class_2022_01_3_week;

import java.util.ArrayList;
import java.util.LinkedList;

// 来自美团
// 小团去参加军训，军训快要结束了，长官想要把大家一排n个人分成m组，然后让每组分别去参加阅兵仪式
// 只能选择相邻的人一组，不能随意改变队伍中人的位置
// 阅兵仪式上会进行打分，其中有一个奇怪的扣分点是每组的最大差值，即每组最大值减去最小值
// 长官想要让这分成的m组总扣分量最小，即这m组分别的极差之和最小
// 长官正在思索如何安排中，就让小团来帮帮他吧
public class Code03_SplitToMArraysMinScore {

	// 暴力方法
	// 为了验证
	public static int minScore1(int[] arr, int m) {
		ArrayList<LinkedList<Integer>> sets = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			sets.add(new LinkedList<>());
		}
		return process(sets, arr, 0, m);
	}

	public static int process(ArrayList<LinkedList<Integer>> sets, int[] arr, int index, int m) {
		if (index == arr.length) {
			return score(sets);
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < m; i++) {
			sets.get(i).add(arr[index]);
			ans = Math.min(ans, process(sets, arr, index + 1, m));
			sets.get(i).removeLast();
		}
		return ans;
	}

	public static int score(ArrayList<LinkedList<Integer>> sets) {
		int ans = 0;
		for (LinkedList<Integer> set : sets) {
			if (set.isEmpty()) {
				return Integer.MAX_VALUE;
			}
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;
			for (int num : set) {
				max = Math.max(max, num);
				min = Math.min(min, num);
			}
			ans += max - min;
		}
		return ans;
	}

	// 正式方法
//	public static int minScore2(int[] arr, int m) {
//
//	}
//
//	public static int needSets(int[] arr, int limit) {
//
//	}
//
//	public static void main(String[] args) {
//
//	}

}

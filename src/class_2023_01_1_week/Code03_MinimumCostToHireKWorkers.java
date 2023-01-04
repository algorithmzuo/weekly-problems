package class_2023_01_1_week;

import java.util.Arrays;
import java.util.PriorityQueue;

// 有 n 名工人。 给定两个数组 quality 和 wage ，
// 其中，quality[i] 表示第 i 名工人的工作质量，其最低期望工资为 wage[i] 。
// 现在我们想雇佣 k 名工人组成一个工资组。在雇佣 一组 k 名工人时，
// 我们必须按照下述规则向他们支付工资：
// 对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资。
// 工资组中的每名工人至少应当得到他们的最低期望工资。
// 给定整数 k ，返回 组成满足上述条件的付费群体所需的最小金额
// 测试链接 : https://leetcode.cn/problems/minimum-cost-to-hire-k-workers/
public class Code03_MinimumCostToHireKWorkers {

	public static class Employee {
		public double rubbishDegree;
		public int quality;

		public Employee(int w, int q) {
			rubbishDegree = (double) w / (double) q;
			quality = q;
		}
	}

	public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
		int n = quality.length;
		Employee[] employees = new Employee[n];
		for (int i = 0; i < n; i++) {
			employees[i] = new Employee(wage[i], quality[i]);
		}
		Arrays.sort(employees, (a, b) -> a.rubbishDegree <= b.rubbishDegree ? -1 : 1);
		PriorityQueue<Integer> minTops = new PriorityQueue<Integer>((a, b) -> b - a);
		double ans = Double.MAX_VALUE;
		for (int i = 0, qualitySum = 0; i < n; i++) {
			int curQuality = employees[i].quality;
			if (minTops.size() < k) {
				qualitySum += curQuality;
				minTops.add(curQuality);
				if (minTops.size() == k) {
					ans = Math.min(ans, qualitySum * employees[i].rubbishDegree);
				}
			} else {
				if (minTops.peek() > curQuality) {
					qualitySum += curQuality - minTops.poll();
					minTops.add(curQuality);
					ans = Math.min(ans, qualitySum * employees[i].rubbishDegree);
				}
			}
		}
		return ans;
	}

}

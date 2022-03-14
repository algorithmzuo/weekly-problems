package class_2022_03_3_week;

import java.util.Arrays;
import java.util.HashMap;

// 来自银联编程比赛
// 为了不断提高用户使用的体验，开发团队正在对产品进行全方位的开发和优化。
// 已知开发团队共有若干名成员，skills[i] 表示第 i 名开发人员掌握技能列表。
// 如果两名成员各自拥有至少一门对方未拥有的技能，则这两名成员可以「合作开发」。
// 请返回当前有多少对开发成员满足「合作开发」的条件。
// 由于答案可能很大，请你返回答案对 10^9 + 7 取余的结果。
// 测试链接 : https://leetcode-cn.com/contest/cnunionpay-2022spring/problems/lCh58I/
public class Code12_CoopDevelop {

	public static long mod = 1000000007L;

	public static int coopDevelop(int[][] skills) {
		Arrays.sort(skills, (a, b) -> b.length - a.length);
		int n = skills.length;
		long ans = (long) n * (long) (n - 1) / 2L;
		HashMap<Long, Long> setsNums = new HashMap<>();
		for (int[] people : skills) {
			long status = 0L;
			for (int skill : people) {
				status = (status << 10) | skill;
			}
			ans -= setsNums.getOrDefault(status, 0L);
			fillMap(people, 0, 0, setsNums);
		}
		return (int) (ans % mod);
	}

	public static void fillMap(int[] people, int i, long status, HashMap<Long, Long> setsNums) {
		if (i == people.length) {
			setsNums.put(status, setsNums.getOrDefault(status, 0L) + 1);
		} else {
			fillMap(people, i + 1, status, setsNums);
			fillMap(people, i + 1, (status << 10) | people[i], setsNums);
		}
	}

}

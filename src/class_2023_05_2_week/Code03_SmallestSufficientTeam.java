package class_2023_05_2_week;

import java.util.Arrays;
import java.util.List;

// 作为项目经理，你规划了一份需求的技能清单 req_skills，
// 并打算从备选人员名单 people 中选出些人组成一个「必要团队」
//（ 编号为 i 的备选人员 people[i] 含有一份该备选人员掌握的技能列表）。
// 所谓「必要团队」，就是在这个团队中，
// 对于所需求的技能列表 req_skills 中列出的每项技能，
// 团队中至少有一名成员已经掌握。可以用每个人的编号来表示团队中的成员：
// 例如，团队 team = [0, 1, 3] 表示掌握技能分别为
// people[0]，people[1]，和 people[3] 的备选人员。
// 请你返回 任一 规模最小的必要团队，团队成员用人员编号表示。
// 你可以按 任意顺序 返回答案，题目数据保证答案存在。
// 测试链接 : https://leetcode.cn/problems/smallest-sufficient-team/
public class Code03_SmallestSufficientTeam {

	public static int[] smallestSufficientTeam(String[] skills, List<List<String>> people) {
		Arrays.sort(skills);
		int n = skills.length;
		int m = people.size();
		int[] statuses = new int[m];
		for (int i = 0; i < m; i++) {
			int skillStatus = 0;
			List<String> skill = people.get(i);
			skill.sort((a, b) -> a.compareTo(b));
			int p1 = 0;
			int p2 = 0;
			while (p1 < n && p2 < skill.size()) {
				int compare = skills[p1].compareTo(skill.get(p2));
				if (compare < 0) {
					p1++;
				} else if (compare > 0) {
					p2++;
				} else {
					skillStatus |= 1 << p1;
					p1++;
					p2++;
				}
			}
			statuses[i] = skillStatus;
		}
		// 上面的过程，其实是把技能变成状态信息
		// 比如:
		// skills = { f,a,e,c}
		// 排个序为 : a c e f
		// 认为a是0技能
		// 认为c是1技能
		// 认为e是2技能
		// 认为f是3技能
		// 然后就可以把每个人的技能变成状态信息了
		// 比如，A会的技能 : e f t x c
		// 认为A会的技能状态为 : 1110
		// 解释一下 :
		// 第0位是0，说明A不会a技能
		// 第1位是1，说明A会c技能
		// 第2位是1，说明A会e技能
		// 第3位是1，说明A会f技能
		// 至于t、x技能，忽略！因为没用
		// 上面的过程，就是把技能编号
		// 然后把每个人的技能变成位信息
		// 比如skills一共8个技能
		// 那么就是为了这个状态 : 00..0011111111
		// 也就是什么时候凑齐8个1，什么时候就可以结束了！
		int[][] dp = new int[m][1 << n];
		for (int i = 0; i < m; i++) {
			Arrays.fill(dp[i], -1);
		}
		// process函数去跑动态规划
		// 目的是算出至少几人
		// 以及填好动态规划表
		int size = process(statuses, n, 0, 0, dp);
		int[] ans = new int[size];
		int ansi = 0;
		int i = 0;
		int status = 0;
		// 大厂刷题班，章节11
		// 看一下，是根据动态规划表生成答案的路径
		// 这就是为什么需要动态规划表，因为答案的路径可以用动态规划表生成
		// 一定要看一下这个课
		while (status != (1 << n) - 1) {
			if (i + 1 == m || dp[i][status] != dp[i + 1][status]) {
				ans[ansi++] = i;
				status |= statuses[i];
			}
			i++;
		}
		return ans;
	}

	// 第i个人的技能列表，是位信息！就是people[i]！people数组是固定参数
	// 一定要凑齐n个技能，n是固定参数
	// 当前来到第i个人了，可以要这个人，也可以不要这个人，i是可变信息！
	// 之前的技能，哪个技能凑到了，哪个技能还没凑到，就是status！你的目的是把它凑齐！也就是凑齐n个1！stauts是可变参数
	// 返回值的含义 :
	// 当前在people[i....]范围上选择人，之前凑齐的技能状态是status，请问所有技能都凑齐，还需要至少几个人
	// dp就是动态规划表，记录返回值的！
	public static int process(int[] people, int n, int i, int status, int[][] dp) {
		if (status == (1 << n) - 1) {
			// 技能已经凑齐了
			// 还需要0个人
			return 0;
		}
		if (i == people.length) {
			// 技能还没凑齐
			// 但是人已经没了
			// 怎么都凑不齐了
			return Integer.MAX_VALUE;
		}
		if (dp[i][status] != -1) {
			// 缓存命中，直接返回
			return dp[i][status];
		}
		// 不要第i个人，后续要几个人能凑齐
		int p1 = process(people, n, i + 1, status, dp);
		// 要第i个人，后续要几个人能凑齐
		int p2 = Integer.MAX_VALUE;
		int next2 = process(people, n, i + 1, status | people[i], dp);
		if (next2 != Integer.MAX_VALUE) {
			p2 = 1 + next2;
		}
		// 选择人数最少的方案
		int ans = Math.min(p1, p2);
		dp[i][status] = ans;
		return ans;
	}

}

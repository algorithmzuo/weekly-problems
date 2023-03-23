package class_2023_03_4_week;

// 来自学员问题
// 一共有n个项目，每个项目都有两个信息
// projects[i] = {a, b}
// 表示i号项目做完要a天，但是当你投入b个资源，它就会缩短1天的时间
// 你一共有k个资源，你的目标是完成所有的项目，但是希望总天数尽可能缩短
// 在所有项目同时开工的情况下，返回尽可能少的天数
// 1 <= n <= 10^5
// 1 <= k <= 10^7
public class Code01_MinDaysDoneAllProjects {

	// 这是二分答案法几乎最简单的题了
	// 不写对数器了
	public static int minDays(int[][] projects, int k) {
		// l......r
		// 0   所有项目中，天数的最大值
		int l = 0;
		int r = 0;
		// project[0] : 既定天数
		// project[1] : 投入多少资源能减少1天
		for (int[] project : projects) {
			r = Math.max(r, project[0]);
		}
		// l......r
		int m, ans = r;
		while (l <= r) {
			m = (l + r) / 2;
			if (yeah(projects, m) <= k) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	// 给你所有的项目！projects
	// 一定要在days天内完成！
	// 返回，需要的资源是多少！
	public static int yeah(int[][] projects, int days) {
		int ans = 0;
		for (int[] p : projects) {
			if (p[0] > days) {
				ans += (p[0] - days) * p[1];
			}
		}
		return ans;
	}

}

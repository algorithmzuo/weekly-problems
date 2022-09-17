package class_2022_10_1_week;

// 来自广联达
// 一张n*m的纸片，想切成k块
// 每一次切都一定是水平方向、或者竖直方向上贯穿纸片的，切的代价是长度的平方
// 返回切成k块的最小代价
public class Code03_CutPaperMinCost {

	public static int slove(int n, int m, int k) {
		if (k == 1) {
			return 0;
		}
		if (n * m < k) {
			return Integer.MAX_VALUE;
		}
		int next1 = Integer.MAX_VALUE;
		for (int up = 1; up < n; up++) {
			for (int upPick = 1; upPick < k; upPick++) {
				int upCost = slove(up, m, upPick);
				int downCost = slove(n - up, m, k - upPick);
				if (upCost != Integer.MAX_VALUE && downCost != Integer.MAX_VALUE) {
					next1 = Math.min(next1, upCost + downCost);
				}
			}
		}
		int p1 = next1 == Integer.MAX_VALUE ? Integer.MAX_VALUE : (m * m + next1);

		int next2 = Integer.MAX_VALUE;
		for (int left = 1; left < m; left++) {
			for (int leftPick = 1; leftPick < k; leftPick++) {
				int leftCost = slove(n, left, leftPick);
				int rightCost = slove(n, m - left, k - leftPick);
				if (leftCost != Integer.MAX_VALUE && leftCost != Integer.MAX_VALUE) {
					next2 = Math.min(next2, leftCost + rightCost);
				}
			}
		}
		int p2 = next2 == Integer.MAX_VALUE ? Integer.MAX_VALUE : (n * n + next2);
		return Math.min(p1, p2);
	}

}

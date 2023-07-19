package class_2023_07_3_week;

// 先来一个最近学员在国外面试的题
// 有一个由x轴和y轴组成的坐标系
// "y下"和"y上"表示一条无限延伸的道路，"y下"表示这个道路的下限，"y上"表示这个道路的上限
// 给定一批长方形，每一个长方形有(x1, x2, y1, y2)，4个坐标可以表示一个长方形
// 判断这条道路整体是不是可以走通的
// 以下为正式题目
// 图片在计算机处理中往往是使用二维矩阵来表示的
// 给你一个大小为 m x n 的二进制矩阵 image 表示一张黑白图片，0 代表白色像素，1 代表黑色像素
// 黑色像素相互连接，也就是说，图片中只会有一片连在一块儿的黑色像素。像素点是水平或竖直方向连接的
// 给你两个整数 x 和 y 表示某一个黑色像素的位置
// 请你找出包含全部黑色像素的最小矩形（与坐标轴对齐），并返回该矩形的面积
// 你必须设计并实现一个时间复杂度低于 O(m*n) 的算法来解决此问题。
// 测试链接 : https://leetcode.cn/problems/smallest-rectangle-enclosing-black-pixels/
public class Code01_SmallestRectangleEnclosingBlackPixels {

	public static int minArea(char[][] image, int x, int y) {
		int left = left(image, y);
		int right = right(image, y);
		int up = up(image, x, left, right);
		int down = down(image, x, left, right);
		return (right - left + 1) * (down - up + 1);
	}

	public static int left(char[][] image, int col) {
		// .....左？....... col
		int l = 0, r = col - 1, m, ans = col;
		boolean find;
		while (l <= r) {
			m = (l + r) / 2;
			find = false;
			for (int i = 0; i < image.length; i++) {
				if (image[i][m] == '1') {
					find = true;
					break;
				}
			}
			if (find) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static int right(char[][] image, int col) {
		int l = col + 1, r = image[0].length - 1, m, ans = col;
		boolean find;
		while (l <= r) {
			m = (l + r) / 2;
			find = false;
			for (int i = 0; i < image.length; i++) {
				if (image[i][m] == '1') {
					find = true;
					break;
				}
			}
			if (find) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static int up(char[][] image, int row, int left, int right) {
		int u = 0, d = row - 1, m, ans = row;
		boolean find;
		while (u <= d) {
			m = (u + d) / 2;
			find = false;
			for (int i = left; i <= right; i++) {
				if (image[m][i] == '1') {
					find = true;
					break;
				}
			}
			if (find) {
				ans = m;
				d = m - 1;
			} else {
				u = m + 1;
			}
		}
		return ans;
	}

	public static int down(char[][] image, int row, int left, int right) {
		int u = row + 1, d = image.length - 1, m, ans = row;
		boolean find;
		while (u <= d) {
			m = (u + d) / 2;
			find = false;
			for (int i = left; i <= right; i++) {
				if (image[m][i] == '1') {
					find = true;
					break;
				}
			}
			if (find) {
				ans = m;
				u = m + 1;
			} else {
				d = m - 1;
			}
		}
		return ans;
	}

}

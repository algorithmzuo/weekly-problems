package class_2021_12_2_week;

// 来自美团
// 所有黑洞的中心点记录在holes数组里
// 比如[[3,5] [6,9]]表示，第一个黑洞在(3,5)，第二个黑洞在(6,9)
// 并且所有黑洞的中心点都在左下角(0,0)，右上角(x,y)的区域里
// 飞船一旦开始进入黑洞，就会被吸进黑洞里
// 返回：
// 如果统一所有黑洞的半径，最大半径是多少，依然能保证飞船从(0,0)能到达(x,y)
public class Code02_AwayFromBlackHole {

	public static int maxRadius(int[][] holes, int x, int y) {
		int L = 1;
		int R = Math.max(x, y);
		int ans = 0;
		while (L <= R) {
			int M = (L + R) / 2;
			if (ok(holes, M, x, y)) {
				ans = M;
				L = M + 1;
			} else {
				R = M - 1;
			}
		}
		return ans;
	}

	public static boolean ok(int[][] holes, int r, int x, int y) {
		int n = holes.length;
		UnionFind uf = new UnionFind(holes, n, r);
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				if (touch(holes[i][0], holes[i][1], holes[j][0], holes[j][1], r)) {
					uf.union(i, j);
				}
				if (uf.block(i, x, y)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean touch(int x1, int y1, int x2, int y2, int r) {
		return (r << 1) > Math.sqrt((Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2)));
	}

	public static class UnionFind {
		public int[] father;
		public int[] size;
		public int[] xmin;
		public int[] xmax;
		public int[] ymin;
		public int[] ymax;
		public int[] help;

		public UnionFind(int[][] holes, int n, int r) {
			father = new int[n];
			size = new int[n];
			xmin = new int[n];
			xmax = new int[n];
			ymin = new int[n];
			ymax = new int[n];
			help = new int[n];
			for (int i = 0; i < n; i++) {
				father[i] = i;
				size[i] = 1;
				xmin[i] = holes[i][0] - r;
				xmax[i] = holes[i][0] + r;
				ymin[i] = holes[i][1] - r;
				ymax[i] = holes[i][1] + r;
			}
		}

		private int find(int i) {
			int hi = 0;
			while (i != father[i]) {
				help[hi++] = i;
				i = father[i];
			}
			for (hi--; hi >= 0; hi--) {
				father[help[hi]] = i;
			}
			return i;
		}

		public void union(int i, int j) {
			int fatheri = find(i);
			int fatherj = find(j);
			if (fatheri != fatherj) {
				int sizei = size[fatheri];
				int sizej = size[fatherj];
				int big = sizei >= sizej ? fatheri : fatherj;
				int small = big == fatheri ? fatherj : fatheri;
				father[small] = big;
				size[big] = sizei + sizej;
				xmin[big] = Math.min(xmin[big], xmin[small]);
				xmax[big] = Math.max(xmax[big], xmax[small]);
				ymin[big] = Math.min(ymin[big], ymin[small]);
				ymax[big] = Math.max(ymax[big], ymax[small]);
			}
		}

		public boolean block(int i, int x, int y) {
			i = find(i);
			return (xmin[i] <= 0 && xmax[i] >= x) || (ymin[i] <= 0 && ymax[i] >= y) || (xmin[i] <= 0 && ymin[i] <= 0)
					|| (xmax[i] >= x && ymax[i] >= y);
		}

	}

	public static void main(String[] args) {
		int[][] holes = { { 1, 2 }, { 4, 4 }, { 3, 0 }, { 5, 2 } };
		int x = 4;
		int y = 6;
		System.out.println(maxRadius(holes, x, y));
	}

}

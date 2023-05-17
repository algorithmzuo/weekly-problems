package class_2023_05_3_week;

// 保证一定是n*n的正方形，实现从里到外转圈打印的功能
// 如果n是奇数，中心点唯一，比如
// a b c
// d e f
// g h i
// e是中心点，依次打印 : e f i h g d a b c
// 如果n是偶数，中心点为最里层2*2的右下点
// 比如
// a b c d e f
// g h i j k l
// m n o p q r
// s t u v w x
// y z 0 1 2 3
// 4 5 6 7 8 9
// 最里层是
// o p
// u v
// v是中心点，依次打印 : v u o p q w ....
public class Code01_PrintFromInnerLoop {

	public static void print(char[][] m) {
		int n = m.length;
		for (int a = (n - 1) / 2, b = (n - 1) / 2, c = n / 2, d = n / 2; a >= 0; a--, b--, c++, d++) {
			loop(m, a, b, c, d);
		}
		System.out.println();
	}

	public static void loop(char[][] m, int a, int b, int c, int d) {
		if (a == c) {
			System.out.print(m[a][b] + " ");
		} else {
			for (int row = a + 1; row <= c; row++) {
				System.out.print(m[row][d] + " ");
			}
			for (int col = d - 1; col >= b; col--) {
				System.out.print(m[c][col] + " ");
			}
			for (int row = c - 1; row >= a; row--) {
				System.out.print(m[row][b] + " ");
			}
			for (int col = b + 1; col <= d; col++) {
				System.out.print(m[a][col] + " ");
			}
		}
	}

	public static void main(String[] args) {
		char[][] map1 = {
				{ 'a' }
				};
		print(map1);

		char[][] map2 = {
				{ 'a', 'b' },
				{ 'c', 'd' }
				};
		print(map2);

		char[][] map3 = {
				{ 'a', 'b', 'c' },
				{ 'd', 'e', 'f' },
				{ 'g', 'h', 'i' }
				};
		print(map3);

		char[][] map4 = {
				{ 'a', 'b', 'c', 'd' },
				{ 'e', 'f', 'g', 'h' },
				{ 'i', 'j', 'k', 'l' },
				{ 'm', 'n', 'o', 'p' }
				};
		print(map4);

		char[][] map5 = {
				{ 'a', 'b', 'c', 'd', 'e' },
				{ 'f', 'g', 'h', 'i', 'j' },
				{ 'k', 'l', 'm', 'n', 'o' },
				{ 'p', 'q', 'r', 's', 't' },
				{ 'u', 'v', 'w', 'x', 'y' }
				};
		print(map5);
		
		char[][] map6 = {
				{ 'a', 'b', 'c', 'd', 'e', 'f' },
				{ 'g', 'h', 'i', 'j', 'k', 'l' },
				{ 'm', 'n', 'o', 'p', 'q', 'r' },
				{ 's', 't', 'u', 'v', 'w', 'x' },
				{ 'y', 'z', '0', '1', '2', '3' },
				{ '4', '5', '6', '7', '8', '9' },
				};
		print(map6);
	}

}

package class_2022_06_1_week;

// 最短回文问题
// 测试链接 : https://ac.nowcoder.com/acm/contest/11218/B
// 提交以下代码，把主类名改成"Main"
import java.util.Scanner;

public class Code05_ShortestPalindrome {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			System.out.println(shortest(sc.next().toCharArray()));
		}
		sc.close();
	}

	// 因为求最短回文串
	// 所以只需要关心有没有长度为2、还有长度为3的
	// 因为更长的回文串，都可以变成长度为2，或者长度为3的
	// 比如 : aaccaa，消除左边，消除右边，就变成了cc
	// 再比如: abcba，可以变成bcb
	// 所以没有必要关心更长的，
	// 只需要关心有没有长度为2的；没有的话，看看有没有长度为3的；再没有，返回-1
	public static int shortest(char[] s) {
		for (int i = 0; i < s.length - 1; ++i) {
			if (s[i] == s[i + 1]) {
				return 2;
			}
		}
		for (int i = 0; i < s.length - 2; ++i) {
			if (s[i] == s[i + 2]) {
				return 3;
			}
		}
		return -1;
	}

}
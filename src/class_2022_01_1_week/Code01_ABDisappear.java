package class_2022_01_1_week;

// 给定一个只由'a'和'b'组成的字符串str，
// str中"ab"和"ba"子串都可以消除，消除之后剩下字符会重新在一起，继续消除...直到不能消除
// 你的任务是决定一种消除的顺序，最后让str消除到尽可能的短
// 返回尽可能的短的剩余字符串
public class Code01_ABDisappear {

	// 暴力尝试，尝试所有的可能性
	// 所有情况都枚举，所有先后消除的顺序都暴力尝试
	public static String disappear1(String str) {
		String ans = str;
		for (int i = 1; i < str.length(); i++) {
			boolean hasA = str.charAt(i - 1) == 'a' || str.charAt(i) == 'a';
			boolean hasB = str.charAt(i - 1) == 'b' || str.charAt(i) == 'b';
			if (hasA && hasB) {
				String curAns = disappear1(str.substring(0, i - 1) + str.substring(i + 1));
				if (curAns.length() < ans.length()) {
					ans = curAns;
				}
			}
		}
		return ans;
	}

	// 好一点的方法尝试
	// 遇到第一个能消除的就消除，
	// 剩下的字符串继续：遇到第一个能消除的就消除
	// 剩下的字符串继续：遇到第一个能消除的就消除
	// ...
	public static String disappear2(String str) {
		String ans = str;
		for (int i = 1; i < str.length(); i++) {
			boolean hasA = str.charAt(i - 1) == 'a' || str.charAt(i) == 'a';
			boolean hasB = str.charAt(i - 1) == 'b' || str.charAt(i) == 'b';
			if (hasA && hasB) {
				return disappear2(str.substring(0, i - 1) + str.substring(i + 1));
			}
		}
		return ans;
	}

	// 最优解，时间复杂度O(N)
	// 利用栈
	public static String disappear3(String s) {
		char[] str = s.toCharArray();
		int n = str.length;
		int[] stack = new int[n];
		int size = 0;
		for (int i = 0; i < n; i++) {
			boolean hasA = size != 0 && str[stack[size - 1]] == 'a';
			boolean hasB = size != 0 && str[stack[size - 1]] == 'b';
			hasA |= str[i] == 'a';
			hasB |= str[i] == 'b';
			if (hasA && hasB) {
				size--;
			} else {
				stack[size++] = i;
			}
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append(str[stack[i]]);
		}
		return builder.toString();
	}

	// 为了测试
	public static String randomString(int len, int varible) {
		char[] str = new char[len];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) ((int) (Math.random() * varible) + 'a');
		}
		return String.valueOf(str);
	}

	// 为了测试
	// 对数器，三种截然不同的方法进行验证
	public static void main(String[] args) {
		// 字符串最大长度，可以随意改变
		// 不过长度太大的话，方法一会超时
		int n = 12;
		// 字符的种类，可以随意改变
		int v = 2;
		// 测试次数
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			String test = randomString(len, v);
			String ans1 = disappear1(test);
			String ans2 = disappear2(test);
			String ans3 = disappear3(test);
			if (!ans1.equals(ans2) || !ans1.equals(ans3)) {
				System.out.println(test);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}

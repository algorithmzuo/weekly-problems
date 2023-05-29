package class_2023_05_5_week;

public class Code06_StringHash {

	// 暴力方法
	// 为了验证
	public static boolean rightCheck(String str, int l1, int l2, int len) {
		if (l1 + len > str.length() || l2 + len > str.length()) {
			return false;
		}
		if (l1 == l2) {
			return true;
		}
		return str.substring(l1, l1 + len).equals(str.substring(l2, l2 + len));
	}

	// 哈希方法检测
	public static int MAXN = 100005;

	public static long[] pow = new long[MAXN];

	public static long[] hash = new long[MAXN];

	public static int base = 1000000007;

	public static void build(String str, int n) {
		pow[0] = 1;
		for (int j = 1; j < n; j++) {
			pow[j] = pow[j - 1] * base;
		}
		hash[0] = str.charAt(0) - 'a' + 1;
		for (int j = 1; j < n; j++) {
			hash[j] = hash[j - 1] * base + str.charAt(j) - 'a' + 1;
		}
	}

	public static boolean hashCheck(int n, int l1, int l2, int len) {
		int r1 = l1 + len - 1;
		int r2 = l2 + len - 1;
		if (r1 >= n || r2 >= n) {
			return false;
		}
		return hash(l1, r1) == hash(l2, r2);
	}

	public static long hash(int l, int r) {
		long ans = hash[r];
		ans -= l == 0 ? 0 : (hash[l - 1] * pow[r - l + 1]);
		return ans;
	}

	// 为了测试
	public static String randomString(int len, int v) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ('a' + (int) (Math.random() * v));
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static void main(String[] args) {
		String test = "abcabcabcabcabcabcabcabc";
		int size = test.length();
		build(test, size);
		System.out.println(hashCheck(size, 6, 15, 3));

		System.out.println("测试开始");
		int N = 10000;
		int V = 3;
		int testTeams = 100;
		int testTimes = 5000;
		int LEN = 6;
		for (int i = 0; i < testTeams; i++) {
			int n = (int) (Math.random() * N) + 1;
			String str = randomString(n, V);
			build(str, n);
			for (int k = 0; k <= testTimes; k++) {
				int l1 = (int) (Math.random() * n);
				int l2 = (int) (Math.random() * n);
				int len = (int) (Math.random() * LEN) + 1;
				boolean ans1 = rightCheck(str, l1, l2, len);
				boolean ans2 = hashCheck(n, l1, l2, len);
				if (ans1 != ans2) {
					System.out.println("出错了!");
					break;
				}
			}
		}
		System.out.println("测试结束");
	}

}

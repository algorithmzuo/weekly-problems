package class_2023_05_5_week;

// 字符串哈希原理和实现
// 课上讲的时候有错误，代码没错，解释有错
// 重新解释一下:
// 比如p = 233, 也就是课上说的选择的质数进制
// " 3 1 2 5 6 ..."
//   0 1 2 3 4
// hash[0] = 3 * p的0次方
// hash[1] = 3 * p的1次方 + 1 * p的0次方
// hash[2] = 3 * p的2次方 + 1 * p的1次方 + 2 * p的0次方
// hash[3] = 3 * p的3次方 + 1 * p的2次方 + 2 * p的1次方 + 5 * p的0次方
// hash[4] = 3 * p的4次方 + 1 * p的3次方 + 2 * p的2次方 + 5 * p的1次方 + 6 * p的0次方
// 次方是倒过来的，课上讲错了
// 所以hash[i] = hash[i-1] * p + arr[i]，这个方式就可以得到上面说的意思
// 于是，你想得到子串"56"的哈希值
// 子串"56"的哈希值 = hash[4] - hash[2]*p的2次方(就是子串"56"的长度次方)
// hash[4] = 3 * p的4次方 + 1 * p的3次方 + 2 * p的2次方 + 5 * p的1次方 + 6 * p的0次方
// hash[2] = 3 * p的2次方 + 1 * p的1次方 + 2 * p的0次方
// hash[2] * p的2次方 = 3 * p的4次方 + 1 * p的3次方 + 2 * p的2次方
// 所以hash[4] - hash[2] * p的2次方 = 5 * p的1次方 + 6 * p的0次方
// 这样就得到子串"56"的哈希值了
// 抱歉，课上讲错了。应该是上面的方式。
// 所以，子串s[l...r]的哈希值 = hash[r] - hash[l-1] * p的(r-l+1)次方
// 也就是说，hash[l-1] * p的(r-l+1)次方，正好和hash[r]所代表的信息，前面对齐了
// 减完之后，正好就是子串s[l...r]的哈希值
// 代码是对的，后续的题目实现也是对的，就是解释错了，抱歉抱歉！
public class Code04_StringHash {

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

	public static int base = 499;

	public static void build(String str, int n) {
		pow[0] = 1;
		for (int j = 1; j < n; j++) {
			pow[j] = pow[j - 1] * base;
		}
		// a -> 1
		// b -> 2
		// c -> 3
		// z -> 26
		// 前缀和的哈希值
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

	// s[l...r]
	public static long hash(int l, int r) {
		// hash[0] : s[0...0]
		// hash[5] : s[0...5]
		// hash[i] : s[0...i]
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

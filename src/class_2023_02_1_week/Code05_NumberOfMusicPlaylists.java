package class_2023_02_1_week;

// 你的音乐播放器里有 N 首不同的歌
// 在旅途中，你的旅伴想要听 L 首歌（不一定不同，即，允许歌曲重复
// 请你为她按如下规则创建一个播放列表
// 每首歌至少播放一次
// 一首歌只有在其他 K 首歌播放完之后才能再次播放
// 返回可以满足要求的播放列表的数量
// 由于答案可能非常大，请返回它模 10^9 + 7 的结果
// 测试链接 : https://leetcode.cn/problems/number-of-music-playlists/
public class Code05_NumberOfMusicPlaylists {

	public static int mod = 1000000007;

	public static int limit = 100;

	// 阶乘表
	public static long[] fac = new long[limit + 1];

	// 阶乘结果的乘法逆元表
	public static long[] inv = new long[limit + 1];

	static {
		fac[0] = inv[0] = 1;
		for (int i = 1; i <= limit; i++) {
			fac[i] = ((long) i * fac[i - 1]) % mod;
		}
		// 费马小定理计算乘法逆元
//		for (int i = 1; i <= limit; i++) {
//			inv[i] = power(fac[i], mod - 2);
//		}
		// 费马小定理计算乘法逆元，优化如下
		inv[limit] = power(fac[limit], mod - 2);
		for (int i = limit; i > 1; i--) {
			inv[i - 1] = ((long) i * inv[i]) % mod;
		}
	}

	public static long power(long x, int n) {
		long ans = 1;
		while (n > 0) {
			if ((n & 1) == 1) {
				ans = (ans * x) % mod;
			}
			x = (x * x) % mod;
			n >>= 1;
		}
		return ans;
	}

	public static int numMusicPlaylists(int n, int l, int k) {
		long cur, ans = 0;
		for (int i = 0, sign = 1; i <= n - k; i++, sign = sign == 1 ? (mod - 1) : 1) {
			cur = (sign * power(n - k - i, l - k)) % mod;
			cur = (cur * fac[n]) % mod;
			cur = (cur * inv[i]) % mod;
			cur = (cur * inv[n - k - i]) % mod;
			ans = (ans + cur) % mod;
		}
		return (int) ans;
	}

	public static void main(String[] args) {

	}

}

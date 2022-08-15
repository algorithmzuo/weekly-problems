package class_2022_08_5_week;

// 测试链接 : https://www.nowcoder.com/practice/e391767d80d942d29e6095a935a5b96b
// 提交如下代码，把主类名改成Main，可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Code02_MinAddToMatch {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(minAdd(line.toCharArray()));
		}
	}

	public static int minAdd(char[] s) {
		int n = s.length;
		int[][] dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dp[i][j] = -1;
			}
		}
		return process(s, 0, s.length - 1, dp);
	}

	// 让s[l...r]都完美匹配
	// 至少需要加几个字符
	public static int process(char[] s, int l, int r, int[][] dp) {
		// 只有一个字符，不管是什么，要想配对，都需要添加一个字符
		if (l == r) {
			return 1;
		}
		// 只有两个字符，
		// 如果是()、[]，那什么也不需要添加
		// 否则，都需要添加2个字符
		if (l == r - 1) {
			if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']')) {
				return 0;
			}
			return 2;
		}
		if (dp[l][r] != -1) {
			return dp[l][r];
		}
		// 重点是如下的过程
		// 可能性1，先搞定l+1...r，然后搞定l
		// 比如s[l...r] = ([][]
		// 先搞定[][]，需要添加0个，然后搞定(，需要添加1个
		// 整体变成([][])搞定
		int p1 = 1 + process(s, l + 1, r, dp);
		// 可能性2，先搞定l...r-1，然后搞定r
		// 和可能性1同理
		int p2 = 1 + process(s, l, r - 1, dp);
		// 可能性3，s[l]和s[r]天然匹配，需要搞定的就是l+1..r-1
		// 比如([[)，搞定中间的[[，就是最优解了
		int p3 = Integer.MAX_VALUE;
		if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']')) {
			p3 = process(s, l + 1, r - 1, dp);
		}
		// 可能性后续：可能，最优解并不是l....r整体变成最大的嵌套
		// 而是，并列关系！
		// l....split 先变成合法
		// split+1...r 再变成合法
		// 是并列的关系！
		// 比如(())[[]]
		// l...split : (())
		// split+1...r : [[]]
		// 这种并列关系下，有可能出最优解
		// 所以，枚举每一个可能的并列划分点(split)
		int ans = Math.min(p1, Math.min(p2, p3));
		for (int split = l; split < r; split++) {
			ans = Math.min(ans, process(s, l, split, dp) + process(s, split + 1, r, dp));
		}
		dp[l][r] = ans;
		return ans;
	}

}

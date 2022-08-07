package class_2021_12_4_week;

// 有m个同样的苹果，认为苹果之间无差别
// 有n个同样的盘子，认为盘子之间也无差别
// 还有，比如5个苹果如果放进3个盘子，
// 那么1、3、1和1、1、3和3、1、1的放置方法，也认为是一种方法
// 如上的设定下，返回有多少种放置方法
// 测试链接 : https://www.nowcoder.com/practice/bfd8234bb5e84be0b493656e390bdebf
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_SplitApples {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int m = (int) in.nval;
			in.nextToken();
			int n = (int) in.nval;
			out.println(ways3(m, n));
			out.flush();
		}
	}

	// 思路来自于分裂数问题
	// 体系学习班代码第22节，题目3，split number问题
	public static int ways1(int apples, int plates) {
		return process1(1, apples, plates);
	}

	// pre : 上一个盘子分到的苹果数量，当前的盘子分到的数量不能小于pre
	// apples : 剩余的苹果数量
	// plates : 剩余的盘子数量
	// 在盘子够用的情况下，把苹果分完，有几种方法
	public static int process1(int pre, int apples, int plates) {
		if (apples == 0) {
			return 1;
		}
		// apples != 0
		if (plates == 0) {
			return 0;
		}
		// apples != 0 && plates != 0
		if (pre > apples) {
			return 0;
		}
		// apples != 0 && plates != 0 && pre <= apples
		int way = 0;
		// 之前的盘子分了3个苹果，现在还剩下8个苹果
		// 当前的盘子，可以装几个苹果：3、4、5、6、7、8
		for (int cur = pre; cur <= apples; cur++) {
			way += process1(cur, apples - cur, plates - 1);
		}
		return way;
	}

	// 新的尝试，最优解
	// 苹果有apples个，盘子有plates个
	// 返回有几种摆法
	// 如果苹果数为0，有1种摆法：什么也不摆
	// 如果苹果数不为0，但是盘子数为0，有0种摆法（做不到）
	// 如果苹果数不为0，盘子数也不为0，进行如下的情况讨论：
	// 假设苹果数为apples，盘子数为plates
	// 可能性 1) apples < plates
	// 这种情况下，一定有多余的盘子，这些盘子完全没用，所以砍掉
	// 后续是f(apples, apples)
	// 可能性 2) apples >= plates
	// 在可能性2)下，讨论摆法，有如下两种选择
	// 选择a) 不是所有的盘子都使用
	// 选择b) 就是所有的盘子都使用
	// 对于选择a)，既然不是所有盘子都使用，那么后续就是f(apples, plates - 1)
	// 意思是：既然不是所有盘子都使用，那盘子减少一个，然后继续讨论吧！
	// 对于选择b)，既然就是所有的盘子都使用，那么先把所有盘子都摆上1个苹果。
	// 剩余苹果数 = apples - plates
	// 然后继续讨论，剩下的这些苹果，怎么摆进plates个盘子里，
	// 所以后续是f(apples - plates, plates)
	public static int ways2(int apples, int plates) {
		if (apples == 0) {
			return 1;
		}
		if (plates == 0) {
			return 0;
		}
		if (plates > apples) {
			return ways2(apples, apples);
		} else { // apples >= plates;
			return ways2(apples, plates - 1) + ways2(apples - plates, plates);
		}
	}

	// 上面最优解尝试的记忆化搜索版本
	public static int[][] dp = null;

	public static int ways3(int apples, int plates) {
		if (dp == null) {
			dp = new int[11][11];
			for (int i = 0; i <= 10; i++) {
				Arrays.fill(dp[i], -1);
			}
		}
		return process3(apples, plates, dp);
	}

	public static int process3(int apples, int plates, int[][] dp) {
		if (dp[apples][plates] != -1) {
			return dp[apples][plates];
		}
		int ans = 0;
		if (apples == 0) {
			ans = 1;
		} else if (plates == 0) {
			ans = 0;
		} else if (plates > apples) {
			ans = process3(apples, apples, dp);
		} else {
			ans = process3(apples, plates - 1, dp) + process3(apples - plates, plates, dp);
		}
		dp[apples][plates] = ans;
		return ans;
	}

}
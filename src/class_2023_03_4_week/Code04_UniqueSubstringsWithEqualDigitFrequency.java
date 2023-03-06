package class_2023_03_4_week;

import java.util.Arrays;
import java.util.HashSet;

// 测试链接 : https://leetcode.cn/problems/unique-substrings-with-equal-digit-frequency/
public class Code04_UniqueSubstringsWithEqualDigitFrequency {

	// 如下的方法是帖子上的一个很骚的方法
	// 其实是不对的，只是可以通过所有当前的测试用例而已
	// 可以构造出让这种方法不通过的例子，原因是这种简陋的hash函数太容易碰撞了
	// 其实这个题的最优解，依然需要使用DC3算法生成后缀数组来做
	// 但是很难，具体可以参考LongestChunkedPalindromeDecomposition问题
	// 课上会简单提一下，详细的就不讲了，因为很少考这么难
	// 课上重点讲一下这个很骚的方法，构造了简陋的hash函数，算是一种博闻强识吧
	public static int equalDigitFrequency(String s) {
		long base = 1000000007;
		HashSet<Long> set = new HashSet<>();
		int[] cnts = new int[10];
		for (int l = 0; l < s.length(); l++) {
			Arrays.fill(cnts, 0);
			long hashCode = 0;
			int curVal, maxCnt = 0, maxKinds = 0, allKinds = 0;
			for (int r = l; r < s.length(); r++) {
				curVal = s.charAt(r) - '0';
				hashCode = hashCode * base + curVal + 1;
				cnts[curVal]++;
				if (cnts[curVal] == 1) {
					allKinds++;
				}
				if (cnts[curVal] > maxCnt) {
					maxCnt = cnts[curVal];
					maxKinds = 1;
				} else if (cnts[curVal] == maxCnt) {
					maxKinds++;
				}
				if (maxKinds == allKinds) {
					set.add(hashCode);
				}
			}
		}
		return set.size();
	}

}

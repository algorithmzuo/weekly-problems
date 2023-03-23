package class_2023_03_4_week;

import java.util.Arrays;
import java.util.HashSet;

// 给你一个由数字组成的字符串 s，返回 s 中独特子字符串数量
// 其中的每一个数字出现的频率都相同。
// 测试链接 : https://leetcode.cn/problems/unique-substrings-with-equal-digit-frequency/
public class Code05_UniqueSubstringsWithEqualDigitFrequency {

//	public static int number(String s) {
//		int ans = 1;
//		long base = 1000000007;
//		long hashCode = 0;
//		HashSet<String> set = new HashSet<>();
//		for (int start = 0; start < s.length(); start++) {
//			for (int end = start; end < s.length(); end++) {
//				// start...end 是不是独特的！
//				// 判断是不是独特，搞定了！O(1)
//				// 1) 词频表
//				// 2) 总的种类数量
//				// 3) 最大次
//				// 4) 达到最大次的种类数!
//				// 更新的时候，O(1)，判断O(1)
//				// 如果是独特的！s[start....end] 加入到set
//				// 哈希表会帮助去重！
//				// '0'  -> 0
//				// '1'  -> 1
//				// '2'  -> 2
//				int curVal = s.charAt(end) - '0';
//				
//				hashCode = hashCode * base + curVal + 1;
//
//			}
//		}
//		return set.size();
//	}
//	

	// 如下的方法是帖子上的一个很骚的方法
	// 其实是不对的，只是可以通过所有当前的测试用例而已
	// 可以构造出让这种方法不通过的例子，原因是这种简陋的hash函数太容易碰撞了
	// 其实这个题的最优解，依然需要使用DC3算法生成后缀数组来做
	// 但是很难，具体可以参考LongestChunkedPalindromeDecomposition问题
	// 课上会简单提一下，详细的就不讲了，因为很少考这么难
	// 课上重点讲一下这个很骚的方法，构造了简陋的hash函数，算是一种博闻强识吧
	// 时间复杂度O(N^2)，确实无法更好
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
				// l....r 字符串的哈希值
				// l...r-1 算出的哈希值 * base + 当前位 + 1
				// 真的哈希函数，不是这样的！
				// 但是对于一般考试的数据量，真的不会重!
				// 很荒谬，但好用
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

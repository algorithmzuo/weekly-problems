package class_2023_07_1_week;

// 先来一个最近国外同学考的题目
// 已知一些供应点的位置，一共n个供应点
// 其中有n-1个供应点一定都在x轴上，比如(15,0)位置，(2,0)位置等
// 只有1个供应点不在x轴上，比如(23,17)位置
// 给出每个供应点的位置，并且给定第k号供应点是出发点
// 要求每个供应点最多走过2次，返回从k点出发，走完所有供应点的最少距离
// 上面这个题没有代码实现
// 因为这个题就是彻底的业务分析，只有一系列的贪心设计，代码也不难写
// 以下是这节课的正式题
// 一个句子是由一些单词与它们之间的单个空格组成
// 且句子的开头和结尾没有多余空格
// 比方说，"Hello World" ，"HELLO" ，"hello world hello world" 都是句子
// 每个单词都 只 包含大写和小写英文字母
// 如果两个句子 sentence1 和 sentence2
// 可以通过往其中一个句子插入一个任意的句子（可以是空句子）而得到另一个句子
// 那么我们称这两个句子是 相似的
// 比方说，sentence1 = "Hello my name is Jane" 
// 且 sentence2 = "Hello Jane"
// 我们可以往 sentence2 中 "Hello" 和 "Jane" 之间插入 "my name is"
// 得到 sentence1
// 给你两个句子 sentence1 和 sentence2
// 如果 sentence1 和 sentence2 是相似的，请你返回 true ，否则返回 false
// 测试链接 : https://leetcode.cn/problems/sentence-similarity-iii/
public class Code01_SentenceSimilarityIII {

	public static boolean areSentencesSimilar(String s1, String s2) {
		String[] w1 = s1.split(" ");
		String[] w2 = s2.split(" ");
		int i = 0, j = 0, n1 = w1.length, n2 = w2.length;
		while (i < n1 && i < n2 && w1[i].equals(w2[i])) {
			i++;
		}
		while (n1 - j > i && n2 - j > i && w1[n1 - 1 - j].equals(w2[n2 - 1 - j])) {
			j++;
		}
		return i + j == Math.min(n1, n2);
	}

}

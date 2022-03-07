package class_2022_03_3_week;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

// 来自字节飞书团队
// 语法补全功能，比如"as soon as possible"
// 当我们识别到"as soon as"时, 基本即可判定用户需要键入"possible"
// 设计一个统计词频的模型，用于这个功能
// 类似(prefix, next word)这样的二元组
// 比如一个上面的句子"as soon as possible"
// 有产生如下的二元组(as, soon, 1)、(as soon, as, 1)、(as soon as, possible, 1)
// 意思是这一个句子产生了如下的统计：
// 当前缀为"as"，接下来的单词是"soon"，有了1个期望点
// 当前缀为"as soon"，接下来的单词是"as"，有了1个期望点
// 当前缀为"as soon as"，接下来的单词是"possible"，有了1个期望点
// 那么如果给你很多的句子，当然就可以产生很多的期望点，同一个前缀下，同一个next word的期望点可以累加
// 现在给你n个句子，让你来建立统计
// 然后给你m个句子，作为查询
// 最后给你k，表示每个句子作为前缀的情况下，词频排在前k名的联想
// 返回m个结果，每个结果最多k个单词
public class Code03_AiFill {

	public static class TrieNode {
		public String word;
		public int times;
		public HashMap<String, TrieNode> nextNodes;
		public TreeSet<TrieNode> nextRanks;

		public TrieNode(String w) {
			word = w;
			times = 1;
			nextNodes = new HashMap<>();
			nextRanks = new TreeSet<>((a, b) -> a.times != b.times ? (b.times - a.times) : a.word.compareTo(b.word));
		}

	}

	public static class AI {
		public TrieNode root;
		public int topk;

		public AI(List<String> sentences, int k) {
			root = new TrieNode("");
			topk = k;
			for (String sentence : sentences) {
				fill(sentence);
			}
		}

		public void fill(String sentence) {
			TrieNode cur = root;
			TrieNode next = null;
			for (String word : sentence.split(" ")) {
				if (!cur.nextNodes.containsKey(word)) {
					next = new TrieNode(word);
					cur.nextNodes.put(word, next);
					cur.nextRanks.add(next);
				} else {
					next = cur.nextNodes.get(word);
					cur.nextRanks.remove(next);
					next.times++;
					cur.nextRanks.add(next);
				}
				cur = next;
			}
		}

		public List<String> suggest(String sentence) {
			List<String> ans = new ArrayList<>();
			TrieNode cur = root;
			TrieNode next = null;
			for (String word : sentence.split(" ")) {
				if (!cur.nextNodes.containsKey(word)) {
					next = new TrieNode(word);
					cur.nextNodes.put(word, next);
					cur.nextRanks.add(next);
				} else {
					next = cur.nextNodes.get(word);
					cur.nextRanks.remove(next);
					next.times++;
					cur.nextRanks.add(next);
				}
				cur = next;
			}
			for (TrieNode n : cur.nextRanks) {
				ans.add(n.word);
				if (ans.size() == topk) {
					break;
				}
			}
			return ans;
		}

	}

	public static void main(String[] args) {
		ArrayList<String> sentences = new ArrayList<>();
		sentences.add("i think you are good");
		sentences.add("i think you are fine");
		sentences.add("i think you are good man");
		int k = 2;
		AI ai = new AI(sentences, k);
		for (String ans : ai.suggest("i think you are")) {
			System.out.println(ans);
		}
		System.out.println("=====");
		ai.fill("i think you are fucking good");
		ai.fill("i think you are fucking great");
		ai.fill("i think you are fucking genius");
		for (String ans : ai.suggest("i think you are")) {
			System.out.println(ans);
		}
		System.out.println("=====");
	}

}

package class_2022_03_3_week;

// 来自字节飞书团队
// 众所周知，飞书自带英文语法补全的功能，有效提升了用户聊天时的输入效率
// 语法补全功能，比如"as soon as possible"
// 当我们识别到"as soon as p"时, 基本即可判定用户需要键入"possible"
// 因此我们向用户展示提示补全即可, 请设计一个统计词频的模型
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

	public static class AI {

	}

}

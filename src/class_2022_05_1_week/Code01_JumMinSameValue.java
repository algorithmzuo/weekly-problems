package class_2022_05_1_week;

import java.util.ArrayList;
import java.util.HashMap;

// 来自蔚来汽车
// 给你一个整数数组 arr ，你一开始在数组的第一个元素处（下标为 0）。
// 每一步，你可以从下标 i 跳到下标 i + 1 、i - 1 或者 j ：
// i + 1 需满足：i + 1 < arr.length
// i - 1 需满足：i - 1 >= 0
// j 需满足：arr[i] == arr[j] 且 i != j
// 请你返回到达数组最后一个元素的下标处所需的 最少操作次数 。
// 注意：任何时候你都不能跳到数组外面。
// leetcode测试链接 : https://leetcode-cn.com/problems/jump-game-iv/
public class Code01_JumMinSameValue {

	public static int minJumps(int[] arr) {
		int n = arr.length;
		// 为了找某个值，有哪些位置，能快一些
		// key : 某个值9，
		// value : 列表：0，7，19
		HashMap<Integer, ArrayList<Integer>> valueIndex = new HashMap<>();
		for (int i = 0; i < n; i++) {
			if (!valueIndex.containsKey(arr[i])) {
				valueIndex.put(arr[i], new ArrayList<>());
			}
			valueIndex.get(arr[i]).add(i);
		}
		// i会有哪些展开：左，右，i通过自己的值，能蹦到哪些位置上去
		// 宽度优先遍历，遍历过的位置，不希望重复处理
		// visited[i] == false：i位置，之前没来过，可以处理
		// visited[i] == true : i位置，之前来过，可以跳过
		boolean[] visited = new boolean[n];
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		// 0位置加到队列里去
		queue[r++] = 0;
		visited[0] = true;
		int jump = 0;
		// 宽度优先遍历
		// 一次，遍历一整层！
		// 该技巧，多次出现！
		while (l != r) { // 队列里还有东西的意思！
			// 此时的r记录！
			// 0 1 2 | 3 4 5 6 7 8
			// 当前层的终止位置
			int tmp = r;
			for (; l < tmp; l++) { // 遍历当前层！
				int cur = queue[l];
				if (cur == n - 1) {
					return jump;
				}
				if (cur + 1 < n && !visited[cur + 1]) {
					visited[cur + 1] = true;
					queue[r++] = cur + 1;
				}
				// cur > 0  cur - 1 >=0
				if (cur > 0 && !visited[cur - 1]) {
					visited[cur - 1] = true;
					queue[r++] = cur - 1;
				}
				// i -> 9
				// 值同样为9的那些位置，也能去
				for (int next : valueIndex.get(arr[cur])) {
					if (!visited[next]) {
						visited[next] = true;
						queue[r++] = next;
					}
				}
				// 重要优化！
				valueIndex.get(arr[cur]).clear();
			}
			jump++;
		}
		return -1;
	}

}

package class_2022_10_1_week;

public class Code02_SplitTowEqualSumSubs {

	public static int nums(int[] arr) {
		return process(arr, 0, 0);
	}

	// i...最后，这个范围上，所有的子序列里。
	// 如果一个子序列，分成两部分，可以随意分
	// 哪怕其中一种分法，能做到让：大集合累加和 - 小集合累加和 == diff
	// 这个子序列就算达标
	// 返回达标的子序列有多少个
	public static int process(int[] arr, int i, int diff) {
		if (i == arr.length) {
			return diff == 0 ? 1 : 0;
		}
		// 第一种可能性，不要i位置的数，所得到的达标子序列个数
		int p1 = process(arr, i + 1, diff);
		// 第二种可能性，要i位置的数，所得到的达标子序列个数
		int p2 = 0;
		if (diff == 0) {
			// 如果diff == 0
			// 如果当前数字是5
			// 那么需要i+1...最后，nextDiff = 5的达标子序列个数
			// 然后5加入到小集合里去，就能做到diff == 0了
			p2 = process(arr, i + 1, arr[i]);
		} else {
			// 如果diff != 0
			if (arr[i] >= diff) {
				// 如果当前数字是5，diff == 3
				// 可以让后续的子序列中，大集合 - 小集合 = 8
				// 然后5加入到小集合里去，能做到最终的diff == 3
				// 也可以让后续的子序列中，大集合 - 小集合 = 2
				// 然后5加入到小集合里去，就让小集合变成了比大集合更大的集合
				// 小集合 + 5 - 大集合 == 3，即最终diff == 3
				p2 = process(arr, i + 1, arr[i] - diff) + process(arr, i + 1, arr[i] + diff);
			} else {
				// 如果当前数字是3，diff == 5
				// 可以让后续的子序列中，大集合 - 小集合 = 8
				// 然后3加入到小集合里去，能做到最终的diff == 5
				// 也可以让后续的子序列中，大集合 - 小集合 = 2
				// 然后3加入到大集合里去，大集合 + 3 - 小集合 == 5
				p2 = process(arr, i + 1, diff - arr[i]) + process(arr, i + 1, arr[i] + diff);
			}
		}
		return p1 + p2;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4 };
		System.out.println(nums(arr));
	}

}

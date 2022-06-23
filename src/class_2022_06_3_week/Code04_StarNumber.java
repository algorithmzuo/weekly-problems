package class_2022_06_3_week;

// 一个字符串s，表示仓库的墙 与 货物，其中'|'表示墙,'*'表示货物。
// 给定一个起始下标start和一个终止下标end，
// 找出子串中 被墙包裹的货物 数量
// 比如
// s = "|**|**|*"
// start = 1, end = 7
// start和end截出的子串是 "**|**|*"
// 被 '|'包裹的 '*' 有两个，所以返回2
// 现在给定一系列的start，startIndices[]，和对应一系列的end ,endIndices[]
// 返回每一对[start,end]的截出来的货物数量
// 数据规模：
// 字符串s长度<=10^5
// startIndices长度 == endIndices长度 <=10^5
public class Code04_StarNumber {

	// 解法
	// 生成每个位置左边离这个位置最近的'|'在哪，left数组
	// 生成每个位置右边离这个位置最近的'|'在哪，right数组
	// 生成每个位置左边一共有几个'*'，sum数组
	// 比如，s如下，第一行是字符串，第二行是下标
	// | * * | * * | *
	// 0 1 2 3 4 5 6 7
	// 生成的left数组，第一行是值，第二行是下标
	// 0 0 0 3 3 3 6 6
	// 0 1 2 3 4 5 6 7
	// 生成的right数组，第一行是值，第二行是下标
	// 0 3 3 3 6 6 6 -1
	// 0 1 2 3 4 5 6 7
	// 生成的sum数组，第一行是值，第二行是下标
	// 0 1 2 2 3 4 4 5
	// 0 1 2 3 4 5 6 7
	// 比如，start = 1, end = 7
	// 找到start右边最近的'|'在哪，根据right直接查到，在3位置
	// 找到end左边最近的'|'在哪，根据left直接查到，在6位置
	// 1~7范围上被墙包裹*的个数 = 3~6范围上被墙包裹*的个数
	// 3~6范围上被墙包裹*的个数 = sum[6] - sum[2] = 2
	public static int[] number(String s, int[] starts, int[] ends) {
		char[] str = s.toCharArray();
		int n = str.length;
		int[] left = new int[n];
		int[] right = new int[n];
		int[] sum = new int[n];

		int pre = -1;
		int num = 0;
		for (int i = 0; i < n; i++) {
			pre = str[i] == '|' ? i : pre;
			num += str[i] == '*' ? 1 : 0;
			left[i] = pre;
			sum[i] = num;
		}
		pre = -1;
		for (int i = n - 1; i >= 0; i--) {
			pre = str[i] == '|' ? i : pre;
			right[i] = pre;
		}

		int m = starts.length;
		int[] ans = new int[m];
		for (int i = 0; i < m; i++) {
			ans[i] = stars(starts[i], ends[i], left, right, sum);
		}
		return ans;
	}

	public static int stars(int start, int end, int[] l, int[] r, int[] s) {
		int left = r[start];
		int right = l[end];
		if (left == -1 || right == -1 || (left >= right)) {
			return 0;
		}
		return left == 0 ? s[right] : (s[right] - s[left - 1]);
	}

	public static void main(String[] args) {
		String s = "|**|**|*";
		int[] a = new int[] { 0, 1, 3, 4 };
		int[] b = new int[] { 7, 7, 6, 5 };
		int[] arr = number(s, a, b);
		for (int ans : arr) {
			System.out.println(ans);
		}
	}

}

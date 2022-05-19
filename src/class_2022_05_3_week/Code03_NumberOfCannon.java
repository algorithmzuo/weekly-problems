package class_2022_05_3_week;

import java.util.TreeMap;
import java.util.TreeSet;

// 来自学员问题
// 给定一个数组arr，表示从早到晚，依次会出现的导弹的高度
// 大炮打导弹的时候，如果一旦大炮定了某个高度去打，那么这个大炮每次打的高度都必须下降一点
// 1) 如果只有一个大炮，返回最多能拦截多少导弹
// 2) 如果所有的导弹都必须拦截，返回最少的大炮数量
public class Code03_NumberOfCannon {

	public static int numOfCannon(int[] arr) {
		// key : 某个大炮打的结尾数值
		// value : 有多少个大炮有同样的结尾数值
		// 比如：
		// 一共有A、B、C三个大炮
		// 如果A大炮此时打的高度是17，B大炮此时打的高度是7，C大炮此时打的高度是13
		// 那么在表中：
		// 7, 1
		// 13, 1
		// 17, 1
		// 如果A大炮此时打的高度是13，B大炮此时打的高度是7，C大炮此时打的高度是13
		// 那么在表中：
		// 7, 1
		// 13, 2
		TreeMap<Integer, Integer> ends = new TreeMap<>();
		for (int num : arr) {
			if (ends.ceilingKey(num + 1) == null) {
				ends.put(Integer.MAX_VALUE, 1);
			}
			int ceilKey = ends.ceilingKey(num + 1);
			if (ends.get(ceilKey) > 1) {
				ends.put(ceilKey, ends.get(ceilKey) - 1);
			} else {
				ends.remove(ceilKey);
			}
			ends.put(num, ends.getOrDefault(num, 0) + 1);
		}
		int ans = 0;
		for (int value : ends.values()) {
			ans += value;
		}
		return ans;
	}

	public static void main(String[] args) {

		// 有序表来说
		// add
		// remove
		// ceiling
		// <= floor
		// O(logN)!
		TreeSet<Integer> set = new TreeSet<>();

		set.add(17);
		set.add(20);
		set.add(25);

		// >= 23
		System.out.println(set.ceiling(26));

		// 有序表是去重的，key去重
		// A ：99
		// B : 99
		// C : 99

		TreeMap<Integer, Integer> map = new TreeMap<>();
		map.put(99, 3);
		// 76
		
		if (map.ceilingKey(76) == null) {
			//  没有大炮可以打76
			// 新开一门大炮，打76
			// 这个新跑，只能打75~
			map.put(75, map.getOrDefault(75, 0) + 1);
		} else { // 之前有大炮可以打76，不需要新开一门炮！
			int key = map.ceilingKey(76);
			// 99  -1    75 +1
			
			if(map.get(key)  > 1) {
				map.put(key, map.get(key) - 1);
			}else {
				map.remove(key);
			}
			
			map.put(75, map.getOrDefault(75, 0) + 1);
		}
		

//		
//		int[] arr = { 15, 7, 14, 6, 5, 13, 5, 10, 9 };
//		System.out.println(numOfCannon(arr));
	}

}

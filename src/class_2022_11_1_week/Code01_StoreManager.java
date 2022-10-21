package class_2022_11_1_week;

import java.util.HashMap;
import java.util.PriorityQueue;

// 来自学员问题
// 设计一个仓库管理器，提供如下的方法：
// 1) void supply(String item, int num, int price)
// 名字叫item的商品，个数num，价格price
// 2) int sell(String item, int num)
// 卖出叫item的商品，个数num个，价格从低到高，返回卖出总价
// 如果商品很多，每种商品的数量可能很多，该怎么设计这个结构
public class Code01_StoreManager {

	public static class StoreManager {
		private HashMap<String, Store> map;

		public StoreManager() {
			map = new HashMap<>();
		}

		public void supply(String item, int num, int price) {
			if (!map.containsKey(item)) {
				map.put(item, new Store());
			}
			map.get(item).add(num, price);
		}

		public int sell(String item, int num) {
			return map.get(item).remove(num);
		}

	}

	public static class Store {
		// 每一个价格，对应的数量
		HashMap<Integer, Integer> priceNums;
		// 价格组成的小根堆
		PriorityQueue<Integer> heap;

		public Store() {
			priceNums = new HashMap<>();
			heap = new PriorityQueue<>();
		}

		public void add(int num, int price) {
			if (priceNums.containsKey(price)) {
				priceNums.put(price, priceNums.get(price) + num);
			} else {
				priceNums.put(price, num);
				heap.add(price);
			}
		}

		public int remove(int num) {
			int money = 0;
			while (!heap.isEmpty() && num != 0) {
				int price = heap.poll();
				int stores = priceNums.get(price);
				if (num >= stores) {
					money += price * stores;
					priceNums.remove(price);
					num -= stores;
				} else { // num < stores
					money += price * num;
					heap.add(price);
					priceNums.put(price, stores - num);
					break;
				}
			}
			return money;
		}

	}

}

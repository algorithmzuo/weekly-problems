package class_2022_11_1_week;

import java.util.HashMap;
import java.util.PriorityQueue;

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

		// 退货时之前的价格根本没用，参数price指的是折旧后的价格
		public void refund(String item, int num, int price) {
			supply(item, num, price);
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

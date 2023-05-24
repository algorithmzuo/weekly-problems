package class_2023_05_4_week;

import java.util.ArrayList;
import java.util.PriorityQueue;

// 来自招商银行
// 给定一个数组arr，长度为n，表示有0~n-1号设备
// arr[i]表示i号设备的型号，型号的种类从0~k-1，一共k种型号
// 给定一个k*k的矩阵map，来表示型号之间的兼容情况
// map[a][b] == 1，表示a型号兼容b型号
// map[a][b] == 0，表示a型号不兼容b型号
// 兼容关系是有向图，也就是a型号兼容b型号，不代表b型号同时兼容a型号
// 如果i设备的型号兼容j设备的型号，那么可以从i设备修建一条去往j设备的线路
// 修建线路的代价是i设备到j设备的距离：|i-j|
// 你的目标是从0号设备到达n-1号设备，并不一定每个设备都联通，只需要到达即可
// 返回最小的修建代价，如果就是无法到达返回-1
// 1 <= n <= 1000
// 1 <= k <= 50
public class Code04_EquipmentFirstToEnd {

	public static int minCost(int[] arr, int[][] map, int n, int k) {
		// 0 : {4,7,13,26}
		// 1 : {5,45,3,17}
		ArrayList<ArrayList<Integer>> own = new ArrayList<>();
		//   0 1 2 3
		// 0 1 0 1 0
		// 1 0 1 1 0
		// 2 1 1 1 1
		// 3
		// 0 : 0, 2
		// 1 : 1, 2
		// 2 : 0 1 2 3
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			own.add(new ArrayList<>());
			nexts.add(new ArrayList<>());
		}
		for (int i = 0; i < n; i++) {
			own.get(arr[i]).add(i);
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				if (i != j && map[i][j] == 1) {
					nexts.get(i).add(j);
				}
			}
		}
		// 放入的数据是一个长度为2的数组
		// 0 : 来到的地点号
		// 1 : 距离0号地点有多远（根据代价排序）
		// 堆里，小根堆 : {7, 10}
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.add(new int[] { 0, 0 });
		boolean[] visited = new boolean[n];
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int position = cur[0];
			int cost = cur[1];
			if (!visited[position]) {
				visited[position] = true;
				if (position == n - 1) {
					return cost;
				}
				// 你来到了一个地点，拿出型号!
				int model = arr[position];
				// 5 : {0,3,5,7}
				// 0: ....
				// 3: ....
				// 5: ....
				// 7: ....
				for (int nextModel : nexts.get(model)) {
					for (int nextPosition : own.get(nextModel)) {
						if (!visited[nextPosition]) {
							heap.add(new int[] { nextPosition, cost + Math.abs(nextPosition - position) });
						}
					}
				}
			}
		}
		return -1;
	}

}

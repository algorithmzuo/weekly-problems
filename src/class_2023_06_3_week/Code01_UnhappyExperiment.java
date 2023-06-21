package class_2023_06_3_week;

import java.util.Arrays;

// 一开始有100个人，每个人都有100元
// 在每一轮都做如下的事情 : 
// 每个人都必须拿出1元钱给除自己以外的其他人，给谁完全随机
// 如果某个人在这一轮的钱数为0，那么他可以不给
// 发生很多很多轮之后，这100人的社会是财富很均匀的吗？
public class Code01_UnhappyExperiment {

	// 按照说的来做实现
	public static void test(int n, int t) {
		double[] wealth = new double[n];
		Arrays.fill(wealth, 100);
		for (int i = 0; i < t; i++) {
			for (int j = 0; j < n; j++) {
				if (wealth[j] > 0) {
					int other = j;
					do {
						other = (int) (Math.random() * n);
					} while (other == j);
					wealth[j]--;
					wealth[other]++;
				}
			}
		}
		Arrays.sort(wealth);
		System.out.println("从贫穷到富有列出每个人的财富 : ");
		for (double num : wealth) {
			System.out.print((int) num + " ");
		}
		System.out.println();
		System.out.print("这个社会的基尼系数为 : ");
		System.out.println(calculateGini(wealth));
		System.out.println("注意，基尼系数到达0.4以上就认为是财富不均很严重了");
	}

	// 计算基尼系数
	public static double calculateGini(double[] wealth) {
		Arrays.sort(wealth);
		double sumOfAbsoluteDifferences = 0;
		double sumOfWealth = 0;
		int n = wealth.length;
		for (int i = 0; i < n; i++) {
			sumOfWealth += wealth[i];
			for (int j = 0; j < n; j++) {
				sumOfAbsoluteDifferences += Math.abs(wealth[i] - wealth[j]);
			}
		}
		return sumOfAbsoluteDifferences / (2 * n * sumOfWealth);
	}

	public static void main(String[] args) {
		int n = 100;
		int t = 1000000;
		test(n, t);
	}

}
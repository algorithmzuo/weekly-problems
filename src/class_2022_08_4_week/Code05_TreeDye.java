package class_2022_08_4_week;

import java.util.ArrayList;
import java.util.Arrays;

// 来自米哈游
// 给定一个正数n，表示有多少个节点
// 给定一个二维数组edges，表示所有无向边
// edges[i] = {a, b} 表示a到b有一条无向边
// edges一定表示的是一个无环无向图，也就是树结构
// 每个节点可以染1、2、3三种颜色
// 要求 : 非叶节点的相邻点一定要至少有两种和自己不同颜色的点
// 返回一种达标的染色方案，也就是一个数组，表示每个节点的染色状况
// 1 <= 节点数量 <= 10的5次方
public class Code05_TreeDye {

	public static int[] rule1 = { 1, 2, 3 };

	public static int[] rule2 = { 1, 3, 2 };

	public static int[] dye(int n, int[][] edges) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		int head = -1;
		for (int i = 0; i < n; i++) {
			if (graph.get(i).size() >= 2) {
				head = i;
				break;
			}
		}
		int[] colors = new int[n];
		if (head == -1) {
			Arrays.fill(colors, 1);
		} else {
			colors[head] = 1;
			dye(graph, graph.get(head).get(0), 1, rule1, colors);
			for (int i = 1; i < graph.get(head).size(); i++) {
				dye(graph, graph.get(head).get(i), 1, rule2, colors);
			}
		}
		return colors;
	}

	public static void dye(ArrayList<ArrayList<Integer>> graph, int head, int level, int[] rule, int[] colors) {
		colors[head] = rule[level % 3];
		for (int next : graph.get(head)) {
			if (colors[next] == 0) {
				dye(graph, next, level + 1, rule, colors);
			}
		}
	}

	// 生成无环无向图
	public static int[][] randomEdges(int n) {
		int[] order = new int[n];
		for (int i = 0; i < n; i++) {
			order[i] = i;
		}
		for (int i = n - 1; i >= 0; i--) {
			swap(order, i, (int) (Math.random() * (i + 1)));
		}
		int[][] edges = new int[n - 1][2];
		for (int i = 1; i < n; i++) {
			edges[i - 1][0] = order[i];
			edges[i - 1][1] = order[(int) (Math.random() * i)];
		}
		return edges;
	}

	// 为了测试
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 为了测试
	public static boolean rightAnswer(int n, int[][] edges, int[] colors) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]);
			graph.get(edge[1]).add(edge[0]);
		}
		boolean[] hasColors = new boolean[4];
		for (int i = 0, colorCnt = 1; i < n; i++, colorCnt = 1) {
			if (colors[i] == 0) {
				return false;
			}
			if (graph.get(i).size() == 1) { // i号点是叶节点
				continue;
			}
			hasColors[colors[i]] = true;
			for (int near : graph.get(i)) {
				if (!hasColors[colors[near]]) {
					hasColors[colors[near]] = true;
					colorCnt++;
				}
			}
			if (colorCnt != 3) {
				return false;
			}
			Arrays.fill(hasColors, false);
		}
		return true;
	}

	public static void main(String[] args) {
		int N = 100;
		int testTimes = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 2;
			int[][] edges = randomEdges(n);
			int[] ans = dye(n, edges);
			if (!rightAnswer(n, edges, ans)) {
				System.out.println("出错了");
			}
		}
		System.out.println("测试结束");
	}

}

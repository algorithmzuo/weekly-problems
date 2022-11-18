package class_2022_11_3_week;

import java.util.HashSet;
import java.util.TreeSet;

// 设计一个叫Bank的类，并提供如下方法
// Bank(int n) 初始化的时候，准备好0、1、2 ... n-1个座位
// int hello() : 
// 如果此时所有座位都无人，那么分配0号座位给当前用户
// 如果此时座位上有人，那么分配一个座位，这个座位保证是所有座位中离最近的人距离最远的座位
// 如果有多个座位都满足，分配座位编号最小的座位
// 返回座位编号
// 如果已经没有座位，返回-1表示无法分配
// void goodbye(int x) : 
// 如果x号座位上无人，什么也不用做
// 如果x号座位上有人，现在这个人离开了，该座位又能重新考虑分配
// 举例 :
// Bank b = new Bank(10)  0~9号座位被初始化出来
// b.hello()，返回0，表示给当前用户分配了0座位
// b.hello()，返回9，因为此时9座位离0座位的人最远，此时
// 0 1 2 3 4 5 6 7 8 9
// X                 X
// b.hello()，虽然座位4和座位5，离最近人的距离都是4(最远)
// 这种情况，根据描述，分配座位编号最小的座位，返回4，此时
// 0 1 2 3 4 5 6 7 8 9
// X       X         X
// b.hello()，座位2、座位6、座位7，都是离最近人的距离最远的(2)
// 这种情况，根据描述，分配座位编号最小的座位，返回2，此时
// 0 1 2 3 4 5 6 7 8 9
// X   X   X         X
// b.goodbye(4)，4座位的人离开了，此时
// 0 1 2 3 4 5 6 7 8 9
// X   X             X
// b.hello()，座位5、座位6，都是离最近人的距离最远的(3)
// 这种情况，根据描述，分配座位编号最小的座位，返回5
// 测试连接 : https://leetcode.cn/problems/exam-room/
public class Code05_FarAwaySuggestion {

	class ExamRoom {

		// 空闲座位的类
		// 比如，一共8个空间
		// 0 1 2 3 4 5 6 7
		// 如果某个空闲座位区间是0~3
		// 0 1 2 3 X
		// 0是开头，3是结尾，提供4的距离，因为0位置离X位置最远
		// 如果某个空闲座位区间是4~7
		// X 4 5 6 7
		// 4是开头，7是结尾，提供4的距离，因为7位置离X位置最远
		// 如果某个空闲座位区间是2~5
		// X 2 3 4 5 X
		// 2是开头，5是结尾，提供2的距离，因为3、4位置离X位置最远
		// 根据start、end的具体情况，可以算出far，也可以算出该分配那个座位
		public static class FreeSpace {
			public int start;
			public int end;
			public int far;

			public FreeSpace(int a, int b, int c) {
				start = a;
				end = b;
				far = c;
			}
		}

		// right是最右的座位在哪
		public int right;
		// 所有空闲区间都在seats里
		// far越远，越早使用
		// far一样，start越小，越早使用
		// 需要看体系学习班，有序表、比较器的内容
		public TreeSet<FreeSpace> seats;
		// 所有空闲区间，根据开头位置从小到大的一个有序表
		// 方便定位，举个例子
		// 比如所有的空闲区间为：
		// 3~7
		// 8~10
		// 23~56
		// 64~78
		// 在heads中，这些空闲区间是根据开头位置组织的
		// 假设x = 60位置，然后被释放了
		// 你需要找到 <= 60位置，最近的空闲区间
		// 你需要找到 >= 60位置，最近的空闲区间
		// 因为你需要可能需要把x位置左边、x位置、x位置右边
		// 三段合起来，如果x可以联通的话！
		// 那么你就可以用这个有序表，快速定位
		// 查找 <= 60且最近的区间，就是有序表的floor方法，速度很快
		// 查找 >= 60且最近的区间，就是有序表的ceiling方法，速度很快
		// 然后你就能查到：
		// 23~56 60~60 64~78
		// 不能合并，因为这三段无法通过60~60连起来
		// 但是如果能连接的话，这么做就很方便了
		public TreeSet<FreeSpace> heads;
		// 那些座位已经使用了，都在这个哈希表里
		public HashSet<Integer> used;

		public ExamRoom(int n) {
			right = n - 1;
			// far越远，越早使用
			// far一样，start越小，越早使用
			// 看比较器的内容！
			seats = new TreeSet<>((a, b) -> a.far != b.far ? (b.far - a.far) : (a.start - b.start));
			// 根据开头位置从小到大的一个有序表
			heads = new TreeSet<>((a, b) -> a.start - b.start);
			used = new HashSet<>();
			// 最开始时，0~n-1整个范围都是空闲区间
			add(0, right, Integer.MAX_VALUE);
		}

		public int seat() {
			if (used.size() == right + 1) {
				return -1;
			}
			FreeSpace cur = poll();
			int ans;
			// 比如有8个位置
			// 0 1 2 3 4 5 6 7
			// 如果cur.start == 0, cur.end == 7
			// 此时肯定使用0位置
			if (cur.start == 0 && cur.end == right) {
				ans = 0;
				add(1, right, right);
			} else if (cur.start == 0) {
				// 如果cur.start == 0, cur.end != 7
				// 0(s) 1 2 3 4 5(e) 6 7
				// 此时肯定使用0位置
				// 剩下就是1(s)...e
				// 提供的距离就是(e - s) / 2 + 1
				ans = 0;
				int start = 1;
				int end = cur.end;
				if (start <= end) {
					add(start, end, (end - start) / 2 + 1);
				}
			} else if (cur.end == right) {
				// 如果cur.start != 0, cur.end == 7
				// 0 1 2(s) 3 4 5 6 7(e)
				// 此时肯定使用7位置
				// 剩下就是s...6(e)
				// 提供的距离就是(e - s) / 2 + 1
				ans = right;
				int start = cur.start;
				int end = cur.end - 1;
				if (start <= end) {
					add(start, end, (end - start) / 2 + 1);
				}
			} else {
				// 如果cur.start != 0, cur.end != 7
				// 0 1 2(s) 3 4 5 6(e) 7
				// 此时肯定使用中点位置，(s + e) / 2
				// 0 1 2(s) 3 4(中点) 5 6(e) 7
				// 剩下就是，s...中点-1
				// 还剩下，中点+1...e
				ans = (cur.start + cur.end) / 2;
				int start1 = cur.start;
				int end1 = ans - 1;
				int start2 = ans + 1;
				int end2 = cur.end;
				if (start1 <= end1) {
					add(start1, end1, (end1 - start1) / 2 + 1);
				}
				if (start2 <= end2) {
					add(start2, end2, (end2 - start2) / 2 + 1);
				}
			}
			used.add(ans);
			return ans;
		}

		private void add(int start, int end, int distance) {
			FreeSpace space = new FreeSpace(start, end, distance);
			seats.add(space);
			heads.add(space);
		}

		private FreeSpace poll() {
			FreeSpace space = seats.pollFirst();
			heads.remove(space);
			return space;
		}

		public void leave(int x) {
			if (used.contains(x)) {
				used.remove(x);
				FreeSpace m = new FreeSpace(x, x, 1);
				FreeSpace l = heads.floor(m);
				FreeSpace r = heads.ceiling(m);
				merge(l, m, r);
			}
		}

		// 左区间、中区间、右区间
		// 能合并就合在一起
		private void merge(FreeSpace l, FreeSpace m, FreeSpace r) {
			int start = m.start;
			int end = m.end;
			if (l != null && l.end == m.start - 1) {
				remove(l);
				start = l.start;
			}
			if (r != null && m.end + 1 == r.start) {
				remove(r);
				end = r.end;
			}
			int far = 0;
			if (start == 0 && end == right) {
				far = Integer.MAX_VALUE;
			} else if (start == 0) {
				far = end + 1;
			} else if (end == right) {
				far = end - start + 1;
			} else {
				far = (end - start) / 2 + 1;
			}
			add(start, end, far);
		}

		private void remove(FreeSpace space) {
			seats.remove(space);
			heads.remove(space);
		}

	}

}

package class_2021_12_1_week;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 来自北京北明数科信息技术有限公司
// area表示的是地区全路径,最多可能有6级,用分隔符连接,分隔符是 spliter,
// 分隔符是逗号
// 例如：
// area = 中国,四川,成都  或者  中国,浙江,杭州  或者  中国,浙江,义乌
// spliter = ,
// count表示门店数
// class AreaResource {
// String area;
// String spliter;
// long count;
// }
// 现在需要把  List<AreaResource> 进行字符串转换，供下游处理，需要做到同级的地域能合并
// 比如 area为中国,四川,成都 有10个门店，   中国,浙江,杭州 有25个门店 中国,浙江,义乌有22个门店
// spliter为逗号 "," 最终转化成JSON的形式，并且同级的地域需要被合并，最终生成的JSON字符串如下所示
// 返回: {"中国":{"四川":{"成都":10]},"浙江":{"义乌":22,"杭州":25}}}
// 请实现下面的方法 public String mergeCount(List<AreaResource> areas) 
public class Code05_MergeArea {

	// 原始类
	public static class AreaResource {
		public String area;
		public String spliter;
		public long count;

		public AreaResource(String a, String s, long c) {
			area = a;
			spliter = s;
			count = c;
		}
	}

	// 要实现的方法
	public static String mergeCount(List<AreaResource> areas) {
		Area all = new Area("", 0);
		for (AreaResource r : areas) {
			String[] path = r.area.split(r.spliter);
			long count = r.count;
			f(path, 0, all, count);
		}
		return all.toString();
	}

	public static void f(String[] path, int index, Area pre, long count) {
		if (index == path.length) {
			pre.count += count;
		} else {
			String cur = path[index];
			if (!pre.next.containsKey(cur)) {
				pre.next.put(cur, new Area(cur, 0));
			}
			f(path, index + 1, pre.next.get(cur), count);
		}
	}

	public static class Area {
		public String name;
		public HashMap<String, Area> next;
		public long count;

		public Area(String n, long c) {
			name = n;
			count = c;
			next = new HashMap<>();
		}

		public String toString() {
			StringBuilder ans = new StringBuilder();
			if (!name.equals("")) {
				ans.append("\"" + name + "\"" + ":");
			}
			if (next.isEmpty()) {
				ans.append(count);
			} else {
				ans.append("{");
				for (Area child : next.values()) {
					ans.append(child.toString() + ",");
				}
				ans.setCharAt(ans.length() - 1, '}');
			}
			return ans.toString();
		}
	}

	public static void main(String[] args) {
		AreaResource a1 = new AreaResource("中国,四川,成都", ",", 10);
		AreaResource a2 = new AreaResource("中国,浙江,杭州", ",", 50);
		AreaResource a3 = new AreaResource("中国_浙江_杭州", "_", 25);
		AreaResource a4 = new AreaResource("中国!浙江!义务", "!", 22);
		AreaResource a5 = new AreaResource("中国0四川0成都", "0", 15);
		AreaResource a6 = new AreaResource("中国a四川a攀枝花", "a", 12);
		AreaResource a7 = new AreaResource("中国 浙江 宁波", " ", 16);

		List<AreaResource> areas = new ArrayList<>();
		areas.add(a1);
		areas.add(a2);
		areas.add(a3);
		areas.add(a4);
		areas.add(a5);
		areas.add(a6);
		areas.add(a7);

		String ans = mergeCount(areas);

		System.out.println(ans);

	}

}

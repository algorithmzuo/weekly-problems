package class_2022_05_4_week;

import java.util.TreeMap;

// 测试链接 : https://leetcode.com/problems/number-of-atoms/
public class Code05_NumberOfAtoms {

	public static String countOfAtoms(String str) {
		char[] s = str.toCharArray();
		Info info = process(s, 0);
		StringBuilder builder = new StringBuilder();
		for (String key : info.cntMap.keySet()) {
			builder.append(key);
			int cnt = info.cntMap.get(key);
			if (cnt > 1) {
				builder.append(cnt);
			}
		}
		return builder.toString();
	}

	public static class Info {
		public TreeMap<String, Integer> cntMap;
		public int end;

		public Info(TreeMap<String, Integer> c, int e) {
			cntMap = c;
			end = e;
		}
	}

	public static Info process(char[] s, int i) {
		TreeMap<String, Integer> cntMap = new TreeMap<>();
		int cnt = 0;
		StringBuilder builder = new StringBuilder();
		Info info = null;
		while (i < s.length && s[i] != ')') {
			if (s[i] >= 'A' && s[i] <= 'Z' || s[i] == '(') {
				if (builder.length() != 0 || info != null) {
					cnt = cnt == 0 ? 1 : cnt;
					if (builder.length() != 0) {
						String key = builder.toString();
						cntMap.put(key, cntMap.getOrDefault(key, 0) + cnt);
						builder.delete(0, builder.length());
					} else {
						for (String key : info.cntMap.keySet()) {
							cntMap.put(key, cntMap.getOrDefault(key, 0) + info.cntMap.get(key) * cnt);
						}
						info = null;
					}
					cnt = 0;
				}
				if (s[i] == '(') {
					info = process(s, i + 1);
					i = info.end + 1;
				} else {
					builder.append(s[i++]);
				}
			} else if (s[i] >= 'a' && s[i] <= 'z') {
				builder.append(s[i++]);
			} else {
				cnt = cnt * 10 + s[i++] - '0';
			}
		}
		if (builder.length() != 0 || info != null) {
			cnt = cnt == 0 ? 1 : cnt;
			if (builder.length() != 0) {
				String key = builder.toString();
				cntMap.put(key, cntMap.getOrDefault(key, 0) + cnt);
				builder.delete(0, builder.length());
			} else {
				for (String key : info.cntMap.keySet()) {
					cntMap.put(key, cntMap.getOrDefault(key, 0) + info.cntMap.get(key) * cnt);
				}
				info = null;
			}
			cnt = 0;
		}
		return new Info(cntMap, i);
	}

}

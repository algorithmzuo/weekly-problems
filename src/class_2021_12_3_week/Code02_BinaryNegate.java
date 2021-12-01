package class_2021_12_3_week;

//测试链接 : https://www.nowcoder.com/test/33701596/summary
//本题目为第2题
public class Code02_BinaryNegate {

	public static String maxLexicographical(String num) {
		char[] arr = num.toCharArray();
		int i = 0;
		while (i < arr.length) {
			if (arr[i] == '0') {
				break;
			}
			i++;
		}
		while(i < arr.length) {
			if(arr[i] == '1') {
				break;
			}
			arr[i++] = '1';
		}
		return String.valueOf(arr);
	}

}

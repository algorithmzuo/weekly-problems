package class_2021_12_3_week;

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

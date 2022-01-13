package class_2022_01_2_week;

// 来自兴业数金
// 给定一个字符串形式的数，比如"3421"或者"-8731"
// 如果这个数不在-32768~32767范围上，那么返回"NODATA"
// 如果这个数在-32768~32767范围上，那么这个数就没有超过16个二进制位所能表达的范围
// 返回这个数的2进制形式的字符串和16进制形式的字符串，用逗号分割
public class Code03_StringNumberConvertBinaryAndHexadecimal {

	// 请保证输入的num字符串一定是数字的形式
	public static String convert(String num) {
		// 因为-32768~32767所有的数，最多6个字符，所以超过就返回"NODATA"
		if (num == null || num.length() == 0 || num.length() > 6) {
			return "NODATA";
		}
		// 既然长度不超过6，那么转成整数一定不会有问题
		// 当然你也可以自己写这个转化过程，这个是比较简单的
		int n = Integer.valueOf(num);
		// 如果转换完成后超过了范围，那么返回"NODATA"
		if (n < -32768 || n > 32767) {
			return "NODATA";
		}
		// 接下来n就是一个在范围上的数字
		// 我们要取出16位信息(info)，这包括：
		// 提取出n的14位~0位的信息 : 也就是(n & 65535)
		// 提取出第15位符号位信息 : 如果n<0，第15位就是1，如果n>=0第15位就是0
		// 然后把(15位)和(14位~0位)，或在一起
		// 比如5323，32位二进制形式如下：
		// 00000000000000000001010011001011
		// 14位~0位是：
		// _________________001010011001011
		// 15位符号位应该是0：
		// ________________0_______________
		// 所以info应该是：
		// ________________0001010011001011
		//
		// 再比如-6725，32位二进制形式如下：
		// 11111111111111111110010110111011
		// 14位~0位是：
		// _________________110010110111011
		// 15位符号位应该是1：
		// ________________1_______________
		// 所以info应该是：
		// ________________1110010110111011
		int info = (n < 0 ? (1 << 15) : 0) | (n & 65535);
		// 此时info的15位~0位，就是我们要的；info的更高位完全没用
		StringBuilder builder = new StringBuilder();
		// 依次提取二进制信息很简单
		for (int i = 15; i >= 0; i--) {
			builder.append((info & (1 << i)) != 0 ? '1' : '0');
		}
		builder.append(",0x");
		// 依次提取16进制的时候，每4位提取
		// 依次提取4位信息的时候
		// 0000 -> 0 -> '0'
		// 0001 -> 1 -> '1'
		// ...
		// 1001 -> 9 -> '9'
		// 1010 -> 10 -> 'a'
		// 1011 -> 11 -> 'b'
		// 1100 -> 12 -> 'c'
		// 1101 -> 13 -> 'd'
		// 1110 -> 14 -> 'e'
		// 1111 -> 15 -> 'f'
		for (int i = 12; i >= 0; i -= 4) {
			// 1111 << 12
			// ( info & (1111 << 12) ) >> 12
			// ( info & (1111 << 8)) >> 8
			// ( info & (1111 << 4)) >> 4
			// ( info & (1111 << 0)) >> 0
			int cur = (info & (15 << i)) >> i;
			if (cur < 10) {
				builder.append(cur);
			} else {
				switch (cur) {
				case 10:
					builder.append('a');
					break;
				case 11:
					builder.append('b');
					break;
				case 12:
					builder.append('c');
					break;
				case 13:
					builder.append('d');
					break;
				case 14:
					builder.append('e');
					break;
				default:
					builder.append('f');
				}
			}
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		String num1 = "0";
		System.out.println(convert(num1));
		
		String zuo = "457";
		System.out.println(convert(zuo));

		String num2 = "-32768";
		System.out.println(convert(num2));

		String num3 = "32768";
		System.out.println(convert(num3));

		String num4 = "32767";
		System.out.println(convert(num4));

		String num5 = "-1";
		System.out.println(convert(num5));
	}

}

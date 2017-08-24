import java.util.Scanner;

public class Utf8ToUnicode {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			// 输入数据
			String[] is = sc.nextLine().split(" ");
			int[] a = new int[is.length]; // 十进制UTF-8数据
			for (int i = 0; i < is.length; ++i) {
				a[i] = Integer.valueOf(is[i]);
			}
			// 转换为二进制8位分组
			String[] utf8s = new String[a.length]; // 二进制UTF-8数据，不足8位补0
			for (int i = 0; i < a.length; ++i) {
				utf8s[i] = convertTo2(a[i]);
			}
			// 结果数组
			int re[] = new int[is.length];
			int num = 0; // 结果的个数
			boolean flag = true; // 是否正常解码
			try {
				// 处理每一字节数据
				for (int i = 0; i < a.length; ++i) {
					// 当前处理的二进制UTF-8数据
					String now = utf8s[i];
					// 结合后的有效数据
					StringBuilder sb = new StringBuilder();
					if (now.startsWith("0")) {
						// 单字节
						// 第2至8位为有效数据
						sb.append(now.substring(1, 8));
					} else {
						// 多字节
						int count = 0;
						for (int j = 0; j < 8; ++j) {
							// 第一个0之前1的个数表示字节数
							if (now.charAt(j) == '1') {
								count++;
							} else {
								break;
							}
						}
						// 至少有2字节，至多有6字节
						if (count < 2 || count > 6) {
							flag = false;
							break;
						}
						// 除去前缀后为有效数据
						sb.append(now.substring(count + 1, 8));
						for (int j = 1; j < count; ++j) {
							// 处理追加的字节
							if (!append(sb, utf8s[++i])) {
								flag = false;
								break;
							}
						}
					}
					// 将有效数据转换为十进制存入结果中
					re[num++] = convertTo10(sb.toString());
				}
				if (flag) {
					// 正常解码
					for (int r : re) {
						// 输出所有有效结果
						if (r == 0) {
							break;
						}
						System.out.print(r + " ");
					}
					System.out.println();
				} else {
					// 不能正常解码
					System.out.println("no");
				}
			} catch (Exception e) {
				// 不能正常解码
				System.out.println("no");
			}
		}
		sc.close();
	}

	/**
	 * 将十进制数字转换为二进制字符串，若不足8位则补0
	 * @param i 十进制数字
	 * @return 二进制字符串
	 */
	public static String convertTo2(int i) {
		StringBuilder sb = new StringBuilder();
		// 生成倒序二进制串
		while (i > 0) {
			sb.append(i % 2);
			i /= 2;
		}
		// 不足8位的字符串补0
		for (int j = sb.length(); j < 8; ++j) {
			sb.append(0);
		}
		// 反向后结果为二进制字符串
		sb.reverse();
		return sb.toString();
	}

	/**
	 * 将二进制字符串转换为十进制数字
	 * @param s 二进制字符串
	 * @return 十进制数字
	 */
	public static int convertTo10(String s) {
		int sum = 0; // 十进制结果
		int base = 1; // 当前位的基数
		// 处理二进制串
		for (int i = s.length() - 1; i >= 0; --i) {
			if (s.charAt(i) == '1') {
				sum += base;
			}
			base *= 2;
		}
		return sum;
	}

	/**
	 * 处理追加的字节，截取有效数据并追加
	 * @param sb 已处理的有效数据
	 * @param utf8 追加的UTF-8字节
	 * @return 是否正常解码
	 */
	public static boolean append(StringBuilder sb, String utf8) {
		if (utf8.startsWith("10")) {
			// 以10开头为正常数据
			// 第3至8位为有效数据
			sb.append(utf8.substring(2, 8));
			return true;
		} else {
			// 不以10开头为无效数据
			return false;
		}
	}
}

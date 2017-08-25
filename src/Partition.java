public class Partition {
	/**
	 * 以数组首元素为基数，将数组划分为2部分，左部都小于等于基数，右部都大于等于基数
	 * 
	 * @param a
	 *            待处理的数组
	 * @param begin
	 *            数组首元素下标
	 * @param end
	 *            数组尾元素下标
	 * @return 基数在数组中的下标
	 */
	public static int partition(int[] a, int begin, int end) {
		// 待处理的数组不合法
		if (begin >= end) {
			return begin;
		}
		// 以首元素为基数
		int base = a[begin];
		// 循环直到确定基数所在位置
		while (begin < end) {
			// 尾部从后向前遍历直到找到小于基数的数
			while (begin < end && a[end] >= base) {
				end--;
			}
			// 交换基数与小于基数的数（基数在base中保证，在找到位置后再覆盖）
			a[begin] = a[end];
			// 首部从前向后遍历直到找到大于基数的数
			while (begin < end && a[begin] <= base) {
				begin++;
			}
			// 交换基数与大于基数的数（基数在base中保证，在找到位置后再覆盖）
			a[end] = a[begin];
		}
		// 最终处理结束后，begin==end，是基数应处的位置
		a[begin] = base;
		return begin;
	}
}

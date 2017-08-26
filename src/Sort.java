public class Sort {

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

	/**
	 * 快速排序
	 * 
	 * @param a
	 *            待排序数组
	 * @param begin
	 *            待排序首元素下标
	 * @param end
	 *            待排序尾元素下标
	 */
	public static void quickSort(int a[], int begin, int end) {
		if (begin < end) {
			int mid = partition(a, begin, end); // 以某元素为基数划分数组
			// 递归调用快排方法，分别为划分出的两部分排序
			quickSort(a, begin, mid - 1);
			quickSort(a, mid + 1, end);
		}
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime(); // 开始运行时间
		int[] a = { 5, 9, 2, 1, 4, 7, 5, 8, 3, 6 }; // 测试数据
		// 快速排序
		quickSort(a, 0, 9);
		long endTime = System.nanoTime(); // 结束运行时间
		// 输出数据
		for (int i : a) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");

	}

}

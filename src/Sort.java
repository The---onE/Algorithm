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
			int index = partition(a, begin, end); // 以某元素为基数划分数组
			// 递归调用快排方法，分别为划分出的两部分排序
			quickSort(a, begin, index - 1);
			quickSort(a, index + 1, end);
		}
	}

	/**
	 * 寻找数组中第K小的数字
	 * 
	 * @param a
	 *            待寻找的数据
	 * @param begin
	 *            // 待寻找首元素下标
	 * @param end
	 *            // 待寻找尾元素下标
	 * @param k
	 *            K
	 * @return 第K小的数字
	 */
	public static int findKthLittleNumber(int a[], int begin, int end, int k) {
		if (begin <= end) {
			// 将数组划分为两部分
			int index = partition(a, begin, end);
			int len = index - begin + 1; // 划分后左部分加上基数的长度
			if (len == k) {
				// 基数位置的数字就是第K小的数字
				return a[index];
			} else if (len < k) {
				// 长度比K小，则第K小的数字在数组右部
				return findKthLittleNumber(a, index + 1, end, k - len);
			} else {
				// 长度比K大，则第K小的数字在数组左部
				return findKthLittleNumber(a, begin, index - 1, k);
			}
		}
		return -1;
	}

	/**
	 * 查找数组中重复次数超过半数的数字
	 * 
	 * @param a
	 *            待查找数组
	 * @param begin
	 *            待查找首元素下标
	 * @param end
	 *            待查找尾元素下标
	 * @return 数组中重复次数超过半数的数字
	 */
	public static int moreThanHalfNumber(int[] a, int begin, int end) {
		// 数组中间位置
		int mid = (end - begin + 1) / 2;
		// 划分数组，获得划分后基数的位置
		int index = partition(a, begin, end);
		// 不断划分直到基数位于中间位置
		while (index != mid) {
			// 在包含中间位置的区间再次划分
			if (index > mid) {
				index = partition(a, begin, index - 1);
			} else {
				index = partition(a, index + 1, end);
			}
		}
		// 位于中间位置的基数即为重复次数超过半数的数字
		return a[index];
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

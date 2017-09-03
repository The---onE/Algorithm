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
		int mid = (begin + end) / 2;
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

	/**
	 * 归并排序
	 * 
	 * @param a
	 *            待排序数组
	 * @param begin
	 *            待排序首元素下标
	 * @param end
	 *            待排序尾元素下标
	 */
	public static void mergeSort(int a[], int begin, int end) {
		if (begin < end) {
			// 数组中间位置
			int mid = (begin + end) / 2;
			// 递归调用归并排序方法，分别为划分出的两部分排序
			mergeSort(a, begin, mid);
			mergeSort(a, mid + 1, end);
			// 合并两部分
			merge(a, begin, mid, end);
		}
	}

	/**
	 * 将数组两部分归并
	 * 
	 * @param a
	 *            待归并数组
	 * @param begin
	 *            待归并首元素下标
	 * @param mid
	 *            待排序中间下标
	 * @param end
	 *            待归并尾元素下标
	 */
	public static void merge(int[] a, int begin, int mid, int end) {
		// 临时数组
		int[] temp = new int[a.length];
		// 临时数组待存放位置的下标
		int tempIndex = begin;
		// 左部分待取出位置的下标
		int tempLeft = begin;
		// 右部分待取出位置的下标
		int tempRight = mid + 1;
		// 直到其中一部分全部取出
		while (tempLeft <= mid && tempRight <= end) {
			// 从两个部分待取出中较小数的存入临时数组
			if (a[tempLeft] <= a[tempRight]) {
				temp[tempIndex++] = a[tempLeft++];
			} else {
				temp[tempIndex++] = a[tempRight++];
			}
		}
		// 未取完的部分的数据直接存入临时数组的剩余部分
		while (tempLeft <= mid) {
			temp[tempIndex++] = a[tempLeft++];
		}
		while (tempRight <= end) {
			temp[tempIndex++] = a[tempRight++];
		}
		// 将临时数组中的内容覆盖到原数组中
		while (begin <= end) {
			a[begin] = temp[begin++];
		}
	}

	/**
	 * 希尔排序
	 * 
	 * @param a
	 *            待排序数组
	 */
	public static void shellSort(int[] a) {
		// 计算出最大的h值
		int h = 1;
		int length = a.length;
		while (h <= length / 3) {
			h = h * 3 + 1;
		}
		// 逐渐缩小分组间距直到排序完成
		while (h > 0) {
			// 分组进行插入排序
			for (int i = h; i < length; i += h) {
				if (a[i] < a[i - h]) {
					int temp = a[i];
					int j = i - h;
					while (j >= 0 && a[j] > temp) {
						a[j + h] = a[j];
						j -= h;
					}
					a[j + h] = temp;
				}
			}
			// 计算出下一个h值
			h = (h - 1) / 3;
		}
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime(); // 开始运行时间
		int[] a = { 5, 9, 2, 1, 4, 7, 5, 8, 3, 6 }; // 测试数据
		// 快速排序
		quickSort(a, 0, a.length - 1);
		long endTime = System.nanoTime(); // 结束运行时间
		// 输出数据
		for (int i : a) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");

		int[] b = { 5, 9, 2, 1, 4, 7, 5, 8, 3, 6 }; // 测试数据
		startTime = System.nanoTime(); // 开始运行时间
		// 归并排序
		mergeSort(b, 0, b.length - 1);
		endTime = System.nanoTime(); // 结束运行时间
		// 输出数据
		for (int i : b) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");

		int[] c = { 5, 9, 2, 1, 4, 7, 5, 8, 3, 6 }; // 测试数据
		startTime = System.nanoTime(); // 开始运行时间
		// 希尔排序
		shellSort(c);
		endTime = System.nanoTime(); // 结束运行时间
		// 输出数据
		for (int i : c) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
	}

}

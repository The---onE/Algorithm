public class Search {

	/**
	 * 二分查找
	 * 
	 * @param a
	 *            用于查找的数组
	 * @param target
	 *            待查找的数
	 * @return 若找到返回所在位置，否则返回-1
	 */
	public static int binarySearch(int[] a, int target) {
		int index = -1;
		int begin = 0;
		int end = a.length;
		while (begin <= end) {
			int mid = (begin + end) / 2;
			int x = a[mid];
			if (x == target) {
				return mid;
			} else if (x < target) {
				begin = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return index;
	}

	public static void main(String[] args) {
		int[] a = { 1, 1, 2, 3, 5, 8, 13, 21, 44, 65 };
		int index = binarySearch(a, 3);
		System.out.println(index + "");
	}
}

public class Tree {

	/**
	 * 二叉树节点
	 * 
	 * @author The_onE
	 * 
	 */
	static public class TreeNode {
		int val; // 节点值
		TreeNode left; // 左子节点
		TreeNode right; // 右子节点

		TreeNode(int x) {
			val = x;
		}

		/**
		 * 后序遍历输出
		 */
		public void postOrderrint() {
			if (left != null) {
				left.postOrderrint();
			}
			if (right != null) {
				right.postOrderrint();
			}
			System.out.print(val + " ");
		}
	}

	/**
	 * 通过前序遍历和中序遍历生成二叉树
	 * 
	 * @param pre
	 *            前序遍历序列
	 * @param in
	 *            中序遍历序列
	 * @return 生成二叉树的根节点
	 */
	public static TreeNode makeBinaryTree(int[] pre, int[] in) {
		return makeBinaryTree(pre, in, 0, pre.length - 1, 0, in.length - 1);
	}

	/**
	 * 通过前序遍历和中序遍历生成二叉树
	 * 
	 * @param pre
	 *            前序遍历序列
	 * @param in
	 *            中序遍历序列
	 * @param preBegin
	 *            前序遍历区间首索引
	 * @param preEnd
	 *            前序遍历区间尾索引
	 * @param inBegin
	 *            中序遍历区间首索引
	 * @param inEnd
	 *            中序遍历区间尾索引
	 * @return
	 */
	public static TreeNode makeBinaryTree(int[] pre, int[] in, int preBegin,
			int preEnd, int inBegin, int inEnd) {
		if (preBegin > preEnd) {
			return null;
		}
		int root = pre[preBegin]; // 根节点
		TreeNode node = null;
		for (int i = inBegin; i <= inEnd; ++i) {
			int x = in[i];
			if (root == x) {
				// 中序遍历的根节点
				node = new TreeNode(root);

				int leftLength = i - inBegin; // 左子树节点数
				// 左子节点
				node.left = makeBinaryTree(pre, in, preBegin + 1, preBegin
						+ leftLength, inBegin, i - 1);
				// 右子节点
				node.right = makeBinaryTree(pre, in, preBegin + 1 + leftLength,
						preEnd, i + 1, inEnd);
			}
		}

		return node;
	}

	public static void main(String[] args) {
		int[] pre = { 1, 2, 4, 7, 3, 5, 6, 8 };
		int[] in = { 4, 7, 2, 1, 5, 3, 8, 6 };
		TreeNode tree = makeBinaryTree(pre, in);
		tree.postOrderrint();
	}
}

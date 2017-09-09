import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class PathFinding {

	private static int n = 0; // 地图宽高
	private static boolean[][] map; // 地图节点是否可达

	// 地图节点
	static class Node {
		int x; // 横坐标
		int y; // 纵坐标
		double f; // 用于A*算法的预估代价 f=g+h
		int g; // 从起点到该节点的实际代价
		Node father; // 父节点，用于获取路径

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Node) {
				Node node = (Node) obj;
				// 坐标相同视为同一节点
				return x == node.x && y == node.y;
			} else {
				return false;
			}
		}

		// 节点比较函数，用于排序OPEN表的预估代价
		static class AStarComparator implements Comparator<Node> {

			@Override
			public int compare(Node o1, Node o2) {
				// 按预估代价排序
				if (o1.f == o2.f) {
					return 0;
				} else {
					return o1.f - o2.f > 0 ? 1 : -1;
				}
			}

		}
	}

	static class AStar {

		/**
		 * 
		 * @param father
		 *            父节点
		 * @param x
		 *            横坐标
		 * @param y
		 *            纵坐标
		 * @param closed
		 *            CLOSED表
		 * @param open
		 *            OPEN表
		 * @return 是否被添加至OPEN表
		 */
		private static boolean testNode(Node father, int x, int y,
				boolean[][] closed, List<Node> open) {
			Node node;
			if (!closed[x][y] && map[x][y]) {
				// 生成节点
				node = new Node();
				node.x = x;
				node.y = y;
				// 若尚未在OPEN表中
				if (!open.contains(node)) {
					// 计算实际代价及预估代价
					node.g = father.g + 1;
					node.f = node.g + h(x, y);
					node.father = father;
					// 添加至OPEN表
					open.add(node);
					return true;
				}
			}
			return false;
		}

		/**
		 * A*算法的启发函数，预估至终点的代价
		 * 
		 * @param x
		 *            横坐标
		 * @param y
		 *            纵坐标
		 * @return 启发值
		 */
		private static double h(int x, int y) {
			return Math.abs(n - 1 - x) + Math.abs(n - 1 - y);
			// return Math.sqrt((n - 1 - x) * (n - 1 - x) + (n - 1 - y) * (n - 1
			// -
			// y));
			// return 0;
		}

		/**
		 * A*算法
		 * 
		 * @param startX
		 *            起点横坐标
		 * @param startY
		 *            起点纵坐标
		 * @param endX
		 *            终点横坐标
		 * @param endY
		 *            终点纵坐标
		 * @return 终点节点，若不可达返回null
		 */
		private static Node aStar(int startX, int startY, int endX, int endY) {
			if (n > 0) {
				// 临时存储空间
				List<Node> open = new ArrayList<>(); // OPEN表
				boolean[][] closed = new boolean[n][n]; // 地图节点是否CLOSED

				// 添加初始节点至OPEN表
				Node start = new Node();
				start.x = startX;
				start.y = startY;
				start.f = AStar.h(startX, startY);
				start.g = 0;
				open.add(start);
				// 循环直到OPEN表为空
				while (!open.isEmpty()) {
					// 获取OPEN表中预估代价最低的节点
					Node no = open.get(0);
					// 若到达终点则返回
					if (no.x == endX && no.y == endY) {
						return no;
					}
					// 将当前节点关闭并移出OPEN表
					closed[no.x][no.y] = true;
					open.remove(no);
					// 测试左侧节点
					if (no.x > 0) {
						testNode(no, no.x - 1, no.y, closed, open);
					}
					// 右侧
					if (no.x < n - 1) {
						testNode(no, no.x + 1, no.y, closed, open);
					}
					// 下侧
					if (no.y > 0) {
						testNode(no, no.x, no.y - 1, closed, open);
					}
					// 下侧
					if (no.y < n - 1) {
						testNode(no, no.x, no.y + 1, closed, open);
					}
					// 按预估代价排序OPEN中节点
					Collections.sort(open, new Node.AStarComparator());
				}
			}
			return null;
		}

		/**
		 * 开始A*算法
		 */
		public static void aStar() {
			// 计算最短路径
			Node node = aStar(0, 0, n - 1, n - 1);
			if (node != null) {
				// 可达
				printPath(node);
			} else {
				// 不可达
				System.out.println("nopath");
			}
		}
	}

	static class BFS {

		/**
		 * 测试节点
		 * 
		 * @param father
		 *            父节点
		 * @param x
		 *            横坐标
		 * @param y
		 *            纵坐标
		 * @param closed
		 *            CLOSED表
		 * @param queue
		 *            待搜索队列
		 * @return 是否被添加至搜索队列
		 */
		private static boolean testNode(Node father, int x, int y,
				boolean[][] closed, Queue<Node> queue) {
			Node node;
			// 若尚未被访问且可到达
			if (!closed[x][y] && map[x][y]) {
				// 生成节点
				node = new Node();
				node.x = x;
				node.y = y;
				// 计算实际代价
				node.g = father.g + 1;
				node.father = father;
				// 添加至队列
				queue.add(node);
				return true;
			}
			return false;
		}

		/**
		 * 广度优先搜索
		 * 
		 * @param startX
		 *            起点横坐标
		 * @param startY
		 *            起点纵坐标
		 * @param endX
		 *            终点横坐标
		 * @param endY
		 *            终点纵坐标
		 * @return 终点节点，若不可达返回null
		 */
		private static Node bfs(int startX, int startY, int endX, int endY) {
			Queue<Node> queue = new LinkedList<>(); // 用于搜索的队列
			boolean[][] closed = new boolean[n][n]; // 地图节点是否被访问
			// 添加初始节点至队列
			Node start = new Node();
			start.x = startX;
			start.y = startY;
			start.g = 0;
			queue.add(start);
			// 节点已被访问
			closed[startX][startY] = true;
			// 循环直到队列为空
			while (!queue.isEmpty()) {
				Node no = queue.poll();
				// 若到达终点则返回
				if (no.x == endX && no.y == endY) {
					return no;
				}
				// 测试左侧节点
				if (no.x > 0) {
					testNode(no, no.x - 1, no.y, closed, queue);
				}
				// 右侧
				if (no.x < n - 1) {
					testNode(no, no.x + 1, no.y, closed, queue);
				}
				// 下侧
				if (no.y > 0) {
					testNode(no, no.x, no.y - 1, closed, queue);
				}
				// 下侧
				if (no.y < n - 1) {
					testNode(no, no.x, no.y + 1, closed, queue);
				}
			}
			return null;
		}

		/**
		 * 开始广度优先搜索
		 */
		public static void bfs() {
			// 搜索路径
			Node node = bfs(0, 0, n - 1, n - 1);
			if (node != null) {
				// 可达
				printPath(node);
			} else {
				// 不可达
				System.out.println("nopath");
			}
		}
	}

	/**
	 * 顺序打印路径
	 * 
	 * @param node
	 *            要打印的终点节点
	 */
	public static void printPath(Node node) {
		if (node.father != null) {
			printPath(node.father);
			System.out.print("→");
		}
		System.out.print(node.x + "," + node.y);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// 读取第一行
		String[] test = sc.nextLine().split(" ");
		n = test.length;
		// 地图数组
		map = new boolean[n][n];
		// 处理第一行
		for (int j = 0; j < n; ++j) {
			if (test[j].equals("1")) {
				map[0][j] = false;
			} else {
				map[0][j] = true;
			}
		}
		// 读取剩余数据
		for (int i = 1; i < n; ++i) {
			test = sc.nextLine().split(" ");
			for (int j = 0; j < n; ++j) {
				if (test[j].equals("1")) {
					map[i][j] = false;
				} else {
					map[i][j] = true;
				}
			}
		}

		// A*算法
		AStar.aStar();
		System.out.println();
		// 广度优先搜索
		BFS.bfs();
		System.out.println();

		sc.close();
	}
}

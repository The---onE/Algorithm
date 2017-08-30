public class DynamicProgramming {

	/**
	 * 0-1背包问题
	 * 
	 * @param cost
	 *            物品重量数组
	 * @param worth
	 *            物品价值数组
	 * @param volume
	 *            背包容量
	 * @return 在背包容量范围内，可选物品的最大价值
	 */
	public static int zeroOneKnapsack(int[] cost, int[] worth, int volume) {
		// 状态转移方程 i:第i个物品 v:剩余容量 dp:最大价值
		// dp[i][v] = max{ dp[i-1][v], dp[i-1][v-cost[i]]+worth[i] }

		int N = cost.length; // 物品总数

		// int[][] dp = new int[N][volume +1];
		// 使用循环覆盖，只创建一维数组
		int[] dp = new int[volume + 1];
		// 初始化数组，背包可不满则均初始化为0，背包必须装满则dp[0]=0,dp[1..volume]=-∞
		for (int i = 0; i <= volume; ++i) {
			dp[i] = 0;
		}

		// 处理每一个物品
		for (int i = 0; i < N; ++i) {
			int c = cost[i]; // 当前物品的重量
			// 对于小于等于v的下标，获取的数据是未被覆盖的上个物品的数据
			for (int v = volume; v >= c; --v) {
				int choose = dp[v - c] + worth[i]; // 选取该物品后的价值
				int notChoose = dp[v]; // 不选取该物品的价值
				dp[v] = Math.max(notChoose, choose); // 状态转移
			}
		}
		// 返回最大价值
		return dp[volume];
	}

	/**
	 * 完全(无界)背包问题
	 * 
	 * @param cost
	 *            物品重量数组
	 * @param worth
	 *            物品价值数组
	 * @param volume
	 *            背包容量
	 * @return 在背包容量范围内，可选物品的最大价值
	 */
	public static int unboundedKnapsack(int[] cost, int[] worth, int volume) {
		// 状态转移方程 i:第i个物品 v:剩余容量 k:第i个物品选取的数量 dp:最大价值
		// dp[i][v] = max{ dp[i-1][v-k*c[i]] + k*w[i], 0<=k*c[i]<=v}

		int N = cost.length; // 物品总数

		// 使用循环覆盖，只创建一维数组
		int[] dp = new int[volume + 1];
		// 初始化数组，背包可不满则均初始化为0，背包必须装满则dp[0]=0,dp[1..volume]=-∞
		for (int i = 0; i <= volume; ++i) {
			dp[i] = 0;
		}

		// 处理每一个物品
		for (int i = 0; i < N; ++i) {
			int c = cost[i]; // 当前物品的重量
			// 对于小于等于v的下标，获取的数据是可能被覆盖的数据
			// 若是被覆盖的数据，则该物品已被选取过，尝试再次选取
			for (int v = c; v <= volume; ++v) {
				int choose = dp[v - c] + worth[i]; // 选取该物品后的价值
				int notChoose = dp[v]; // 不选取该物品的价值
				dp[v] = Math.max(notChoose, choose); // 状态转移
			}
		}
		// 返回最大价值
		return dp[volume];
	}

	/**
	 * 多重(有界)背包问题
	 * 
	 * @param cost
	 *            物品重量数组
	 * @param worth
	 *            物品价值数组
	 * @param volume
	 *            背包容量
	 * @return 在背包容量范围内，可选物品的最大价值
	 */
	public static int boundedKnapsack(int[] cost, int[] worth, int[] number,
			int volume) {
		// 状态转移方程 i:第i个物品 v:剩余容量 k:第i个物品选取的数量 dp:最大价值
		// dp[i][v] = max{ dp[i-1][v-k*c[i]] + k*w[i], 0<=k*c[i]<=n[i]}

		int N = cost.length; // 物品总数

		// 使用循环覆盖，只创建一维数组
		int[] dp = new int[volume + 1];
		// 初始化数组，背包可不满则均初始化为0，背包必须装满则dp[0]=0,dp[1..volume]=-∞
		for (int i = 0; i <= volume; ++i) {
			dp[i] = 0;
		}

		// 处理每一个物品
		for (int i = 0; i < N; ++i) {
			int c = cost[i]; // 当前物品的重量
			int w = worth[i]; // 当前物品的价值
			int n = number[i]; // 当前物品的数量

			if (c * n >= volume) {
				// 该物品不能全部装入背包
				// 视为完全背包问题
				for (int v = c; v <= volume; ++v) {
					int choose = dp[v - c] + w; // 选取该物品后的价值
					int notChoose = dp[v]; // 不选取该物品的价值
					dp[v] = Math.max(notChoose, choose); // 状态转移
				}
			} else {
				// 将物品分组，个数分别为1,2,4,...,2^(k-1),n-2^k+1，可以组合出1-n的所有个数
				// 将每一组视为0-1背包问题
				for (int k = 1; k < n; k *= 2) {
					int kc = k * c;
					int kw = k * w;
					for (int v = volume; v >= kc; --v) {
						int choose = dp[v - kc] + kw; // 选取该物品后的价值
						int notChoose = dp[v]; // 不选取该物品的价值
						dp[v] = Math.max(notChoose, choose); // 状态转移
					}
					n -= k;
				}
				// 处理最后一组
				for (int v = volume; v >= n * c; --v) {
					int choose = dp[v - n * c] + n * w; // 选取该物品后的价值
					int notChoose = dp[v]; // 不选取该物品的价值
					dp[v] = Math.max(notChoose, choose); // 状态转移
				}
			}
		}
		// 返回最大价值
		return dp[volume];
	}

	public static void main(String[] args) {
		int[] cost = { 2, 7, 3, 4, 8, 5, 8, 6, 4, 16 };
		int[] worth = { 15, 25, 8, 9, 15, 9, 13, 9, 6, 14 };
		int[] number = { 13, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		int zeroOne = zeroOneKnapsack(cost, worth, 34);
		int unbounded = unboundedKnapsack(cost, worth, 34);
		int bounded = boundedKnapsack(cost, worth, number, 34);
		System.out.println(zeroOne);
		System.out.println(unbounded);
		System.out.println(bounded);
	}

}

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

	public static void main(String[] args) {
		int[] cost = { 2, 7, 3, 4, 8, 5, 8, 6, 4, 16 };
		int[] worth = { 15, 25, 8, 9, 15, 9, 13, 9, 6, 14 };
		int re = zeroOneKnapsack(cost, worth, 34);
		System.out.println(re);
	}

}

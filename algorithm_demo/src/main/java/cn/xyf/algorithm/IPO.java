package cn.xyf.algorithm;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 最小化费问题，贪心
 */
public class IPO {
    public static class Project {
        public int capital;
        public int profit;
        public Project(int c, int p) {
            capital = c;
            profit = p;
        }
    }

    // 花费的小根堆
    private PriorityQueue<Project> minCost;
    // 收益的大根堆
    private PriorityQueue<Project> maxProfit;

    public IPO() {
        minCost = new PriorityQueue<>(Comparator.comparingInt(p -> p.capital));
        maxProfit = new PriorityQueue<>(Comparator.comparingInt(p -> -p.profit));
    }

    /**
     * costs[i]表示i号项目的花费
     * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
     * k 表示你不能并行、只能串行的最多做k个项目
     * m 表示你初始的资金
     * 说明: 你每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
     * 输出: 你最后获得的最大钱数。
     */
    public int findMaximizedCapital(int k, int m, int[] profits, int[] capitals) {
        if(profits == null || profits.length == 0) {
            return 0;
        }

        List<Project> projects = new ArrayList<>(profits.length);
        for(int i=0; i<profits.length; i++) {
            projects.add(new Project(capitals[i], profits[i]));
        }

        minCost.addAll(projects);

        int profit = 0;
        for(int i=0; i<k; i++) {
            profit = invest(m);
            if(profit == 0) {
                break;
            } else {
                m += profit;
            }
        }

        return m;
    }

    /**
     * 投资做项目，赚取收益
     */
    private int invest(int initial_capital) {
        int profit = 0;

        // 选择能做的项目
        while(!minCost.isEmpty() && minCost.peek().capital<=initial_capital) {
            maxProfit.add(minCost.poll());
        }

        // 选择收益最大的项目，一个项目只能做一次
        if(!maxProfit.isEmpty()) {
            profit = maxProfit.poll().profit;
        }

        return profit;
    }
}

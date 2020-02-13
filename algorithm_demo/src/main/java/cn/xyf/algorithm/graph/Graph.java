package cn.xyf.algorithm.graph;

import cn.xyf.algorithm.UnionFind;

import java.util.*;

public class Graph {
    // 顶点的集合，key 为顶点编号，也就是 Vertex 的 value
    private HashMap<Integer, Vertex> vertexes;
    // 边的集合
    private HashSet<Edge> edges;

    public Graph() {
        this.vertexes = new HashMap<>();
        this.edges = new HashSet<>();
    }

    public HashMap<Integer, Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(HashMap<Integer, Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public void setEdges(HashSet<Edge> edges) {
        this.edges = edges;
    }

    /**
     * BFS 宽度优先遍历，广度优先遍历
     */
    public void bfs(Vertex head) {
        if(head == null) {
            return;
        }
        Queue<Vertex> queue = new LinkedList<>();
        // 用于去重，保证一个节点只被处理一次
        HashSet<Vertex> set = new HashSet<>();

        queue.add(head);
        set.add(head);

        while(!queue.isEmpty()) {
            Vertex cur = queue.poll();
            System.out.println(cur.getValue());

            for(Vertex next : cur.getNexts()) {
                // 如果没有在集合中，就添加
                if(!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

    /**
     * DFS 深度优先遍历
     */
    public void dfs(Vertex head) {
        if(head == null) {
            return;
        }

        Stack<Vertex> stack = new Stack<>();
        // 用于去重，保证一个节点只被处理一次
        HashSet<Vertex> set = new HashSet<>();

        stack.push(head);
        set.add(head);
        System.out.println(head.getValue());

        while(!stack.isEmpty()) {
            Vertex cur = stack.pop();

            for(Vertex next : cur.getNexts()) {
                // 一条胡同走到黑
                // 后代中有一个没走过，就把当前顶点跟这一个后代顶点压入栈，
                // 压入这个后代顶点就打印
                if(!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.getValue());
                    break;
                }
            }
        }
    }

    /**
     * 图的拓扑排序，只针对有向无环图
     */
    public List<Vertex> topologicalSort(Graph graph) {
        // 图的所有顶点以及对应的入度
        HashMap<Vertex, Integer> inMap = new HashMap<>();
        // 入度为零的队列
        Queue<Vertex> zeroInQueue = new LinkedList<>();

        // 遍历所有顶点，初始化 inMap，zeroInQueue
        for (Vertex vertex : graph.vertexes.values()) {
            inMap.put(vertex, vertex.getIn());

            if(vertex.getIn() == 0) {
                zeroInQueue.add(vertex);
            }
        }

        List<Vertex> result = new ArrayList<>();
        // 消费入度为零的顶点，让后续顶点也加入
        while (!zeroInQueue.isEmpty()) {
            Vertex cur = zeroInQueue.poll();
            // 收集，作为返回集合
            result.add(cur);

            // 刷新入度非零的顶点，消除已经处理过的顶点对之后顶点入度的影响
            for (Vertex next : cur.getNexts()) {
                inMap.put(next, inMap.get(next)-1);
                // 收集
                if(inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }

        return result;
    }


    /*

    def topological_sort(graph_unsorted):
        graph_sorted = []

        while graph_unsorted:
            acyclic = False
            for node, edges in list(graph_unsorted.items()):
                for edge in edges:
                    if edge in graph_unsorted:
                        break
                else:
                    acyclic = True
                    del graph_unsorted[node]
                    graph_sorted.append((node, edges))

            if not acyclic:
                raise RuntimeError("A cyclic dependency occurred")

        return graph_sorted

     */

    /**
     * 最小生成树, kruskal 算法, 从边出发，优先判断权重小的边
     */
    public Set<Edge> kruskalMST(Graph graph) {
        // 用于合并以及判断是否有环
        UnionFind<Vertex> unionFind = new UnionFind<>(graph.vertexes.values());
        // 按照权重从小到大保存边
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>((e1, e2) -> e1.getWeight()-e2.getWeight());
        priorityQueue.addAll(graph.edges);

        Set<Edge> result = new HashSet<>();

        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            // 判断边的两个顶点是否在同一个集合里
            if(unionFind.isSameSet(edge.getFrom(), edge.getTo())) {
                result.add(edge);
                unionFind.union(edge.getFrom(), edge.getTo());
            }
        }
        return result;
    }

    /**
     * 最小生成树, prim 算法, 从点出发，遍历当前所有遍历过的点的 权重最小的边，加入点的集合
     */
    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>((e1, e2) -> e1.getWeight()-e2.getWeight());
        HashSet<Vertex> set = new HashSet<>();
        Set<Edge> result = new HashSet<>();

        // 遍历点，比如森林图
        for (Vertex vertex : graph.getVertexes().values()) {
            // 判断当前点是否判断过
            if (!set.contains(vertex)) {
                set.add(vertex);
                // 收集这个点的所有边，权重从小到大排序
                priorityQueue.addAll(vertex.getEdges());

                // 每次都是选择已有边中权重最小的边，然后判断其 to 点
                while (!priorityQueue.isEmpty()) {
                    Edge edge = priorityQueue.poll();
                    Vertex to = edge.getTo();

                    // 如果 to 点之前没有走过，收集其所有边，加入堆
                    if (!set.contains(to)) {
                        set.add(to);
                        result.add(edge);
                        priorityQueue.addAll(vertex.getEdges());
                    }
                }
            }
        }
        return result;
    }

    public static class GraphBuilder {
        /**
         * 基于 matrix 生成图
         * [[weight, fromV, toV]]
         * [
         *  [7, 1, 2],
         *  [5, 1, 3],
         *  [2, 2, 3]
         * ]
         */
        public static Graph builder(Integer[][] matrix) {
            Graph graph = new Graph();
            for(Integer[] line: matrix) {
                Integer weight = line[0];
                Integer from = line[1];
                Integer to = line[2];

                // 把 from 和 to 顶点都添加到图中，没有则先创建
                if(!graph.vertexes.containsKey(from)) {
                    graph.vertexes.put(from, new Vertex(from));
                }
                if(!graph.vertexes.containsKey(to)) {
                    graph.vertexes.put(to, new Vertex(to));
                }

                // 把 from 到 to 的边先创建后添加图中
                Vertex fromVertex = graph.vertexes.get(from);
                Vertex toVertex = graph.vertexes.get(to);
                Edge edge = new Edge(weight, fromVertex, toVertex);
                graph.edges.add(edge);

                // 修改两个顶点信息：入度，出度，邻居，边
                fromVertex.setIn(fromVertex.getIn()+1);
                fromVertex.setOut(fromVertex.getOut()+1);
                fromVertex.getNexts().add(toVertex);
                fromVertex.getEdges().add(edge);
            }

            return graph;
        }
    }
}

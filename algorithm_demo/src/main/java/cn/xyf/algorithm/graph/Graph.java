package cn.xyf.algorithm.graph;

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

package cn.xyf.algorithm.graph;

import java.util.HashMap;
import java.util.HashSet;

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

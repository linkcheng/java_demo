package cn.xyf.algorithm.graph;

public class Edge {
    // 边的权重
    private int weight;
    // 出发的节点
    private Vertex from;
    // 指向的节点
    private Vertex to;

    public Edge(int weight, Vertex from, Vertex to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }
}

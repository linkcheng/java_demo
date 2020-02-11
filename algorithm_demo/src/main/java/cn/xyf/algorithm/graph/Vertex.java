package cn.xyf.algorithm.graph;

import java.util.ArrayList;

/**
 * 图的顶点
 */
public class Vertex {
    // 顶点编号
    private int value;
    // 入度
    private int in;
    // 出度
    private int out;
    // 从我出发，发散出的边
    private ArrayList<Edge> edges;
    // 从我出发，邻居的节点
    private ArrayList<Vertex> nexts;

    public Vertex(int value) {
        this.value = value;
        in = 0;
        out = 0;
        edges = new ArrayList<>();
        nexts = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public ArrayList<Vertex> getNexts() {
        return nexts;
    }

    public void setNexts(ArrayList<Vertex> nexts) {
        this.nexts = nexts;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}

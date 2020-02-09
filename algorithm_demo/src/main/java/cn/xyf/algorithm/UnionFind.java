package cn.xyf.algorithm;

import java.util.HashMap;
import java.util.List;

/**
 * 并查集
 */
public class UnionFind {
    public static class Node {
        public int data;
    }

    public HashMap<Node, Node> parentMap;
    public HashMap<Node, Integer> sizeMap;

    public UnionFind(List<Node> nodes) {
        parentMap = new HashMap<>();
        sizeMap = new HashMap<>();
        makeSets(nodes);
    }

    /**
     * 生成基础数据结构，所有数据都放入 hashmap
     * parentMap：key 为数据节点 node, value 为 node 的父节点
     * sizeMap：key 为数据节点 node，value 为以 node 为代表节点的大小
     */
    private void makeSets(List<Node> nodes) {
        parentMap.clear();
        sizeMap.clear();
        for(Node node : nodes) {
            parentMap.put(node, node);
            sizeMap.put(node, 1);
        }
    }


    /**
     * 搜索 node 节点的代表节点 rep，并且把搜索路上的所有节点的代表节点都设置为 rep
     */
    public Node findRep(Node node) {
        Node parent = parentMap.get(node);
        Node rep = parent;
        if(parent != node) {
            rep = findRep(parent);
        }
        parentMap.put(node, rep);
        return parent;
    }

    /**
     * 并查集合并，返回合并后的代表节点
     */
    public Node union(Node node1, Node node2) {
        if(node1 == null || node2 == null) {
            return null;
        }

        Node rep1 = findRep(node1);
        Node rep2 = findRep(node2);
        Node rep = rep1;

        // 如果不在同一个并查集，进行合并
        if(rep1 != rep2) {
            int size1 = sizeMap.get(node1);
            int size2 = sizeMap.get(node2);
            // 小的并查集合并到大的上
            if(size1 < size2) {
                rep = rep2;
                parentMap.put(rep1, rep2);
                sizeMap.put(rep2, size1+size2);
            } else {
                rep = rep1;
                parentMap.put(rep2, rep1);
                sizeMap.put(rep1, size1+size2);
            }
        }

        return rep;
    }
}

package cn.xyf.algorithm;

import java.util.Collection;
import java.util.HashMap;

/**
 * 并查集
 */
public class UnionFind<T> {
    // parentMap：key 为数据节点 node, value 为 node 的父节点
    public HashMap<T, T> parentMap;
    // sizeMap：key 为数据节点 node，value 为以 node 为代表节点的大小
    public HashMap<T, Integer> sizeMap;

    public UnionFind() {
        parentMap = new HashMap<>();
        sizeMap = new HashMap<>();
    }

    public UnionFind(Collection<T> nodes) {
        parentMap = new HashMap<>();
        sizeMap = new HashMap<>();
        makeSets(nodes);
    }

    /**
     * 生成基础数据结构，所有数据都放入 hashmap
     * parentMap：key 为数据节点 node, value 为 node 的父节点
     * sizeMap：key 为数据节点 node，value 为以 node 为代表节点的大小
     */
    public void makeSets(Collection<T> nodes) {
        parentMap.clear();
        sizeMap.clear();
        for(T node : nodes) {
            parentMap.put(node, node);
            sizeMap.put(node, 1);
        }
    }

    /**
     * 搜索 node 节点的代表节点 rep，并且把搜索路上的所有节点的代表节点都设置为 rep
     */
    public T findRep(T node) {
        T parent = parentMap.get(node);
        T rep = parent;
        if(parent != node) {
            rep = findRep(parent);
        }
        parentMap.put(node, rep);
        return parent;
    }

    /**
     * 并查集合并，返回合并后的代表节点
     */
    public T union(T node1, T node2) {
        if(node1 == null || node2 == null) {
            return null;
        }

        T rep1 = findRep(node1);
        T rep2 = findRep(node2);
        T rep = rep1;

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

    /**
     * 是否在同一个集合中
     */
    public boolean isSameSet(T a, T b) {
        return findRep(a) == findRep(b);
    }
}

package cn.xyf.algorithm;

import java.util.HashMap;

/**
 * O(1) 时间复杂度实现 insert , delete, getRandom
 * @param <K>
 */
public class RandomPool<K> {
    private HashMap<K, Integer> keyIndexMap;
    private HashMap<Integer, K> indexKeyMap;
    private int size;

    public RandomPool() {
        keyIndexMap = new HashMap<>();
        indexKeyMap = new HashMap<>();
        size = 0;
    }

    public void insert(K key) {
        keyIndexMap.put(key, size++);
        indexKeyMap.put(size, key);
    }

    /**
     * 根据 size 计算随机值
     * @return K
     */
    public K getRandom() {
        if(size == 0) {
            return null;
        }
        int randomIndex = (int)(Math.random() * size);
        return indexKeyMap.get(randomIndex);
    }

    /**
     * 防止出现空洞，避免 size 内不连续
     * @param key
     */
    public void delete(K key) {
        int index = keyIndexMap.get(key);
        K lastKey = indexKeyMap.get(--size);

        // 调整 index 的对应关系
        indexKeyMap.put(index, indexKeyMap.get(--size));
        keyIndexMap.put(lastKey, index);

        // 移除 key 与最后一个 index
        keyIndexMap.remove(key);
        indexKeyMap.remove(size);
    }
}

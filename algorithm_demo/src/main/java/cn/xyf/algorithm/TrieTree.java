package cn.xyf.algorithm;

/**
 * 前缀树
 * 节点不含包内容，或者节点只包含统计数据，节点包含为通往下一级节点所有边，边包含信息
 * 可以用于
 *  搜索：判断一个字符串是否存在，或者按照开头几个字母搜索
 *  统计：统计字符串出现次数
 */
public class TrieTree {

    // 树的根节点
    private Node root;

    public TrieTree() {
        root = new Node();
    }

    /**
     * 字符串插入树
     */
    public void insert(String word) {
        if (word == null) {
            return;
        }
        // 字符串转化为 char 数组
        char[] chs = word.toCharArray();
        Node cur = root;
        int index = 0;

        for(char ch : chs) {
            index = ch - 'a';
            // 没有下一级的路就建立
            if(cur.map[index] == null) {
                cur.map[index] = new Node();
            }
            cur = cur.map[index];
            // 经过节点次数自增
            cur.pass++;
        }
        // 结尾节点次数自增
        cur.end++;
    }

    /**
     * 在树上搜索，是否存在一个字符串
     */
    public boolean search(String word) {
        if (word == null) {
            return false;
        }
        char[] chs = word.toCharArray();
        Node cur = root;
        int index = 0;

        for(char ch : chs) {
            index = ch - 'a';
            if(cur.map[index] == null) {
                return false;
            }
            cur = cur.map[index];
        }
        return cur.end != 0;
    }

    /**
     * 从树上删除字符串
     * @param word
     */
    public void delete(String word) {
        if(search(word)) {
            char[] chs = word.toCharArray();
            Node cur = root;
            int index = 0;

            for(char ch : chs) {
                index = ch - 'a';
                // 经过节点次数自减，如果当前节点只经过一次，其后的也只有一次，直接删除
                if(cur.map[index].pass-- == 1) {
                    cur.map[index] = null;
                    return;
                }
                cur = cur.map[index];
            }
            // 结尾节点次数自减
            cur.end--;
        }
    }

    /**
     * 前缀配置
     * @param pre
     * @return
     */
    public int prefixNumber(String pre) {
        if (pre == null) {
            return 0;
        }
        char[] chs = pre.toCharArray();
        Node node = root;
        int index = 0;
        for (int i = 0; i < chs.length; i++) {
            index = chs[i] - 'a';
            if (node.map[index] == null) {
                return 0;
            }
            node = node.map[index];
        }
        return node.pass;
    }

    public static void main(String[] args) {
        TrieTree trie = new TrieTree();
        System.out.println(trie.search("java"));

        trie.insert("java");
        System.out.println(trie.search("java"));

        trie.delete("java");
        System.out.println(trie.search("java"));

        trie.insert("java");
        trie.insert("java");
        trie.delete("java");
        System.out.println(trie.search("java"));

        trie.delete("java");
        System.out.println(trie.search("java"));

        trie.insert("hello");
        trie.insert("helloworld");
        trie.insert("hellojava");
        trie.insert("helloabc");
        trie.delete("hello");
        System.out.println(trie.search("helloa"));
        System.out.println(trie.prefixNumber("hello"));

    }

}


/**
 * 树节点
 */
class Node {
    // 经过该节点的次数
    int pass;
    // 以该节点结尾的次数
    int end;
    // 通往下一级节点所有边
    // 如果只有英文字符
    Node[] map;
    // HashMap<String, Node> edge;

    Node() {
        pass = 0;
        end = 0;
        map = new Node[26];
    }
}
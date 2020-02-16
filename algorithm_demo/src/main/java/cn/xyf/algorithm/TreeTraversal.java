package cn.xyf.algorithm;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

/**
 * 树的遍历
 */
public class TreeTraversal {
    public static class Node {
        int value;
        Node left;
        Node right;

        public Node(int v) {
            value = v;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    /**
     * 先序递归
     */
    public void preOrderRecur(Node root) {
        if(root == null) {
            return;
        }
        System.out.print(root.value + " ");
        preOrderRecur(root.left);
        preOrderRecur(root.right);
    }

    /**
     * 中序递归
     */
    public void inOrderRecur(Node root) {
        if(root == null) {
            return;
        }

        inOrderRecur(root.left);
        System.out.print(root.value + " ");
        inOrderRecur(root.right);
    }

    /**
     * 后序递归
     */
    public void postOrderRecur(Node root) {
        if(root == null) {
            return;
        }
        postOrderRecur(root.left);
        postOrderRecur(root.right);
        System.out.print(root.value + " ");
    }

    /**
     * 先序非递归
     */
    public void preOrderNonRecur(Node root) {
        if(root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        // 先加入根节点
        stack.push(root);
        Node cur;

        while(!stack.isEmpty()) {
            // 对于（子）根节点，弹出就打印
            cur = stack.pop();
            System.out.print(cur.value + " ");

            // 先压入右孩子，后弹出
            if(cur.right != null) {
                stack.push(cur.right);
            }
            // 在压入左孩子，先弹出
            if(cur.left != null) {
                stack.push(cur.left);
            }
        }
    }

    /**
     * 中序非递归
     */
    public void inOrderNonRecur(Node root) {
        if(root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        Node cur = root;

        while (!stack.isEmpty() || cur != null) {
            // 整棵树的所有左边界上的压入栈
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                // 左孩子为空了，弹出根节点并打印，
                cur = stack.pop();
                System.out.print(cur.value + " ");
                // 然后 cur 指向根的右孩子，为了把右孩子的所有左边界压入栈
                cur = cur.right;
            }
        }
    }

    /**
     * 后序非递归
     */
    public void postOrderNonRecur(Node root) {
        if(root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        // 在先序的时候，压栈顺序是 中 右 左 ，这里换成 中 左 右，然后借助辅助栈，逆序弹出即为 左 右 中
        Stack<Node> help = new Stack<>();
        stack.push(root);
        Node cur;

        while(!stack.isEmpty()) {
            cur = stack.pop();
            help.push(cur);

            if(cur.left != null) {
                stack.push(cur.left);
            }

            if(cur.right != null) {
                stack.push(cur.right);
            }
        }

        while(!help.isEmpty()) {
            System.out.print(help.pop().value + " ");
        }
    }

    /**
     * 后序非递归
     */
    public void postOrderNonRecur2(Node root) {
        if(root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node c;

        while (!stack.isEmpty()) {
            c = stack.peek();
            if (c.left != null && root != c.left && root != c.right) {
                stack.push(c.left);
            } else if (c.right != null && root != c.right) {
                stack.push(c.right);
            } else {
                System.out.print(stack.pop().value + " ");
                root = c;
            }
        }
    }

    /**
     * 以先序的方式序列化树
     * 空位置用 # , 节点间间隔用 _
     */
    public String serialPreOrderTree(Node root) {
        if(root == null) {
            return "#_";
        }

        String res = root.value + "_";
        res += serialPreOrderTree(root.left);
        res += serialPreOrderTree(root.right);

        return res;
    }

    // ================== 深度优先序列化 ==================
    /**
     * 以先序的方式反序列化，把字符串反序列化为树结构
     * 空位置用 # , 节点间间隔用 _
     */
    public Node deserialPreOrderTree(String preStr) {
        Queue<String> queue = split(preStr);
        return deserialPreOrderTree(queue);
    }

    public Node deserialPreOrderTree(Queue<String> queue) {
        String value = queue.poll();
        if(value == null || value.equals("#")) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = deserialPreOrderTree(queue);
        head.right = deserialPreOrderTree(queue);

        return head;
    }

    /**
     * 按层序列化树
     */
    public String serialTreeByLevel(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        String res = "";

        while (!queue.isEmpty()) {
            Node t = queue.poll();
            if(t != null) {
                queue.add(t.left);
                queue.add(t.right);
                res += t.value+"_";
            } else {
                res += "#_";
            }
        }
        return res;
    }

    private Queue<String> split(String str) {
        String[] strings = str.split("_");
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < strings.length; i++) {
            queue.add(strings[i]);
        }
        return queue;
    }

    // ================== 宽度优先序列化 ==================

    /**
     * 按层序列化树
     */
    public Node deserialTreeByLevel(String lelStr) {
        String[] strings = lelStr.split("_");
        return deserialTreeByLevel(strings, 0);
    }

    public Node deserialTreeByLevel(String[] strings, int index) {
        Node head = null;

        if(index < strings.length && !strings[index].equals("#")) {
            head = new Node(Integer.valueOf(strings[index]));
        }

        if(head != null) {
            head.left = deserialTreeByLevel(strings, index*2 + 1);
            head.right = deserialTreeByLevel(strings, index*2 + 2);
        }
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        TreeTraversal tt = new TreeTraversal();

        // recursive
        System.out.println("==============recursive==============");
        System.out.print("pre-order: ");
        tt.preOrderRecur(head);
        System.out.println();

        System.out.print("in-order: ");
        tt.inOrderRecur(head);
        System.out.println();

        System.out.print("pos-order: ");
        tt.postOrderRecur(head);
        System.out.println();

        // unrecursive
        System.out.println("============unrecursive=============");
        tt.preOrderNonRecur(head);
        System.out.println();
        tt.inOrderNonRecur(head);
        System.out.println();
        tt.postOrderNonRecur(head);
        System.out.println();
        tt.postOrderNonRecur2(head);
        System.out.println();

        // serial
        System.out.println("============serial in pre order=============");
        String res = tt.serialPreOrderTree(head);
        System.out.println(res);

        Node t1 = tt.deserialPreOrderTree(res);
        System.out.print("pre-order t1: ");
        tt.preOrderRecur(t1);
        System.out.println();

        System.out.println("============serial by level=============");
        res = tt.serialTreeByLevel(head);
        System.out.println(res);

        t1 = tt.deserialTreeByLevel(res);
        System.out.print("pre-order t1: ");
        tt.preOrderRecur(t1);
        System.out.println();

    }

}

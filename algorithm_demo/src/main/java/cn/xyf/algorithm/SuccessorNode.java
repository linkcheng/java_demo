package cn.xyf.algorithm;

/**
 * 中序遍历的方式，返回一棵树的任意节点的后续节点
 */
public class SuccessorNode {
    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    public Node getSuccessorNode(Node root) {
        if(root == null) {
            return null;
        }

        // 当前节点有右子树
        if(root.right != null) {
            return getLeftMostNode(root.right);
        } else {  // 没有右子树
            Node parent = root.parent;
            // 当前节点有父节点并且当前节点是父节点的左孩子
            while(parent!=null && parent.left != root) {
                root = parent;
                parent = root.parent;
            }
            return parent;
        }
    }

    /**
     * 获取当前节点的最左边的节点
     * @param root
     * @return
     */
    private Node getLeftMostNode(Node root) {
        if(root == null) {
            return null;
        }

        while(root.left != null) {
            root = root.left;
        }
        return root;
    }

    public static void main(String[] args) {
        SuccessorNode sn = new SuccessorNode();

        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + sn.getSuccessorNode(test));
    }
}

package cn.xyf.algorithm;

/**
 * 不使用额外的数据结构，只用有限几个变量，且在时间复杂度为 O(N) 内完成带有随机指针的链表复制
 */
public class CopyList {
    public static  class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }

    public Node copyList(Node head) {
        if(head == null) {
            return null;
        }

        // 每个节点后复制一个节点
        doubleList(head);
        // 给新增的节点设置 random 指针
        setNewNodeRandPtr(head);
        // 分离新增节点
        Node newHead = splitList(head);
        return newHead;
    }

    /**
     * 每个节点后复制一个节点
     */
    private Node doubleList(Node head) {
        Node cur = head;
        Node tmp;
        // 每个节点后复制一个节点
        while(cur != null) {
            tmp = new Node(cur.value);
            tmp.next = cur.next;
            cur.next = tmp;
            cur = tmp.next;
        }

        return head;
    }

    /**
     * 设置新增节点的 rand 指针
     */
    private Node setNewNodeRandPtr(Node head) {
        Node cur = head;

        while(cur != null) {
            if(cur.rand != null) {
                cur.next.rand = cur.rand.next;
            }
            cur = cur.next.next;
        }
        return head;
    }

    /**
     * 分离链表，奇偶位分离
     */
    private Node splitList(Node head) {
        Node newHead = head.next;

        Node cur = head;
        Node newCur = newHead;
        Node tmp;

        // cur 与 newCur 分别指向要处理的节点
        while (cur != null && cur.next != null && newCur != null) {
            tmp = cur.next.next;
            cur.next = tmp;
            newCur.next = tmp != null ? tmp.next : null;

            cur = cur.next;
            newCur = newCur.next;

        }
        return newHead;
    }

    private Node splitList1(Node head) {
        Node res = head.next;
        Node cur = head;
        Node next;
        Node curCopy;

        // split
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            cur.next = next;
            curCopy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }

    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        printRandLinkedList(head);
        CopyList cl = new CopyList();
//        printRandLinkedList(cl.doubleList(head));
//        printRandLinkedList(cl.setNewNodeRandPtr(head));
        printRandLinkedList(cl.copyList(head));
//        printRandLinkedList(cl.splitList(head));


        System.out.println("=========================");

    }
}

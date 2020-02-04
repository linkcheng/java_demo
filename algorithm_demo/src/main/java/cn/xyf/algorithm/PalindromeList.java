package cn.xyf.algorithm;

import java.util.Stack;

/**
 * 判断是否是回文  1 2 2 1， 1 2 3 2 1
 */
public class PalindromeList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
            next = null;
        }
    }

    /**
     * 基于栈实现, 空间复杂度 O(n)
     */
    public static boolean isPalindrome1(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Stack<Node> list = new Stack<>();
        Node cur = head;

        while (cur != null) {
            list.push(cur);
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            if(list.pop().value != cur.value) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    /**
     * 基于快慢指针与栈实现，空间复杂度为 O(n)，但是常数项较小
     */
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node mid = getMiddle(head);
        Node cur = mid.next;
        Stack<Node> list = new Stack<>();

        while (cur != null) {
            list.push(cur);
            cur = cur.next;
        }

        cur = head;
        while (!list.isEmpty()) {
            if(list.pop().value != cur.value) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    /**
     * 基于快慢指针以及链表逆序的方式实现，空间复杂度为 O(1)
     */
    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 中点
        Node mid = getMiddle(head);
        // 翻转
        Node newHead = reverse(mid);

        Node h1 = head;
        Node h2 = newHead;
        boolean isPalindrome = true;

        // 判断是否是回文
        while (h1 != null && h2 != null) {
            if(h1.value != h2.value) {
                isPalindrome = false;
                break;
            }
            h1 = h1.next;
            h2 = h2.next;
        }
        // 恢复
        reverse(newHead);
        return isPalindrome;
    }
    /**
     * 返回链表的中点位置的节点
     */
    private static Node getMiddle(Node head) {
        if(head == null || head.next == null || head.next.next == null) {
            return head;
        }
        Node fast = head;
        Node slow = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 反转链表
     */
    private static Node reverse(Node head) {
        if(head == null || head.next == null) {
            return head;
        }

        // 使用中间变量，防止丢失链表
        Node cur = head;
        Node nex = cur.next;
        Node tmp = null;
        // 原来的头结点将会变为尾结点，next 指向 null
        head.next = null;

        // 判断是否还有后续节点，如果 nex 为 null, 则表示 cur 为最后一各节点
        while (nex != null) {
            // 标记下一个节点，保留后续指针
            tmp = nex.next;
            // 指向前一个节点实现翻转
            nex.next = cur;
            // 向后移动
            cur = nex;
            nex = tmp;
        }

        return cur;
    }

    /**
     * 当前节点与后一个交换
     */
    private static Node swap(Node cur) {
        // 当前节点的下一个节点
        Node n1 = cur.next;
        // 当前节点的下一个节点的点一个节点
        Node n2 = n1.next;
        // 交换
        n1.next = cur;
        // 连接
        cur.next = n2;
        return n1;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");
//
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }
}

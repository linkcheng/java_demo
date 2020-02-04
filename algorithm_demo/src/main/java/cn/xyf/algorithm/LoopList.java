package cn.xyf.algorithm;

/**
 * 链表相交问题，可能有环
 * 只会有两种情况：
 *  1. 两个无环链表的相交
 *  2. 两个有环链表的相交（又有三种情况）
 *      a. 6 6 情况，即不相交
 *      b. 两个链表相交在环外，环上只有一个交点
 *      c. 两个链表相交在环上，环上只有两个交点
 *  * 3. 一个有环一个无环不可能在单链表中相交
 */
public class LoopList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 基于快慢指针判断是都有环
     */
    public boolean isLoop(Node head) {
        if(head == null) {
            return false;
        }

        Node fast = head;
        Node slow = head;

        while (slow.next != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回入环节点或者null
     */
    public Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        // 确定快慢指针相遇的地方
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        fast = head;
        // 计算交点
        while(fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /**
     * 计算链表从 head 到 tail 的长度，当 tail 为 null 时表示完整链表的长度
     * @return 链表长度跟 tail 前最后一个节点或者整个链表最后一个节点
     */
    private Node getLengthAndEnd(Node head, Node tail) {
        if(head == null) {
            return null;
        }

        Node cur = head;
        int length = 1;

        while (cur.next != tail) {
            length++;
            cur = cur.next;
        }

        Node res = new Node(length);
        res.next = cur;

        return res;
    }

    /**
     * 计算无环相交链表的交点
     * @param head1 链表1
     * @param head2 链表2
     * @param length1 head1 的长度
     * @param length2 head2 的长度
     * @return
     */
    private Node getCrossNodeForNoLoop(Node head1, Node head2, int length1, int length2) {
        int delta = length1 - length2;
        Node lng = delta > 0 ? head1 : head2;
        Node sht = lng == head1 ? head2 : head1;
        delta = Math.abs(delta);

        // 长链表先走 delta 步
        for(int i=0; i<delta; i++) {
            lng = lng.next;
        }

        // 相遇时即为交点
        while(sht != lng) {
            sht = sht.next;
            lng = lng.next;
        }

        return sht;
    }

    /**
     * 判断两个节点是否共同处于同一个环
     */
    private boolean isInSameLoop(Node n1, Node n2) {
        Node cur = n1.next;
        boolean isSameLoop = false;
        while(cur != n1) {
            if(cur == n2) {
                isSameLoop = true;
                break;
            }
            cur = cur.next;
        }
        return isSameLoop;
    }

    /**
     * 两个有环链表相交
     * 三种情况
     *      a. 6 6 情况，即不相交
     *      b. 两个链表相交在环外，环上只有一个交点
     *      c. 两个链表相交在环上，环上只有两个交点
     */
    public Node bothLoop(Node head1, Node head2) {
        Node n1 = getLoopNode(head1);
        Node n2 = getLoopNode(head2);

        if(n1 == n2) {  // 入环节点相等说明环上有一个交点，在环外相交，等效为 计算无环相交链表的交点 问题
            Node t1 = getLengthAndEnd(head1, n1);
            Node t2 = getLengthAndEnd(head2, n2);
            return getCrossNodeForNoLoop(head1, head2, t1.value, t2.value);
        } else { // 不相等
            if(isInSameLoop(n1, n2)) {  // 入环节点不相同，并且两个节点在同一个环上，说明
                return n1;  // n1 或者 n2 都可以
            } else {  // 6 6 模式，不相交
                return null;
            }

        }
    }

    /**
     * 两个无环链表相交，返回交点
     */
    public Node noLoop(Node head1, Node head2) {
        Node n1 = getLengthAndEnd(head1, null);
        Node n2 = getLengthAndEnd(head2, null);

        // 不相交
        if(n1.next != n2.next) {
            return null;
        }

        return getCrossNodeForNoLoop(head1, head2, n1.value, n2.value);
    }

    /**
     * 找交点
     */
    public Node getIntersectNode(Node head1, Node head2) {
        Node n1 = getLoopNode(head1);
        Node n2 = getLoopNode(head2);

        if(n1 == null && n2 == null) {
            return noLoop(head1, head2);
        } else if(n1 != null && n2 != null) {
            return bothLoop(head1, head2);
        } else {
            return null;
        }
    }


    public static void main(String[] args) {
        LoopList ll = new LoopList();

        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(ll.getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(ll.getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(ll.getIntersectNode(head1, head2).value);

    }
}

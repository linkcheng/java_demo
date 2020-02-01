package cn.xyf.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用两个队列实现栈
 */
public class TwoQueueStack {
    private Queue<Integer> queue;
    private Queue<Integer> help;

    public TwoQueueStack() {
        queue = new LinkedList<Integer>();
        help = new LinkedList<Integer>();
    }

    public void push(int obj) {
        queue.add(obj);
    }

    public Integer pop() {
        if(queue.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        // 返回队列最后一个值，也就是栈顶的值
        while (queue.size() > 1) {
            help.add(queue.poll());
        }
        Integer res = queue.poll();
        // 两个队列引用交换
        swap();

        return res;
    }

    public Integer peek() {
        Integer res = pop();
        push(res);
        return res;
    }

    /**
     * 交换引用
     */
    private void swap() {
        Queue<Integer> tmp = queue;
        queue = help;
        help = tmp;
    }
}

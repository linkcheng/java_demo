package cn.xyf.algorithm;

import java.util.Stack;

/**
 * 两个栈实现队列
 */
public class TwoStackQueue {
    private Stack<Integer> pushStack;
    private Stack<Integer> popStack;

    public TwoStackQueue() {
        pushStack = new Stack<>();
        popStack = new Stack<>();
    }

    public void add(int obj) {
        pushStack.add(obj);
        pour();
    }

    public Integer poll() {
        if (popStack.isEmpty() && pushStack.isEmpty()) {
            throw new RuntimeException("The queue is empty");
        }
        pour();
        return popStack.pop();
    }

    public Integer peek() {
        if (popStack.isEmpty() && pushStack.isEmpty()) {
            return null;
        }
        pour();

        return popStack.peek();
    }

    /**
     * 倒数据
     * pop 栈有东西的时候，不要往里 pop 栈里倒东西
     * pop 为空的时候可以从 push 栈里把数据倒进去，倒得时候一次性全部倒完
     */
    private void pour() {
        if(popStack.isEmpty()) {
            while(!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
    }
}

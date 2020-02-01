package cn.xyf.algorithm;

/**
 * 最小栈
 */
public class MinStack {
    private Stack dataStack;
    private Stack minStack;

    public MinStack(int size) {
        dataStack = new Stack(size);
        minStack = new Stack(size);
    }

    public void push(int obj) {
        if(dataStack.isFull()) {
            throw new RuntimeException("The stack is full");
        }

        dataStack.push(obj);

        if(minStack.isEmpty()){
            minStack.push(obj);
        } else {
            minStack.push(Math.min(minStack.peek(), obj));
        }
    }

    public Integer pop() {
        if(dataStack.isEmpty()) {
            throw new RuntimeException("The stack is empty");
        }
        minStack.pop();
        return dataStack.pop();
    }

    public Integer getMin() {
        return minStack.peek();
    }
}

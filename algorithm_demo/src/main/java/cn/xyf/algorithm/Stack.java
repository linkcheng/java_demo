package cn.xyf.algorithm;

/**
 * 栈
 */
public class Stack {
    private Integer[] arr;
    // 可以写入数据的位置
    private Integer index;

    public Stack(int size) {
        if(size<0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        arr = new Integer[size];
        index = 0;
    }

    public boolean isEmpty() {
        return index <= 0;
    }

    public boolean isFull() {
        return index >= arr.length;
    }

    public Integer peek() {
        if(isEmpty()) {
            return null;
        }
        return arr[index-1];
    }

    public void push(int obj) {
        if(isFull()) {
            throw new IndexOutOfBoundsException("The stack is full");
        }
        // 数据写入 index 的位置，index 向下移动
        arr[index++] = obj;
    }

    public Integer pop() {
        if(isEmpty()) {
            throw new IndexOutOfBoundsException("The stack is empty");
        }
        // 返回index 位置的前一个数，index 向前移动一下
        return arr[--index];
    }
}



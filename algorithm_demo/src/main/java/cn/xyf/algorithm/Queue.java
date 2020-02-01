package cn.xyf.algorithm;

/**
 * 队列
 */
public class Queue {
    private Integer[] arr;
    // 表示已经使用队列的大小
    // 通过 size 解耦 start 跟 end
    private Integer size;
    // 可以读取的开始位置
    private Integer start;
    // 可以读取的最后位置
    private Integer end;

    public Queue(int size) {
        if(size<0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        arr = new Integer[size];
        this.size = 0;
        start = 0;
        end = 0;
    }

    public Integer peek() {
        if(size <= 0) {
            return null;
        }
        return arr[start];
    }

    public void push(int obj) {
        // 通过控制 size 与 数组的大小实现是否可以继续 push
        // 避免通过 start 跟 end 反复比较以及扣边界
        if(size >= arr.length) {
            throw new IndexOutOfBoundsException("The queue is full");
        }
        size++;
        arr[end] = obj;
        end = end == arr.length-1 ? 0 : end+1;
    }

    public Integer poll() {
        if(size <= 0) {
            throw new IndexOutOfBoundsException("The queue is empty");
        }
        size--;
        Integer res = arr[start];
        start = start == arr.length-1 ? 0 : start+1;
        return res;
    }
}

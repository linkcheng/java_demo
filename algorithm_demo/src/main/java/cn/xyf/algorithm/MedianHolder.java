package cn.xyf.algorithm;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 从源源不断的数据源中，保存并快速去除中位数
 * 保存速度时间复杂度 O(log n)
 * 计算中位数时间复杂度 O(1)
 *
 * 利用一个大根堆和一个小根堆，分别保存一半数据，当大小差值超过 1 时，搬移数据
 * 大根堆 保存小于中位数的部分
 * 小根堆 保存大于中位数的部分
 * 两个堆的堆顶就是中位数所在
 */
public class MedianHolder {
    // 大根堆 保存小于中位数的部分
    private PriorityQueue<Integer> maxHeap;
    // 小根堆 保存大于中位数的部分
    private PriorityQueue<Integer> minHeap;

    public MedianHolder() {
        maxHeap = new PriorityQueue<>((o1, o2) -> o2-o1);
        minHeap = new PriorityQueue<>();
    }

    /**
     * 调整两个堆的大小，使两个堆的大小差不超过1
     */
    private void rebalance() {
        int maxSize = maxHeap.size();
        int minSize = minHeap.size();

        if(maxSize - minSize > 1) {
            minHeap.add(maxHeap.poll());
        }

        if(minSize - maxSize > 1) {
            maxHeap.add(minHeap.poll());
        }
    }

    /**
     * 保存数
     * @param num
     */
    public void addNumber(int num) {
        // 默认先添加到大根堆
        if(maxHeap.isEmpty()) {
            maxHeap.add(num);
            return;
        }

        // 小于等于 大根堆的最大值的时候，加入大根堆
        if(num <= maxHeap.peek()) {
            maxHeap.add(num);
        } else {
            if (minHeap.isEmpty()) {
                minHeap.add(num);
                return;
            }

            // 大于等于 小根堆的最小值的时候，加入小根堆
            if (num >= minHeap.peek()) {
                minHeap.add(num);
            } else {
                maxHeap.add(num);
            }
        }

        rebalance();
    }

    /**
     * 计算中位数
     * @return
     */
    public Integer getMedian() {
        int maxSize = maxHeap.size();
        int minSize = minHeap.size();

        if(maxSize+minSize == 0) {
            return null;
        }

        Integer maxHead = maxHeap.peek();
        Integer minHead = minHeap.peek();
        if(maxSize == minSize) {
            return (maxHead+minHead) >> 1;
        }

        return maxSize > minSize ? maxHead : minHead;
    }

    // for test
    public static int[] getRandomArray(int maxLen, int maxValue) {
        int[] res = new int[(int) (Math.random() * maxLen) + 1];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue);
        }
        return res;
    }

    // for test, this method is ineffective but absolutely right
    public static int getMedianOfArray(int[] arr) {
        int[] newArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(newArr);
        int mid = (newArr.length - 1) / 2;
        if ((newArr.length & 1) == 0) {
            return (newArr[mid] + newArr[mid + 1]) / 2;
        } else {
            return newArr[mid];
        }
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        boolean err = false;
        int testTimes = 200000;

        for (int i = 0; i != testTimes; i++) {
            int len = 30;
            int maxValue = 1000;
            int[] arr = getRandomArray(len, maxValue);
            MedianHolder medianHold = new MedianHolder();

            for (int j = 0; j != arr.length; j++) {
                medianHold.addNumber(arr[j]);
            }

            if (medianHold.getMedian() != getMedianOfArray(arr)) {
                err = true;
                printArray(arr);
                break;
            }
        }
        System.out.println(err ? "Oops..what a fuck!" : "today is a beautiful day^_^");

    }

}

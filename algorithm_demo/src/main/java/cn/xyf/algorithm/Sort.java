package cn.xyf.algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 升序
 */
public class Sort {
    public void bubbleSort(int[] arr) {
        if(arr==null || arr.length<2) {
            return;
        }

        for(int i=arr.length-1; i>0; i--) {
            for(int j=0; j<i; j++) {
                if(arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                }
            }
        }
    }

    public void selectionSort(int[] arr) {
        if(arr==null || arr.length<2) {
            return;
        }

        for(int i=0; i<arr.length; i++) {
            int minIndex = i;
            for(int j=i+1; j<arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    public void insertSort(int[] arr) {
        if(arr==null || arr.length<2) {
            return;
        }

        for(int i=1; i<arr.length; i++) {
            for(int j=i-1; j>=0 && arr[j]>arr[j+1]; j--) {
                swap(arr, j, j+1);
            }
        }
    }


    public void swap(int[] arr, int start, int end) {
        int length = arr.length;

        if(start>length || end>length) {
            return;
        }

        int tmp = arr[start];
        arr[start] = arr[end];
        arr[end] = tmp;
    }


    /**
     * 归并排序
     * @param arr 要排序的数组
     * 时间复杂度：O(n*log n)
     * 空间复杂度：O(n)
     * 稳定性：可以实现成稳定的
     */
    public void mergeSort(int[] arr) {
        if(arr==null || arr.length<2) {
            return;
        }
        msSortProcess(arr, 0, arr.length-1);
    }

    /**
     * 归并排序的排序过程
     * @param arr 要排序的数组
     * @param left 数组中待排序部分的左边位置
     * @param right 数组中待排序部分的右边位置
     */
    private void msSortProcess(int[] arr, int left, int right) {
        if(left==right) {
            return;
        }
        // 中点
        int mid = left + ((right-left)>>1);

        // 左边排序
        msSortProcess(arr, left, mid);

        // 右边排序
        msSortProcess(arr, mid+1, right);

        // 合并
        msMergeProcess(arr, left, mid, right);
    }

    /**
     * 归并排序的归并过程
     * @param arr 要排序的数组
     * @param left 数组中待排序部分的左边位置
     * @param mid 数组中待排序部分的中间位置
     * @param right 数组中待排序部分的右边位置
     */
    private void msMergeProcess(int[] arr, int left, int mid, int right) {
        int[] help = new int[right-left+1];
        int i = 0;
        int p1 = left;
        int p2 = mid+1;

        // 从小到大依次从两个数组中选择数据合并
        while(p1<=mid && p2<=right) {
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        // 两个有且只有一个越界
        while(p1<=mid) {
            help[i++] = arr[p1++];
        }

        while(p2<=right) {
            help[i++] = arr[p2++];
        }

        // 覆盖原数组
        for(i=0; i<help.length; i++) {
            arr[left+i] = help[i];
        }
    }

    /**
     * 求小和问题
     * 求左边数小于当前数的累计和
     * 4 1 3 5     0 6
     * 0 0 1 4+1+3 0 4+1+3+5  =  22
     * 转化为：右边数比当前数大的数量*当前数
     */
    public int sumByMergeSort(int[] arr) {
        if(arr==null || arr.length<2) {
            return 0;
        }
        return msSortProcessForSum(arr, 0, arr.length-1);
    }

    /**
     * 归并排序的排序过程
     * @param arr 要排序的数组
     * @param left 数组中待排序部分的左边位置
     * @param right 数组中待排序部分的右边位置
     */
    private int msSortProcessForSum(int[] arr, int left, int right) {
        if(left==right) {
            return 0;
        }
        // 中点
        int mid = left + ((right-left)>>1);

        // 左边排序
        int sumLeft = msSortProcessForSum(arr, left, mid);

        // 右边排序
        int sumRight = msSortProcessForSum(arr, mid+1, right);

        // 合并
        int sumMerge = msMergeProcessForSum(arr, left, mid, right);
        return sumMerge + sumLeft + sumRight;
    }

    /**
     * 求小和问题
     * 求左边数小于当前数的累计和
     * 4 1 3 5     0 6
     * 0 0 1 4+1+3 0 4+1+3+5  =  22
     */
    private int msMergeProcessForSum(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        int sum = 0;

        // 从小到大依次从两个数组中选择数据合并
        while(p1<=mid && p2<=right) {
//            if(arr[p1] < arr[p2]) {
//                sum += arr[p1]*(right-p2+1);
//            }
            // 发现一个右边大于左边的数字，记录一次，发现多个记录多次
            sum +=  arr[p1] < arr[p2] ?  arr[p1]*(right-p2+1) : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        // 两个有且只有一个越界
        while(p1<=mid) {
            help[i++] = arr[p1++];
        }

        while(p2<=right) {
            help[i++] = arr[p2++];
        }

        // 覆盖原数组
        for(i=0; i<help.length; i++) {
            arr[left+i] = help[i];
        }

        return sum;
    }

    /**
     * 快排
     * @param arr 数组
     * 时间复杂度：O(n*log n)
     * 空间复杂度：O(log n)
     * 稳定性：不能是实现成稳定的
     */
    public void quickSort(int[] arr) {
        if(arr==null || arr.length<2) {
            return;
        }
        qsQuickSort(arr, 0, arr.length-1);
    }

    /**
     * @param arr 数组
     * @param left 左边界
     * @param right 右边界
     */
    public void qsQuickSort(int[] arr, int left, int right) {
        if(left < right) {
            // 随机获取划分值
            swap(arr, left+(int)(Math.random()*(right-left+1)), right);

            int[] p = partition(arr, left, right);
            qsQuickSort(arr, left, p[0]-1);
            qsQuickSort(arr, p[1]+1, right);
        }
    }

    /**
     * 把一个乱序数据，分成小于，等于，大于数组最后一个数的三部分，每一部分可以乱序
     * @param arr 需要分组的数组
     * @param left 左边界
     * @param right 右边界
     * @return 相等区域的左右边界
     */
    public int[] partition(int[] arr, int left, int right) {
        // 小于区域的右边界
        int less = left - 1;
        // 大于区域的左边界以及分界数，分界数为数组当前区域的最后一个数字
        int more = right;
        // left 为 cur 指针，遍历 arr

        while(left < more) {
            if(arr[left]<arr[right]) {  // 当前数<分界数
                // 交换，并且小于区域扩展，并使用下一个数
                swap(arr, ++less, left++);
            } else if (arr[left]>arr[right]) {  // 当前数>分界数
                // 交换，并且大于区域扩展，交换后使用当前位置的数
                swap(arr, --more, left);
            } else {  // // 当前数==分界数
                // 下一个数
                left++;
            }
        }

        swap(arr, more, right);

        return new int[] {less+1, more};
    }


    /**
     * 堆排序：大根堆
     * @param arr 数组
     * 时间复杂度：O(n * log n)，常数项比较大
     * 稳定性：不稳定
     */
    public void heapSort(int[] arr) {
        if(arr==null || arr.length<2) {
            return;
        }

        // 堆大小
        int heapSize = arr.length;
        // 构建大根堆
        for(int i=0; i<heapSize; i++) {
            heapInsert(arr, i);
        }

        // 通过控制数组边界，也就是size的大小，实现调整堆容量大小
        // 第一个数跟最后一个数交换，也就是移动出最大的（根节点）
        // 重新调整新的大小的堆，使之再次成为大根堆（heapify）
        // 互换操作，最终是堆的大小减成 0，结束
        swap(arr, 0, --heapSize);

        // 互换操作，最终是堆的大小减成 0，结束
        while(heapSize>0) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    /**
     * 往堆中插入数据，构建大根堆，但是无序
     * @param arr 数组
     * @param index 位置
     * 时间复杂度：O(n)
     */
    public void heapInsert(int[] arr, int index) {
        // 通过下标位置的计算实现父子节点对应
        int parent = (index-1)>>1;

        // 如果当前节点>父节点，一直往上交换
        while(parent>=0 && arr[index] > arr[parent]) {
            swap(arr, parent, index);
            index = parent;
            parent = (index-1)>>1;
        }
    }

    /**
     * 调整堆，使之保持大根堆结构
     * @param arr 数组
     * @param parent 开始的位置
     * @param size 堆大小
     */
    public void heapify(int[] arr, int parent, int size) {
        int leftChild = parent * 2 + 1;

        while(leftChild<size) {
            int rightChild = leftChild + 1;
            // 根据左右孩子的值的大小，取得最大值的下标
            int largest = rightChild<size && arr[rightChild]>arr[leftChild] ? rightChild : leftChild;
            // 再跟父节点判断谁大
            largest = arr[largest] > arr[parent] ? largest : parent;
            // 父节点就是最大的，不需要调整
            if(largest == parent) {
                break;
            }

            // 父节点跟较大节点交换
            swap(arr, largest, parent);
            parent = largest;
            leftChild = parent * 2 + 1;
        }
    }

    /*
     * 贪心策略：脑补一个标准，按照这个标准确定一个答案
     * 比如：时间轴上有如多课程，按照课程最早结束，可以安排出一天最多的课程数量
     */

    public static void main(String[] args) {
//        A a = new B(1);
//        a.hello();

        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 123);
        map.put("pear", 456);
        map.put("banana", 789);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }

        // 优化的比较器
        A[] aList = new A[] {new A(1), new A(3), new A(2)};
        Arrays.sort(aList, (o1, o2) -> o1.id - o2.id);
        for(A i: aList) {
            System.out.println(i.id);
        }

        B[] bList = new B[] {new B(1), new B(3), new B(2)};
        Arrays.sort(bList, Comparator.comparingInt(o -> o.id));
        for(B i: bList) {
            System.out.println(i.id);
        }
    }
}


class A {
    public int id;
    public A(int id) {
        this.id = id;
    }
    public void hello() {
        System.out.println("hello A");
    }
}

class B extends A {
    public B(int id) {
        super(id);
    }
    @Override
    public void hello() {
        System.out.println("hello B");
    }
}


/**
 * 比较器
 */
class IdAscendingComparator implements Comparator<A> {

    @Override
    public int compare(A o1, A o2) {
        return o1.id - o2.id;
    }
}
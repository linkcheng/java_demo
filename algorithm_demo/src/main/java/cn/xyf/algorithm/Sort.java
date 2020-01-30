package cn.xyf.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*升序*/
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
            // 发现一个右边大于左边的数字，记录一次
            if(arr[p1] < arr[p2]) {
                sum += arr[p1]*(right-p2+1);
            }
//            sum +=  arr[p1] < arr[p2] ?  arr[p1]*(right-p2+1) : 0;
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

    public void quickSort(int[] arr, int left, int right) {
        if(left < right) {
            // 随机获取划分值
            swap(arr, left+(int)(Math.random()*(right-left+1)), right);

            int[] p = partition(arr, left, right);
            quickSort(arr, left, p[0]-1);
            quickSort(arr, p[1]+1, right);
        }
    }


    /*
     * 贪心策略：脑补一个标准，按照这个标准确定一个答案
     * 比如：时间轴上有如多课程，按照课程最早结束，可以安排出一天最多的课程数量
     */

    public static void main(String[] args) {
        A a = new B();
        a.hello();

        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 123);
        map.put("pear", 456);
        map.put("banana", 789);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }
}


class A {
    public void hello() {
        System.out.println("hello A");
    }
}

class B extends A {
    @Override
    public void hello() {
        System.out.println("hello B");
    }
}
package cn.xyf.algorithm;


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
     * 贪心策略：脑补一个标准，按照这个标准确定一个答案
     * 比如：时间轴上有如多课程，按照课程最早结束，可以安排出一天最多的课程数量
     */

}


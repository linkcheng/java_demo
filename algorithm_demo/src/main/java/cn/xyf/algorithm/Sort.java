package cn.xyf.algorithm;


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
     * 贪心策略：脑补一个标准，按照这个标准确定一个答案
     * 比如：时间轴上有如多课程，按照课程最早结束，可以安排出一天最多的课程数量
     */

}


package cn.xyf.algorithm;

/**
 * 无序数组中，有序后相邻数据差的最大值
 */
public class MaxGap {

    public static int getMaxGap(int[] arr) {
        int length = arr.length;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(int i=0; i<length; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        if(max == min) {
            return 0;
        }

        boolean[] hasNum = new boolean[length+1];
        int[] maxs = new int[length+1];
        int[] mins = new int[length+1];
        int index = 0;
        int bucketSize = (max - min) / (length+1) + 1;

        for(int i=0; i<length; i++) {
            // 所有数字的位置都是相对于最小值的位置
            index = (arr[i] - min) / bucketSize;
            maxs[index] = hasNum[index] ? Math.max(arr[i], maxs[index]) : arr[i];
            mins[index] = hasNum[index] ? Math.min(arr[i], mins[index]) : arr[i];
            hasNum[index] = true;
        }

        int lastMax = maxs[0];
        int res = 0;
        for(int i=1; i<length; i++) {
            if(hasNum[i]) {
                res = Math.max(res, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {0, 49, 13, 37};

        int res = getMaxGap(arr);
        System.out.println(res);
    }
}

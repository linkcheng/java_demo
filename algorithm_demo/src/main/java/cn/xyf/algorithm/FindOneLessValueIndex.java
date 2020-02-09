package cn.xyf.algorithm;

/**
 * 在数组中找到一个局部最小的位置
 */
public class FindOneLessValueIndex {

    public static int getMinIndex(int[] arr) {
        if(arr == null || arr.length == 0) {
            return -1;
        }

        // 0 位置是否是局部最小位置
        if(arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }

        // n-1 位置是否是局部最小位置
        if(arr[arr.length-1] < arr[arr.length-2]) {
            return arr.length - 1;
        }

        // 基于二分查找的思想
        int left = 0;
        int right = arr.length-1;
        int mid;
        while(left <= right) {
            mid = left + ((right - left) >> 1);
            /* 正常二分查找，基于对比某个 key
            if (arr[mid] == key) return mid;
            else if(arr[mid] > key) right = mid - 1;
            else left = mid + 1;
            */
            // 此处是比较左右临近的节点的大小关系
            if(arr[mid] > arr[mid-1]) {  // mid 不是局部最小,先检查右边
                right = mid - 1;
            } else if(arr[mid] > arr[mid+1]) {  // mid 不是局部最小, 再检查右边
                left = mid + 1;
            } else {  // 中间为就是局部最小
                return mid;
            }
        }

        return 0;
    }

    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1; // no exist
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        int left = 1;
        int right = arr.length - 2;
        int mid = 0;
        while (left < right) {
            mid = (left + right) / 2;
            if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = { 6, 5, 3, 4, 6, 7, 8 };
        printArray(arr);
        int index = getLessIndex(arr);
        System.out.println("index: " + index + ", value: " + arr[index]);

        int min = getMinIndex(arr);
        System.out.println("index: " + index + ", value: " + arr[index]);

    }

}

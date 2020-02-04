package cn.xyf.algorithm;

/**
 * 给定一个有 N*M 的整型矩阵 matrix 和一个整数K，matrix 的每一行和每一 列都是排好序的。
 * 时间复杂度为O(N+M)，额外空间复杂度为O(1)，判断 K 是否在 matrix 中。
 */
public class FindNumInSortedMatrix {
    public static boolean isContains(int[][] matrix, int key) {
        // 根据右上的点判断
        // 行坐标
        int row = 0;
        // 列坐标
        int col = matrix[0].length -1;

        // 边界判断
        while (row < matrix.length && col > -1) {
            if(matrix[row][col] > key) {
                col--;
            } else if(matrix[row][col] < key) {
                row++;
            } else {  // matrix[row][col] == key
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][] { { 0, 1, 2, 3, 4, 5, 6 },// 0
                { 10, 12, 13, 15, 16, 17, 18 },// 1
                { 23, 24, 25, 26, 27, 28, 29 },// 2
                { 44, 45, 46, 47, 48, 49, 50 },// 3
                { 65, 66, 67, 68, 69, 70, 71 },// 4
                { 96, 97, 98, 99, 100, 111, 122 },// 5
                { 166, 176, 186, 187, 190, 195, 200 },// 6
                { 233, 243, 321, 341, 356, 370, 380 } // 7
        };
        int K = 213;
        System.out.println(isContains(matrix, K));
    }

}

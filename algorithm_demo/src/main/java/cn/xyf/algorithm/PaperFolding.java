package cn.xyf.algorithm;

/**
 * 对折纸条，打印折痕方向
 */
public class PaperFolding {
    public static void printAllFolds(int N) {
        printProcess(1, N, true);
    }

    /**
     *
     * @param i 当前层数
     * @param N 一共要打印的层数
     * @param down 是否是凹折痕
     */
    public static void printProcess(int i, int N, boolean down) {
        if (i > N) {
            return;
        }
        printProcess(i + 1, N, true);
        System.out.println(down ? "down " : "up ");
        printProcess(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }
}

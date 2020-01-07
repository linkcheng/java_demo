package cn.xyf.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class SortTest {
    int[] array;
    Sort sort;

    @Before
    public void init() {
        array = new int[]{9,8,7,6,5,4,3,2,1};
        sort = new Sort();
    }

    @Test
    public void testBubbleSort() {
        int[] arr = array.clone();
        log.info(Arrays.toString(arr));
        sort.bubbleSort(arr);
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testSelectionSort() {
        int[] arr = array.clone();
        log.info(Arrays.toString(arr));
        sort.selectionSort(arr);
        log.info(Arrays.toString(arr));
    }
}

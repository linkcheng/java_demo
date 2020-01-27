package cn.xyf.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    @Test
    public void testInsertSort() {
        int[] arr = array.clone();
        log.info(Arrays.toString(arr));
        sort.insertSort(arr);
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testMergeSort() {
        int[] arr = array.clone();
        log.info(Arrays.toString(arr));
        sort.mergeSort(arr);
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testSumByMergeSort() {
        int[] arr = {4,1,3,5,0,6};
        log.info(Arrays.toString(arr));
        int sum = sort.sumByMergeSort(arr);
        log.info("sum="+sum);
        log.info(Arrays.toString(arr));
    }

    @Test
    public void testReflect() throws Exception{
        printClassInfo("".getClass());
        printClassInfo(Runnable.class);
        printClassInfo(java.time.Month.class);
        printClassInfo(String[].class);
        printClassInfo(int.class);
        printClassInfo(int[].class);
//        printMethodInfo(int.class);
        printMethodInfo(String.class);
    }

    public void printClassInfo(Class cls) {
        log.info("=================");
        log.info("Class name: " + cls.getName());
        log.info("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            log.info("Package name: " + cls.getPackage().getName());
        }
        log.info("is interface: " + cls.isInterface());
        log.info("is enum: " + cls.isEnum());
        log.info("is array: " + cls.isArray());
        log.info("is primitive: " + cls.isPrimitive());
    }

    public void printMethodInfo(Class cls) throws Exception{
        log.info("=================");
//        log.info(Arrays.toString(cls.getMethods()));
        // 获取public方法getScore，参数为String:
        log.info(cls.getMethod("indexOf", int.class)+"");
        // 获取继承的public方法getName，无参数:
        log.info(cls.getMethod("toString")+"");
        Method indexOf = cls.getMethod("indexOf", String.class);
        log.info(indexOf.invoke("hello22", "2")+"");

    }


    @Test
    public void testList() {
        List<String> list = Arrays.asList("apple", "pear", "banana");
        list.toArray();

        for(Iterator<String> iterator=list.iterator(); iterator.hasNext(); ) {
            log.info(iterator.next());
        }

        for(String string: list){
            log.info(string);
        }

        String s = "c";
        log.info(s.equals(new String("c"))+"");
    }

}

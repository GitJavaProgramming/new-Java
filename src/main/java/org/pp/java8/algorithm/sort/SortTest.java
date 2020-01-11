package org.pp.java8.algorithm.sort;

import java.util.Random;

/**
 * 排序算法可视化参考地址:
 * https://www.cnblogs.com/onepixel/articles/7674659.html
 * https://www.runoob.com/w3cnote/ten-sorting-algorithm.html
 */
public class SortTest {

    private static final int N = 10;
    public static Integer[] arr = new Integer[N];
    static String[] arr2 = {"a", "b", "c", "d", "e"};

    public static void main(String[] args) {
        Util.random();
        /* 冒泡排序 */
//        Util.sorted(new BubbleArraySort</*类型推断*/>()::sort, arr);
//        sorted(new BubbleArraySort</*类型推断*/>()::sort, arr2);
        /* 插入排序 */
        Util.sorted(new InsertionArraySort()::sort, arr);
    }

    static class Util {

        /**
         * 数组存放生成的N个随机数，生成的数可重复
         */
        public static void random() {
            Random rnd = new Random();
            int i = 0;
            while (i < N) {
                arr[i++] = rnd.nextInt(N);
            }
        }

        public static <T> void sorted(ArraySortInterface sort, T[] arr) {
            sort.sort(arr);
        }
    }
}

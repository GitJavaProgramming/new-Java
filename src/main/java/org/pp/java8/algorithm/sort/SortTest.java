package org.pp.java8.algorithm.sort;

import org.pp.java8.algorithm.random.MathRandom;

import java.util.List;

/**
 * 排序算法可视化参考地址:
 * https://www.cnblogs.com/onepixel/articles/7674659.html
 * https://www.runoob.com/w3cnote/ten-sorting-algorithm.html
 */
public class SortTest {

    /* 待排序序列的元素个数 */
    private static final int N = 10;
    public static Integer[] arr = new Integer[N];
    static String[] arr2 = {"a", "c", "d", "b", "e"};

    public static void main(String[] args) {
        Util.randomDistinct(100/*100以内的N个随机数*/);
//        Util.randomDistinct(100, 110);

        /* 冒泡排序 */
//        sorted(new BubbleSort</*类型推断*/>()::sort, arr);
//        sorted(new BubbleSort</*类型推断*/>()::sort, arr2);
        /* 插入排序 */
//        sorted(new InsertionSort()::sort, arr); // == new InsertionSort().sort(arr);
        // 接口形式是这样的情况下这里如何才能用ClassName::instanceMethodName这种形式？？
        // 泛型类由于类型擦除不能通过ClassName::instanceMethodName这种形式引用吗？？？
//        sorted(InsertionSort::sort, arr);

        /*************************
         * 分治思想
         * **********************/
        /* 合并排序 */
//        System.out.println(Arrays.toString(arr));
//        sorted(new MergeSort()::sort, arr);
//        Util.randomRepeat();
        sorted(new QuickSort()::sort, arr);

    }

    public static <T extends Comparable<T>> void sorted(ArraySortInterface<T> sort, T[] arr) {
        sort.sort(arr);
    }

    static class Util {


        /**
         * 数组存放生成的N个随机数，生成的数可重复
         */
        public static void randomRepeat() {
            java.util.Random rnd = new java.util.Random();
            int i = 0;
            while (i < N) {
                arr[i++] = rnd.nextInt(N);
            }
        }

        /**
         * 生成N个不同数 生成的数都不同
         */
        public static void randomDistinct() {
            randomDistinct(N);
//            List<Integer> list = new ArrayList<>(N);
//            int index = 0;
//            while (index < N) {
//                list.add(index++);
//            }
//            Collections.shuffle(list);
//            list.toArray(arr);
        }

        /**
         * 生成m个不同数 生成的数都不同 m个数中取n个不同数
         */
        public static void randomDistinct(int m) {
            randomDistinct(0, m);
        }

        /**
         * 生成m-s个随机数，随机数在范围[s,m)内，从中取N个数
         *
         * @param s 起始值
         * @param m 结束值
         */
        public static void randomDistinct(int s/*from 包含 s<m and m-s>=N*/, int m/*to 不包含*/) {
            List<Integer> list = MathRandom.randomIntegerListDistinct(s, m, N);
            list.toArray(arr);
        }
    }
}

package org.pp.java8.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
//        Util.sorted(new BubbleArraySort</*类型推断*/>()::sort, arr);
//        Util.sorted(new BubbleArraySort</*类型推断*/>()::sort, arr2);
        /* 插入排序 */
        Util.sorted(new InsertionArraySort()::sort, arr);
    }

    static class Util {

        /**
         * 数组存放生成的N个随机数，生成的数可重复
         */
        public static void randomRepeat() {
            Random rnd = new Random();
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
            List<Integer> list = randomDistinct(s, m, N);
            list.toArray(arr);
        }

        /**
         * 生成整数数值范围在[s, m)区间的m-s个不同的随机数，从中取出n个数
         * @param s 数值开始值 包含这个值s
         * @param m 数值结束值 不包含m
         * @param n 取n个数
         * @return 取到的集合
         */
        private static List<Integer> randomDistinct(int s, int m, int n) {
            if ((s > m) || (m - s < n)) {
                throw new IllegalArgumentException("参数s必须不大于参数m的值,并且区间[s,m)值不小于n");
            }
            List<Integer> list = new ArrayList<>(m - s);
            int index = s;
            while (index < m) {
                list.add(index++);
            }
            Collections.shuffle(list); // 乱序
            if (list.size() == n) {
                return list;
            }
            return list.subList(0, n); // 取N个乱序数 (rand(s), rand(s) + n)--rand(s)<s and s+n<=m
        }

        public static <T> void sorted(ArraySortInterface sort, T[] arr) {
            sort.sort(arr);
        }
    }
}

package org.pp.java8.algorithm.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 排序算法可视化参考地址:
 * https://www.cnblogs.com/onepixel/articles/7674659.html
 * https://www.runoob.com/w3cnote/ten-sorting-algorithm.html
 */
public class SortTest {

    public static void main(String[] args) {
        Util.random();
        /* 冒泡排序 */
//        Util.sorted(new BubbleArraySort</*类型推断*/>()::sort, Util.arr);
//        sorted(new BubbleArraySort</*类型推断*/>()::sort, Util.arr2);
        /* 插入排序 */
        Util.sorted(new InsertionArraySort()::sort, Util.arr);

//        Util.strCmp("abcdefghcde", "cde");
    }

    static class Util {

        private static final int N = 10;
        private static Integer[] arr = new Integer[N];
        static String[] arr2 = {"a", "b", "c", "d", "e"};

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

        /**
         * 字符串（比较）匹配 文本：T[0...n-1] 模式：P[0...m-1]
         * for i<-0 to n-m do
         *   j<-0
         *   while j<m and P[j]=T[i+j] do
         *     j<-j+1
         *     if j=m return i
         * return -1
         */
        public static Integer[] strCmp(String text, String regex) {
            char[] textChars = text.toCharArray();
            char[] regexChars = regex.toCharArray();
            int n = textChars.length;
            int m = regexChars.length;
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i <= n - m; i++) {
                int j = 0;
                while (j < m && regexChars[j] == textChars[i + j]) { // 子串绝对匹配
                    j++;
                    if (j == m) {
                        System.out.println("找到子串，起始位置：" + i);
//                        return i; // 找到要查找字串的起始位置
                        list.add(i);
                    }
                }
            }
            Integer[] result = new Integer[list.size()];
            return list.toArray(result);
        }
    }
}

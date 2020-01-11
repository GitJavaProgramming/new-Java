package org.pp.java8.algorithm.sort;

import org.junit.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        /* 字符串匹配 */
//        Util.strCmp("abcdefghcde", "cde");

        /* 递归、排列组合问题、二进制反射格雷码 */
        List<String> brgc = Util.BRGC(5);
        System.out.println(brgc.size() == Math.pow(2, 5)); // int -> double 自动转型
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

        /**
         * 生成长度为n的二进制反射格雷码
         * @param n 格雷码位数
         */
        public static List<String> BRGC(int n) {
            if (n == 1) {
                return Stream.of("0", "1").collect(Collectors.toList());
            }
            List<String> L2 = BRGC(n - 1);
            List<String> L1 = new ArrayList(L2); // backup list
            Collections.reverse(L2); // L2 point reversed L1
            L1 = L1.parallelStream().map(x-> "0" + x).collect(Collectors.toList());
            L2 = L2.parallelStream().map(x-> "1" + x).collect(Collectors.toList());
            L1.addAll(L2);
            System.out.println(L1);
            return L1;
        }
    }
}

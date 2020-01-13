package org.pp.java8.algorithm.sort;

import java.util.Arrays;

/**
 * Java实现 直接插入排序---比较和复制
 * 描述：对于已非降序排序数组A[0...n-2]，将A[n-1]插入到有序数组中：从后向前扫描已排序数组，找到第一个小于等于（最后一个大于）A[n-1]
 * 的元素A[j]，则将A[n-1]插入到该元素后，位置j+1
 * 算法：
 *   for i<-1 to n-1 do // 排序开始时首元素A[0]为已排序序列,1 to n-1未排序序列，插入元素后，已排序序列长度递增
 *     v = A[i]
 *     j = i-1
 *     while j>=0 and A[j]>v do
 *       A[j+1] = A[j]
 *       j <- j-1
 *     A[j+1] <- v
 */
public class InsertionSort<T extends Comparable<T>> implements ArraySortInterface<T> {

    /**
     * 插入排序，考虑正好是降序排列最差效率和非降序的最优效率
     * 该算法是稳定算法，出现相同数值时，会插入到相同数值后面，顺序扫描确保相同数值出现的顺序
     * @param arr 待排序数组
     */
    @Override
    public void sort(T... arr) {
        System.out.println("  -- " + Arrays.toString(arr));
        int arrLen = arr.length;
        for (int i = 1; i < arrLen; i++) {
            T v = arr[i]; // 临时变量 要插入的值
            System.out.println("insert v=" + v);
            int j = i - 1;
            while (j >= 0 && compare(v, arr[j])) { // 从后往前扫描 v与已排序数组比较  非降序排序 如 1 2 3 3 4
                arr[j + 1] = arr[j]; // 找到一个值A[j]>v时,A[j]往后复制(移动)一位，继续下一次比较
                System.out.println("found index[" + j + "]"+ " after copy, arr=" + Arrays.toString(arr));
                j--;
            }
            arr[j + 1] = v; // 全部比较，复制移位完成之后，在j+1位置插入元素
            System.out.println(i + " -- " + Arrays.toString(arr));
        }
    }
}

package org.pp.java8.algorithm.sort;

import java.util.Arrays;

/**
 * Java实现 冒泡排序---比较和交换
 * 比较表中相邻元素，如果他们是逆序就交换，重复多次以后，最大的元素就沉到列表的最后一个位置。
 *
 * BubbleSort(A[0...n-1])
 * // 输入：一个可排序数组A[0...n-1]
 * // 输出：非降序排列的数组A[0...n-1]
 * for i<-0 to n-2 do
 *   for j <- 0 to n-2-i do
 *     if A[j+1] < A[j] swap A[j] and A[j+1]
 *
 * // 级数求和或者等差数列求和、数学归纳法归纳一般项和求和公式
 * // 最后得出时间代价 O(n^2)
 *
 */
public class BubbleArraySort<T> implements ArraySortInterface<T> {

    public void sort(T[] arr) {
        System.out.println("  -- " + Arrays.toString(arr));
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            boolean swapFlag = true; // 交换标识位，如果一次循环内没有发生元素交换则说明排序完成
            for (int j = 0; j < len - 1 - i; j++) {
                if (!compare(arr[j], arr[j + 1])) {
                    swap(arr, j, j + 1);
                    swapFlag = false;
                }
            }
            if(swapFlag) {
                break;
            }
            System.out.println(i + " -- " + Arrays.toString(arr));
        }
    }
}

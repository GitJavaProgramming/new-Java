package org.pp.java8.algorithm.sort;

import java.util.Arrays;

/**
 * Java实现  快速排序--基于递归的、选择点的划分（小于和大于等于选择点值的两部分）排序
 * 描述：参见《算法导论》second edition -- ch07 快速排序
 * 划分：数组A[p...r]被划分成两个（可能空）子数组A[p...q-1]和A[q+1...r]，使得any A[p...q-1]<=A[q] and A[p...q-1]<=A[q+1...r]
 * 下标q在划分过程中进行计算
 * 解决：递归调用快排
 * 合并：由于选择基准点时左右两边已经排序，所以不需要合并操作，整个数组A[p...r]已经有序
 * 算法伪代码：
 * QUICKSORT(A, p, r)
 *   if p < r
 *     then q<-PARTITION(A, p, r)
 *          QUICKSORT(A, p, q-1)
 *          QUICKSORT(A, q+1, r)
 *
 * 在一次划分之后找到一个主元，数组A[p...r]近似有序，所以数组元素的随机性以及划分过程决定了算法效率
 * 自快排196x年提出以来，产生了很多改良的划分算法
 *
 * PARTITION(A,p,r)
 *   x <- A[r]
 *   i <- p-1
 *   for j <- p to r-1
 *     do if A[j] <= x
 *       then i <- i+1
 *            exchange(A[i], A[j])
 *   exchange(A[i+1], A[r])
 *   return i+1
 *
 * 下例是一个Java实现的快排
 */
public class QuickSort implements ArraySortInterface<Integer> {

    @Override
    public void sort(Integer... arr) {
        System.out.println("输入数组：" + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

    private void quickSort(Integer[] arr, int p, int r) {
        if (p < r) {
            int q = partition(arr, p, r);
            System.out.println("基准点位置：" + q + " 划分后：" + Arrays.toString(arr));
            quickSort(arr, p, q - 1);
            quickSort(arr, q + 1, r);
        }
    }

    /**
     * 一种划分算法
     */
    private int partition(Integer[] arr, int p, int r) {
        int x = arr[r]; // 选择数组最后一个元素进行比较来确定主元
        int i = p - 1;
        for (int j = p; j < r; j++) { // 一次遍历会近似排序，分成较小无序和较大的无序两个数组
            if (arr[j] <= x) {
                i++; // 较小元素数组的尾部指针，使用指针不需要额外栈空间存放较小元素数组，只需要交换
                swap(arr, i, j); // 元素交换，较小元素
                // 没打印一次就意味着发生一次元素交换
                System.out.println(Arrays.toString(arr));
            }
        }
        swap(arr, i + 1, r); // 找到主元，一次划分完成
        return i + 1;
    }
}

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
 * 快速排序的性能  《算法导论》 P88
 * QUICKSORT的运行时间由花在过程PARTITION上的时间所决定，每当PARTITION过程被调用时，就要选出一个主元元素。后续对QUICKSORT和
 * PARTITION的各次递归调用中，都不会包含该元素。在整个快排算法中，至多只可能调用PARTITION过程n次。
 * PARTITION中和主元比较的总次数决定了算法的运行时间。对于数组A[0...n]，T(n)表示n次PARTITION过程的最坏时间
 * 如果每次划分都产生A[1...n-1]和A[0]两个子问题，那么具有最差划分效率，此时快排时间代价Θ(n^2) 递推式如下：
 *   T[n] = T[n-1] + T[0] + Θ(n) = T[n-1] + Θ(n) =>（推导出）T(n) = Θ(n^2)
 * 如果每次划分得到的两个子问题大小为floor(n/2)、floor(n/2)-1，这时具有最佳划分效率，此时时间代价为O(nlgn)
 *   T[n] <= 2T(n/2) + Θ(n) => T(n) = O(nlgn)
 * 如果每次划分都出现相同比例的子问题，那么称为平衡的划分，此时总的运行时间为O(nlgn)
 * 运行时间的求解  参考  《算法导论》 P93
 * 级数求和 -> 总的比较次数 -> 求期望值（第一次选取A[0]或A[n-1]时，它们将会进行比较，往后就不会再比较）
 * E[X] => O(nlgn)
 *
 * 关于期望---离散型随机变量的均值
 * 设随机变量X的可能取值为[a1,a2,...,ar],取ai的概率为pi (i=1,2...r),即X的分布列为
 *     P(X=ai) = pi (i=1,2...r)
 * 定义X的均值为
 *     a1P(x=a1) + a2P(X=a2) + ... + arP(X=ar) = a1p1+a2p2+...+arpr
 * 即随机变量X的取值ai乘以取值为ai的概率P(X=ai)再求和，X的均值也称作X的【数学期望】（简称期望），它是一个数，记为EX
 *    EX = a1p1+a2p2+...+arpr
 * EX描述X取值的【中心位置】，这是随机变量X的一个重要特征，均值能反映随机变量取值的【平均水平】，对于两个随机变量期望相同时，
 * 它们的取值情况也可能由很大差异，此时使用【方差】来反映随机变量取值的【集中程度】，方差指示随机变量取值情况与期望值的集中程度
 * 设X是一个离散型随机变量，用E(X-EX)^2来衡量X与EX的平均偏离程度。
 * E(X-EX)^2是(X-EX)^2的期望，称为随机变量X的方差，记为DX，方差越小说明X的取值就越集中再均值周围。方差越大说明X取值就越分散
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
     * 一种划分算法：对于数组A[p...r]总是取A[r]作为主元进行划分
     * 其他划分：Hoare划分、三数取中（随机主元的变式）
     */
    private int partition(Integer[] arr, int p, int r) {
        int x = arr[r]; // 选择数组最后一个元素作为主元进行比较，
        int i = p - 1;
        for (int j = p; j < r; j++) { // 一次遍历会近似排序，分成较小无序和较大的无序两个数组
            if (arr[j] <= x) { // 和主元比较，相等也交换-->减小问题规模(交换之后就不会再比较)
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

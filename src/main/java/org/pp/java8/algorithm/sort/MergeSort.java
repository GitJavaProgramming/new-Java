package org.pp.java8.algorithm.sort;

import java.util.Arrays;

/**
 * Java实现 合并排序--分治、递归
 * 描述：对于需要排序的数组A[0...n-1],合并排序把他一分为二：A[0...floor(n/2)-1]和A[floor(n/2)...n-1]
 * 对每个子数组递归排序，然后把排序好的子数组合并为一个有序数组
 * 递归排序算法：MergeSort(A[0...n-1])
 * if n > 1
 *   copy A[0...floor(n/2)-1] to B[0...floor(n/2)-1]
 *   copy A[floor(n/2)...n-1] to C[floor(n/2)...n-1]
 *   MergeSort(B)
 *   MergeSort(C)
 *   merge(A,B,C)
 * 有序数组合并算法：merge(A,B,C)
 * i<-0;j<-0;k<-0
 * while i<p and j<q
 *   if B[i] <= C[j] // 升序排序
 *     A[k++] = B[i++]
 *   else A[k++] = C[j++]
 * if i == p // B数组先插入完 把C中剩余元素拷贝到A中
 *   copy C[j...q-1] to A[k...p+q-1]
 * else copy B[i...p-1] to A[k...p+q-1]
 */
public class MergeSort implements ArraySortInterface<Integer> {

    @Override
    public void sort(Integer... arr) {
        int len = arr.length;
        if (len > 1) {
            int middle = len >> 1; // 等价于Math.floor(len/2)
            int secondHalf = len - middle;
            Integer[] arrB = new Integer[middle];
            Integer[] arrC = new Integer[secondHalf];
            System.arraycopy(arr, 0, arrB, 0, middle);
            System.arraycopy(arr, middle, arrC, 0, secondHalf);
            sort(arrB); // 递归调用，退出条件为数组长度为1
            sort(arrC);
            merge(arr, arrB, arrC); // 被拆分的两个数组进行合并，注意合并、排序时间效率会影响整个算法事件效率
        }
        System.out.println(Arrays.toString(arr)); // 分到不可再分时len=1开始打印数组，合并之后执行到这里也会打印
    }

    /**
     * 合并有序数组B、C到A
     * @param arr 合并后的数组A
     * @param arrB 有序数组B
     * @param arrC 有序数组C
     * @param <T> 数组元素类型，可比较
     */
    private <T extends Comparable<T>> void merge(T[] arr, T[] arrB, T[] arrC) {
        int i = 0, j = 0, k = 0;
        int p = arrB.length;
        int q = arrC.length;
        while (i < p && j < q) { // i、j分治，取决于B、C数组元素的大小
            if (arrB[i].compareTo(arrC[j]) < 1) {
                arr[k++] = arrB[i++];
            } else {
                arr[k++] = arrC[j++];
            }
        }
        if (i == p) {
            System.arraycopy(arrC, j, arr, k, q - j);
        } else {
            System.arraycopy(arrB, i, arr, k, p - i);
        }
    }

    /* 下面注释代码是挖的一个大坑！！！ */
//    private Class<T> tClass;
//
//    public MergeArraySort() {
//        tClass = getSuperInterfaceGenericType(getClass());
//    }
//
//    @Override
//    public void sort(T... arr) {
//        int len = arr.length;
//        if (len > 1) {
//            int middle = (len >> 1) - 1;
//            int secondHalf = len - middle;
//            T[] arrB = newArray(tClass, middle);
//            T[] arrC = newArray(tClass, secondHalf);
//            System.arraycopy(arr, 0, arrB, 0, middle);
//            System.arraycopy(arr, middle, arrC, 0, secondHalf);
//            sort(arrB);
//            sort(arrC);
//            merge(arr, arrB, arrC);
//        }
//        System.out.println(Arrays.toString(arr));
//    }
//
//    /**
//     * 生成指定类型的数组 转换成Object[]
//     * @param tClass Class对象
//     * @param capacity 数组长度
//     */
//    public T[] newArray(Class<T> tClass, int capacity) {
//        Comparable<T>[] objects = (Comparable<T>[]) Array.newInstance(tClass, capacity);
//        List<T> list =  Arrays.stream(objects).map((o)-> (T)o).collect(Collectors.toList());
//        T[] ts = null;
//        return list.toArray(ts);
//    }
//
//    private void merge(T[] arr, T[] arrB, T[] arrC) {
//        int i = 0, j = 0, k = 0;
//        int p = arrB.length;
//        int q = arrC.length;
//        while (i < p && j < q) {
//            if (compare(arrB[i], arrC[j])) {
//                arr[k++] = arrB[i++];
//            } else {
//                arr[k++] = arrC[j++];
//            }
//        }
//        if (i == p) {
//            System.arraycopy(arrC, j, arr, k, q - j);
//        } else {
//            System.arraycopy(arrB, i, arr, k, p - i);
//        }
//    }
}

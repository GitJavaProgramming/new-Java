package org.pp.java8.algorithm.sort;

import java.util.Comparator;

/**
 * 线性表排序接口，函数式接口，提供lambda表达式支持
 * 参考源码
 * List  default void sort(Comparator<? super E> c)
 * Arrays  public static <T> void sort(T[] a, Comparator<? super T> c)
 */
@FunctionalInterface
public interface ArraySortInterface<T> {

    /**
     * 元素比较器
     */
    Comparator comparator = Comparator.comparing((n) -> {
        if (n instanceof Number) {
            return ((Number) n).longValue();
        } /*else if (n instanceof String) {
            return ((String) n).toString();  为啥不能指定，函数声明在接口中???
        } */ else {
            throw new RuntimeException("必须为数值，此错误被隐藏，小心。");
        }
    });

    /**
     * 比较表中的两个元素大小，元素必须位数值型
     */
    @SuppressWarnings({"必须为数值类型，否则会出现意外情况。", "小心使用，这个错误会被隐藏。"})
    default boolean compare(T t1, T t2) {
        return comparator.compare(t1, t2) < 1/*t1 < t2返回true*/;
    }

    /**
     * 交换数组中两个元素
     */
    default void swap(T[] arr, int t1, int t2) {
        T t = arr[t1];
        arr[t1] = arr[t2];
        arr[t2] = t;
    }

    /**
     * 线性结构排序 -- 数组、队列等
     */
    void sort(T... arr/*T[] arr*/);

//    /**
//     * 指定排序的比较器排序
//     *
//     * @param arr
//     * @param comparator
//     * @return
//     */
//    default T[] sorted(T[] arr, Comparator<T> comparator) {
////        Arrays.sort(arr);
//        List<T> list = Arrays.stream(arr).parallel().sorted(comparator).collect(Collectors.toList());
//        return (T[]) list.toArray(); // 泛型安全由数组保证
//    }
//
//    /**
//     * 默认比较器 排序
//     *
//     * @param arr
//     * @return
//     */
//    default T[] sorted(T[] arr) {
//        List<T> list = Arrays.stream(arr).parallel().sorted().collect(Collectors.toList());
//        return (T[]) list.toArray();
//    }
}

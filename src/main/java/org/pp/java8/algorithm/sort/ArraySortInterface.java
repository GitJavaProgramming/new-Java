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
     * 元素比较器 表达式接收一元操作符 转换函数 比较转换后的值U----U extends Comparable<? super U>
     * <T, U extends Comparable<? super U>>
     * 接口中只能有常量 static final--这里不能包含泛型，最终类型推断只能确定一种类型 编译期间泛型擦除至上限
     * 但是java中的行为是动态分派的--继承与多态特性，在运行期确定具体类型
     *
     * 这样看来下面的写法是不是存在很大问题？？？ 接口常量 泛型擦除 表达式类型推断？？？
     *
     * Comparator函数式接口提供各种类型比较函数
     */
    Comparator comparator = Comparator.comparing((/*Comparable*/ n) -> {
        if (n instanceof Number) {
            return ((Number) n).longValue();
        } /*else if (n instanceof CharSequence) { // 编译错误 no instances of type valiables exist so that Integer conforms to long
            return ((CharSequence)n).length();  // 为啥不能指定，函数声明在接口中???
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

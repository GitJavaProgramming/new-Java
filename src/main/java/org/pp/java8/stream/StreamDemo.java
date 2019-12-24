package org.pp.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> myList = new ArrayList();
        myList.add(7);
        myList.add(8);
        myList.add(6);
        myList.add(1);
        myList.add(3);
        myList.add(5);
        myList.add(4);
        System.out.println("Original List:" + myList);

        // Interface Stream<T>; T is the type of the stream elements
        Stream<Integer> stream = myList.stream();
        Optional<Integer> minVal = stream.min(Integer::compareTo);
        if (minVal.isPresent()) {
            System.out.println("Minimum is : " + minVal.get());
        }
        stream = myList.stream();
        // lambda方法引用 ClassName:instanceMethodName
        Optional<Integer> maxVal = stream.max(Integer::compareTo);
        maxVal.ifPresent(integer -> System.out.println("Maximum is : " + integer));

        // list 排序
        Stream<Integer> sortedStream = myList.stream().sorted();
        sortedStream.forEach((n) -> {
            n += 100;
            System.out.print(n + " ");
        });
        System.out.println();
        // 经过排序流之后 流不会改变myList--不变
        System.out.println("Original List:" + myList);

        // 过滤器
        Stream<Integer> oddVal = myList.stream().sorted().filter((n) -> (n & 1) == 1);
        System.out.println("odd values : ");
        oddVal.forEach((n) -> System.out.print(n + " "));
        System.out.println();

        oddVal = myList.stream().sorted().filter((n) -> (n % 2) != 0).filter((n) -> n >= 5);
        System.out.println("odd values greater than 5: ");
        oddVal.forEach((n) -> System.out.print(n + " "));
        System.out.println();

        System.out.println("Optional<T> reduce(BinaryOperator<T> accumulator);");

        // 演示Stream.reduce  list中所有数的乘积
        // reduce累加器操作需要满足三个约束条件：
        // 累加器无状态所以时线程安全的  累加器不干预（不会改变数据源）  累加时满足结合性（结合律 (a+b)+c = a+(b+c) ）
        // Optional<T> reduce(BinaryOperator<T> accumulator);
        Optional<Integer> optionalInteger = myList.stream().reduce((a, b) -> a * b);
        if (optionalInteger.isPresent()) {
            System.out.println("乘法运算： " + optionalInteger.get());
        }
        optionalInteger = myList.stream().reduce((a, b) -> {
            System.out.println("当前值a = " + a);
            System.out.println("下一个元素值b = " + b);
            return (a + b);
        });
        optionalInteger.ifPresent(integer -> System.out.println("加法运算： " + integer));

        System.out.println("T reduce(T identity, BinaryOperator<T> accumulator);");
        // T reduce(T identity, BinaryOperator<T> accumulator);
        Integer sum = myList.stream().reduce(1, (a, b) -> {
            System.out.println("当前a1 = " + a);
            System.out.println("下一个元素b1 = " + b);
            return (a + b);
        });
        System.out.println("sum=" + sum);
    }
}

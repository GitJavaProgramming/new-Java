package org.pp.java8.functional.stream.demo;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapStreamDemo {
    public static void main(String[] args) {
        List<Double> myList = ParallelStreamDemo.myList; // 类初始化 -> static静态块执行 -> myList数据初始化
        Stream<Double> mapStream = myList.stream().map((a) -> {
            double result = Math.sqrt(a);
            System.out.println("Math.sqrt(" + a + ")=" + result);
            return result;
        });
        // Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
        // forEach终结流操作，不可再用
//        mapStream.forEach((a) -> System.out.println(a)); // 遍历打印
        Double sum = mapStream.reduce(0.0, (a, b) -> a + b); // 加法操作规约，返回总和值
        System.out.println("reduce sum = " + sum);


        // public interface IntStream extends BaseStream<Integer, IntStream> 内置具体实现
        IntStream intStream = myList.stream().mapToInt((a) -> (int) Math.round(a));  // long to int 可能丢失精度
        intStream.forEach((n) -> System.out.print(n + " "));
    }
}

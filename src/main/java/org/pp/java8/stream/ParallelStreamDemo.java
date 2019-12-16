package org.pp.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * 泛化的reduce，通过reduce可以基于任意条件，从流中返回一个值
 * public interface Stream<T> extends BaseStream<T, Stream<T>>
 *
 * T reduce(T identity, BinaryOperator<T> accumulator);
 * Optional<T> reduce(BinaryOperator<T> accumulator);
 *
 *
 * <U> U reduce(U identity,
 *                  BiFunction<U, ? super T, U> accumulator, // 累加器
 *                  BinaryOperator<U> combiner);  // 合并器
 * 这个规约结果和下面的相同 但是不强制按顺序执行
 * U result = identity;
 *      *     for (T element : this stream)
 *      *         result = accumulator.apply(result, element)
 *      *     return result;
 *
 * BiFunction<T, U, R>
 *      R apply(T t, U u);
 * BinaryOperator<T> extends BiFunction<T,T,T>
 *
 */
public class ParallelStreamDemo {
    public static void main(String[] args) {
        List<Double> myList = new ArrayList();
        myList.add(7.0);
        myList.add(8.0);
        myList.add(6.0);
        myList.add(1.0);
        myList.add(3.0);
        myList.add(5.0);
        myList.add(4.0);
        System.out.println("Original List:" + myList);

        double u = 1.0;
        double doubleOptional = myList.parallelStream().reduce(
                u,
                (a, b) -> {
                    System.out.println("a1=" + a);
                    System.out.println("b1=" + b);
                    return a * Math.sqrt(b);
                },
                /*(a, b) -> a * b,*/
                (a, b) -> {
                    System.out.println("a2=" + a);
                    System.out.println("b2=" + b);
                    return a + b; // 返回列表中所有元素平方根的和
                }
        );
//        double result = 1.0;
//        for(Double d : myList) {
//            result *= d;
//        }
//        System.out.println("result=" + result);

        System.out.println("doubleOptional : " + doubleOptional);

        System.out.println("Original List:" + myList);
    }
}

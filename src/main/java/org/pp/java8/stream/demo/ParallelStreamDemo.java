package org.pp.java8.stream.demo;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 泛化的reduce，通过reduce可以基于任意条件，从流中返回一个值
 * public interface Stream<T> extends BaseStream<T, Stream<T>>
 * <p>
 * 第1个 T reduce(T identity, BinaryOperator<T> accumulator);
 * 第2个 Optional<T> reduce(BinaryOperator<T> accumulator);
 * 第3个 <U> U reduce(U identity,
 * BiFunction<U, ? super T, U> accumulator, // 累加器
 * BinaryOperator<U> combiner);  // 合并器
 * <p>
 * 这个规约结果和下面的相同 但是不强制按顺序执行
 * U result = identity;
 * *     for (T element : this stream)
 * *         result = accumulator.apply(result, element)
 * *     return result;
 * <p>
 * BiFunction<T, U, R>
 * R apply(T t, U u);
 * BinaryOperator<T> extends BiFunction<T,T,T>
 */
public class ParallelStreamDemo {
    public static /*final*/ List<Double> myList = new CopyOnWriteArrayList<>() /*ArrayList()*/;

    static {
        myList.add(7.4);
        myList.add(8.5);
        myList.add(6.3);
        myList.add(1.0);
        myList.add(3.6);
        myList.add(5.8);
        myList.add(4.7);
        System.out.println("Original List:" + myList);
    }

    public static void main(String[] args) {

        double u = 1.0;
        double doubleOptional = myList.parallelStream().reduce(
                u,
                (a, b) -> {  // 规约操作
                    List<Double> tmpList = myList /*Collections.unmodifiableList(myList)*/;
                    tmpList.add(111.0);  //干预myList 失效
                    System.out.println("tmpList=" + tmpList);
//                    myList.add(111.0);  //干预myList 失效
                    System.out.println("a1=" + a);
                    System.out.println("b1=" + b);
                    return a * Math.sqrt(b)/* + a*/;
                },
                /*(a, b) -> a * b,*/
                (a, b) -> {  // 指定规约每次中间结果的关联性，返回最终结果
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

//        Optional<Double> sum = myList.parallelStream().reduce((a, b) -> a + b);  // 求和
        Optional<Double> sum = myList.parallelStream().max(Double::compareTo); // 最大数
        if (sum.isPresent()) {
            System.out.println("sum=" + sum);
        }

        System.out.println("Original List:" + myList);
    }
}

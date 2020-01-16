package org.pp.java8.stream.exercise;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
//        System.out.println(addUp(Stream.of(1, 2, 3, 4)));
//        Stream.of(addUp(Stream.of(1, 2, 3, 4))).forEach(System.out::println);


        int sum = Stream.of(1, 2, 3, 4)/*.parallel()*/.reduce(1, (t, u) -> { // 规约操作
            System.out.println("t = " + t);
            System.out.println("u = " + u);
            System.out.println("t + u = " + (t + u));
            return t + u;  // t保存中间结果，u为（元素-流）元素
        }, (t, u) -> {
            System.out.println("a=" + t);
            System.out.println("u=" + u);
            System.out.println("a * u = " + t * u);
            return t * u;
        });
        System.out.println("sum = " + sum);


    }

    /**
     * 求和
     *
     * @param stream 流
     * @return 和值
     */
    static int addUp(Stream<Integer> stream) {
        return stream.reduce(0, (x, y) -> x + y);
    }
}

class MyStream<T> {

    // 只用reduce和lambda表达式实现Stream filter操作
    public Stream<T> filter(Predicate<? super T> predicate) {
        return null;
    }

    // 只用reduce和lambda表达式实现Stream filter操作
    private <T> Stream<T> filter(Stream<T> stream, Predicate<T> pre) {
        return stream.reduce(new ArrayList<T>().stream(),   // Stream<T>是reduce的U
                (u, t) -> {
                    if (pre.test(t))
                        return Stream.concat(u, Stream.of(t));
                    return u;
                },
                (a, b) -> Stream.concat(a, b)
        );
    }

    // 只用reduce和lambda表达式实现Stream map操作
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
//        Optional<R> optionalR = reduce((a, b) -> {
//            R r = mapper.apply(a);
//            return r;
//        });
//        return Stream.of(optionalR.get());
        return null;
    }

    // 只用reduce和lambda表达式实现Stream map操作
    private <T, R> Stream<R> map(Stream<T> stream, Function<T, R> fun) {
        return stream.reduce(new ArrayList<R>().stream(),  // Stream<R>是reduce参数的的U
                (u, t) -> Stream.concat(u, Stream.of(fun.apply(t))),
                (a, b) -> Stream.concat(a, b));
    }
}

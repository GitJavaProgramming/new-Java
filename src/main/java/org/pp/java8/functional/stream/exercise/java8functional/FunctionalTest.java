package org.pp.java8.functional.stream.exercise.java8functional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.pp.java8.functional.stream.exercise.java8functional.model.Artist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class FunctionalTest {
    private static List<Artist> artists;

    @BeforeClass
    public static void begin() {
        artists = new ArrayList<>();
        artists.add(new Artist("London1", "London", "London"));
        artists.add(new Artist("London2", "London", "London"));
        artists.add(new Artist("London3", "London", "London"));
        artists.add(new Artist("London4", "London", "London4"));
        artists.add(new Artist("London5", "London", "London4"));
        artists.add(new Artist("London6", "London", "London4"));
    }

    /**
     * 函数式接口测试
     * 一元操作符Function 二元操作符BiFunction 谓词Predicate 生产者Supplier 消费者Consumer
     */
    @Test
    public void functionalInterfaceTest() {

        /**
         * 特殊的二元运算
         *         表示对两个相同类型的操作数进行的运算，产生与该操作数相同类型的结果。
         */
        /* BinaryOperator<T> extends BiFunction<T,T,T> */
        BinaryOperator<Long> add = (x, y) -> x + y;
        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y; // 显示指定类型
        /**
         * 二元运算符
         * BiFunction<T, U, R>
         *     R apply(T t, U u);
         *     Applies this function to the given arguments.
         */
        BiFunction<Long, Long, String> biFunction = (Long x, Long y) -> x + y + ""; // 返回String

        /**
         * 结果供应（生产类）
         * Supplier<T>
         *     T get();
         *     Gets a result.
         */
        Supplier<Long> supplier = () -> {
            System.out.println();
            return 100L; // 自动封箱
        };
        Long long1 = supplier.get();

        /**
         * 消费者
         * Consumer<T>
         *     void accept(T t);
         *     Performs this operation on the given argument.
         */
        Consumer</*? extends */Serializable/*Cloneable*/> consumer = (/*Object*/ obj) -> {
        }; // ? 到 Object  泛型擦除，比如定义标识（空）接口 然后做事务aop
        consumer.accept("");

        /**
         * 谓词，用来描述或判定客体性质、特征或者客体之间关系的词项。
         * Predicate<T>
         *     boolean test(T t);
         *     Evaluates this predicate on the given argument.
         */
        Predicate<Serializable> predicate = (a) -> false; // 谓词测试
        predicate.test(1);

        /**
         * 一元运算符
         * Function<T, R>
         *     R apply(T t);
         */
        Function<Long, String> function = (a) -> {
            return a.toString();
        };

        /**
         * 一元操作符
         * @FunctionalInterface
         * public interface UnaryOperator<T> extends Function<T, T> {
         *     static <T> UnaryOperator<T> identity() {
         *         return t -> t; // 这是什么操作！！！这个lambda有点厉害！
         *     }
         * }
         * 返回一元运算符，该运算符始终返回其输入参数。
         */
        UnaryOperator<Long> unaryOperator = (a) -> a + 100;
        unaryOperator.apply(200L);
        UnaryOperator.identity();
    }

    /**
     * 流API操作
     * Stream<T> filter(Predicate<? super T> predicate);
     * <R, A> R collect(Collector<? super T, A, R> collector); // 查阅Collector实现
     * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
     * <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper); // T转换成R的流
     * Comparator.comparing // 查阅源码 用到一元运算符
     * reduce的三种形式---查阅源码，画出具体算法流程
     * Optional<T> reduce(BinaryOperator<T> accumulator); // 二元操作符规约
     * T reduce(T identity, BinaryOperator<T> accumulator); // 指定初始值的规约
     * // 第三种形式
     * <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
     * *     U result = identity;
     * *     for (T element : this stream)
     * *         result = accumulator.apply(result, element)
     * *     return result;
     * *
     * * combiner.apply(u, accumulator.apply(identity, t)) == accumulator.apply(u, t)
     */
    @Test
    public void streamOpsTest() {
        /**
         * filter
         * */
        /* 内部迭代 计算来自伦敦的艺术家人数 */
        long count = 0;

        count = artists.stream().filter((artist) -> artist.isFrom("London")).count();
        System.out.println(count);

        /**
         * 操作分解
         */
        Stream<Artist> artistStream = artists.stream();
        Stream<Artist> newArtistStream1 = artistStream.filter((artist) -> {
            System.out.println(artist.getName());
            return artist.isFrom("London");
        });
        // 注释下面两行 将不会输出，流没有完成终端操作
        count = newArtistStream1.count();
        System.out.println(count);

        /**
         * collect map
         */
        List<String> collectedList = Stream.of("a", "b", "c").collect(Collectors.toList());
        collectedList = Stream.of("a", "b", "c").map((str) -> str.toUpperCase()).collect(Collectors.toList());

        /**
         * flatMap  <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
         */
        List<Integer> flatMapList = Stream.of(asList(1, 2, 3), asList(5, 6, 7), asList(4, 8, 9))
                .flatMap(numbers -> numbers.stream())
                .collect(Collectors.toList());
        System.out.println(flatMapList);

        /**
         * Optional
         */
        Optional<Integer> optionalInteger = flatMapList.stream().min(Comparator.comparing(integer -> 10 - integer.intValue()));
        if (optionalInteger.isPresent()) {
            Integer integer = optionalInteger.get();
            System.out.println("min(10 - integer.intValue()) = " + integer);
        }

        /**
         * reduce
         * T reduce(T identity, BinaryOperator<T> accumulator);
         *      *     T result = identity;
         *      *     for (T element : this stream)
         *      *         result = accumulator.apply(result, element)
         *      *     return result;
         */
        int sum = Stream.of(1, 2, 3, 4, 5).reduce((a, b) -> a + b)/*二元操作符，返回Optional*/.get();
        sum = Stream.of(5, 6, 7, 8, 9).reduce(Integer::sum).get();
        sum = Stream.of(1, 2, 3, 4, 5).reduce(1, (a, b) -> a * b)/*返回结果 identity指定初始值*/;
        /**
         * <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
         *      * *     U result = identity;
         *      * *     for (T element : this stream)
         *      * *         result = accumulator.apply(result, element)
         *      * *     return result;
         *      * *
         *      * * combiner.apply(u, accumulator.apply(identity, t)) == accumulator.apply(u, t)
         *
         *      参看    org.pp.java8.functional.stream.exercise.StreamTest
         *              org.pp.java8.functional.stream.demo.ParallelStreamDemo
         */
        sum = Stream.of(1, 2, 3, 4).reduce(0, (t, u) -> t + u, (a, b) -> {
            System.out.println("a=" + a);
            System.out.println("b=" + b);
            System.out.println("a * b = " + a * b);
            return a * b;
        });
        System.out.println("sum = " + sum);
    }
}

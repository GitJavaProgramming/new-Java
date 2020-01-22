/**
 * 参考资料
 * http://zh.lucida.me/blog/java-8-lambdas-insideout-language-features/
 * http://zh.lucida.me/blog/java-8-lambdas-inside-out-library-features/
 * https://cloud.tencent.com/developer/article/1497544
 *
 * 流的创建 这里说的创建为顶层操作 给出的方法请查阅源码理解具体实现
 * Collection
 * -- default Stream<E> stream()
 * -- default Stream<E> parallelStream()
 * Arrays
 * -- public static <T> Stream<T> stream(T[] array)
 * Stream API
 * -- public static<T> Stream<T> of(T... values)
 * -- public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
 * -- public static<T> Stream<T> generate(Supplier<T> s)
 * IntStream
 * LongStream
 * DoubleStream
 *
 * 流的使用
 * 中间操作
 * -- 筛选与切片系列
 * -- 映射系列
 * -- 排序
 * 终端操作
 * -- 查找与匹配
 * -- 归约
 * -- 收集
 *
 * 流的运行原理与特性
 * -- 流的内部迭代（满足可迭代性、排序查找等满足可比性）
 * -- 惰性求值（流式API）
 * -- 无干扰性（数据源的不可变性）
 */
package org.pp.java8.functional.stream;
package org.pp.java8.functional.stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        // 去除整型数组1 2 3中的偶数并返回集合
        Stream.of(1, 2, 3, 4, 5).filter(x -> (x & 1) == 1).collect(Collectors.toList());
    }
}

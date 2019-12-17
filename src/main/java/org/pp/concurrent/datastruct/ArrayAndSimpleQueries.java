package org.pp.concurrent.datastruct;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java8实现  参考c++实现 传说中的平衡树？？？ Treap.cpp
 */
public class ArrayAndSimpleQueries {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] nm = scanner.nextLine().split(" ");
        int N = Integer.parseInt(nm[0]);
        int M = Integer.parseInt(nm[1]);
        String[] A = scanner.nextLine().split(" ");
        Stream<Integer> stream = Arrays.stream(A).map((a) -> Integer.parseInt(a));
        List<Integer> list = stream.collect(Collectors.toList());
        int a,b,k;
        for (int i = 0; i < M; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            a = Integer.parseInt(queriesRowItems[0]);
            b = Integer.parseInt(queriesRowItems[1]);
            k = Integer.parseInt(queriesRowItems[2]);
            // array copy???  数据量太大,有毒...
        }
    }
}

//interface TypeMap<T, I, J> {
//    <I, J> T getIJ();
//}

package org.pp.java8.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static void main(String[] args) {

        /* 递归、排列组合问题、二进制反射格雷码 */
        int BRGC_N = 5;
        List<String> brgc = BRGC(BRGC_N);
        System.out.println(brgc.size() == Math.pow(2, BRGC_N)); // int -> double 自动转型
    }

    /**
     * 生成长度为n的二进制反射格雷码
     * @param n 格雷码位数
     */
    public static List<String> BRGC(int n) {
        if (n == 1) {
            return Stream.of("0", "1").collect(Collectors.toList());
        }
        List<String> L2 = BRGC(n - 1);
        List<String> L1 = new ArrayList(L2); // backup list
        Collections.reverse(L2); // L2 point reversed L1
        L1 = L1.parallelStream().map(x-> "0" + x).collect(Collectors.toList());
        L2 = L2.parallelStream().map(x-> "1" + x).collect(Collectors.toList());
        L1.addAll(L2);
        System.out.println(L1);
        return L1;
    }
}

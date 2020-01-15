package org.pp.java8.algorithm.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MathRandom {

    /**
     * 生成整数数值范围在[s, m)区间的m-s个不同的随机数，从中取出n个数
     *
     * @param s 数值开始值 包含这个值s
     * @param m 数值结束值 不包含m
     * @param n 取n个数
     * @return 取到的集合
     */
    public static /*<T extends Number>>*/List<Integer> randomIntegerListDistinct(int s, int m, int n) {
        if ((s > m) || (m - s < n)) {
            throw new IllegalArgumentException("参数s必须不大于参数m的值,并且区间[s,m)值不小于n");
        }
        List<Integer> list = new ArrayList<>(m - s);
        int index = s;
        while (index < m) {
            list.add(index++);
        }
        Collections.shuffle(list); // 乱序
        if (list.size() == n) {
            return list;
        }
        return list.subList(0, n); // 取N个乱序数 (rand(s), rand(s) + n)--rand(s)<s and s+n<=m
    }
}

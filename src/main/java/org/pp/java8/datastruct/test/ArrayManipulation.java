package org.pp.java8.datastruct.test;

import java.io.IOException;
import java.util.Scanner;

/**
 * 问题描述： https://www.hackerrank.com/challenges/crush/problem
 * <p>
 * 最高60分 本例24分 你能得多少分 嘿嘿~~~ 听说平衡树O(NlogN)时间内可以？
 */
public class ArrayManipulation {
    // Complete the arrayManipulation function below.

    /**
     * Your code did not execute within the time limits 最佳时间复杂度
     */
    static long arrayManipulation(int n, int[][] queries) {
        long[][] repeatArray = new long[n][2];
        long maxNum = 0; // 最大值
        int a, b, k;
        for (int[] qF : queries) { // 分治思想？ how?
            a = qF[0];
            b = qF[1];
            k = qF[2]; // 被加数
            for (int i = a - 1; i < b; i++) {
                repeatArray[i][0] = i; // 第i个数
                repeatArray[i][1] += k; // 第i个数的值
                if (maxNum < repeatArray[i][1]) {
                    maxNum = repeatArray[i][1]; // 每一次相加后的最大值
                }
            }
        }
        return maxNum;
    }

    static long arrayManipulation2(int n, int[][] queries) {
        long[][] repeatArray = new long[n][2];
        return sum(queries, 0, repeatArray, 0);
    }

    static long sum(int[][] arr, int m, long[][] repeatArray, long maxNum) {
        if (m > arr.length - 1) {
            return maxNum;
        } else {
            int a = arr[m][0];
            int b = arr[m][1];
            int k = arr[m][2]; // 被加数
            for (int i = a - 1; i < b; i++) {
                repeatArray[i][0] = i; // 第i个数
                repeatArray[i][1] += k; // 第i个数的值
                if (maxNum < repeatArray[i][1]) {
                    maxNum = repeatArray[i][1]; // 每一次相加后的最大值
                }
            }
            m++;
            maxNum = sum(arr, m, repeatArray, maxNum);
        }
        return maxNum;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        int[][] queries = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int queriesItem = Integer.parseInt(queriesRowItems[j]);
                queries[i][j] = queriesItem;
            }
        }
        scanner.close();

        long result = arrayManipulation2(n, queries);

        System.out.println(result);
    }
}

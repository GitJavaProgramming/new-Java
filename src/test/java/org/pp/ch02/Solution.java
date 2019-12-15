package org.pp.ch02;

import java.io.*;


class Result {

    /**
     * 递归合并两个已按升序排列的数组.
     *
     * <p>
     * 提示：
     * 1. 必须使用递归写法，循环写法无效。
     * 2. 可以定义辅助函数。
     * 3. test case执行时可能出现StackOverFlowError。
     * </p>
     *
     * @param a 升序排列数组，长度可能为0-10000
     * @param b 升序排列数组，长度可能为0-10000
     * @return a和b合并后的升序排列数组
     */
    public static int[] merge(int[] a, int[] b) {
        return new int[0];
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int arrayACount = Integer.parseInt(bufferedReader.readLine().trim());
        int[] arrayA = new int[arrayACount];

        for (int i = 0; i < arrayACount; i++) {
            arrayA[i] = Integer.parseInt(bufferedReader.readLine().trim());
        }

        int arrayBCount = Integer.parseInt(bufferedReader.readLine().trim());

        int[] arrayB = new int[arrayBCount];

        for (int i = 0; i < arrayBCount; i++) {
            arrayB[i] = Integer.parseInt(bufferedReader.readLine().trim());
        }

        int[] result = Result.merge(arrayA, arrayB);

        for (int i : result) {
            bufferedWriter.write(String.valueOf(i));
            bufferedWriter.write('\n');
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}

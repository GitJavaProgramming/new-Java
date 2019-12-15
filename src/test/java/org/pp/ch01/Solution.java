package org.pp.ch01;

import java.io.*;


class Result {

    /**
     * 找出字符串中出现次数最多的字母，次数相同的情况下返回ascii code更小的，没有字母的话返回null(忽略大小写，统一当成小写处理).
     *
     * <p>
     * 提示:
     * 1. 字符串长度在1000以内，且只包含ascii字符
     * </p>
     *
     * @param string 只包含ascii字符的字符串.
     * @return 出现次数最多的字母.
     */
    public static Character mostFrequentLetter(String string) {
        final int letterLength = 128;
        int[] letterArrayCount = new int[letterLength];
        int len = string.length();
        for (int i = 0; i < len; i++) {
            letterArrayCount[string.charAt(i)]++;
        }
        int max = 0; // 字母出现的次数
        int valueIndex = 0; // 字母
        for (int i = 1; i < letterLength; i++) {
            int value = letterArrayCount[i];
            if (value > max) {
                max = value;
                valueIndex = i;
            }
        }
        Character c = Character.valueOf((char) valueIndex);
        System.out.println("出现最多的字母是：" + c + " 出现次数:" + max);
        return c;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        try(
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String string = bufferedReader.readLine();

            Character result = Result.mostFrequentLetter(string);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }
    }
}


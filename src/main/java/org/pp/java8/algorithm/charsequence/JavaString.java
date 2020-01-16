package org.pp.java8.algorithm.charsequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaString {

    public static void main(String[] args) {
//        mostFrequentLetter("abcdefghcde");

        /**
         * 字符串匹配
         * */
//        strCmp("abcdefghcde", "cde");

        int index = horspoolMatching("abaadddcdefghcde", "cde");
        System.out.println("match start index : " + index);
    }



    /**
     * 找出字符串中出现次数最多的字母，次数相同的情况下返回ascii code更小的，没有字母的话返回null(忽略大小写，统一当成小写处理).
     *
     * 提示:
     * 1. 字符串长度在1000以内，且只包含ascii字符
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

    /**
     * 字符串（比较）匹配 文本：T[0...n-1] 模式：P[0...m-1]
     * for i<-0 to n-m do
     *   j<-0
     *   while j<m and P[j]=T[i+j] do
     *     j<-j+1
     *     if j=m return i
     * return -1
     */
    public static Integer[] strCmp(String text, String regex) {
        char[] textChars = text.toCharArray();
        char[] regexChars = regex.toCharArray();
        int n = textChars.length;
        int m = regexChars.length;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && regexChars[j] == textChars[i + j]) { // 子串绝对匹配
                j++;
                if (j == m) {
                    System.out.println("找到子串，起始位置：" + i);
//                        return i; // 找到要查找字串的起始位置
                    list.add(i);
                }
            }
        }
        Integer[] result = new Integer[list.size()];
        return list.toArray(result);
    }

    /**
     * Horspool算法 -- Java实现
     * 该算法最差效率属于O(nm)，对于随机文本来说，它的效率属于⊙(n)
     * @param text 字符串
     * @param regex 模式
     * @return 匹配位置，-1表示没有匹配
     */
    public static int horspoolMatching(String text, String regex) {
        int[] shiftTable = shiftTable(regex); // 移动距离表
        char[] chars = text.toCharArray();
        char[] regexChars = regex.toCharArray();
        int m = regexChars.length; // 模式长度
        int n = chars.length;
        int i = m - 1; // 从模式中最后一个字符比较
        while (i <= n - 1) {
            int k = 0; // 匹配字符计数
            while (k <= m - 1 && regexChars[m - 1 - k] == chars[i - k]) { // 模式字符从后向前与文本字符比较，并计数
                System.out.println("找到匹配字符：" + chars[i - k]);
                k++;
            }
            if (k == m) { // 完全匹配
                return i - m + 1;
            } else { // 不完全匹配则按移动距离规则来移动表
                System.out.println("一次模式(从后向前)未完全匹配，当前文本匹配位置：" + i);
                i = i + shiftTable[chars[i]]; // 移动之后，模式与文本在i的位置上的字符相等
            }
        }
        return -1;
    }

    /**
     * 找出模式字符串在字符表中的移动距离
     * 默认距离为模式长度
     * 模式中出现的字符距离为字符到模式最后字符的距离
     * @param regex 模式
     * @return 移动表
     */
    private static int[] shiftTable(String regex) {
        int m = regex.length(); // 模式长度 m超过字符集支持最大值时 indexOutOfBound chars[m] 不存在
        if(m > Character.MAX_VALUE) {
            throw new IllegalArgumentException("模式太长了");
        }
        int[] chars = new int[Character.MAX_VALUE]; // 构造字符表~ 使用int[]是因为模式长度可能超过字符集最大值
        int size = chars.length;
        char[] regexChars = regex.toCharArray();
        for (int i = 0; i < size; i++) {
            chars[i] = m; // 初始距离都为匹配模式字符串的长度
        }
        for (int j = 0; j < m - 1; j++) {
            char c = regexChars[j];
            chars[c] = m - 1 - j; // 字符串中字符到最后一个字符的距离
            System.out.println("字符" + c + "往后移动：" + (m - 1 - j));
        }
        return chars;
    }

    /**
     * 构建字母表，分析字符串text中包含的字符--去除重复字符
     * @param text 输入字符串
     * @return 字母表数组
     */
    private Character[] buildCharTable(String text) {
        char[] chars = text.toCharArray();
        // 最简单的想法
        Set<Character> byteSet = new TreeSet<>(); // 又是装箱对象！！！
        for(Character c : chars) {
            byteSet.add(c);
        }
        Character[] characters = new Character[byteSet.size()];
        byteSet.toArray(characters);
        return characters;
    }
}

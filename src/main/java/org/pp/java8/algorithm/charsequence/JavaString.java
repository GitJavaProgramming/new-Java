package org.pp.java8.algorithm.charsequence;

import java.util.ArrayList;
import java.util.List;

public class JavaString {

    public static void main(String[] args) {
        /* 字符串匹配 */
        strCmp("abcdefghcde", "cde");

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
}

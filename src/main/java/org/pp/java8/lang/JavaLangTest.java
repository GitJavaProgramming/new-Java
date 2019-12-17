package org.pp.java8.lang;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;

public class JavaLangTest {

    public static void main(String[] args) {
//        testMath();
        testDataTypeConvert();
//        testArray();
    }

    /**
     * 找出有多少对整数
     */
    public static void testArray() {
        int n = 10;
        int[] ar = {1, 1, 3, 1, 2, 1, 3, 3, 3, 3};  // n个数
        HashMap<Integer, Integer> map = new HashMap();
        while (n-- > 0) {
            Integer count = map.get(ar[n]);
            if (count == null) {
                map.put(ar[n], 1);
            } else {
                map.put(ar[n], ++count);
            }
        }
        System.out.println(map);
        int pair = 0;
        for (Integer c : map.values()) {
            pair += c >> 1;
        }
        System.out.println(pair);
    }

    /**
     * 测试数据类型转换，精度丢失问题
     */
    public static void testDataTypeConvert() {
        int a = 10000;
        long b = a; // ?????? 能自动类型转换吗？
        float fc = a; // 这个呢
        int d = (int) fc;
        System.out.println(b);
        System.out.println(fc);
        System.out.println(d);

//        b = Long.MAX_VALUE;
        b = Integer.MAX_VALUE + 1/*(0001)*/;
        a = (int) b;
        System.out.println(a); // 结果：-2147483648

        // 二进制取反码/按位取反  ~(1000) = -1001  ~8=-9
        // 0000 1000 -> 1111 0111 -> 1111 1000(符号位不变) -> 1111 1001(反码+1) 高位为1 为负数
        System.out.println("~8 = " + ~(1000));  // -9
        System.out.println("~(128) = " + ~(128));
        System.out.println("~(129) = " + ~(129));
        b = ~b;
        System.out.println(b);  // 结果： 2147483647
        // 位异或 相同为0 不同为1  符号位不参与？？？
        b = 2 ^ b;
        System.out.println(b);  // 结果： 2147483645
//        System.arraycopy();
//        Arrays.copyOf()

        System.out.println("Java没有无符号类型unsigned");
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Byte.MIN_VALUE);
        System.out.println(Byte.MAX_VALUE);
        System.out.println(Short.MIN_VALUE);
        System.out.println(Short.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Float.MIN_VALUE);
        System.out.println(Float.MAX_VALUE);
        System.out.println(Double.MIN_VALUE);
        System.out.println(Double.MAX_VALUE);
//        System.out.println(Character.MIN_VALUE);
        System.out.println(Character.MAX_VALUE - Character.MIN_VALUE); // 65535  2^16
//        System.out.println(Boolean.MIN_VALUE);
        /**
         * Boolean.hashCode
         * public static int hashCode(boolean value) {
         *         return value ? 1231 : 1237;
         *     }
         */

        System.out.println();
    }

    /**
     * 比较Math.floor、Math.ceil与Math.round方法的区别
     * public static double floor(double a)  去除小数位
     * public static double ceil(double a)  进一制
     * public static long round(double a)   四舍五入
     */
    public static void testMath() {
        int i1 = 2;

        double d1 = 0.3;
        double d2 = d1 + i1;
        // floor 返回一个double值，该double值小于等于参数值，并等于某个整数
        // 去除小数点
        System.out.println("原始值：" + d2);
        System.out.println("Math.floor=" + Math.floor(d2));
        // 返回一个double值，该double值大于等于参数值，并等于某个整数
        // 进一制
        System.out.println("Math.ceil=" + Math.ceil(d2));
        System.out.println("Math.round=" + Math.round(d2));
        System.out.println();

        d1 = 0.8;
        d2 = d1 + i1;
        System.out.println("原始值：" + d2);
        System.out.println("Math.floor=" + Math.floor(d2));
        System.out.println("Math.ceil=" + Math.ceil(d2));
        System.out.println("Math.round=" + Math.round(d2));
        System.out.println();

        d1 = 0.5;
        d2 = d1 + i1;
        System.out.println("原始值：" + d2);
        System.out.println("Math.floor=" + Math.floor(d2));
        System.out.println("Math.ceil=" + Math.ceil(d2));
        // (long)Math.floor(a + 0.5d) 返回最接近参数的long值 四舍五入
        System.out.println("Math.round=" + Math.round(d2));
    }
}

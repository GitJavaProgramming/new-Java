package org.pp.java8.lang;

public class JavaLangTest {

    public static void main(String[] args) {
//        testMath();
//        testDataTypeConvert();
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
//        System.arraycopy();
//        Arrays.copyOf()
    }

    /**
     * 比较Math.floor与Math.round方法的区别
     */
    public static void testMath() {
        int i1 = 2;

        double d1 = 0.3;
        double d2 = d1 + i1;
        // floor 返回一个double值，该double值小于等于参数值，并等于某个整数  去除小数点
        System.out.println("Math.floor=" + Math.floor(d2));
        System.out.println("Math.round=" + Math.round(d2));

        double d3 = 0.8;
        double d4 = d3 + i1;
        System.out.println("Math.floor=" + Math.floor(d4));
        System.out.println("Math.round=" + Math.round(d4));

        double d5 = 0.5;
        double d6 = d5 + i1;
        System.out.println("Math.floor=" + Math.floor(d6));
        // (long)Math.floor(a + 0.5d) 返回最接近参数的long值 四舍五入
        System.out.println("Math.round=" + Math.round(d6));
    }
}

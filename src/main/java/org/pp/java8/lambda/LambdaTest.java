package org.pp.java8.lambda;

public class LambdaTest {

    public static void main(String[] args) {
        // 不含参数测试
        testMyNum();
        // 含参数测试
        testNumericDemo();
        System.out.println("包含多个参数，指定类型时必须全部指定~");
        testNumericDemo2();

        System.out.println("块lambda表达式");
        testNumericDemo3();
        testStringFunc();

        System.out.println("测试泛型函数式接口");
        testGenericLambda();

        System.out.println("作为参数的lambda");
        testArgumentLambda();
    }

    public static void testStringFunc() {
        StringFunc reverse = (str) -> {
            int strLen = str.length();
            StringBuilder strTmp = new StringBuilder("");
            for (int i = strLen - 1; i >= 0; i--) {
                strTmp.append(str.charAt(i));
            }
            return strTmp.toString();
        };
        System.out.println("字符串反转：" + reverse.reverse("Hello"));
    }

    public static void testNumericDemo3() {
        NumericDemo3 factorial = (n) -> {
            int sum = 1;
            int i = 1;
            while (i < n) {
                sum *= ++i;
            }
            return sum;
        };
        System.out.println("求数的阶乘：" + factorial.factorial(5));
    }

    public static void testNumericDemo2() {
        NumericDemo2 isFactor = (n, d) -> (n % d) == 0;
        System.out.println(isFactor.test(100, 21));
        System.out.println(isFactor.test(100, 5));
    }

    public static void testNumericDemo() {
        // 偶数判断
        NumericDemo isEven = (n) -> (n % 2) == 0;
        System.out.println(isEven.test(10));
        System.out.println(isEven.test(9));
        // 正整数判断
        NumericDemo isNonNegative = (n) -> (n >= 0);
        System.out.println(isNonNegative.test(1));
        System.out.println(isNonNegative.test(-1));
    }

    public static void testMyNum() {
        MyNum myNum = () -> 1.2;
        System.out.println(myNum.getValue());
        myNum = () -> Math.random() * 100;
        System.out.println("random1 = " + myNum.getValue());
        System.out.println("random2 = " + myNum.getValue());
//        myNum = () -> "123.0"; // 编译错误
    }

    public static void testGenericLambda() {
        GenericLambda<Integer> genericLambda = (n) -> n * n;
        System.out.println("不在类上指定泛型，只在方法指定泛型时编译出错：" + genericLambda.func(1));
    }

    public static void testArgumentLambda() {
        String inStr = "hello";
        ArgumentLambda lambda = (str) -> {
            if (str.isEmpty()) {
                throw new RuntimeException("字符串为空串...");
            }
            return str.substring(1); // 只有一个表达式时编译器提示可以简化，不需要花括号如： (str) -> str.substring(1);
        };
        String outStr = stringOp(lambda, inStr);
        System.out.println(outStr);
        outStr = stringOp(lambda, "");
        System.out.println(outStr);
    }

    static String stringOp(ArgumentLambda lambda, String str) {
        return lambda.func(str);
    }
}

/**
 * 将lambda作为方法参数传递
 */
interface ArgumentLambda {
    String func(String string) throws RuntimeException;
}

/**
 * 泛型Lambda，只指定泛型方法会编译报错
 */
interface GenericLambda<T> {
    /*<T> */T func(T t);
}

interface StringFunc {
    String reverse(String str);
}

interface NumericDemo3 {
    int factorial(int n);
}

interface NumericDemo2 {
    boolean test(int n, int d);
}

interface NumericDemo {
    boolean test(int n);
}

interface MyNum {
    double getValue();
}

package org.pp.java8.lambda;

public class LambdaTest2 {
    static /*final*/ int field = 10;

    public static void main(String[] args) {
        test1();
        test2();
    }

    /**
     * 测试局部变量与lambda表达式对变量的捕获与限定
     */
    public static void test1() {
        int partVar = 10;

        VarCapture capture = (n -> {
            n += LambdaTest2.field;
            int lambdaLocalVar = partVar;
            n += lambdaLocalVar;
            lambdaLocalVar++;
//            partVar ++; // partVar 隐式final不可变
            LambdaTest2.field++;
            System.out.println("lambdaLocalVar=" + lambdaLocalVar);
            System.out.println("LambdaTest2.field=" + LambdaTest2.field);
            return n;
        });
        capture.func(222);
//        partVar ++; // 在lambda中使用后隐式指定为final，之后不可变；如不在lambda中使用则可变
    }

    /**
     * 测试lambda方法引用
     */
    public static void test2() {
//        String str = stringOp(MyClass::toUpper, "nihao"); // 类方法引用
        MyClass clazz = new MyClass();
        String str = stringOp(clazz::toUpper, "nihao"); // 实例方法引用
        System.out.println(str);
    }

    public static String stringOp(GenericLambda2 lambda2, String str) {
        return lambda2.func(str);
    }


}

/*interface MyFunc<T> {
    boolean func(T t1, T t2);
}*/

class MyClass {
    /*private *//*static*/ String toUpper(String arg) {
        return arg.toUpperCase();
    }

    private String toUpper2(String arg) {
        return arg.toUpperCase();
    }

}

/**
 * 泛型Lambda，只指定泛型方法会编译报错
 */
interface GenericLambda2 {
    //    <T> T func(T t);
    String func(String t);
}

interface VarCapture {
    int func(int n);
}
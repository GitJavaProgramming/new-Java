package org.pp.java8.lambda;

public class LambdaTest3 {
    public static void main(String[] args) {

//        Function ff = Function.getInstance();
//        ff.func(1,1);

        int count = 0;
        HighTemp[] highTemps = {new HighTemp(89),
                new HighTemp(81),
                new HighTemp(82),
                new HighTemp(83),
                new HighTemp(84), // 小于
                new HighTemp(101), // 大于
                new HighTemp(89),
                new HighTemp(89)};
        count = counter(highTemps, HighTemp::sameTemp, new HighTemp(89));
        System.out.println("等于89数：" + count);
        count = counter(highTemps, HighTemp::lessThanTemp, new HighTemp(89));
        System.out.println("小于89数：" + count);

        /**
         * ClassName::instanceMethodName形式引用
         * 函数式接口 模式匹配
         * */
//        count = counter(highTemps, HighTemp::moreThanTemp, new HighTemp(89));
//        System.out.println("大于89数：" + count);

        AnOther anOther = () -> 100;
        count = counter2(highTemps, HighTemp::moreThanTemp, anOther);
        System.out.println("大于89数：" + count);

        Boolean flag = true;
        System.out.println("Outer flag.hashCode()=" + flag.hashCode());
        count = counter3(highTemps, HighTemp::moreThanTemp2, anOther, flag);
        System.out.println("大于89数：" + count);

//        Function3 function3 = MyTemp::comp;
        count = MyTemp.counter3(MyTemp::comp, new MyTemp()/*1*/, anOther, flag); // 这里必须通过对象引用
        System.out.println("大于89数：" + count);
    }

    static <T> int counter(T[] arr, Function<T> f, T v) {
        int count = 0;
        for (T t : arr) {
            if (f.func(t, v)) {
                count++;
            }
        }
        return count;
    }

    static <T, K> int counter2(T[] arr, Function2<T, K> f, K k) {
        int count = 0;
        for (T t : arr) {
            if (f.func(t, k)) {
                count++;
            }
        }
        return count;
    }

    /**
     * HighTemp::moreThanTemp2返回函数式接口（闭包）实例
     * f.func(t, k, v)
     * interface Function3<T, K, V>
     */
    static <T, K, V> int counter3(T[] arr, Function3<T, K, V> f, K k, V v) {
        int count = 0;
        for (T t : arr) {
            if (f.func(t, k, v)) {
                count++;
            }
        }
        return count;
    }
}

//  counter3-----------------------------------------------------
@FunctionalInterface
interface Function3<T, K, V> {
    boolean func(T v1, K v2, V v3);

    default MyTemp getInstance() {
        return new MyTemp();
    }
}

class MyTemp {

    /**
     * HighTemp::moreThanTemp2返回函数式接口（闭包）实例
     * f.func(t, k, v)
     * interface Function3<T, K, V>
     */
    static <T, K, V> int counter3(Function3<T, K, V> f, T t, K k, V v) {
        int count = 0;
        if (f.func(t, k, v)) {
            count++;
        }
        return count;
    }

    <K, V> boolean comp(/*MyTemp tmp, */K anOther, V object) {
        return true;
    }
/*
    boolean comp(HighTemp highTemp, AnOther anOther, Object object) {
        return true;
    }*/
}

//  counter2-----------------------------------------------------
interface AnOther<T> {
    T getValue();
}

interface Function2<T, K> {
    boolean func(T v1, K v2);
}

//  counter-----------------------------------------------------

/**
 * 指定泛型函数式接口
 * 这种方法第一个参数匹配调用对象类型
 * 第二个参数匹配方法参数类型  规约形式？？
 *
 * @param <T> 泛型
 */
interface Function<T> {
    boolean func(T v1, T v2);
//    <T>  boolean func3(T v1, T v2);

    boolean equals(Object obj);

    public static <T> Function<T> getInstance() {
        return new Function<T>() {
            @Override
            public boolean func(T v1, T v2) {
                return false;
            }
        };
    }
}

// ===========================================================
class HighTemp {
    private final int hTemp;

    HighTemp(int hTemp) {
        this.hTemp = hTemp;
    }

    boolean sameTemp(HighTemp highTemp) {
        return hTemp == highTemp.hTemp;
    }

    boolean lessThanTemp(HighTemp highTemp) {
        return hTemp < highTemp.hTemp;
    }

    // counter2 add
    <T> boolean moreThanTemp(T t) {
        if (t instanceof AnOther) {
            int temp = (int) ((AnOther) t).getValue();
            return hTemp > temp;
        }
        return true;
    }

    // counter3 add
    private /*static*/ Boolean flag = false;

    <T, V> boolean moreThanTemp2(T t, V v) {
        System.out.println("this.hashCode()=" + this.hashCode());// 每次hashcode不同说明被同类型所有对象调用
//        System.out.println("v.getClass().getSimpleName()=" + v.getClass().getSimpleName());
        if (!flag) {
            if (v instanceof Boolean) {
                flag = (Boolean) v;
            }
        }
        System.out.println("flag.hashCode()=" + flag.hashCode());// 所有flag hashcode相同，公用外部flag
        if (t instanceof AnOther) {
            int temp = (int) ((AnOther) t).getValue();
            return hTemp > temp;
        }
        return true;
    }
}
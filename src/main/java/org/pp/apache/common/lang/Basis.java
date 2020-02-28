package org.pp.apache.common.lang;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 源码阅读
 * ArrayUtils
 * CLassUtils
 * NumberUtils
 * RandomStringUtils
 * RandomUtils
 * SystemUtils
 * StringUtils
 *
 * org.apache.commons.lang3
 * reflect
 * time
 */
public class Basis {

    @Test
    public void testNumber() {
        System.out.println(Stream.of(1,5,2).max(Integer::compareTo).get());
        System.out.println(NumberUtils.max(1,5,2));
        System.out.println(NumberUtils.isDigits("155"));
    }

    /**
     * isEmpty  // "" or null
     * isNotEmpty
     * isAnyEmpty // 任意一个参数为空的话，返回true
     * isBlank
     * isNotBlank
     * trim
     * truncate
     * strip // 移除左右空白
     * stripAll(String...) // 移除数组中每个元素的空白
     * compare
     * indexOf
     * subString
     * reverse
     * split
     * join(T...)
     * replace
     * repeat
     * leftPad
     */
    @Test
    public void testString() {
        Boolean[] booleans = new Boolean[] {
                StringUtils.isEmpty(null),
                StringUtils.isEmpty(""),
                StringUtils.isEmpty(" "),
                StringUtils.isNotEmpty(""),
                StringUtils.isNotEmpty(" "),

                StringUtils.isBlank(null),
                StringUtils.isBlank(""),
                StringUtils.isBlank(" ")
        };
        Arrays.stream(booleans).forEach(System.out::println);

        String str = "abcdefg  hi jk";
        System.out.println(StringUtils.truncate(str, 3)); // abc
        System.out.println(StringUtils.reverse(str)); // kj ih  gfedcba
        System.out.println(StringUtils.strip(str)); // abcdefg  hi jk
        System.out.println(StringUtils.remove(str, ' ')); // abcdefghijk
        System.out.println(StringUtils.deleteWhitespace(str)); // abcdefghijk
        System.out.println(StringUtils.removeStart(str, "abcde")); // fg  hi jk
        System.out.println(StringUtils.leftPad(str, 20)); // 长度不够30的左边补空格

    }

    @Test
    public void testRandom() {
        System.out.println(RandomStringUtils.random(10)); // Character.MAX_CODE_POINT
        System.out.println(RandomStringUtils.randomAscii(10));
        System.out.println(RandomStringUtils.randomAlphabetic(1, 10)); // 字母
        System.out.println(RandomStringUtils.randomAlphanumeric(1, 10)); // 字母数字
        System.out.println(RandomStringUtils.randomGraph(1, 10)); // POSIX Graph字符集  非空格(nonspace)字符
        System.out.println(RandomStringUtils.randomPrint(1, 10)); // POSIX Print字符集  可显示的字符
        System.out.println(RandomUtils.nextInt(10, 20));
    }

    @Test
    public void testSystem() {
        String[] properties = new String[] {
                SystemUtils.FILE_ENCODING,
                SystemUtils.JAVA_CLASS_PATH,
                SystemUtils.JAVA_CLASS_VERSION,
                SystemUtils.JAVA_COMPILER,
                SystemUtils.JAVA_HOME
        };
        Arrays.stream(properties).forEach(System.out::println);
    }

    @Test
    public void testClass() {
        System.out.println(ClassUtils.getPackageName(this.getClass())); // 包名
        System.out.println(ClassUtils.getCanonicalName(this.getClass())); // 全限定名
        System.out.println(ClassUtils.getPackageCanonicalName(this.getClass())); // 正规包名
        System.out.println(ClassUtils.getAllSuperclasses(Integer.class)); // 所有父类
        System.out.println(ClassUtils.getAllInterfaces(Integer.class)); // 所有父接口
        System.out.println("************************************************************");
        // 打印向上继承体系 Interfaces枚举指定是否包含实现的接口
        Iterable<Class<?>> iterable = ClassUtils.hierarchy(Integer.class, ClassUtils.Interfaces.INCLUDE);
        iterable.forEach(c -> {
            System.out.println(ClassUtils.getCanonicalName(c)); // 全限定名
        });
    }

    static final int[] arr = new int[]{
            60, 1000, 200, 40, 120, 110, 520, 30, 0, 50, 100, 70, 90, 20
    };

    /**
     * 具体实现请阅读源码
     * toString(Object)
     * toMap(Object[])
     * toArray(T...)
     * clone(T[])
     * nullToEmpty(T[]) // 空引用转换为empty数组
     * subArray(T[],int,int) // [) 左闭右开
     * isSameLength(Object[], Object[])
     * getLength(Object) // null object length=0
     * isSameType(Object, Object) // 不考虑不同类加载器的情况 判断类型的全限定名是否相等
     * reverse(Object[]) // 数组反转, 重载方法可以反转指定子数组
     * swap(Object, int, int)
     * shift(Object, int) // 旋转元素位置，指定（基准）偏移量
     * indexOf(Object[], Object)
     * lastIndexOf(Object[], Object)
     * contains(Object[], object)
     * toPrimitive(Object[])
     * isEmpty(Object[]) // empty or null
     * isNotEmpty(Object[]) // not empty and not null
     * addAll(T[], T...) // 浅拷贝还是深拷贝
     * add(T[], T)
     * remove(T[], int)  // 移除某个索引上的值，返回新数组，旧数组不变
     * removeElements(T[], T)
     * isSorted(T[])
     * removeAllOccurences(T[], T)  // 移除所有
     * insert(int, T[], T...)
     * shuffle(Object[])
     * isArrayIndexValid(T[], int)
     *
     * 上面所有
     */
    @Test
    public void testArray() {
        Integer[] array = ArrayUtils.toObject(arr);
        ArrayUtils.shift(array, 1);
        System.out.println(Arrays.toString(array));
//        System.out.println(ArrayUtils.toString(array));

        Integer[] newArray = ArrayUtils.remove(array, 5);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(newArray));

        System.out.println(ArrayUtils.isSorted(arr));
    }
}

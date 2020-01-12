package org.pp.java8.lang;

import org.pp.java8.datastruct.linear.GenericArrayList;

import java.nio.charset.Charset;
import java.util.*;

public class JavaLangTest {

    public static void main(String[] args) {
//        testMath();
//        testDataTypeConvert();
//        testArray();
//        testCollection();
//        testFinals();
//        testGenericArrayList();
//        testCharset();
//        testBinaryNumber();
//        testHashCode();

        testModifyRef();
    }

    private static void testModifyRef() {
        // 集合引用修改
        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = listA;
        listA.add(1);
        System.out.println(listA); // [1]
        System.out.println(listB); // [1]
        // 数组引用 固定长度 编译期确定类型 无法改动
        int[] arrA = {1,2,3};
        int[] arrB = arrA;
        // String
        String strA = "a";
        String strB = strA;
        strA += "b"; // 常量 + 编译期确定 参考Java语言规范 基于Java8 ch4.3.3、ch15.18.1
        System.out.println(strA);
        System.out.println(strB);
    }

    /**
     * 被问到用Long、String哪个作为HashMap的key好时 用这个方法说服他
     * HashMap通过hash算法函数算出hash值来定位key所在位置，所以这个hash算法应该尽可能优，因为算法得出相同的hash值
     * 这个叫hash碰撞，此时会进行key值的比较和节点插入操作（至少HashMap实现是这样）
     * HashMap中的key比较，会比较key的hashcode、equals方法，现在去查阅String、Long这两个方法
     * String.hashCode
     * public int hashCode() {
     * int h = hash;
     * if (h == 0 && value.length > 0) {
     * char val[] = value;
     *
     * 以31为权，每一位为字符的ASCII值进行运算，用自然溢出来等效取模。 ASCII码见 http://blog.csdn.net/lucky_bo/article/details/52247939
     * 哈希计算公式可以计为s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * 用31做基础 ，主要是因为31是一个奇质数，所以31*i=32*i-i=(i<<5)-i，这种位移与减法结合的计算相比一般的运算快很多。
     * for (int i = 0; i < value.length; i++) {
     *      h = 31 * h + val[i]; // 逐个按照字符的utf-16的编码值求和
     * }
     * hash = h;
     * }
     * return h;
     * }
     *
     * Long.hashCode 最终调用这个方法
     * public static int hashCode(long value) {
     *  return (int)(value ^ (value >>> 32)); // 位异或（相同为0，不同为1） 无符号右移
     * }
     * Long 64位，右移32位之后高位补0 变成二进制64位：32/0 + long的高32位（移位后的数的低32位）
     * 可以看到hashCode返回值为int，所以是高32位与低32异或（都舍弃高32位）
     * 由于 long 的hash 值是将 64 位运算得到32位，即讲一个大范围映射到小范围，因此必然会有 hash冲突。
     *
     * >>：带符号右移。正数右移高位补0，负数右移高位补1。比如：
     * 4 >> 1，结果是2；-4 >> 1，结果是-2。-2 >> 1，结果是-1。
     * >>>：无符号右移。无论是正数还是负数，高位通通补0。
     * 对于正数而言，>>和>>>没区别。
     * 对于负数而言，-2 >>> 1，结果是2147483647（Integer.MAX_VALUE），-1 >>> 1，结果是2147483647（Integer.MAX_VALUE）。
     * 以下代码可以判断两个数的符号是否相等
     * return ((a >> 31) ^ (b >> 31)) == 0;
     * <p>
     *
     * 参考：
     * https://www.iteye.com/blog/jackyrong-1979686
     * https://www.cnblogs.com/zyzcj/p/8117695.html
     * https://blog.csdn.net/w605283073/article/details/103001627
     *
     * 由结果可知都会产生hash冲突，就看应用场景怎样
     */
    public static void testHashCode() {
        Long l1 = 1234L;
        Long l2 = Integer.MAX_VALUE + 10L;
        System.out.println(l1.hashCode()); // 1234
        System.out.println(l2.hashCode()); // -2147483639
        Long newValue = Long.MAX_VALUE & l2; // // 高位取反，低位 0 填充
        System.out.println("newValue = " + newValue); // newValue = 2147483657
        System.out.println(newValue.hashCode()); // -2147483639

        System.out.println("C9".hashCode()); // 2134
        System.out.println("Aw".hashCode()); // 2134
    }

    /**
     * 测试进制转换  自定义实现？？
     * <p>
     * 找出二进制表示中最大连续出现1的串的长度
     * <p>
     * 将最大串打印出来  动态规划
     */
    public static void testBinaryNumber() {
        int n = 27;
        String s = Integer.toBinaryString(n); // 将整数转换成二进制字符串
        /**
         * 找出二进制表示中最大连续出现1的串的长度
         */
        System.out.println(s);
        byte[] bytes = s.getBytes();
//        System.out.println(Arrays.toString(s.getBytes()));
        int count = 1;
        int max = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 49) {
                if (i == bytes.length - 1) {
                    if ((n & 1) == 1) { // test 101（5） 1011（11） 1101（13） 11011（27）
                        if (max <= count) {
                            max = count++;
                        }
                    }
                    break;
                }
                if (bytes[i + 1] == 49) {
                    count++;
                } else {
                    if (max < count) {
                        max = count;
                    }
                    count = 1;
                }
            }
        }
        System.out.println(max);
    }

    /**
     * 测试字符集、编码，解码
     * 自行查阅字符集编码表、编码规则
     * 如 Unicode字符集和UTF-8编码规则
     * <p>
     * unicode编码表对照
     * https://unicode-table.com/cn/#currency-symbols
     */
    public static void testCharset() {
        // 编码，默认字符集
        String str = "ni号";
        System.out.println(str.length()); // 字符串长度为3  3个字符

        System.out.println("默认字符集：" + Charset.defaultCharset().name());

        byte[] bytes = str.getBytes(); // 编码  默认字符集 utf-8
        // 字节数组长度为5 UTF-8规则中文占3个字节
        System.out.println("str.getBytes(defaultCharset)=" + Arrays.toString(bytes));

        Charset gbkCharset = Charset.forName("GBK");
        System.out.println("str.getBytes(GBK)=" + Arrays.toString(str.getBytes(gbkCharset)));

        str = new String(bytes, Charset.forName("GBK")); // 解码 使用GBK
        System.out.println("GBK decode: " + str); // 和预期的一样 乱码啦！

//        char c = 65536; // 编译错误
        char c = 65535;
        System.out.println("最大的Unicode字符：" + new String(new char[]{c})); // 结果：￿ 文件utf-8编码，可以看到是这个字符，试试其他字符集编码文件！
    }

    /**
     * 测试编写的泛型数组
     */
    public static void testGenericArrayList() {
        GenericArrayList<Integer> arrayList = new GenericArrayList(10);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(5);
        arrayList.add(4);
        arrayList.add(3);
        arrayList.add(4);
        System.out.println(arrayList.size());
        arrayList.remove(4);
        System.out.println(arrayList);
    }

    /**
     * 测试final、finally、finalize
     */
    public static void testFinals() {
        final Map<String, String> finalMap = new HashMap<>();
        finalMap.put("11", "22");
        System.out.println(finalMap);
//        finalMap = new HashMap<>();  // 编译出错！
    }

    /**
     * 测试集合操作 Iterator迭代器 remove方法等
     */
    public static void testCollection() {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(1);
        list.add(6);
        list.add(1);
        /**
         * public boolean remove(Object o)
         * 引用下面方法删除 删除元素时，置空，向前移动后续元素(本质是数组拷贝！！！)
         * private void fastRemove(int index) {
         *         modCount++;
         *         int numMoved = size - index - 1;
         *         if (numMoved > 0)
         *             System.arraycopy(elementData, index+1, elementData, index,
         *                              numMoved);
         *         elementData[--size] = null; // clear to let GC do its work
         *     }
         */
//        System.out.println("list.remove=" + list.remove((Object)1)); // object 找到的第一个元素
//        System.out.println("list.remove=" + list.remove((Object)1)); // object 找到的第一个元素
//        System.out.println("list.remove=" + list.remove(1)); // index
//        boolean b = list.removeIf((l) -> l.compareTo(1) == 0);
//        System.out.println(b);
//        System.out.println(list);

        /*
        // 新的元素删除方法
        for(Integer integer : list) { // get()方法中的操作抛出Exception in thread "main" java.util.ConcurrentModificationException
            boolean b = list.removeIf((l) -> l.compareTo(1) == 0);
            System.out.println(b);
        }
        */
        // 迭代器删除，在遍历时可用
        /**  ArrayList<E> extends AbstractList<E> implements ....
         * AbstractList提供迭代器实现，remove方法实现如下
         * public void remove() {
         *             if (lastRet < 0)
         *                 throw new IllegalStateException();
         *             checkForComodification();  // 集合是否已经变动了
         *
         *             try {
         *                 AbstractList.this.remove(lastRet);
         *                 if (lastRet < cursor)
         *                     cursor--;
         *                 lastRet = -1;
         *                 expectedModCount = modCount; // modCount所有修改操作会modCount++;比如remove clear等操作
         *             } catch (IndexOutOfBoundsException e) { // 检查异常，并发操作时，由于可见性可能存在 lastRet < 0 情况？？？
         *                 throw new ConcurrentModificationException();
         *             }
         *         }
         */
//        list = new ArrayList<>();
        Iterator<Integer> iter = list.iterator();
//        iter.remove();
        while (iter.hasNext()) {
            // remove之前应该调用next()操作判断是否存在可删除元素
//            iter.remove();  // 直接调用 Exception in thread "main" java.lang.IllegalStateException

            /**
             * public E next() {
             *             checkForComodification();
             *             try {
             *                 int i = cursor;
             *                 E next = get(i);
             *                 lastRet = i; // 更新要操作的元素索引
             *                 cursor = i + 1;
             *                 return next;
             *             } catch (IndexOutOfBoundsException e) {
             *                 checkForComodification();
             *                 throw new NoSuchElementException();
             *             }
             *         }
             */
            Integer integer = iter.next();
            if (1 == integer) {
                iter.remove(); // 本质：AbstractList.this.remove(lastRet/*索引*/);
                System.out.println("iter.remove() list=" + list);

                // 下面的操作可行，有了break就不有下次遍历
                list.remove((Object) 1);
                System.out.println("list.remove=" + list);
                break;
            }
        }
        System.out.println(list);
    }

    /**
     * 找出有多少对整数
     * 数组操作
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
     * 整型、浮点型、字符、位运算、JDK1.7之后字面值
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
        b = Integer.MAX_VALUE/*(0001)*/;  // 自动类型转换
        System.out.println("long类型 b0 = " + b);  //  结果：2147483647
        b = Integer.MAX_VALUE + 1/*(0001)*/;  // 相加结果为整数（位运算试试！），int到long 自动类型转换
        System.out.println("long类型 b1 = " + b);  //  结果：-2147483648
        a = (int) b;
        System.out.println("long强制转换为int高位丢失 a1 = " + a); // 结果：-2147483648

        b = Integer.MAX_VALUE + 1L/*(0001)*/; // 相加结果为双精度整数
        System.out.println("long类型 b2 = " + b);  //  结果：2147483648
        a = (int) b;
        System.out.println("long强制转换为int高位丢失 a2 = " + a); // 结果：-2147483648

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

        System.out.println("参考本目录下--Java数据类.png、类型转换.png");

        int tt = 0B1000;
        System.out.println(~tt);

        System.out.println("输出所有字节：");
        for (int i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
            System.out.print((char) i);
        }
        System.out.println();
        List<Character> characterList = new ArrayList<>();
        characterList.add('\u2660');
        characterList.add('\u2665');
        characterList.add('\u2663');
        characterList.add('\u2666');
        System.out.println(characterList); // [♠, ♥, ♣, ♦]
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

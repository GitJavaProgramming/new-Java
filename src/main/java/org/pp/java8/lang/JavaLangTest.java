package org.pp.java8.lang;

import java.util.*;

public class JavaLangTest {

    public static void main(String[] args) {
//        testMath();
//        testDataTypeConvert();
//        testArray();
//        testCollection();
//        testFinals();
        testGenericArrayList();
    }

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

    public static void testFinals() {
        final Map<String, String> finalMap = new HashMap<>();
        finalMap.put("11","22");
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
